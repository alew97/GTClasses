import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test the efficiency of Stack and Queue methods
 *
 * @author Joshua Dwire
 * @version 1.0
 */
public class StacksQueuesJDwireFuzzyBigOTests {

    private StackInterface<Integer> stack;
    private QueueInterface<Integer> queue;

    public static final int TIMEOUT = 60000;

    @Test(timeout = TIMEOUT)
    public void test01ArrayStackPush() {
        stack = new ArrayStack<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (stack.size() < count - 2) {
                stack.push(1000);
            }
            System.gc();

            long start = System.nanoTime();
            stack.push(1000);
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test02ArrayStackPop() {
        stack = new ArrayStack<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (stack.size() < count) {
                stack.push(1000);
            }
            System.gc();

            long start = System.nanoTime();
            stack.pop();
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test03ArrayQueueEnqueue() {
        queue = new ArrayQueue<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (queue.size() < count - 2) {
                queue.enqueue(1000);
            }
            System.gc();

            long start = System.nanoTime();
            queue.enqueue(1000);
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test04ArrayQueueDequeue() {
        queue = new ArrayQueue<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (queue.size() < count) {
                queue.enqueue(1000);
            }
            System.gc();

            long start = System.nanoTime();
            queue.dequeue();
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test05LinkedListStackPush() {
        stack = new LinkedListStack<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (stack.size() < count - 2) {
                stack.push(1000);
            }
            System.gc();

            long start = System.nanoTime();
            stack.push(1000);
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test06LinkedListStackPop() {
        stack = new LinkedListStack<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (stack.size() < count) {
                stack.push(1000);
            }
            System.gc();

            long start = System.nanoTime();
            stack.pop();
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test07LinkedListQueueEnqueue() {
        queue = new LinkedListQueue<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (queue.size() < count - 2) {
                queue.enqueue(1000);
            }
            System.gc();

            long start = System.nanoTime();
            queue.enqueue(1000);
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    @Test(timeout = TIMEOUT)
    public void test08LinkedListQueueDequeue() {
        queue = new LinkedListQueue<>();

        long[] durations = new long[6];

        for (int count = 1, i = 0; count <= 100000; count *= 10, i++) {

            while (queue.size() < count) {
                queue.enqueue(1000);
            }
            System.gc();

            long start = System.nanoTime();
            queue.dequeue();
            long duration = System.nanoTime() - start;
            durations[i] = duration;
        }

        assertTrue("This doesn't seem to be O(1).", getIsO1(durations));
    }

    /**
     * Tries to detect if the values represent an O(1) running time
     *
     * @param values Must be an even number of ints
     * @return True if the average of the second half is less than three times the average of the first half
     */
    protected boolean getIsO1(long[] values) {
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("Needs to be an even number of durations");
        }

        long firstSum = 0;
        long secondSum = 0;

        for (int i = 0; i < values.length; i++) {
            if (i < values.length / 2) {
                firstSum += values[i];
            } else {
                secondSum += values[i];
            }
        }

        double firstAvg = firstSum / (values.length / 2);
        double secondAvg = secondSum / (values.length / 2);

        return secondAvg < 3 * firstAvg;
    }
}