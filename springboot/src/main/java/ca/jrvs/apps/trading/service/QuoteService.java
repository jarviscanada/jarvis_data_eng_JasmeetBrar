package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class QuoteService {

    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private final QuoteDao quoteDao;
    private final MarketDataDao marketDataDao;

    private static final Map<String, String> iexToQuoteMap = new HashMap<>();

    static {
        iexToQuoteMap.put("Symbol", "Ticker");
        iexToQuoteMap.put("LatestPrice", "LastPrice");
        iexToQuoteMap.put("IexBidPrice", "BidPrice");
        iexToQuoteMap.put("IexBidSize", "BidSize");
        iexToQuoteMap.put("IexAskPrice", "AskPrice");
        iexToQuoteMap.put("IexAskSize", "AskSize");
    }

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Update quote table against IEX source
     * - get all quotes from the db
     * - foreach ticker get iexQuote
     * - convert iexQuote to quote entity
     * - persist quote to db
     *
     * @throws IllegalStateException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        findAllQuotes().stream()
                .map(Quote::getTicker)
                .map(marketDataDao::findById)
                .map(optionalQuote -> optionalQuote.orElseThrow(
                        () -> new IllegalStateException("One or more tickets were not found from IEX")
                ))
                .map(QuoteService::buildQuoteFromIexQuote)
                .forEach(quoteDao::save);
    }

    /**
     * Helper method. Map a IEXQuote to a Quote entity.
     * Note: `iexQuote.getLatestPrice() == null` if the stock market is closed.
     * Default values are set for fields that are missing.
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();

        iexToQuoteMap.keySet().forEach(iexField -> {
            Method iexFieldGetter;
            Method quoteFieldSetter;
            Class<?>[] parameterTypes;

            try {
                iexFieldGetter = IexQuote.class.getMethod("get" + iexField);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot find IexQuote getter for " + iexField, e);
            }

            try {
                quoteFieldSetter = Arrays.stream(Quote.class.getMethods())
                        .filter(method -> method.getName().equals("set" + iexToQuoteMap.get(iexField)))
                        .findFirst()
                        .orElseThrow(NoSuchMethodException::new);

                parameterTypes = quoteFieldSetter.getParameterTypes();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot find Quote setter for " + iexToQuoteMap.get(iexField), e);
            }

            Object value;

            try {
                value = iexFieldGetter.invoke(iexQuote);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot invoke IexQuote getter method for: " + iexField, e);
            }

            Class<?> parameterType = parameterTypes[0];

            try {
                quoteFieldSetter.invoke(quote, parameterType.cast(value));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Cannot invoke Quote setter method for: " + iexToQuoteMap.get(iexField), e);
            }

        });

        return quote;

    }

    /**
     * Validate (against IEX) and save given tickers to the quote table.
     * - Get iexQuote(s)
     * - convert each iexQuote to Quote entity
     * - persist the quote to db
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        return tickers.stream().map(this::saveQuote).collect(Collectors.toList());
    }

    /**
     * Helper method
     */
    public Quote saveQuote(String ticker) {
        Optional<IexQuote> optionalIexQuote = marketDataDao.findById(ticker);
        IexQuote iexQuote = optionalIexQuote.orElseThrow(() ->
                new RuntimeException(ticker + " does not exist in IEX")
        );

        Quote quote = buildQuoteFromIexQuote(iexQuote);
        return quoteDao.save(quote);
    }

    /**
     * Find an IexQuote
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
    }

    /**
     * Update a given quote to quote table without validation
     * @param quote entity
     * @return Quote that was saved
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table.
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return StreamSupport.stream(quoteDao.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
