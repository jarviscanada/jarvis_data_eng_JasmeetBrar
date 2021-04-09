package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class OddEvenTest {

    @Test
    public void oddEvenMod() {
        OddEven oddEven = new OddEven();

        assertEquals(oddEven.oddEvenMod(1), "odd");
        assertEquals(oddEven.oddEvenMod(2), "even");
        assertEquals(oddEven.oddEvenMod(0), "even");
        assertEquals(oddEven.oddEvenMod(-1), "odd");
        assertEquals(oddEven.oddEvenMod(25), "odd");
    }
}