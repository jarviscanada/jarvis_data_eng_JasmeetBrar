package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoIntTest {

    private MarketDataDao dao;

    @Before
    public void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));

        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void findIexQuotesByTickers() throws IOException {
        // happy path
        List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
        assertEquals(2, quoteList.size());
        assertEquals("AAPL", quoteList.get(0).getSymbol());

        // sad path
        try {
            dao.findAllById(Arrays.asList("AAPL", "FB2"));
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void findByTicker() {
        String ticker = "AAPL";
        IexQuote iexQuote = dao.findById(ticker).get();
        assertEquals(ticker, iexQuote.getSymbol());
    }

    @Test
    public void save() {
        IexQuote quote = new IexQuote();
        try {
            dao.save(quote);
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void saveAll() {
        IexQuote quote = new IexQuote();
        try {
            dao.saveAll(Arrays.asList(quote, quote));
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void existsById() {
        try {
            dao.existsById("1");
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void findAll() {
        try {
            dao.findAll();
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void count() {
        try {
            dao.count();
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteById() {
        try {
            dao.deleteById("1");
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void delete() {
        IexQuote quote = new IexQuote();

        try {
            dao.delete(quote);
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteAll_With_Iterable() {
        IexQuote quote = new IexQuote();

        try {
            dao.deleteAll(Arrays.asList(quote, quote));
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteAll() {
        try {
            dao.deleteAll();
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }
}