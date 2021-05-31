package ca.jrvs.practice.search;

import java.util.Arrays;
import java.util.Optional;

public class BinarySearch {

    /**
     * find the the target index in a sorted array
     *
     * @param arr input array is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not found
     */
    public <E extends Comparable<E>> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
        if(arr == null || arr.length == 0) {
            return Optional.empty();
        }

        int middleIndex = arr.length / 2;

        switch(arr[middleIndex].compareTo(target)) {
            case 1:
                if(middleIndex == 0) return Optional.empty();
                return binarySearchIteration(Arrays.copyOfRange(arr, 0, middleIndex), target);
            case 0:
                return Optional.of(middleIndex);
            case -1:
                Optional<Integer> result = binarySearchIteration(Arrays.copyOfRange(arr, middleIndex + 1, arr.length), target);
                return result.map(integer -> middleIndex + integer + 1);

        }

        return Optional.empty();
    }

    /**
     * find the the target index in a sorted array
     *
     * @param arr input array is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not found
     */
    public <E extends Comparable<E>> Optional<Integer> binarySearchIteration(E[] arr, E target) {
        if(arr == null || arr.length == 0) {
            return Optional.empty();
        }

        int beginning = 0;
        int ending = arr.length;

        while(beginning <= ending) {
            int middle = beginning + ((ending - beginning) / 2);

            switch(arr[middle].compareTo(target)) {
                case 1:
                    ending = middle - 1;
                    break;
                case 0:
                    return Optional.of(middle);
                case -1:
                    beginning = middle + 1;
                    break;
            }
        }

        return Optional.empty();
    }
}