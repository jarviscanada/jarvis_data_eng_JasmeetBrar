package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.Optional;

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
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
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
}
