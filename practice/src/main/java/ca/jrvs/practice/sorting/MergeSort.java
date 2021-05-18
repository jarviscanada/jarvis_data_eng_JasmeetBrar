package ca.jrvs.practice.sorting;

import java.util.Arrays;

public class MergeSort {

    public void mergeSort(int[] arr) {
        if(arr.length < 2) {
            return;
        }

        int middle = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, middle);
        int[] right = Arrays.copyOfRange(arr, middle, arr.length);

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    private void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while(i < left.length && j < right.length) {
            if(left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while(i < left.length) {
            arr[k++] = left[i++];
        }

        while(j < right.length) {
            arr[k++] = right[j++];
        }
    }
}
