package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.assertj.core.util.Lists;
import org.junit.*;
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
public class SecurityOrderDaoIntTest {

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;

    @Before
    public void insertOne() {
        Trader trader = new Trader();
        trader.setId(1);
        trader.setFirstName("Bob");
        trader.setLastName("Bill");
        trader.setCountry("Canada");
        trader.setDob(new GregorianCalendar(2000, Calendar.JANUARY, 2).getTime());
        trader.setEmail("bob@example.com");
        traderDao.save(trader);

        Account account = new Account();
        account.setId(1);
        account.setTraderId(1);
        account.setAmount(500f);
        accountDao.save(account);

        Quote quote = new Quote();
        quote.setId("AAPL");
        quote.setAskPrice(125d);
        quote.setAskSize(100L);
        quote.setBidPrice(58.75d);
        quote.setBidSize(75L);
        quote.setLastPrice(65d);
        quoteDao.save(quote);

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setId(1);
        securityOrder.setAccountId(1);
        securityOrder.setStatus("Okay");
        securityOrder.setTicker("AAPL");
        securityOrder.setPrice(50f);
        securityOrder.setSize(100);
        securityOrder.setNotes("");
        securityOrderDao.save(securityOrder);
    }

    @After
    public void deleteOne() {
        securityOrderDao.deleteById(1);
        accountDao.deleteAll();
        traderDao.deleteAll();
        quoteDao.deleteAll();
    }

    @Test
    public void findAllById() {
        List<SecurityOrder> securityOrders = Lists.newArrayList(securityOrderDao.findAllById(Collections.singletonList(1)));
        assertEquals(1, securityOrders.size());
        assertEquals(50f, (float) securityOrders.get(0).getPrice(), 0);
    }
}