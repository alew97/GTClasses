import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Can't have a null comparator.");
        }
        
        boolean swapped = true;
        for (int i = 0; i < arr.length && swapped; i++) {
            swapped = false;
            for (int j = (arr.length - 1); j > i; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    swapped = true;
                }
            }
        }
    }
    
    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Can't have a null comparator.");
        }
        
        for (int i = 1; i < arr.length; i++) {
            boolean swapped = true;
            for (int k = i; k > 0 && swapped; k--) {
                if (comparator.compare(arr[k], arr[k - 1]) < 0) {
                    T temp = arr[k];
                    arr[k] = arr[k - 1];
                    arr[k - 1] = temp;
                } else {
                    swapped = false;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * Note that there may be duplicates in the array, but they may not
     * necessarily stay in the same relative order.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Can't have a null comparator.");
        }
        
        for (int i = 0; i < (arr.length - 1); i++) {
            int min = i;
            for (int j = (i + 1); j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            
            T temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     * 
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Can't have a null comparator.");
        } else if (rand == null) {
            throw new IllegalArgumentException("Random object can't be null.");
        }
        
        recursiveQuickSort(arr, comparator, rand, 0, (arr.length - 1));
    }
    
    /**
     * Recursive helper method to operate a quick-sort algorithm.
     * 
     * @param <T> data type to sort
     * @param array the array to edit in-place
     * @param comparator the Comparator object to compare the items by
     * @param rand the Random object to generate a random pivot index
     * @param low the lower bound of the partition
     * @param high the upper bound of the partition
     */
    public static <T> void recursiveQuickSort(T[] array,
            Comparator<T> comparator, Random rand, int low, int high) {
        // Finding pivot position and item
        int pivotPos = rand.nextInt(high - low) + low;
        T pivot = array[pivotPos];

        // Swapping pivot to front of partition
        array[pivotPos] = array[low];
        array[low] = pivot;
        
        // Setting the i and j pointers
        int i = low + 1;
        int j = high;
        
        // Sorting partition
        while (i <= j) {
            // Finds an item greater than the pivot
            while ((i <= j) && comparator.compare(array[i], pivot) <= 0) {
                i++;
            }
            // Finds an item less than the pivot
            while ((i <= j) && comparator.compare(array[j], pivot) >= 0) {
                j--;
            }

            // If i and j haven't crossed, swap them
            if (i < j) {
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        
        // Setting pivot into correct position
        array[low] = array[j];
        array[j] = pivot;
        
        // Partitioning left and right sides
        if (low < (j - 1)) {
            recursiveQuickSort(array, comparator, rand, low, (j - 1));
        }
        if ((j + 1) < high) {
            recursiveQuickSort(array, comparator, rand, (j + 1), high);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("Can't have a null comparator.");
        }
        
        arr = recursiveMergeSort(arr, comparator);
    }
    
    /**
     * Recursive helper method to operate a merge-sort algorithm.
     * 
     * @param <T> data type to sort
     * @param array the array to edit in-place
     * @param comparator the Comparator object to compare the items by
     * @return returns the merged array
     */
    public static <T> T[] recursiveMergeSort(T[] array,
            Comparator<T> comparator) {
        // Checking that the partition contains at least 2 items
        if (array.length > 1) {
            // Finding middle index
            int middleIndex = (array.length) / 2;
            
            // Partitioning left side
            T[] arrayLeft = (T[]) new Object[middleIndex];
            for (int i = 0; i < middleIndex; i++) {
                arrayLeft[i] = array[i];
            }
            arrayLeft = recursiveMergeSort(arrayLeft, comparator);
            
            // Partitioning right side
            T[] arrayRight = (T[]) new Object[array.length - middleIndex];
            for (int i = middleIndex, k = 0; i < array.length; i++, k++) {
                arrayRight[k] = array[i];
            }
            arrayRight = recursiveMergeSort(arrayRight, comparator);
            
            // The "merging" section of the merge-sort algorithm
            int total = arrayLeft.length + arrayRight.length;
            int leftArrayPtr = 0;
            int rightArrayPtr = 0;
            
            for (int i = 0; i < total; i++) {
                if (rightArrayPtr >= arrayRight.length) {
                    array[i] = arrayLeft[leftArrayPtr];
                    leftArrayPtr++;
                } else if (leftArrayPtr >= arrayLeft.length) {
                    array[i] = arrayRight[rightArrayPtr];
                    rightArrayPtr++;
                } else if (comparator.compare(arrayLeft[leftArrayPtr],
                        arrayRight[rightArrayPtr]) <= 0) {
                    array[i] = arrayLeft[leftArrayPtr];
                    leftArrayPtr++;
                } else {
                    array[i] = arrayRight[rightArrayPtr];
                    rightArrayPtr++;
                }
            }
            
            return array;
        } else {
            return array;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        }
        
        // Finding the largest number by significant digits
        int max = 0;
        int largestDigit = 0;
        for (int i = 0; i < arr.length; i++) {
            int absValue = Math.abs(arr[i]);
            if (absValue > max) {
                max = absValue;
                int count = 0;
                while (absValue != 0) {
                    count++;
                    absValue = absValue / 10;
                }
                if (count > largestDigit) {
                    largestDigit = count;
                }
            }
        }
        
        // Initializing the digit buckets
        ArrayList<Integer>[] digitBuckets = new ArrayList[10];
        for (int i = 0; i < digitBuckets.length; i++) {
            digitBuckets[i] = new ArrayList<Integer>();
        }

        // Sorting part of LSD-radix sort
        for (int i = 0; i < largestDigit; i++) {
            // Sorting item into buckets
            for (int j = 0; j < arr.length; j++) {
                // Using the modulus operator to get next significant digit
                int absValue = Math.abs(arr[j]);
                int checkNum = 0;
                int digitFactor = pow(10, i);
                if (i > 0) {
                    checkNum = (absValue / digitFactor);
                } else {
                    checkNum = absValue;
                }
                int lsd = checkNum % 10;
                digitBuckets[lsd].add(arr[j]);
            }
            
            // Removing item back into array, in ascending order of LSD
            int index = 0;
            for (int j = 0; j < digitBuckets.length; j++) {
                while (digitBuckets[j].size() != 0) {
                    arr[index] = digitBuckets[j].remove(0);
                    index++;
                }
            }
        }
        
        // Arranging positive/negative items into appropriate sign bucket
        ArrayList<Integer> positiveBucket = new ArrayList<Integer>();
        ArrayList<Integer> negativeBucket = new ArrayList<Integer>();
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                positiveBucket.add(arr[i]);
            } else {
                negativeBucket.add(arr[i]);
            }
        }
        
        // Returns the items in correct order from negative to positive
        for (int i = 0; i < arr.length; i++) {
            while (!negativeBucket.isEmpty()) {
                arr[i] = negativeBucket.remove(negativeBucket.size() - 1);
                i++;
            }
            while (!positiveBucket.isEmpty()) {
                arr[i] = positiveBucket.remove(0);
                i++;
            }
        }
        return arr;
    }
    
    /**
     * Implement MSD (most significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should:
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Can't have a null array.");
        }
        
        // Finding the largest number by significant digits
        int max = 0;
        int largestDigit = 0;
        for (int i = 0; i < arr.length; i++) {
            int absValue = Math.abs(arr[i]);
            if (absValue > max) {
                max = absValue;
                int count = 0;
                while (absValue != 0) {
                    count++;
                    absValue = absValue / 10;
                }
                if (count > largestDigit) {
                    largestDigit = count;
                }
            }
        }
        
        // Initializing the digit buckets
        ArrayList<Integer>[] digitBuckets = new ArrayList[10];
        for (int i = 0; i < digitBuckets.length; i++) {
            digitBuckets[i] = new ArrayList<Integer>();
        }
        
        // Calling the merge-sorting recursive method to arrange by MSD
        recursiveMSDRadixSort(arr, (largestDigit - 1), 0, arr.length);
        
        // Arranging positive/negative items into appropriate sign bucket
        ArrayList<Integer> positiveBucket = new ArrayList<Integer>();
        ArrayList<Integer> negativeBucket = new ArrayList<Integer>();
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                positiveBucket.add(arr[i]);
            } else {
                negativeBucket.add(arr[i]);
            }
        }
        
        // Returns the items in correct order from negative to positive
        for (int i = 0; i < arr.length; i++) {
            while (!negativeBucket.isEmpty()) {
                arr[i] = negativeBucket.remove(negativeBucket.size() - 1);
                i++;
            }
            while (!positiveBucket.isEmpty()) {
                arr[i] = positiveBucket.remove(0);
                i++;
            }
        }
        return arr;
    }
    
    /**
     * Recursive helper method to do MSD-radix sort on a specific range.
     * Mostly used to help sort within certain digit buckets.
     * 
     * @param <T> data type to sort
     * @param arr the array to sort
     * @param largestDigit the largest digit place to consider
     * @param startIndex the starting index of the array to look at
     * @param endIndex the ending index of the array to look at
     */
    private static <T> void recursiveMSDRadixSort(int[] arr, int largestDigit,
            int startIndex, int endIndex) {
        // Initializing the digit buckets
        ArrayList<Integer>[] digitBuckets = new ArrayList[10];
        for (int i = 0; i < digitBuckets.length; i++) {
            digitBuckets[i] = new ArrayList<Integer>();
        }
        
        // Sorting item into buckets
        for (int j = startIndex; j < endIndex; j++) {
            // Using the modulus operator to get next significant digit
            int absValue = Math.abs(arr[j]);
            int checkNum = 0;
            int digitFactor = pow(10, largestDigit);
            if (largestDigit > 0) {
                checkNum = (absValue / digitFactor);
            } else {
                checkNum = absValue;
            }
            int lsd = checkNum % 10;
            digitBuckets[lsd].add(arr[j]);
        }
        
        // Removing item back into array, in ascending order of MSD
        int index = startIndex;
        for (int j = 0; j < digitBuckets.length; j++) {          
            int bucketSize = digitBuckets[j].size();
            int start = index;
            while (digitBuckets[j].size() != 0) {
                arr[index] = digitBuckets[j].remove(0);
                index++;
            }
            int end = index;
            if (largestDigit > 0 && bucketSize > 1) {
                recursiveMSDRadixSort(arr, largestDigit - 1, start, end);
            }
        }
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     * 
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
