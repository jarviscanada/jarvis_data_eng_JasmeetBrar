package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class LinkedListAPIs {

    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();

        // add elements
        list.add("Hello");
        list.add("World!");
        list.add(2, "Bob");

        // size not length
        int s = list.size();

        // get
        String firstElement = list.getFirst();
        String lastElement = list.getLast();
        String secondElement = list.get(1);

        // search O(n)
        Boolean hasBob = list.stream().anyMatch(element -> element.equals("Bob"));

        // index
        int index = list.indexOf("Bob");

        // remove
        list.remove("Hello");
        list.remove(0);

        // convert list to array
        System.out.println(Arrays.toString(list.toArray()));

    }
}
