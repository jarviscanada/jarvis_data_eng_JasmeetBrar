package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    @Autowired
    private TraderAccountService traderAccountService;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AccountDao accountDao;

    private TraderAccountView savedView;
    private Trader trader;

    @Before
    public void setUp() {
        trader = new Trader();
        trader.setFirstName("Bob");
        trader.setLastName("Bill");
        trader.setDob(new GregorianCalendar(1995, Calendar.APRIL, 4).getTime());
        trader.setCountry("USA");
        trader.setEmail("bob@example.com");
    }

    @After
    public void tearDown() {
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Test
    public void createTraderAndAccount() {
        savedView = traderAccountService.createTraderAndAccount(trader);

        assertEquals("Bob", savedView.getTrader().getFirstName());
        assertEquals(savedView.getTrader().getId(), savedView.getAccount().getTraderId());
        assertEquals(0f, savedView.getAccount().getAmount(), 0);
    }

    @Test
    public void deleteTraderById() {
        savedView = traderAccountService.createTraderAndAccount(trader);
        traderAccountService.deleteTraderById(trader.getId());
        assertFalse(traderDao.existsById(trader.getId()));
        assertFalse(accountDao.existsById(savedView.getAccount().getId()));
    }

    @Test
    public void deposit() {
        savedView = traderAccountService.createTraderAndAccount(trader);
        Account account = traderAccountService.deposit(trader.getId(), 500d);
        assertEquals(500d, (double) account.getAmount(), 0.001);
    }

    @Test
    public void withdraw() {
        savedView = traderAccountService.createTraderAndAccount(trader);
        traderAccountService.deposit(trader.getId(), 500d);
        Account account = traderAccountService.withdraw(trader.getId(), 250.56);
        assertEquals((double) 500 - 250.56, (double) account.getAmount(), 0.001);
    }

}