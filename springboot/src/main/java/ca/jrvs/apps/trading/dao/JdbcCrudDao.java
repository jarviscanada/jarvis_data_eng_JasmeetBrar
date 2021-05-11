package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    public abstract JdbcTemplate getJdbcTemplate();

    public abstract SimpleJdbcInsert getSimpleJdbcInsert();

    public abstract String getTableName();

    public abstract String getIDColumnName();

    public abstract Class<T> getEntityClass();

    /**
     * Save an entity and update auto-generated integer ID
     * @param entity to be saved
     * @return saved entity
     */
    @Override
    public <S extends T> S save(S entity) {
        if(existsById(entity.getId())) {
            if(updateOne(entity) != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(entity);
        }
        return entity;
    }

    /**
     * helper method that saves one quote
     */
    private <S extends T> void addOne(S entity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        Number id = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(id.intValue());
    }

    /**
     * helper method that updates one quote
     */
    public abstract int updateOne(T entity);

    @Override
    public Optional<T> findById(Integer id) {
        Optional<T> entity = Optional.empty();
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIDColumnName() + " = ?";

        try {
            entity = Optional.ofNullable((T) getJdbcTemplate()
                    .queryForObject(selectSql,
                            BeanPropertyRowMapper.newInstance(getEntityClass()), id));
        } catch(IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader id: " + id, e);
        }
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        SqlParameterSource[] entries = StreamSupport.stream(iterable.spliterator(), false)
                .map(BeanPropertySqlParameterSource::new).toArray(SqlParameterSource[]::new);
        int[] rows = getSimpleJdbcInsert().executeBatch(entries);
        if(rows.length != entries.length) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", rows.length);
        }

        return iterable;
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public List<T> findAll() {
        List<T> objects;
        String selectSql = "SELECT * FROM " + getTableName();
        try {
            objects = getJdbcTemplate().query(selectSql, new BeanPropertyRowMapper<>(getEntityClass()));
            return objects;
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<T> findAllById(Iterable<Integer> ids) {
        List<T> objects;

        String idSqlString = StreamSupport.stream(ids.spliterator(), false).map(Object::toString).collect(Collectors.joining(", "));

        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIDColumnName() + " IN (" + idSqlString + ")";

        try {
            objects = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(getEntityClass()));
            return objects;
        } catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIDColumnName() + " = ?";
        getJdbcTemplate().update(sql, id);
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        Long count = getJdbcTemplate().queryForObject(sql, Long.class);
        if(count == null) {
            throw new IllegalStateException("Encountered an issue when fetching the count");
        }
        return count;
    }

    public void delete(T object) {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIDColumnName() + " IN (SELECT " + getIDColumnName() + " FROM " + getTableName() + ")";
        getJdbcTemplate().execute(sql);
    }
}
