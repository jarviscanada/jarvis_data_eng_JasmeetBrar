package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote = new Quote();
    private Quote savedQuote2 = new Quote();
    private Quote savedQuote3 = new Quote();

    @Before
    public void insertOne() {
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10L);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10L);
        savedQuote.setId("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        savedQuote2.setAskPrice(12d);
        savedQuote2.setAskSize(11L);
        savedQuote2.setBidPrice(9d);
        savedQuote2.setBidSize(45L);
        savedQuote2.setId("GOOGL");
        savedQuote2.setLastPrice(10d);

        savedQuote3.setAskPrice(12d);
        savedQuote3.setAskSize(11L);
        savedQuote3.setBidPrice(9d);
        savedQuote3.setBidSize(50L);
        savedQuote3.setId("MSFT");
        savedQuote3.setLastPrice(5d);
    }

    @After
    public void deleteOne() {
        quoteDao.deleteById(savedQuote.getId());
    }

    @Test
    public void daoTest() {
        assertEquals(1, quoteDao.count());
        assertTrue(quoteDao.existsById(savedQuote.getId()));

        Collection<Quote> allQuotes = (Collection<Quote>) quoteDao.findAll();
        assertEquals(allQuotes.size(), 1);

        savedQuote.setBidSize(100L);
        quoteDao.save(savedQuote);
        Optional<Quote> optionalResult = quoteDao.findById("AAPL");
        Quote result = optionalResult.orElse(null);
        assertNotNull(result);
        assertEquals(100, (long) result.getBidSize());

        ArrayList<Quote> newQuotes = new ArrayList<>();
        newQuotes.add(savedQuote2);
        newQuotes.add(savedQuote3);

        quoteDao.saveAll(newQuotes);

        assertEquals(3, quoteDao.count());

        quoteDao.deleteAll();

        assertEquals(0, quoteDao.count());

        quoteDao.save(savedQuote);
    }
}
