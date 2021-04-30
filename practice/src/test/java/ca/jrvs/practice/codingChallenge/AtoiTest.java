package ca.jrvs.practice.codingChallenge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtoiTest {

    private Atoi atoi;

    private final String num1 = "123";
    private final String num2 = "-17465";
    private final String num3 = "0";
    private final String num4 = "1764a45";

    @Before
    public void setUp() throws Exception {
        atoi = new Atoi();
    }

    @Test
    public void atoi1() {
        assertEquals(atoi.atoi1(num1), 123);
        assertEquals(atoi.atoi1(num2), -17465);
        assertEquals(atoi.atoi1(num3), 0);

        try {
            atoi.atoi1(num4);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void atoi2() {
        assertEquals(atoi.atoi2(num1), 123);
        assertEquals(atoi.atoi2(num2), -17465);
        assertEquals(atoi.atoi2(num3), 0);

        try {
            atoi.atoi2(num4);
            fail();
        } catch(RuntimeException e) {
            assertTrue(true);
        }
    }
}