import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Basic tests for the stack and queue classes.
 *
 * @author CS 1332 TAs
 * @version 1.1
 */
public class StacksQueuesStudentTests {

    private StackInterface<Integer> stack;
    private QueueInterface<Integer> queue;

    public static final int TIMEOUT = 200;

    @Test(timeout = TIMEOUT)
    public void testArrayStackPush() {
        stack = new ArrayStack<>();
        assertEquals(0, stack.size());

        stack.push(34);
        stack.push(29);
        stack.push(48);
        stack.push(59);

        assertEquals(4, stack.size());

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        Object[] expected = new Object[10];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayStackPop() {
        stack = new ArrayStack<>();
        assertEquals(0, stack.size());

        stack.push(34);
        stack.push(29);
        stack.push(48);
        stack.push(59);
        assertEquals((Integer) 59, stack.pop());

        assertEquals(3, stack.size());

        Object[] backingArray = ((ArrayStack<Integer>) stack).getBackingArray();

        Object[] expected = new Object[10];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueuePush() {
        queue = new ArrayQueue<>();
        assertEquals(0, queue.size());

        queue.enqueue(34);
        queue.enqueue(29);
        queue.enqueue(48);
        queue.enqueue(59);

        assertEquals(4, queue.size());

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        Object[] expected = new Object[10];
        expected[0] = 34;
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void testArrayQueuePop() {
        queue = new ArrayQueue<>();
        assertEquals(0, queue.size());

        queue.enqueue(34);
        queue.enqueue(29);
        queue.enqueue(48);
        queue.enqueue(59);
        assertEquals((Integer) 34, queue.dequeue());

        assertEquals(3, queue.size());

        Object[] backingArray = ((ArrayQueue<Integer>) queue).getBackingArray();

        Object[] expected = new Object[10];
        expected[1] = 29;
        expected[2] = 48;
        expected[3] = 59;

        assertArrayEquals(expected, backingArray);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedListStackPush() {
        stack = new LinkedListStack<>();
        assertEquals(0, stack.size());

        stack.push(84);
        stack.push(32);
        stack.push(63);
        stack.push(19);

        assertEquals(4, stack.size());

        LinkedListInterface<Integer> backingList =
            ((LinkedListStack<Integer>) stack).getBackingList();

        LinkedListNode<Integer> node = backingList.getHead();
        assertEquals((Integer) 19, node.getData());
        node = node.getNext();
        assertEquals((Integer) 63, node.getData());
        node = node.getNext();
        assertEquals((Integer) 32, node.getData());
        node = node.getNext();
        assertEquals((Integer) 84, node.getData());
        node = node.getNext();
        assertNull(node);
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedListStackPushPop() {
        stack = new LinkedListStack<>();
        assertEquals(0, stack.size());

        stack.push(84);
        stack.push(32);
        stack.push(63);
        stack.push(19);
        assertEquals((Integer) 19, stack.pop());

        assertEquals(3, stack.size());

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
    public void testLinkedListQueuePush() {
        queue = new LinkedListQueue<>();
        assertEquals(0, queue.size());

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);

        assertEquals(4, queue.size());

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
    public void testLinkedListQueuePushPop() {
        queue = new LinkedListQueue<>();
        assertEquals(0, queue.size());

        queue.enqueue(84);
        queue.enqueue(32);
        queue.enqueue(63);
        queue.enqueue(19);
        assertEquals((Integer) 84, queue.dequeue());

        assertEquals(3, queue.size());

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
    public void testArrayQueueResize() {
        queue = new ArrayQueue<>();
        assertEquals(0, queue.size());

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

        Object[] expected = new Object[20];
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

        assertArrayEquals(expected, backingArray);
    }
}
