package ca.jrvs.practice.dataStructure.list;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayJListTest {

    ArrayJList<String> arr;

    @Before
    public void setUp() throws Exception {
        arr = new ArrayJList<>();
    }

    @Test
    public void add() {
        arr.add("Hello");
        assertEquals(arr.size(), 1);
        arr.add("World!");
        assertEquals(arr.size(), 2);
        assertEquals(arr.elementData.length, 10);
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        arr.add("Test");
        assertEquals(arr.elementData.length, 20);
    }

    @Test
    public void toArray() {
        Object[] objArray = new Object[]{"Hello", "World!"};
        arr.elementData = objArray;
        assertSame(arr.toArray(), objArray);
    }

    @Test
    public void size() {
        assertEquals(arr.size(), 0);
        arr.add("Hi");
        assertEquals(arr.size(), 1);
    }

    @Test
    public void isEmpty() {
        assertTrue(arr.isEmpty());
        arr.add("Hi");
        assertFalse(arr.isEmpty());
    }

    @Test
    public void indexOf() {
        arr.add("Hi");
        arr.add("Bob");
        assertEquals(arr.indexOf("Hi"), 0);
        assertEquals(arr.indexOf("Bob"), 1);
        assertEquals(arr.indexOf("Bill"), -1);
    }

    @Test
    public void contains() {
        assertFalse(arr.contains("Bill"));
        arr.add("Bill");
        assertTrue(arr.contains("Bill"));
    }

    @Test
    public void get() {
        try {
            arr.get(0);
            fail();
        } catch(IndexOutOfBoundsException e) {
            assertTrue(true);
        }

        arr.add("Bob");
        assertEquals(arr.get(0), "Bob");
    }

    @Test
    public void remove() {
        arr.add("Hi");
        arr.add("Hello");
        arr.add("Bob");
        arr.remove(1);
        Object[] myStrings = arr.elementData;
        assertEquals(myStrings[0], "Hi");
        assertEquals(myStrings[1], "Bob");
    }

    @Test
    public void clear() {
        arr.add("Hi");
        arr.clear();
        assertEquals(arr.size(), 0);
    }
}