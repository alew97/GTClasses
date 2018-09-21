import java.util.HashSet;
import java.util.Set;

/**
 * Your implementation of a skip list.
 * 
 * @author Alice Lew
 * @version 1.0
 */
public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    // Do not add any additional instance variables
    private CoinFlipper coinFlipper;
    private int size;
    private SkipListNode<T> head;

    /**
     * Constructs a SkipList object that stores data in ascending order.
     * When an item is inserted, the flipper is called until it returns a tails.
     * If, for an item, the flipper returns n heads, the corresponding node has
     * n + 1 levels.
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        head = new SkipListNode<T>(null, 1);
    }

    @Override
    public T first() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "The skip list is empty.");
        }

        SkipListNode<T> current = head;
        while (current.getLevel() > 1) {
            current = current.getDown();
        }
        if (current.getNext() != null) {
            current = current.getNext();
        }
        return current.getData();
    }

    @Override
    public T last() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "The skip list is empty.");
        }

        SkipListNode<T> current = head;
        while (current.getNext() != null || current.getLevel() > 1) {
            if (current.getNext() != null) {
                current = current.getNext();
            } else if (current.getLevel() > 1) {
                current = current.getDown();
            }
        }
        return current.getData();
    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        // Getting coin flips
        int levelPromoted = 1;
        while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            levelPromoted++;
        }
        int levelNeeded = levelPromoted + 1;

        // Allocating enough levels for skip list
        while (head.getLevel() < levelNeeded) {
            SkipListNode<T> newHead = new SkipListNode<T>(
                    null, head.getLevel() + 1);
            newHead.setDown(head);
            head.setUp(newHead);
            head = newHead;
        }

        // Iterating through skip-list
        SkipListNode<T> current = head.getDown();
        while (current.getLevel() > levelPromoted) {
            while (current.getNext() != null
                    && current.getNext().getData().compareTo(data) < 0) {
                current = current.getNext();
            }
            current = current.getDown();
        }

        putHelper(current, data);
        size++;
    }

    /**
     * Helper method for the put method to add nodes top-down.
     * 
     * @param current the current node to look at
     * @param data the data to be compared to
     * @return returns the node that should be one level 
     *         lower than the level the method was called in.
     */
    private SkipListNode<T> putHelper(SkipListNode<T> current, T data) {
        while (current.getNext() != null
                && current.getNext().getData().compareTo(data) < 0) {
            current = current.getNext();
        }
        
        // Reassigning left and right pointers
        SkipListNode<T> newNode = new SkipListNode<T>(
                data, current.getLevel());
        newNode.setPrev(current);
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        if (newNode.getNext() != null) {
            newNode.getNext().setPrev(newNode);
        }
        
        if (newNode.getLevel() > 1) {
            // Reassigning up and down pointers
            SkipListNode<T> bottomNode = putHelper(current.getDown(), data);
            newNode.setDown(bottomNode);
            bottomNode.setUp(newNode);
        }

        return newNode;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (size != 0) {
            T returnData = null;
            
            SkipListNode<T> current = head.getDown();
            while (current != null) {
                if (current.getNext() != null
                        && current.getNext().getData().compareTo(data) <= 0) {
                    current = current.getNext();
                } else if (current.getLevel() >= 1) {
                    if (data.equals(current.getData())) {
                        // Get return data
                        returnData = current.getData();
                        
                        // Checks to see if item is only element in level
                        if (current.getPrev().getData() == null
                                && current.getNext() == null) {
                            head = head.getDown();
                            head.setUp(null);
                        }
                        
                        // Reassign pointers
                        current.getPrev().setNext(current.getNext());
                        if (current.getNext() != null) {
                            current.getNext().setPrev(current.getPrev());
                        }
                        current.setUp(null);
                    }
                    // Traverse down
                    current = current.getDown();
                }
            }
            if (returnData != null) {
                size--;
                return returnData;
            }
        }
        throw new java.util.NoSuchElementException(
                "Data does not exist in skip list.");
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        SkipListNode<T> current = head.getDown();
        while (current != null) {
            if (current.getNext() != null
                    && current.getNext().getData().compareTo(data) <= 0) {
                current = current.getNext();
            } else if (data.equals(current.getData())) {
                return true;
            } else {
                current = current.getDown();
            }
        }
        return false;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        SkipListNode<T> current = head.getDown();
        while (current != null) {
            if (current.getNext() != null
                    && current.getNext().getData().compareTo(data) <= 0) {
                current = current.getNext();
            } else if (data.equals(current.getData())) {
                return current.getData();
            } else {
                current = current.getDown();
            }
        }
        throw new java.util.NoSuchElementException(
                "Data does not exist in skip list.");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        head = new SkipListNode<T>(null, 1);
    }

    @Override
    public Set<T> dataSet() {
        Set<T> returnSet = new HashSet<T>(size);
        SkipListNode<T> current = head;
        while (current.getLevel() > 1) {
            current = current.getDown();
        }
        while (current.getNext() != null) {
            current = current.getNext();
            returnSet.add(current.getData());
        }
        return returnSet;
    }

    @Override
    public SkipListNode<T> getHead() {
        return head;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("**********************\n");
        builder.append(String.format("SkipList (size = %d)\n", size()));
        SkipListNode<T> levelCurr = getHead();

        while (levelCurr != null) {
            SkipListNode<T> curr = levelCurr;
            int level = levelCurr.getLevel();
            builder.append(String.format("Level: %2d   ", level));

            while (curr != null) {
                builder.append(String.format("(%s)%s", curr.getData(),
                            curr.getNext() == null ? "\n" : ", "));
                curr = curr.getNext();
            }
            levelCurr = levelCurr.getDown();
        }
        builder.append("**********************\n");
        return builder.toString();
    }

}
