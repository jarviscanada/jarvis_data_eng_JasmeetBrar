package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private final String IEX_BATCH_URL;

    private final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    /**
     * Get an IexQuote (helper method which class findAllById)
     * @param ticker ticker String
     * @return IexQuote if ticker is valid
     * @throws IllegalArgumentException if a given ticker is invalid
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if(quotes.size() == 0) {
            return Optional.empty();
        } else if(quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }

        return iexQuote;
    }

    /**
     * Get quotes frm IEX
     * @param tickers is a list of tickers
     * @return a list of IexQuote object
     * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers) {
        if(Iterables.size(tickers) == 0) {
            throw new IllegalArgumentException("Tickers is empty");
        }

        String tickerString = String.join(",", tickers);
        String uri = String.format(IEX_BATCH_URL, tickerString);

        String response = executeHttpGet(uri).orElseThrow(() -> new IllegalArgumentException("Invalid ticker"));

        JSONObject IexQuotesJson = new JSONObject(response);

        if(IexQuotesJson.length() == 0) {
            throw new IllegalArgumentException("Invalid ticker");
        }

        ObjectMapper mapper = new ObjectMapper();

        return StreamSupport.stream(tickers.spliterator(), false).map(ticker -> {
            try {
                JSONObject jsonObject = IexQuotesJson.getJSONObject(ticker);
                IexQuote quote = mapper.readValue(jsonObject.get("quote").toString(), IexQuote.class);

                if(quote == null) {
                    throw new IllegalArgumentException("Invalid ticker");
                }

                return quote;
            } catch (JSONException | IOException e) {
                throw new IllegalArgumentException("Invalid ticker");
            }
        }).collect(Collectors.toList());
    }

    /**
     * Execute a get and return http entity/body as a string
     *
     * Tip: use EntityUtils.toString to process HTTP entity
     *
     * @param url resource URL
     * @return http response body or Optional.empty for 404 response
     * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
     */
    private Optional<String> executeHttpGet(String url) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpUriRequest request = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(request);
            String entity = EntityUtils.toString(response.getEntity());
            return Optional.of(entity);
        } catch(IOException e) {
            throw new DataRetrievalFailureException("HTTP request failed/encounted bad status code", e);
        }

    }

    /**
     * Borrow an HTTP client from the httpClientConnectionManager
     * @return an httpClient
     */
    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                // prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
