package ca.jrvs.practice.codingChallenge;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompareMapsTest {

    private static CompareMaps compareMaps;

    private static Map<String, Integer> m1;
    private static Map<String, Integer> m2;
    private static Map<String, Integer> m3;

    @BeforeClass
    public static void beforeClass() throws Exception {
        compareMaps = new CompareMaps();

        m1 = new HashMap<String, Integer>(){{
            put("Test", 1);
            put("Test2", 7);
        }};

        m2 = new HashMap<>();

        m3 = new HashMap<String, Integer>(){{
            put("Test", 1);
        }};
    }

    @Test
    public void compareMaps1() {
        assertTrue(compareMaps.compareMaps1(new HashMap<String, String>(), new HashMap<>()));

        assertTrue(compareMaps.compareMaps1(m1, m1));

        assertFalse(compareMaps.compareMaps1(m1, m2));

        assertFalse(compareMaps.compareMaps1(m1, m3));

    }
}