package ca.jrvs.practice.dataStructure.list;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedJListTest {

    LinkedJList<String> list;

    @Before
    public void setUp() throws Exception {
        list = new LinkedJList<>();
    }

    @Test
    public void add() {
        list.add("Hello");
        assertEquals(list.size(), 1);
        list.add("World!");
        assertEquals(list.size(), 2);
    }

    @Test
    public void toArray() {
        Object[] objArray = new Object[]{"Hello", "World!"};
        list.add("Hello");
        list.add("World!");
        Object[] result = list.toArray();
        assertEquals(result[0], objArray[0]);
        assertEquals(result[1], objArray[1]);
    }

    @Test
    public void size() {
        assertEquals(list.size(), 0);
        list.add("Hi");
        assertEquals(list.size(), 1);
    }

    @Test
    public void isEmpty() {
        assertTrue(list.isEmpty());
        list.add("Hi");
        assertFalse(list.isEmpty());
    }

    @Test
    public void indexOf() {
        list.add("Hi");
        list.add("Bob");
        assertEquals(list.indexOf("Hi"), 0);
        assertEquals(list.indexOf("Bob"), 1);
        assertEquals(list.indexOf("Bill"), -1);
    }

    @Test
    public void contains() {
        assertFalse(list.contains("Bill"));
        list.add("Bill");
        assertTrue(list.contains("Bill"));
    }

    @Test
    public void get() {
        try {
            list.get(0);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assertTrue(true);
        }

        list.add("Bob");
        assertEquals(list.get(0), "Bob");
    }

    @Test
    public void remove() {
        list.add("Hi");
        list.add("Hello");
        list.add("Bob");
        list.remove(1);
        assertEquals(list.get(0), "Hi");
        assertEquals(list.get(1), "Bob");
    }

    @Test
    public void clear() {
        list.add("Hi");
        list.clear();
        assertEquals(list.size(), 0);
    }
}