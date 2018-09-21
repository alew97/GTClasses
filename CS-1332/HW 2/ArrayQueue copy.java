/**
 * Your implementation of a Queue backed by an array.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add instance variables.
    private T[] backingArray;
    private int size;
    private int front;
    private int back;

    /**
     * Construct a Queue with an initial capacity of {@code INITIAL_CAPACITY}.
     *
     * Use Constructor Chaining
     */
    public ArrayQueue() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct a Queue with the specified initial capacity of
     * {@code initialCapacity}.
     * @param initialCapacity Initial capacity of the backing array.
     */
    public ArrayQueue(int initialCapacity) {
        backingArray = (T[]) new Object[initialCapacity];
    }

    @Override
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        } else if (size == backingArray.length) {
            T[]newArray = (T[]) new Object[backingArray.length * 2];
            for (int index = 0; index < backingArray.length; index++) {
                newArray[index] = backingArray[(index
                    + front) % backingArray.length];
            }
            front = 0;
            back = backingArray.length;
            backingArray = newArray;
        }
        backingArray[back] = item;
        back = (back + 1) % backingArray.length;
        size++;
    }

    @Override
    public T dequeue() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException(
                "The queue is empty. No elements exist.");
        }
        T returnItem = backingArray[front];
        backingArray[front] = null;
        front = (front + 1) % backingArray.length;
        size--;
        return returnItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Used for testing your code.
     * DO NOT USE THIS METHOD!
     *
     * @return the backing array of this queue.
     */
    public Object[] getBackingArray() {
        return backingArray;
    }

}
