package ca.jrvs.practice.codingChallenge;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyQueue1Test {

    private MyQueue1 queue;

    @Before
    public void setUp() throws Exception {
        queue = new MyQueue1();
    }

    @After
    public void tearDown() throws Exception {
        queue = null;
    }

    @Test
    public void push() {
        queue.push(1);
        assertEquals(1, queue.peek());
        queue.push(5);
        assertEquals(1, queue.pop());
        assertEquals(5, queue.pop());
    }

    @Test
    public void pop() {
        queue.push(5);
        assertEquals(5, queue.pop());
        queue.push(53);
        queue.push(23);
        queue.push(167);
        assertEquals(53, queue.pop());
        assertEquals(23, queue.pop());
    }

    @Test
    public void peek() {
        queue.push(6);
        assertEquals(6, queue.peek());
        queue.push(356);
        assertEquals(6, queue.peek());
    }

    @Test
    public void empty() {
        assertTrue(queue.empty());
        queue.push(115);
        assertFalse(queue.empty());
    }
}