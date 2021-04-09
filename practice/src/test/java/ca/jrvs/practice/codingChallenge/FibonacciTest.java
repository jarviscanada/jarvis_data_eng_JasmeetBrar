package ca.jrvs.practice.codingChallenge;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest {

    private Fibonacci fib;

    @Before
    public void setUp() throws Exception {
        fib = new Fibonacci();
    }

    @Test
    public void fib1() {
        assertEquals(fib.fib1(0), 0);
        assertEquals(fib.fib1(1), 1);
        assertEquals(fib.fib1(2), 1);
        assertEquals(fib.fib1(5), 5);
        assertEquals(fib.fib1(10), 55);
    }
}