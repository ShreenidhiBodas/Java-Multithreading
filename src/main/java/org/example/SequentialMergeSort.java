package org.example;

public class SequentialMergeSort {
    private int[] nums;
    private int[] temp;

    public SequentialMergeSort(int[] nums) {
        this.nums = nums;
        temp = new int[nums.length];
    }

    public void sort() {
        mergeSort(0, nums.length - 1);
    }

    public void print() {
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }

    private void swap(int i, int j) {
        int temp = nums[i];
        nums[i]  = nums[j];
        nums[j] = temp;
    }

    private void mergeSort(int low, int high) {
        if (low >= high) {
            return;
        }

        int middle = low + (high - low) / 2;

        mergeSort(low, middle);
        mergeSort(middle + 1, high);

        // combine
        merge(low, middle, high);
    }

    private void merge(int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            temp[i] = nums[i];
        }

        int i = low;
        int j =  middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (temp[i] < temp[j]) {
                nums[k] = temp[i];
                i++;
            } else {
                nums[k] = temp[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            nums[k] = temp[i];
            k++;
            i++;
        }

        while (j <= high) {
            nums[k] = temp[j];
            k++;
            j++;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{100, 2, 4, 3, -9, 1, 0, 0, -89, 11};
        SequentialMergeSort sequentialMergeSort = new SequentialMergeSort(nums);
        sequentialMergeSort.sort();
        sequentialMergeSort.print();
    }
}
