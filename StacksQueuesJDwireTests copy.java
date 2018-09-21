import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

/**
 * Basic tests for the stack and queue classes.
 *
 * @author CS 1332 TAs and Joshua Dwire
 * @version 2.0
 */
public class StacksQueuesJDwireTests {

    private StackInterface<Integer> stack;
    private QueueInterface<Integer> queue;

    public static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT)
    public void test01ArrayStackDefaultCapacity() {
        stack = new ArrayStack<>();

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        assertEquals("Incorrect default array stack length", 10, backingArray.length);
    }

    @Test(timeout = TIMEOUT)
    public void test02ArrayStackCapacityParam() {
        stack = new ArrayStack<>(17);

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        assertEquals("Incorrect array stack capacity", 17, backingArray.length);
    }

    @Test(timeout = TIMEOUT)
    public void test03ArrayStackPop() {
        stack = new ArrayStack<>();
        assertEquals("Array stack should start with size = 0", 0, stack.size());

        stack.push(34);
        stack.push(29);
        stack.push(48);
        stack.push(59);
        assertEquals("Array stack should return the last item added", (Integer) 59, stack.pop());

        assertEquals("Array should have 3 items after adding 4 and removing 1", 3, stack.size());

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        Object[] expected = new Object[10];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;

        assertArrayEquals("Unexpected backing array structure", expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void test04ArrayStackResize() {
        stack = new ArrayStack<>(11);
        assertEquals("Array stack should start with size = 0", 0, stack.size());

        stack.push(84);
        stack.push(32);
        stack.push(63);
        stack.push(19);
        stack.push(43);
        stack.push(58);
        stack.push(28);
        stack.push(93);
        stack.push(12);
        stack.push(59);
        stack.push(68);
        stack.push(9);

        assertEquals("Incorrect array stack size", 12, stack.size());

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        // Custom initial size to make sure we regrow to size*2 and not just 20
        assertEquals("When regrowing an array, the new length should be 2 times previous length", 22, backingArray.length);

        Object[] expected = new Object[22];
        expected[0] = 84;
        expected[1] = 32;
        expected[2] = 63;
        expected[3] = 19;
        expected[4] = 43;
        expected[5] = 58;
        expected[6] = 28;
        expected[7] = 93;
        expected[8] = 12;
        expected[9] = 59;
        expected[10] = 68;
        expected[11] = 9;

        assertArrayEquals("Unexpected backing array value", expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void test05ArrayStackPush() {
        stack = new ArrayStack<>();
        assertEquals("Array stack should start with size = 0", 0, stack.size());

        stack.push(34);
        stack.push(29);
        stack.push(48);
        stack.push(59);

        assertEquals("Incorrect array stack size", 4, stack.size());

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        Object[] expected = new Object[10];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals("Unexpected backing array value", expected, backingArray);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test06ArrayStackPushNull() {
        stack = new ArrayStack<>();
        stack.push(null);
    }

    @Test(timeout = TIMEOUT)
    public void test07ArrayStackIsEmpty() {
        stack = new ArrayStack<>();

        assertTrue("isEmpty should return true for an empty array stack", stack.isEmpty());
        stack.push(1);
        assertFalse("isEmpty should return false for an empty array stack after adding an element", stack.isEmpty());
        stack.push(1);
        assertFalse("isEmpty should return false for an empty array stack after adding an element", stack.isEmpty());
        stack.pop();
        assertFalse("isEmpty should return false for an empty array stack after adding an element", stack.isEmpty());
        stack.pop();
        assertTrue("isEmpty should return true for an empty array stack", stack.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void test08ArrayStackPopEmpty() {
        stack = new ArrayStack<>();
        stack.push(1);
        stack.pop();
        stack.pop();
    }

    @Test(timeout = TIMEOUT)
    public void test09ArrayQueueDefaultCapacity() {
        queue = new ArrayQueue<>();

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        assertEquals("Incorrect default array queue length", 10, backingArray.length);
    }

    @Test(timeout = TIMEOUT)
    public void test10ArrayQueueCapacityParam() {
        queue = new ArrayQueue<>(19);

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        assertEquals("Incorrect array queue length", 19, backingArray.length);
    }

    @Test(timeout = TIMEOUT)
    public void test11ArrayQueuePush() {
        queue = new ArrayQueue<>();
        assertEquals("Array queue should start with size = 0", 0, queue.size());

        queue.enqueue(34);
        queue.enqueue(29);
        queue.enqueue(48);
        queue.enqueue(59);

        assertEquals("Incorrect array queue size", 4, queue.size());

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        Object[] expected = new Object[10];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals("Unexpected value for backing array", expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void test12ArrayQueuePop() {
        queue = new ArrayQueue<>();
        assertEquals("Array queue should start with size = 0", 0, queue.size());

        queue.enqueue(34);
        queue.enqueue(29);
        queue.enqueue(48);
        queue.enqueue(59);
        assertEquals("Array queue should return the first item added", (Integer) 34, queue.dequeue());

        assertEquals("Incorrect size for array queue", 3, queue.size());

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        Object[] expected = new Object[10];
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals("Unexpected value for backing array", expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void test13ArrayQueueIsEmpty() {
        queue = new ArrayQueue<>();

        assertTrue("isEmpty should return true for an empty array queue", queue.isEmpty());
        queue.enqueue(1);
        assertFalse("isEmpty should return false for an non-empty array queue", queue.isEmpty());
        queue.enqueue(1);
        assertFalse("isEmpty should return false for an non-empty array queue", queue.isEmpty());
        queue.dequeue();
        assertFalse("isEmpty should return false for an non-empty array queue", queue.isEmpty());
        queue.dequeue();
        assertTrue("isEmpty should return true for an empty array queue", queue.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test14ArrayQueueEnqueueNull() {
        queue = new ArrayQueue<>();
        queue.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void test15ArrayQueueDequeueEmpty() {
        queue = new ArrayQueue<>();
        queue.enqueue(1);
        queue.dequeue();
        queue.dequeue();
    }

    @Test(timeout = TIMEOUT)
    public void test16ArrayQueueResize() {
        queue = new ArrayQueue<>(11);
        assertEquals("Array queue should start with size = 0", 0, queue.size());

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);
        queue.enqueue(43);
        queue.enqueue(58);
        queue.enqueue(28);
        queue.enqueue(93);
        queue.enqueue(12);
        queue.enqueue(59);
        queue.enqueue(68);
        queue.enqueue(9);

        assertEquals(12, queue.size());

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        // Custom initial size to make sure we regrow to size*2 and not just 20
        assertEquals("When regrowing an array, the new length should be 2 times previous length", 22, backingArray.length);


        Object[] expected = new Object[22];
        expected[0] = 84;
        expected[1] = 32;
        expected[2] = 63;
        expected[3] = 19;
        expected[4] = 43;
        expected[5] = 58;
        expected[6] = 28;
        expected[7] = 93;
        expected[8] = 12;
        expected[9] = 59;
        expected[10] = 68;
        expected[11] = 9;

        assertArrayEquals("Unexpected value for backing array", expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void test17ArrayQueueWraparound() {
        // See Piazza: "When is a backing array full?" (https://piazza.com/class/ijhd4dhacv9du?cid=109)

        // Custom initial size to detect hardcoded 10
        queue = new ArrayQueue<>(11);
        assertEquals("Array queue should start with size = 0", 0, queue.size());

        Object[] backingArray;

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);

        queue.enqueue(43);
        queue.enqueue(58);
        queue.enqueue(28);
        queue.enqueue(93);
        queue.enqueue(12);

        assertEquals("Unexpected queue size", 9, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect initial capacity for array queue", 11, backingArray.length);

        assertEquals("Incorrect 1st item in queue", new Integer(84), queue.dequeue());
        assertEquals("Incorrect 2nd item in queue", new Integer(32), queue.dequeue());
        assertEquals("Incorrect 3rd item in queue", new Integer(63), queue.dequeue());
        assertEquals("Incorrect 4th item in queue", new Integer(19), queue.dequeue());

        assertEquals("Incorrect queue size after removing items", 5, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 11, backingArray.length);

        queue.enqueue(47);
        queue.enqueue(63);
        queue.enqueue(55);
        queue.enqueue(92);
        queue.enqueue(95);
        queue.enqueue(19);

        assertEquals("Incorrect array capacity", 11, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("There should be 11 items in the queue", 11, backingArray.length);

        assertEquals("Incorrect 1st item in queue after wraparound", new Integer(43), queue.dequeue());
        assertEquals("Incorrect 2nd item in queue after wraparound", new Integer(58), queue.dequeue());
        assertEquals("Incorrect 3rd item in queue after wraparound", new Integer(28), queue.dequeue());
        assertEquals("Incorrect 4th item in queue after wraparound", new Integer(93), queue.dequeue());
        assertEquals("Incorrect 5th item in queue after wraparound", new Integer(12), queue.dequeue());

        assertEquals("Incorrect 6th item in queue after wraparound", new Integer(47), queue.dequeue());
        assertEquals("Incorrect 7th item in queue after wraparound", new Integer(63), queue.dequeue());
        assertEquals("Incorrect 8th item in queue after wraparound", new Integer(55), queue.dequeue());
        assertEquals("Incorrect 9th item in queue after wraparound", new Integer(92), queue.dequeue());
        assertEquals("Incorrect 10th item in queue after wraparound", new Integer(95), queue.dequeue());
        assertEquals("Incorrect 11th item in queue after wraparound", new Integer(19), queue.dequeue());
    }

    @Test(timeout = TIMEOUT)
    public void test18ArrayQueueWraparoundResize() {
        // See Piazza: "When is a backing array full?" (https://piazza.com/class/ijhd4dhacv9du?cid=109)

        // Custom initial size to detect hardcoded 10
        queue = new ArrayQueue<>(11);
        assertEquals("Array queue should start with size = 0", 0, queue.size());

        Object[] backingArray;

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);

        queue.enqueue(43);
        queue.enqueue(58);
        queue.enqueue(28);
        queue.enqueue(93);
        queue.enqueue(12);

        assertEquals("Incorrect queue size", 9, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 11, backingArray.length);

        assertEquals("Incorrect 1st item in queue", new Integer(84), queue.dequeue());
        assertEquals("Incorrect 2nd item in queue", new Integer(32), queue.dequeue());
        assertEquals("Incorrect 3rd item in queue", new Integer(63), queue.dequeue());
        assertEquals("Incorrect 4th item in queue", new Integer(19), queue.dequeue());

        assertEquals("Incorrect queue size after removing items", 5, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 11, backingArray.length);

        queue.enqueue(47);
        queue.enqueue(63);
        queue.enqueue(55);
        queue.enqueue(92);
        queue.enqueue(95);
        queue.enqueue(19);

        assertEquals("Incorrect queue size after wraparound", 11, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity after wraparound", 11, backingArray.length);

        queue.enqueue(492);

        assertEquals("Incorrect queue size after wraparound and regrow", 12, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity after wraparound and regrow", 22, backingArray.length);

        queue.enqueue(117);
        queue.enqueue(647);
        queue.enqueue(939);
        queue.enqueue(464);

        assertEquals("Incorrect queue size", 16, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect capacity size", 22, backingArray.length);

        assertEquals("Incorrect 1st item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(43), queue.dequeue());
        assertEquals("Incorrect 2nd item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(58), queue.dequeue());
        assertEquals("Incorrect 3rd item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(28), queue.dequeue());
        assertEquals("Incorrect 4th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(93), queue.dequeue());
        assertEquals("Incorrect 5th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(12), queue.dequeue());
        assertEquals("Incorrect 6th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(47), queue.dequeue());
        assertEquals("Incorrect 7th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(63), queue.dequeue());
        assertEquals("Incorrect 8th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(55), queue.dequeue());
        assertEquals("Incorrect 9th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(92), queue.dequeue());
        assertEquals("Incorrect 10th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(95), queue.dequeue());
        assertEquals("Incorrect 11th item in queue after wraparound and regrow (check how your regrow handles wraparounds)", new Integer(19), queue.dequeue());

        assertEquals("Incorrect 12th item in queue after wraparound and regrow (make sure your first and last are set correctly after a regrow)", new Integer(492), queue.dequeue());

        assertEquals("Incorrect 13th item in queue after wraparound and regrow (make sure your first and last are set correctly after a regrow)", new Integer(117), queue.dequeue());
        assertEquals("Incorrect 14th item in queue after wraparound and regrow (make sure your first and last are set correctly after a regrow)", new Integer(647), queue.dequeue());
        assertEquals("Incorrect 15th item in queue after wraparound and regrow (make sure your first and last are set correctly after a regrow)", new Integer(939), queue.dequeue());
        assertEquals("Incorrect 16th item in queue after wraparound and regrow (make sure your first and last are set correctly after a regrow)", new Integer(464), queue.dequeue());
    }

    @Test(timeout = TIMEOUT)
    public void test19ArrayQueueWraparoundResizeWraparound() {
        // See Piazza: "When is a backing array full?" (https://piazza.com/class/ijhd4dhacv9du?cid=109)

        // Custom initial size to detect hardcoded 10
        queue = new ArrayQueue<>(11);
        assertEquals("Array queue should start with size = 0", 0, queue.size());

        Object[] backingArray;

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);

        queue.enqueue(43);
        queue.enqueue(58);
        queue.enqueue(28);
        queue.enqueue(93);
        queue.enqueue(12);

        assertEquals("Incorrect queue size", 9, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 11, backingArray.length);

        assertEquals("Incorrect 1st item in queue", new Integer(84), queue.dequeue());
        assertEquals("Incorrect 2nd item in queue", new Integer(32), queue.dequeue());
        assertEquals("Incorrect 3rd item in queue", new Integer(63), queue.dequeue());
        assertEquals("Incorrect 4th item in queue", new Integer(19), queue.dequeue());

        assertEquals("Incorrect queue size after removing items", 5, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 11, backingArray.length);

        queue.enqueue(47);
        queue.enqueue(63);
        queue.enqueue(55);
        queue.enqueue(92);
        queue.enqueue(95);
        queue.enqueue(19);

        assertEquals("Incorrect queue size", 11, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 11, backingArray.length);

        queue.enqueue(492);

        assertEquals("Incorrect queue size", 12, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 22, backingArray.length);

        queue.enqueue(117);
        queue.enqueue(647);
        queue.enqueue(939);
        queue.enqueue(464);

        assertEquals("Incorrect queue size", 16, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 22, backingArray.length);

        assertEquals("Incorrect 1st element in queue", new Integer(43), queue.dequeue());
        assertEquals("Incorrect 2nd element in queue", new Integer(58), queue.dequeue());
        assertEquals("Incorrect 3rd element in queue", new Integer(28), queue.dequeue());
        assertEquals("Incorrect 4th element in queue", new Integer(93), queue.dequeue());
        assertEquals("Incorrect 5th element in queue", new Integer(12), queue.dequeue());
        assertEquals("Incorrect 6th element in queue", new Integer(47), queue.dequeue());

        assertEquals("Incorrect queue size", 10, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 22, backingArray.length); // It didn't shrink, did it?

        // Lets try the wraparound again now that we have a bigger array

        queue.enqueue(6871);
        queue.enqueue(9761);
        queue.enqueue(3715);
        queue.enqueue(9786);
        queue.enqueue(9789);
        queue.enqueue(9761);
        queue.enqueue(6972);
        queue.enqueue(9736);
        queue.enqueue(4956);
        queue.enqueue(8787);
        queue.enqueue(7824);

        assertEquals("Incorrect queue size", 21, queue.size());

        backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();
        assertEquals("Incorrect queue capacity", 22, backingArray.length);

        assertEquals("Incorrect 1st item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(63), queue.dequeue());
        assertEquals("Incorrect 2nd item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(55), queue.dequeue());
        assertEquals("Incorrect 3rd item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(92), queue.dequeue());
        assertEquals("Incorrect 4th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(95), queue.dequeue());
        assertEquals("Incorrect 5th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(19), queue.dequeue());

        assertEquals("Incorrect 6th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(492), queue.dequeue());

        assertEquals("Incorrect 7th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(117), queue.dequeue());
        assertEquals("Incorrect 8th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(647), queue.dequeue());
        assertEquals("Incorrect 9th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(939), queue.dequeue());
        assertEquals("Incorrect 10th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(464), queue.dequeue());

        assertEquals("Incorrect 11th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(6871), queue.dequeue());
        assertEquals("Incorrect 12th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(9761), queue.dequeue());
        assertEquals("Incorrect 13th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(3715), queue.dequeue());
        assertEquals("Incorrect 14th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(9786), queue.dequeue());
        assertEquals("Incorrect 15th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(9789), queue.dequeue());
        assertEquals("Incorrect 16th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(9761), queue.dequeue());
        assertEquals("Incorrect 17th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(6972), queue.dequeue());
        assertEquals("Incorrect 18th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(9736), queue.dequeue());
        assertEquals("Incorrect 19th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(4956), queue.dequeue());
        assertEquals("Incorrect 20th item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(8787), queue.dequeue());
        assertEquals("Incorrect 21st item in queue after regrow and wraparound (are you handling wraparounds after a regrow)", new Integer(7824), queue.dequeue());
    }

    @Test(timeout = TIMEOUT)
    public void test20LinkedListStackPush() {
        stack = new LinkedListStack<>();
        assertEquals("Incorrect initial linked list stack size", 0, stack.size());

        stack.push(84);
        stack.push(32);
        stack.push(63);
        stack.push(19);

        assertEquals("Incorrect stack size", 4, stack.size());

        LinkedListInterface<Integer> backingList =
            ((LinkedListStack<Integer>) stack).getBackingList();

        LinkedListNode<Integer> node = backingList.getHead();
        assertNotEquals("Stacks should be LIFO not FIFO", (Integer) 84, node.getData());
        assertEquals("Incorrect 1st item in stack", (Integer) 19, node.getData());
        node = node.getNext();
        assertEquals("Incorrect 2nd item in stack", (Integer) 63, node.getData());
        node = node.getNext();
        assertEquals("Incorrect 3rd item in stack", (Integer) 32, node.getData());
        node = node.getNext();
        assertEquals("Incorrect 4th item in stack", (Integer) 84, node.getData());
        node = node.getNext();
        assertNull(node);
    }

    @Test(timeout = TIMEOUT)
    public void test21LinkedListStackPushPop() {
        stack = new LinkedListStack<>();
        assertEquals("Incorrect initial stack size", 0, stack.size());

        stack.push(84);
        stack.push(32);
        stack.push(63);
        stack.push(19);
        assertEquals("Incorrect 1st item in stack", (Integer) 19, stack.pop());

        assertEquals("Incorrect stack size after removal", 3, stack.size());

        LinkedListInterface<Integer> backingList =
            ((LinkedListStack<Integer>) stack).getBackingList();

        LinkedListNode<Integer> node = backingList.getHead();
        assertEquals((Integer) 63, node.getData());
        node = node.getNext();
        assertEquals((Integer) 32, node.getData());
        node = node.getNext();
        assertEquals((Integer) 84, node.getData());
        node = node.getNext();
        assertNull(node);
    }

    @Test(timeout = TIMEOUT)
    public void test22LinkedListStackIsEmpty() {
        stack = new LinkedListStack<>();

        assertTrue("isEmpty should return true for an empty stack", stack.isEmpty());
        stack.push(1);
        assertFalse("isEmpty should return false for an non-empty stack", stack.isEmpty());
        stack.push(1);
        assertFalse("isEmpty should return false for an non-empty stack", stack.isEmpty());
        stack.pop();
        assertFalse("isEmpty should return false for an non-empty stack", stack.isEmpty());
        stack.pop();
        assertTrue("isEmpty should return true for an empty stack", stack.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test23LinkedListStackPushNull() {
        stack = new LinkedListStack<>();
        stack.push(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void test24LinkedListStackPopEmpty() {
        stack = new LinkedListStack<>();
        stack.push(1);
        stack.pop();
        stack.pop();
    }

    @Test(timeout = TIMEOUT)
    public void test25LinkedListQueuePush() {
        queue = new LinkedListQueue<>();
        assertEquals("Incorrect initial queue size", 0, queue.size());

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);

        assertEquals("Incorrect queue size after adding elements", 4, queue.size());

        LinkedListInterface<Integer> backingList =
            ((LinkedListQueue<Integer>) queue).getBackingList();

        LinkedListNode<Integer> node = backingList.getHead();
        assertEquals((Integer) 84, node.getData());
        node = node.getNext();
        assertEquals((Integer) 32, node.getData());
        node = node.getNext();
        assertEquals((Integer) 63, node.getData());
        node = node.getNext();
        assertEquals((Integer) 19, node.getData());
        node = node.getNext();
        assertNull(node);
    }

    @Test(timeout = TIMEOUT)
    public void test26LinkedListQueuePushPop() {
        queue = new LinkedListQueue<>();
        assertEquals("Incorrect initial queue size", 0, queue.size());

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);
        assertEquals((Integer) 84, queue.dequeue());

        assertEquals("Incorrect queue size after add/remove", 3, queue.size());

        LinkedListInterface<Integer> backingList =
            ((LinkedListQueue<Integer>) queue).getBackingList();

        LinkedListNode<Integer> node = backingList.getHead();
        assertEquals((Integer) 32, node.getData());
        node = node.getNext();
        assertEquals((Integer) 63, node.getData());
        node = node.getNext();
        assertEquals((Integer) 19, node.getData());
        node = node.getNext();
        assertNull(node);
    }

    @Test(timeout = TIMEOUT)
    public void test27LinkedListQueueIsEmpty() {
        queue = new LinkedListQueue<>();

        assertTrue("isEmpty should return true for an empty queue", queue.isEmpty());
        queue.enqueue(1);
        assertFalse("isEmpty should return false for an non-empty queue", queue.isEmpty());
        queue.enqueue(1);
        assertFalse("isEmpty should return false for an non-empty queue", queue.isEmpty());
        queue.dequeue();
        assertFalse("isEmpty should return false for an non-empty queue", queue.isEmpty());
        queue.dequeue();
        assertTrue("isEmpty should return true for an empty queue", queue.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test28LinkedListQueueEnqueueNull() {
        queue = new LinkedListQueue<>();
        queue.enqueue(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void test29LinkedListQueueDequeueEmpty() {
        queue = new LinkedListQueue<>();
        queue.enqueue(1);
        queue.dequeue();
        queue.dequeue();
    }
}