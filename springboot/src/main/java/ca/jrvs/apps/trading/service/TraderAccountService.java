package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TraderAccountService {

    private final TraderDao traderDao;
    private final AccountDao accountDao;
    private final PositionDao positionDao;
    private final SecurityOrderDao securityOrderDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao, PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non-empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new TraderAccountView
     *
     * Assumption: to simplify the logic, each trader has only one account with traderId == accountId
     *
     * @param trader cannot be null. All fields cannot be null except for id (auto-generated by db)
     * @return TraderAccountView
     * @throws IllegalArgumentException if a trader has null fields or id is not null
     */
    public TraderAccountView createTraderAndAccount(Trader trader) {
        validateTrader(trader);

        traderDao.save(trader);

        Account account = new Account();
        account.setTraderId(trader.getId());
        account.setAmount(0f);

        accountDao.save(account);

        return new TraderAccountView(trader, account);
    }

    /**
     * Helper method to validate the trader and ensure that none of the fields are null
     */
    private void validateTrader(Trader trader) {
        String[] fields = new String[]{"FirstName", "LastName", "Dob", "Country", "Email"};

        Arrays.stream(fields).forEach(field -> {
            try {
                Method method = Trader.class.getMethod("get" + field);
                if(method.invoke(trader) == null) {
                    throw new IllegalArgumentException("The given trader object has a null value for" + field);
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot get getter method for " + field);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Encountered issues when invoking the getter method for " + field);
            }
        });
    }

    /**
     * A trader can be delete iff it has no open position and 0 cash balance
     * - validate tradeId
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId must not be null
     * @throws IllegalArgumentException if tradeId is null or not found or unable to delete
     */
    public void deleteTraderById(Integer traderId) {
        if(traderId == null) {
            throw new IllegalArgumentException("Trader ID is null");
        }

        Trader trader = traderDao.findById(traderId).orElseThrow(() -> new IllegalArgumentException("Trader does not exist"));
        Account account = accountDao.findByTraderId(traderId).orElseThrow(() -> new IllegalArgumentException("Account does not exist"));

        if(account.getAmount() != 0) {
            throw new IllegalArgumentException("Trader has a non-zero balance");
        }

        Optional<Position> position = positionDao.findById(account.getId());

        if(position.isPresent()) {
            throw new IllegalArgumentException("Trader has open positions");
        }

        securityOrderDao.deleteByAccountId(account.getId());
        accountDao.deleteById(account.getId());
        traderDao.deleteById(trader.getId());
    }

    /**
     * Deposit a fund to an account by traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId must not be null
     * @param fund must be greater than 0
     * @return updated Account
     * @throws IllegalArgumentException if traderId is null or not found, and fund is less than or equal to 0
     */
    public Account deposit(Integer traderId, Double fund) {
        validateTraderIdAndFund(traderId, fund);
        return addFunds(traderId, fund);
    }

    /**
     * Withdraw a fund to an account by traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     * @param traderId trade ID
     * @param fund amount can't be 0
     * @return updated Account
     * @throws IllegalArgumentException if traderID is null or not found, fund is less than or equal to 0,
     *                                  and insufficient funds
     */
    public Account withdraw(Integer traderId, Double fund) {
        validateTraderIdAndFund(traderId, fund);
        return addFunds(traderId, -fund);
    }

    /**
     * Helper method to validate trader ID and funds
     */
    private void validateTraderIdAndFund(Integer traderId, Double fund) {
        if(traderId == null) {
            throw new IllegalArgumentException("Trader ID is null");
        }

        if(fund == null) {
            throw new IllegalArgumentException("Fund is null");
        }

        if(fund <= 0) {
            throw new IllegalArgumentException("Fund is non-positive");
        }
    }

    /**
     * Helper method to update account funds for the given trader ID
     */
    private Account addFunds(Integer traderId, Double fund) {
        Trader trader = traderDao.findById(traderId).orElseThrow(() -> new IllegalArgumentException("Trader does not exist"));
        Account account = accountDao.findByTraderId(trader.getId()).orElseThrow(() -> new IllegalArgumentException("Account does not exist"));

        if(fund < 0 && account.getAmount() < -fund) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        accountDao.updateAmountById(account.getId(), account.getAmount() + fund);

        account.setAmount((float) (account.getAmount() + fund));
        return account;
    }
}
