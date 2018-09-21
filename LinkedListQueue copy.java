/**
 * Your implementation of a Queue backed by a LinkedList.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class LinkedListQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    // Do not modify this variable.
    private LinkedListInterface<T> backingList;

    /**
     * Initialize the Queue.
     */
    public LinkedListQueue() {
        backingList = new SinglyLinkedList<T>();
    }

    @Override
    public void enqueue(T item) {
        backingList.addToBack(item);
    }

    @Override
    public T dequeue() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException(
                "The queue is empty. No elements exist.");
        }
        return backingList.removeFromFront();
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    public boolean isEmpty() {
        return backingList.isEmpty();
    }

    /**
     * Used for testing your code.
     * DO NOT USE THIS METHOD!
     *
     * @return the backing list of this queue.
     */
    public LinkedListInterface<T> getBackingList() {
        return backingList;
    }

}
