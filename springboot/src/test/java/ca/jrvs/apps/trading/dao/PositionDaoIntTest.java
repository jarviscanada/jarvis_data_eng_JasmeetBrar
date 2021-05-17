package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Position;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql("classpath:schema.sql")
public class PositionDaoIntTest {

    @Autowired
    private PositionDao dao;

    private static Position position;

    @BeforeClass
    public static void beforeClassSetup() {
        position = new Position();
        position.setId(1);
        position.setTicker("GOOGL");
        position.setPosition(5);
    }

    @Test
    public void getEntityClass() {
        assertEquals(Position.class, dao.getEntityClass());
    }

    @Test
    public void updateOne() {
        try {
            dao.updateOne(position);
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void save() {
        try {
            dao.save(position);
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void saveAll() {
        try {
            dao.saveAll(Arrays.asList(position, position));
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteById() {
        try {
            dao.deleteById(1);
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void delete() {
        try {
            dao.delete(position);
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteAll_With_Iterable() {
        try {
            dao.deleteAll(Arrays.asList(position, position));
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deleteAll() {
        try {
            dao.deleteAll();
            fail();
        } catch(UnsupportedOperationException e) {
            assertTrue(true);
        }
    }
}