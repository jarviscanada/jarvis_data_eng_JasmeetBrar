package ca.jrvs.practice.sorting;

public class QuickSort {

    public void quickSort(int[] arr, int begin, int end) {
        if(begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, 0, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }

    }

    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = begin - 1;

        for(int j = begin; j < end; j++) {
            if(arr[j] <= pivot) {
                i++;
                swap(arr, i , j);
            }
        }

        swap(arr, i+1, end);

        return i+1;
    }

    private void swap(int[] arr, int firstIndex, int secondIndex) {
        int element = arr[firstIndex];
        arr[firstIndex] = arr[secondIndex];
        arr[secondIndex] = element;
    }
}
