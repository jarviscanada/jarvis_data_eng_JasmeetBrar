package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteDao quoteDao;

    @Before
    public void setup() {
        quoteDao.deleteAll();
    }

    @Test
    public void findIexQuoteByTicker() {
        IexQuote iexQuote = quoteService.findIexQuoteByTicker("AAPL");
        assertNotNull(iexQuote);
        assertEquals("AAPL", iexQuote.getSymbol());
    }

    @Test
    public void updateMarketData() {
        Quote apple = new Quote();
        Quote google = new Quote();

        apple.setTicker("AAPL");
        apple.setLastPrice(-10d);
        apple.setBidPrice(-10d);
        apple.setBidSize(-15L);
        apple.setAskPrice(-5d);
        apple.setAskSize(-125L);

        google.setTicker("GOOGL");
        google.setLastPrice(-10d);
        google.setBidPrice(-10d);
        google.setBidSize(-15L);
        google.setAskPrice(-5d);
        google.setAskSize(-150L);

        quoteService.saveQuote(apple);
        quoteService.saveQuote(google);

        quoteService.updateMarketData();

        List<Quote> quotes = quoteService.findAllQuotes();

        assertEquals(2, quotes.size());

        int quotesChecked = 0;

        for(Quote quote: quotes) {
            if(quote.getId().equals("AAPL")) {
                assertNotEquals(-125, (long) quote.getAskSize());
                quotesChecked++;
            }

            if(quote.getId().equals("GOOGL")) {
                assertNotEquals(-150, (long) quote.getAskSize());
                quotesChecked++;
            }
        }

        assertEquals(2, quotesChecked);
    }

    @Test
    public void saveQuotes() {
        List<String> tickers = Arrays.asList("AAPL", "GOOGL");
        List<Quote> quotes = quoteService.saveQuotes(tickers);

        assertEquals(2, quotes.size());

        if(quotes.get(0).getId().equals("AAPL")) {
            assertEquals("GOOGL", quotes.get(1).getId());
        }
        else if(quotes.get(0).getId().equals("GOOGL")) {
            assertEquals("AAPL", quotes.get(1).getId());
        }
        else {
            fail();
        }
    }

    @Test
    public void saveQuote() {
        Quote quote = quoteService.saveQuote("MSFT");
        assertEquals("MSFT", quote.getId());
    }

    @Test
    public void findAllQuotes() {
        List<Quote> quotes = quoteService.findAllQuotes();
        assertEquals(0, quotes.size());
        Quote quote = quoteService.saveQuote("MSFT");
        quotes = quoteService.findAllQuotes();
        assertEquals(1, quotes.size());
        assertEquals("MSFT", quotes.get(0).getId());
    }
}
