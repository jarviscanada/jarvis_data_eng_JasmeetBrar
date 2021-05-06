package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public <S extends Quote> S save(S quote) {
        if(existsById(quote.getId())) {
            int updatedRowNo = updateOne(quote);
            if(updatedRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * helper method that saves one quote
     */
    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if(row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    /**
     * helper method that updates one quote
     */
    private int updateOne(Quote quote) {
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, " +
                "ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    /**
     * helper method that makes sql update values objects
     * @param quote to be updated
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Quote quote) {
        String[] fields = new String[]{"LastPrice", "BidPrice", "BidSize", "AskPrice", "AskSize", "Id"};
        Object[] values = new Object[fields.length];

        for(int i = 0; i < fields.length; i++) {
            try {
                Method getter = Quote.class.getMethod("get" + fields[i]);
                values[i] = getter.invoke(quote);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("Quote object does not contain a getter method for: " + fields[i], e);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new IllegalStateException("Encountered an issue when accessing the getter method for " + fields[i], e);
            }
        }

        return values;
    }

    @Override
    public <S extends Quote> Iterable<S> saveAll(Iterable<S> iterable) {
        SqlParameterSource[] entries = StreamSupport.stream(iterable.spliterator(), false)
                .map(BeanPropertySqlParameterSource::new).toArray(SqlParameterSource[]::new);
        int[] rows = simpleJdbcInsert.executeBatch(entries);
        if(rows.length != entries.length) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", rows.length);
        }

        return iterable;
    }

    @Override
    public Optional<Quote> findById(String id) {
        Quote quote = null;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        try {
            quote = jdbcTemplate.queryForObject(selectSql, BeanPropertyRowMapper.newInstance(Quote.class), id);
            return quote == null ? Optional.empty() : Optional.of(quote);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> quotes;
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        try {
            quotes = jdbcTemplate.query(selectSql, new BeanPropertyRowMapper<>(Quote.class));
            return quotes;
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        if(count == null) {
            throw new IllegalStateException("Encountered an issue when fetching the count");
        }
        return count;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " IN (SELECT " + ID_COLUMN_NAME + " FROM " + TABLE_NAME + ")";
        jdbcTemplate.execute(sql);
    }
}
