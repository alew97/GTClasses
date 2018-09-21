/**
 * Your implementation of a Stack backed by an array.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {
    // Do not add any new instance variables!
    private T[] backingArray;
    private int size;

    /**
     * Construct a Stack with an initial capacity of {@code INITIAL_CAPACITY}.
     *
     * Use constructor chaining.
     */
    public ArrayStack() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct a Stack with the specified initial capacity of
     * {@code initialCapacity}.
     * @param initialCapacity Initial capacity of the backing array.
     */
    public ArrayStack(int initialCapacity) {
        backingArray = (T[]) new Object[initialCapacity];
    }

    @Override
    public void push(T item) {
        if (item == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        } else if (size == backingArray.length) {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int index = 0; index < backingArray.length; index++) {
                newArray[index] = backingArray[index];
            }
            backingArray = newArray;

        }
        backingArray[size] = item;
        size++;
    }

    @Override
    public T pop() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException(
                "The stack is empty. No elements exist.");
        }
        T returnItem = backingArray[size - 1];
        backingArray[size - 1] = null;
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
