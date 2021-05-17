package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql("classpath:schema.sql")
public class TraderDaoIntTest {

    @Autowired
    private TraderDao traderDao;

    private Trader savedTrader;

    @Before
    public void insertOne() {
        savedTrader = new Trader();
        savedTrader.setFirstName("Bob");
        savedTrader.setLastName("Bill");
        savedTrader.setCountry("Canada");
        savedTrader.setDob(new GregorianCalendar(2000, Calendar.JANUARY, 2).getTime());
        savedTrader.setEmail("bob@example.com");
        traderDao.save(savedTrader);
    }

    @After
    public void deleteOne() {
        traderDao.deleteById(1);
    }

    @Test
    public void findAllByID() {
        List<Trader> traders = Lists.newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId(), -1)));
        assertEquals(1, traders.size());
        assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
    }

    @Test
    public void deleteAll() {
        traderDao.deleteAll();
        assertEquals(0, traderDao.findAll().size());
        insertOne();
    }

    @Test
    public void count() {
        assertEquals(1, traderDao.count());
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(1));
    }

    @Test
    public void saveAll() {
        traderDao.deleteById(1);

        Trader savedTrader2;
        savedTrader2 = new Trader();
        savedTrader2.setFirstName("Anne");
        savedTrader2.setLastName("School");
        savedTrader2.setCountry("Canada");
        savedTrader2.setDob(new GregorianCalendar(2000, Calendar.MARCH, 5).getTime());
        savedTrader2.setEmail("anne@example.com");

        traderDao.saveAll(Arrays.asList(savedTrader, savedTrader2));

        assertTrue(traderDao.existsById(savedTrader.getId()));
        assertTrue(traderDao.existsById(savedTrader.getId()));

        traderDao.deleteById(2);
    }
}