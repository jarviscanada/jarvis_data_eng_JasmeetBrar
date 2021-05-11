package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIDColumnName() {
        return ID_COLUMN;
    }

    @Override
    public Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    @Override
    public int updateOne(SecurityOrder entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void deleteByAccountId(Integer accountId) {
        String sql = "DELETE FROM " + getTableName() + " WHERE account_id = ?";
        getJdbcTemplate().update(sql, accountId);
    }
}
