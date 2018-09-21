/**
 * Your implementation of a SinglyLinkedList.  You may reuse your code from HW1,
 * but make sure to only include the methods in the interface given in this
 * assignment.
 *
 * @author James Lee
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        } else if (this.isEmpty()) {
            head = new LinkedListNode<T>(data);
            tail = head;
        } else {
            head = new LinkedListNode<T>(data, head);
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        } else if (this.isEmpty()) {
            head = new LinkedListNode<T>(data);
            tail = head;
        } else {
            tail.setNext(new LinkedListNode<T>(data));
            tail = tail.getNext();
        }
        size++;
    }

    @Override
    public T removeFromFront() {
        if (this.isEmpty()) {
            return null;
        } else {
            T returnData = head.getData();
            head = head.getNext();
            if (size == 1) {
                tail = null;
            }
            size--;
            return returnData;
        }
    }

    @Override
    public T removeFromBack() {
        if (this.isEmpty()) {
            return null;
        } else if (size == 1) {
            T returnData = tail.getData();
            head = null;
            tail = null;
            size--;
            return returnData;
        } else {
            T returnData = tail.getData();

            LinkedListNode<T> current = head;
            for (int i = 0; i < (size - 2); i++) {
                current = current.getNext();
            }
            current.setNext(null);
            tail = current;
            size--;
            return returnData;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
