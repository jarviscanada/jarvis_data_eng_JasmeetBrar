package ca.jrvs.practice.codingChallenge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyStack2Test {
    private MyStack2 stack;

    @Before
    public void setup() {
        stack = new MyStack2();
    }

    @After
    public void tearDown() {
        stack = null;
    }

    @Test
    public void push() {
        stack.push(1);
        assertEquals(1, (int) stack.top());
        stack.push(2);
        assertEquals(2, (int) stack.top());
    }

    @Test
    public void pop() {
        stack.push(1);
        assertEquals(1, (int) stack.pop());
        stack.push(5);
        stack.push(14);
        stack.push(3);
        assertEquals(3, (int) stack.pop());
        assertEquals(14, (int) stack.pop());

    }

    @Test
    public void top() {
        stack.push(1);
        assertEquals(1, (int) stack.top());
        stack.push(2);
        assertEquals(2, (int) stack.top());
    }

    @Test
    public void empty() {
        assertTrue(stack.empty());
        stack.push(5);
        assertFalse(stack.empty());
    }

}