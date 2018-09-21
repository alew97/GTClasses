import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection cannot be null.");
        }
        for (T element: data) {
            if (element == null) {
                throw new IllegalArgumentException("Collection cannot contain "
                    + "any null elements.");
            }
            this.add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = addRecursive(root, data);
    }
    
    /**
     * Private helper method for add() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param data the data to be added to the appropriate BST leaf node
     * @return current the appropriate BST leaf node to be returned
     */
    private BSTNode<T> addRecursive(BSTNode<T> current, T data) {
        if (current == null) {
            current = new BSTNode<T>(data);
            size++;
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addRecursive(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addRecursive(current.getRight(), data));
        }
        return current;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        T returnData = removeRecursive(root, null, data);
        size--;
        return returnData;
    }
    
    /**
     * Private helper method for remove() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param parent the parent of the current BST node
     * @param data the data to be removed from the appropriate BST leaf node
     * @return returnData the appropriate BST leaf node to be returned
     */
    private T removeRecursive(BSTNode<T> current, BSTNode<T> parent, T data) {
        T returnData = null;
        if (current == null) {
            throw new java.util.NoSuchElementException("Data does not exist "
                + "in BST.");
        } else if (data.compareTo(current.getData()) < 0) {
            returnData = removeRecursive(current.getLeft(), current, data);
        } else if (data.compareTo(current.getData()) > 0) {
            returnData = removeRecursive(current.getRight(), current, data);
        } else {
            returnData = current.getData();
            if (current.getLeft() == null && current.getRight() == null) {
                if (parent == null) {
                    root = null;
                } else if (parent.getLeft() != null
                    && current.getData().equals(parent.getLeft().getData())) {
                    parent.setLeft(null);
                } else if (parent.getRight() != null
                    && current.getData().equals(parent.getRight().getData())) {
                    parent.setRight(null);
                }
            } else if (current.getLeft() != null
                && current.getRight() == null) {
                if (parent == null) {
                    root = current.getLeft();
                } else if (parent.getLeft() != null
                    && current.getData().equals(parent.getLeft().getData())) {
                    parent.setLeft(current.getLeft());
                } else if (parent.getRight() != null
                    && current.getData().equals(parent.getRight().getData())) {
                    parent.setRight(current.getLeft());
                }
            } else if (current.getLeft() == null
                && current.getRight() != null) {
                if (parent == null) {
                    root = current.getRight();
                } else if (parent.getLeft() != null
                    && current.getData().equals(parent.getLeft().getData())) {
                    parent.setLeft(current.getRight());
                } else if (parent.getRight() != null
                    && current.getData().equals(parent.getRight().getData())) {
                    parent.setRight(current.getRight());
                }
            } else {
                current.setData(removeSuccessor(current));
            }
        }
        return returnData;
    }

    /**
     * Private helper method for remove() to deal with BST successors.
     *
     * @param current the current BST node to be replaced with successor
     * @return successorData the successor BST node data for remove()
     */
    private T removeSuccessor(BSTNode<T> current) {
        BSTNode<T> parent = current;
        current = current.getRight();
        while (current.getLeft() != null) {
            parent = current;
            current = current.getLeft();
        }
        T successorData = current.getData();
        
        if (parent.getRight() != null
                && parent.getRight().getData().equals(current.getData())) {
            parent.setRight(current.getRight());
        } else if (current.getRight() != null) {
            parent.setLeft(current.getRight());
        } else if (current.getRight() == null) {
            parent.setLeft(null);
        } else {
            throw new RuntimeException("An edge case I didn't expect! FIX IT!");
        }
        return successorData;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        T returnData = getRecursive(root, data);
        return returnData;
    }

    /**
     * Private helper method for get() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param data the data to check against a BST node
     * @return returnData returns the appropriate BST node's data
     */
    private T getRecursive(BSTNode<T> current, T data) {
        T returnData = null;
        if (current == null) {
            throw new java.util.NoSuchElementException("Data does not exist "
                + "in BST.");
        } else if (data.equals(current.getData())) {
            returnData = current.getData();
        } else if (data.compareTo(current.getData()) < 0) {
            returnData = getRecursive(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            returnData = getRecursive(current.getRight(), data);
        }
        return returnData;
    }
    
    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        boolean returnValue = containsRecursive(root, data);
        return returnValue;
    }

    /**
     * Private helper method for contains() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param data the data to check against a BST node
     * @return state the appropriate boolean value to be returned
     */
    private boolean containsRecursive(BSTNode<T> current, T data) {
        boolean state = false;
        if (current == null) {
            return state;
        } else if (data.equals(current.getData())) {
            state = true;
        } else if (data.compareTo(current.getData()) < 0) {
            state = containsRecursive(current.getLeft(), data);
        } else if (data.compareTo(current.getData()) > 0) {
            state = containsRecursive(current.getRight(), data);
        }
        return state;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> returnList = new ArrayList<T>(size);
        return preorderRecursive(root, returnList);
    }

    /**
     * Private helper method for preorder() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param list the list to append to
     * @return list the list version of the BST
     */
    private List<T> preorderRecursive(BSTNode<T> current, List<T> list) {
        if (current == null) {
            return list;
        }

        list.add(current.getData());
        preorderRecursive(current.getLeft(), list);
        preorderRecursive(current.getRight(), list);
        return list;
    }

    @Override
    public List<T> postorder() {
        List<T> returnList = new ArrayList<T>(size);
        return postorderRecursive(root, returnList);
    }

    /**
     * Private helper method for postorder() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param list the list to append to
     * @return list the list version of the BST
     */
    private List<T> postorderRecursive(BSTNode<T> current, List<T> list) {
        if (current == null) {
            return list;
        }

        postorderRecursive(current.getLeft(), list);
        postorderRecursive(current.getRight(), list);
        list.add(current.getData());
        return list;
    }

    @Override
    public List<T> inorder() {
        List<T> returnList = new ArrayList<T>(size);
        return inorderRecursive(root, returnList);
    }

    /**
     * Private helper method for inorder() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @param list the list to append to
     * @return list the list version of the BST
     */
    private List<T> inorderRecursive(BSTNode<T> current, List<T> list) {
        if (current == null) {
            return list;
        }

        inorderRecursive(current.getLeft(), list);
        list.add(current.getData());
        inorderRecursive(current.getRight(), list);
        return list;
    }

    @Override
    public List<T> levelorder() {
        List<T> returnList = new ArrayList<T>(size);

        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> qhead = queue.remove();
            if (qhead != null) {
                returnList.add(qhead.getData());
                queue.add(qhead.getLeft());
                queue.add(qhead.getRight());
            }
        }
        return returnList;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightRecursive(root);
    }
    
    /**
     * Private helper method for height() to recursively traverse the BST.
     *
     * @param current the current BST node
     * @return height the height of the BST
     */
    private int heightRecursive(BSTNode<T> current) {
        if (current == null) {
            return -1;
        }
        int height = Math.max(heightRecursive(current.getLeft()),
            heightRecursive(current.getRight())) + 1;

        return height;
    }
    
    /**
     * Compares two BSTs and checks to see if the trees are the same.  If
     * the trees have the same data in a different arrangement, this method
     * should return false.  This will only return true if the tree is in the
     * exact same arrangement as the other tree.
     *
     * You may assume that you won't get a BST with a different generic type.
     * For example, if this BST holds Strings, then you will not get as an input
     * a BST that holds Integers.
     * 
     * Be sure to also implement the other general checks that .equals() should
     * check as well.
     *
     * Should have a running time of O(n).
     * 
     * @param other the Object we are comparing this BST to
     * @return true if other is equal to this BST, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof BST<?>)) {
            return false;
        }
        BST<T> otherTree = (BST<T>) other;
        boolean isEqual = equalsRecursive(this.getRoot(), otherTree.getRoot());
        return isEqual;
    }
    
    /**
     * Private helper method for equals() to implemented recursively.
     *
     * @param thisNode the current BST Node of this tree
     * @param otherNode the current BST Node of the other tree
     * @return isEqual the boolean that tells if the BST Node is equal
     */
    private boolean equalsRecursive(BSTNode<T> thisNode, BSTNode<T> otherNode) {
        boolean isEqual = false;
        if (thisNode == null && otherNode == null) {
            return true;
        } else if (thisNode == null && otherNode != null) {
            return false;
        } else if (thisNode != null && otherNode == null) {
            return false;
        } else if (thisNode.getData().equals(otherNode.getData())) {
            isEqual = equalsRecursive(thisNode.getLeft(), otherNode.getLeft())
                && equalsRecursive(thisNode.getRight(), otherNode.getRight());
        }
        return isEqual;
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
