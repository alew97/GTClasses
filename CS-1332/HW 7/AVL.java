import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        for (T entry : data) {
            if (entry == null) {
                throw new IllegalArgumentException("Data cannot be null");
            }
            add(entry);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        root = add(root, data);
    }
    
    /**
     * Private helper method to recursively add to an AVL tree.
     * 
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking to add to the AVL tree
     * @return currentNode the new updated node
     */
    private AVLNode<T> add(AVLNode<T> currentNode, T data) {
        if (currentNode == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (currentNode.getData().compareTo(data) > 0) {
            currentNode.setLeft(add(currentNode.getLeft(), data));
        } else if (currentNode.getData().compareTo(data) < 0) {
            currentNode.setRight(add(currentNode.getRight(), data));
        }
        update(currentNode);
        
        // Rebalancing code block
        if (currentNode.getBalanceFactor() > 1) {
            if (currentNode.getLeft().getBalanceFactor() == -1) {
                currentNode = leftRightRotation(currentNode);
                update(currentNode);
            } else {
                currentNode = rightRotation(currentNode);
                update(currentNode);
            }
        } else if (currentNode.getBalanceFactor() < -1) {
            if (currentNode.getRight().getBalanceFactor() == 1) {
                currentNode = rightLeftRotation(currentNode);
                update(currentNode);
            } else {
                currentNode = leftRotation(currentNode);
                update(currentNode);
            }
        }
        
        return currentNode;
    }
    
    /**
     * Private helper method to update the height
     * and balance factor the AVL node.
     * 
     * @param currentNode the node you are seeking to update
     */
    private void update(AVLNode<T> currentNode) {
        int leftNodeHeight = -1;
        if (currentNode.getLeft() != null) {
            leftNodeHeight = currentNode.getLeft().getHeight();
        }
        int rightNodeHeight = -1;
        if (currentNode.getRight() != null) {
            rightNodeHeight = currentNode.getRight().getHeight();
        }
        currentNode.setHeight(Math.max(leftNodeHeight, rightNodeHeight) + 1);
        currentNode.setBalanceFactor(leftNodeHeight - rightNodeHeight);
    }
    
    /**
     * Private helper method to do left rotation.
     * 
     * @param currentNode the current node we are rotating
     * @return returns the node that should become the new parent node
     */
    private AVLNode<T> leftRotation(AVLNode<T> currentNode) {
        AVLNode<T> returnNode = currentNode.getRight();
        AVLNode<T> temp = currentNode.getRight().getLeft();
        returnNode.setLeft(currentNode);
        currentNode.setRight(temp);
        update(currentNode);
        return returnNode;
    }
    
    /**
     * Private helper method to do right rotation.
     * 
     * @param currentNode the current parent node we are rotating
     * @return returnNode the node that should become the new parent node
     */
    private AVLNode<T> rightRotation(AVLNode<T> currentNode) {
        AVLNode<T> returnNode = currentNode.getLeft();
        AVLNode<T> temp = currentNode.getLeft().getRight();
        returnNode.setRight(currentNode);
        currentNode.setLeft(temp);
        update(currentNode);
        return returnNode;
    }
    
    /**
     * Private helper method to do left-right rotation.
     * 
     * @param currentNode the current parent node we are rotating
     * @return returns the new parent node after the rotation operation
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> currentNode) {
        currentNode.setLeft(leftRotation(currentNode.getLeft()));
        return rightRotation(currentNode);
    }
    
    /**
     * Private helper method to do right-left rotation.
     * 
     * @param currentNode the current parent node we are rotating
     * @return returns the new parent node after the rotation operation
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> currentNode) {
        currentNode.setRight(rightRotation(currentNode.getRight()));
        return leftRotation(currentNode);
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        AVLNode<T> returnNode = new AVLNode<T>(null);
        root = remove(root, data, returnNode);
        return returnNode.getData();
    }
    
    /**
     * Private helper method to recursively remove from an AVL tree.
     * 
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking to destroy
     * @param returnNode the dummy AVL node
     * @return returns the new node after the rotation operation
     */
    private AVLNode<T> remove(AVLNode<T> currentNode, T data,
            AVLNode<T> returnNode) {
        if (currentNode == null) {
            throw new NoSuchElementException("Element does not exist");
        } else if (currentNode.getData().compareTo(data) > 0) {
            currentNode.setLeft(remove(currentNode.getLeft(), data,
                    returnNode));
        } else if (currentNode.getData().compareTo(data) < 0) {
            currentNode.setRight(remove(currentNode.getRight(), data,
                    returnNode));
        } else {
            size--;
            returnNode.setData(currentNode.getData());
            
            if (currentNode.getLeft() == null
                    && currentNode.getRight() == null) {
                currentNode = null;
            } else if (currentNode.getLeft() != null
                    && currentNode.getRight() == null) {
                currentNode = currentNode.getLeft();
            } else if (currentNode.getLeft() == null
                    && currentNode.getRight() != null) {
                currentNode = currentNode.getRight();
            } else {
                AVLNode<T> dummyNode = new AVLNode<T>(null);
                currentNode.setRight(removeSuccessorRecursive(
                        currentNode.getRight(), dummyNode));
                currentNode.setData(dummyNode.getData());
            }
        }
        
        if (currentNode != null) {
            update(currentNode);
            
            if (currentNode.getBalanceFactor() > 1) {
                if (currentNode.getLeft().getBalanceFactor() == -1) {
                    currentNode = leftRightRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = rightRotation(currentNode);
                    update(currentNode);
                }
            } else if (currentNode.getBalanceFactor() < -1) {
                if (currentNode.getRight().getBalanceFactor() == 1) {
                    currentNode = rightLeftRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = leftRotation(currentNode);
                    update(currentNode);
                }
            }
        }
        
        return currentNode;
    }
    
    /**
     * Private helper method for remove() to deal with AVL successors.
     *
     * @param currentNode the current node we're traversing for successor
     * @param dummyNode the dummy node for storing the successor data
     * @return currentNode the new updated parent node
     */
    private AVLNode<T> removeSuccessorRecursive(AVLNode<T> currentNode,
            AVLNode<T> dummyNode) {
        if (currentNode.getLeft() != null) {
            currentNode.setLeft(removeSuccessorRecursive(
                    currentNode.getLeft(), dummyNode));
        } else {
            dummyNode.setData(currentNode.getData());
            
            currentNode = currentNode.getRight();
        }
        
        if (currentNode != null) {
            update(currentNode);
            
            if (currentNode.getBalanceFactor() > 1) {
                if (currentNode.getLeft().getBalanceFactor() == -1) {
                    currentNode = leftRightRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = rightRotation(currentNode);
                    update(currentNode);
                }
            } else if (currentNode.getBalanceFactor() < -1) {
                if (currentNode.getRight().getBalanceFactor() == 1) {
                    currentNode = rightLeftRotation(currentNode);
                    update(currentNode);
                } else {
                    currentNode = leftRotation(currentNode);
                    update(currentNode);
                }
            }
        }
        
        return currentNode;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return get(root, data);
    }
    
    /**
     * Private helper method to recursively get from an AVL tree.
     * 
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking
     * @return returns the data of the found node; else throws exception
     */
    private T get(AVLNode<T> currentNode, T data) {
        if (currentNode == null) {
            throw new NoSuchElementException("Element does not exist");
        } else if (currentNode.getData().compareTo(data) > 0) {
            return get(currentNode.getLeft(), data);
        } else if (currentNode.getData().compareTo(data) < 0) {
            return get(currentNode.getRight(), data);
        }
        return currentNode.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return contains(root, data);
    }
    
    /**
     * Private helper method to recursively check if an element exists.
     * 
     * @param currentNode the node you are currently traversing
     * @param data the data you are seeking
     * @return returns true if data is in AVL tree; else returns false
     */
    private boolean contains(AVLNode<T> currentNode, T data) {
        if (currentNode == null) {
            return false;
        } else if (currentNode.getData().compareTo(data) > 0) {
            return contains(currentNode.getLeft(), data);
        } else if (currentNode.getData().compareTo(data) < 0) {
            return contains(currentNode.getRight(), data);
        }
        return true;
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
     * Private helper method for preorder() to traverse the AVL tree.
     *
     * @param current the current AVL node
     * @param list the list to append to
     * @return list the list version of the AVL tree
     */
    private List<T> preorderRecursive(AVLNode<T> current, List<T> list) {
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
     * Private helper method for postorder() to traverse the AVL tree.
     *
     * @param current the current AVL node
     * @param list the list to append to
     * @return list the list version of the AVL tree
     */
    private List<T> postorderRecursive(AVLNode<T> current, List<T> list) {
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
     * Private helper method for inorder() to traverse the AVL tree.
     *
     * @param current the current AVL node
     * @param list the list to append to
     * @return list the list version of the AVL tree
     */
    private List<T> inorderRecursive(AVLNode<T> current, List<T> list) {
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

        Queue<AVLNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode<T> qhead = queue.remove();
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
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        return height(root);
    }
    
    /**
     * Private helper method to recursively check the height of the AVL tree.
     * 
     * @param currentNode the node you are currently traversing
     * @return returns the height of the current node
     */
    private int height(AVLNode<T> currentNode) {
        if (currentNode == null) {
            return -1;
        }
        
        return Math.max(height(currentNode.getLeft()),
                height(currentNode.getLeft())) + 1;
    }
    
    /**
     * Compares two AVLs and checks to see if the trees are the same.  If
     * the trees have the same data in a different arrangement, this method
     * should return false.  This will only return true if the tree is in the
     * exact same arrangement as the other tree.
     *
     * You may assume that you won't get an AVL with a different generic type.
     * For example, if this AVL holds Strings, then you will not get as an input
     * an AVL that holds Integers.
     * 
     * Be sure to also implement the other general checks that .equals() should
     * check as well.
     * 
     * @param other the Object we are comparing this AVL to
     * @return true if other is equal to this AVL, false otherwise.
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other == null) {
            return false;
        } else if (!(other instanceof AVL<?>)) {
            return false;
        }
        AVL<T> otherTree = (AVL<T>) other;
        boolean isEqual = equalsRecursive(this.getRoot(), otherTree.getRoot());
        return isEqual;
    }
    
    /**
     * Private helper method for equals() to implemented recursively.
     *
     * @param thisNode the current AVL node of this tree
     * @param otherNode the current AVL node of the other tree
     * @return isEqual the boolean that tells if the AVL node is equal
     */
    private boolean equalsRecursive(AVLNode<T> thisNode, AVLNode<T> otherNode) {
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
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
