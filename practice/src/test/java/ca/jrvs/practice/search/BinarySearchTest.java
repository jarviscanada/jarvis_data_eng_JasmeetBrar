package ca.jrvs.practice.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinarySearchTest {

    BinarySearch binarySearch = new BinarySearch();
    Integer[] arr = new Integer[]{1,3,7,12,24,33,40,56,75,100,123,256};

    @Test
    public void binarySearchRecursion() {
        assertEquals((Integer) 3, binarySearch.binarySearchRecursion(arr, 12).get());
        assertEquals((Integer) 11, binarySearch.binarySearchRecursion(arr, 256).get());
        assertFalse(binarySearch.binarySearchRecursion(arr, -1).isPresent());
    }

    @Test
    public void binarySearchIteration() {
        assertEquals((Integer) 3, binarySearch.binarySearchIteration(arr, 12).get());
        assertEquals((Integer) 11, binarySearch.binarySearchIteration(arr, 256).get());
        assertFalse(binarySearch.binarySearchIteration(arr, -1).isPresent());
    }
}