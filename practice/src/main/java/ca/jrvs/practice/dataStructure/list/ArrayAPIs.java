package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.List;

public class ArrayAPIs {

    public static void main(String[] args) {
        // create an array of integers
        int[] intArray = new int[10];
        intArray[0] = 100;
        intArray[1] = 200;
        intArray[2] = 300;

        // shortcut syntax to create and initialize an array
        int[] inlineArray = {
                100, 200, 300
        };

        // 2D array
        String[][] names = {
                {"Mr. ", "Mrs. ", "Ms. "},
                {"Smith", "Jones"}
        };

        // copy array
        char[] copyFrom = {'d', 'e', 'c', 'a', 'f', 'f', 'e', 'i', 'n', 'a', 't', 'e', 'd'};
        char[] copyTo = new char[7];

        System.arraycopy(copyFrom, 2, copyTo, 0, 7);
        System.out.println(new String(copyTo));

        // convert an array to a List
        List<String> fruits = Arrays.asList("apple", "orange");
        fruits = Arrays.asList(new String[]{"apple", "orange"});

        //copy
        String[] fruitArray = new String[]{"mango", "banana"};
        String[] anotherFruitArray = Arrays.copyOfRange(fruitArray, 0, 1);
        System.out.printf(anotherFruitArray.toString());

        // sort
        Arrays.sort(fruitArray);
        System.out.println(Arrays.toString(fruitArray));

        // binarySearch
        int exact = Arrays.binarySearch(fruitArray, "banana");
        System.out.println(exact);
    }
}
