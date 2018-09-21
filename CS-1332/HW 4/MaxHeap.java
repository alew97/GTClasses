/**
 * Your implementation of a max heap.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial size of {@code STARTING_SIZE}.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        } else if (size == backingArray.length - 1) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int index = 1; index < backingArray.length; index++) {
                newArray[index] = backingArray[index];
            }
            backingArray = newArray;
        }
        size++;
        backingArray[size] = item;
        //Heapify-ing
        int index = size;
        while (index > 1 && (backingArray[index].compareTo(
                backingArray[index / 2]) > 0)) {
            T temp = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = temp;
            index = index / 2;
        }
    }

    @Override
    public T remove() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("The heap is empty.");
        }
        T returnData = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        //Heapify-ing
        int index = 1;
        while (index <= size + 1) {
            if (index * 2 >= backingArray.length) {
                return returnData;
            } else if (backingArray[index * 2] == null) {
                index = index * 2;
            } else if (backingArray[index * 2 + 1] != null) {
                if (backingArray[index * 2].compareTo(
                        backingArray[index * 2 + 1]) >= 0) {
                    if (backingArray[index].compareTo(
                            backingArray[index * 2]) < 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[index * 2];
                        backingArray[index * 2] = temp;
                    } else {
                        return returnData;
                    }
                    index = index * 2;
                } else {
                    if (backingArray[index].compareTo(
                            backingArray[index * 2 + 1]) < 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[index * 2 + 1];
                        backingArray[index * 2 + 1] = temp;
                    } else {
                        return returnData;
                    }
                    index = index * 2 + 1;
                }
            } else if (backingArray[index * 2 + 1] == null) {
                if (backingArray[index].compareTo(
                        backingArray[index * 2]) < 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[index * 2];
                    backingArray[index * 2] = temp;
                }
                index = index * 2;
            }
        }
        return returnData;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

}
