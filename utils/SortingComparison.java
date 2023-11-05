package utils;

import java.util.Arrays;
import java.util.Random;

// import mergesort.MergeSort;
import peeksort.PeekSort;
import radixsort.RadixSort;

public class SortingComparison {
    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void test() {
        test(1000, "Small");
        System.out.println();
        test(10000, "Medium");
        System.out.println();
        test(100000, "Large");
    }

    private static void test(int size, String type) {
        int[] random = generateRandomDataset(size, size);
        int[] copy_original_random = Arrays.copyOf(random, random.length);

        int[] sorted = generateSortedDataset(copy_original_random);
        int[] reversed = generateReversedDataset(sorted, sorted.length);

        int[] copy_sorted = Arrays.copyOf(sorted, sorted.length);
        int[] copy_random = Arrays.copyOf(random, random.length);
        int[] copy_reversed = Arrays.copyOf(reversed, reversed.length);

        // int[] copy_sorted2 = Arrays.copyOf(sorted, sorted.length);
        // int[] copy_random2 = Arrays.copyOf(random, random.length);
        // int[] copy_reversed2 = Arrays.copyOf(reversed, reversed.length);

        testPeeksort(sorted, type, "Sorted");
        testPeeksort(random, type, "Random");
        testPeeksort(reversed, type, "Reversed");
        System.out.println();
        testRadixSort(copy_sorted, type, "Sorted");
        testRadixSort(copy_random, type, "Random");
        testRadixSort(copy_reversed, type, "Reversed");
        // System.out.println();
        // testMergeSort(copy_sorted2, type, "Sorted");
        // testMergeSort(copy_random2, type, "Random");
        // testMergeSort(copy_reversed2, type, "Reversed");
    }

    private static void testPeeksort(int[] dataset, String type, String datasetName) {
        long peeksortStartTime = System.currentTimeMillis();
        long peeksortStartMemory = getUsedMemory();
        PeekSort.sort(dataset, 0, dataset.length-1);
        long peeksortEndTime = System.currentTimeMillis();
        long peeksortEndMemory = getUsedMemory();
        // System.out.println(Arrays.toString(dataset));
        System.out.printf("Peeksort - %s - %s:\n", type, datasetName);
        System.out.println("Waktu eksekusi: " + (peeksortEndTime - peeksortStartTime) + " ms");
        System.out.println("Penggunaan memori: " + (peeksortEndMemory - peeksortStartMemory) + " bytes");
    }

    private static void testRadixSort(int[] dataset, String type, String datasetName) {
        long radixStartTime = System.currentTimeMillis();
        long radixStartMemory = getUsedMemory();
        RadixSort.sort(dataset, dataset.length);
        long radixEndTime = System.currentTimeMillis();
        long radixEndMemory = getUsedMemory();

        System.out.printf("Radix Sort - %s - %s:\n", type, datasetName);
        System.out.println("Waktu eksekusi: " + (radixEndTime - radixStartTime) + " ms");
        System.out.println("Penggunaan memori: " + (radixEndMemory - radixStartMemory) + " bytes");
    }

    // private static void testMergeSort(int[] dataset, String type, String datasetName) {
    //     long mergeStartTime = System.currentTimeMillis();
    //     long mergeStartMemory = getUsedMemory();
    //     MergeSort.sort(dataset, 0, dataset.length-1);
    //     long mergeEndTime = System.currentTimeMillis();
    //     long mergeEndMemory = getUsedMemory();

    //     System.out.printf("Merge Sort - %s - %s:\n", type, datasetName);
    //     System.out.println("Waktu eksekusi: " + (mergeEndTime - mergeStartTime) + " ms");
    //     System.out.println("Penggunaan memori: " + (mergeEndMemory - mergeStartMemory) + " bytes");
    // }

    private static int[] generateSortedDataset(int[] arr) {
        Arrays.sort(arr);
        return arr;
    }

    private static int[] generateReversedDataset(int a[], int n) {
        int[] b = new int[n]; 
        int j = n; 
        for (int i = 0; i < n; i++) { 
            b[j - 1] = a[i]; 
            j = j - 1; 
        }
        
        return b;
    }

    private static int[] generateRandomDataset(int u, int size) {
        int[] dataset = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            dataset[i] = random.nextInt(u) + 1;
        }
        return dataset;
    }
}
