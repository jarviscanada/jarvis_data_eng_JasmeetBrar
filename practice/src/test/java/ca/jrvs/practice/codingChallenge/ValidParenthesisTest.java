package ca.jrvs.practice.codingChallenge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesisTest {

    private ValidParenthesis vp;

    @Before
    public void setUp() throws Exception {
        vp = new ValidParenthesis();
    }

    @After
    public void tearDown() throws Exception {
        vp = null;
    }

    @Test
    public void isValid() {
        assertTrue(vp.isValid(""));
        assertTrue(vp.isValid("[]"));
        assertTrue(vp.isValid("()"));
        assertTrue(vp.isValid("{}"));
        assertTrue(vp.isValid("[]()"));
        assertTrue(vp.isValid("[{}]"));
        assertTrue(vp.isValid("([])"));
        assertTrue(vp.isValid("((([[[{{}}]]])))"));

        assertFalse(vp.isValid("("));
        assertFalse(vp.isValid("[(])"));
        assertFalse(vp.isValid("[]{"));
        assertFalse(vp.isValid("]"));
        assertFalse(vp.isValid("((([[[{{}}]]))"));
    }
}