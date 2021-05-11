package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
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
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    private Account account;
    private Trader trader;

    @Before
    public void insertOne() {
        trader = new Trader();
        trader.setId(1);
        trader.setFirstName("Bob");
        trader.setLastName("Bill");
        trader.setCountry("Canada");
        trader.setDob(new GregorianCalendar(2000, Calendar.JANUARY, 2).getTime());
        trader.setEmail("bob@example.com");
        traderDao.save(trader);

        account = new Account();
        account.setId(1);
        account.setTraderId(1);
        account.setAmount(500f);
        accountDao.save(account);
    }

    @After
    public void deleteOne() {
        accountDao.deleteById(1);
        traderDao.deleteById(1);
    }

    @Test
    public void findAllById() {
        List<Account> accounts = accountDao.findAllById(Collections.singletonList(1));
        assertEquals(1, accounts.size());
        assertEquals(1, (int) accounts.get(0).getTraderId());
    }

    @Test
    public void deleteAll() {
        accountDao.deleteAll();
        traderDao.deleteAll();
        assertEquals(0, accountDao.findAll().size());
        insertOne();
    }

    @Test
    public void count() {
        assertEquals(1, accountDao.count());
    }

    @Test
    public void existsById() {
        assertTrue(accountDao.existsById(1));
    }

    @Test
    public void saveAll() {
        accountDao.deleteById(1);

        Trader savedTrader2;
        savedTrader2 = new Trader();
        savedTrader2.setId(2);
        savedTrader2.setFirstName("Anne");
        savedTrader2.setLastName("School");
        savedTrader2.setCountry("Canada");
        savedTrader2.setDob(new GregorianCalendar(2000, Calendar.MARCH, 5).getTime());
        savedTrader2.setEmail("anne@example.com");
        traderDao.save(savedTrader2);

        Account account2 = new Account();
        account2.setId(2);
        account2.setTraderId(2);
        account2.setAmount(1000f);

        accountDao.saveAll(Arrays.asList(account, account2));

        assertTrue(accountDao.existsById(2));
        assertTrue(accountDao.existsById(3));

        accountDao.deleteById(2);
        accountDao.deleteById(3);
        traderDao.deleteById(2);
    }

}