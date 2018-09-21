import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BSTUtilitiesJDwireTests {
    private static final int TIMEOUT = 200;
    private BSTUtilities utilities;

    @Before
    public void setup() {
    }

    @Test(timeout = TIMEOUT)
    public void test01DefaultConstructor() {
        utilities = new BSTUtilities();
        assertNull("Root of empty tree should be null", utilities.getRoot());
        assertEquals("Sum of empty tree should be 0", 0, utilities.dataSum());
        assertEquals("Size of empty tree should be 0", 0, utilities.size());
        assertEquals("zigAdd of empty tree should be 0", 0, utilities.zigAdd());
        assertEquals("baseMultiply of empty tree should be 0", 0, utilities.baseMultiply());
        assertEquals("thresholdCount of empty tree should be 0", 0, utilities.thresholdCount(-1));
        assertEquals("sumEvenLevels of empty tree should be 0", 0, utilities.sumEvenLevels());
        assertNull("min of empty tree should be null", utilities.getMin());
        assertNull("max of empty tree should be null", utilities.getMax());
        assertEquals("Height of empty tree should be -1", -1, utilities.height());
        assertEquals("toString of empty tree should be \"[]\"", "[]", utilities.toString());
    }

    @Test(timeout = TIMEOUT)
    public void test02Constructor2() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177});

        /*
         *                   199
         *                   / \
         *                  /   \
         *                 /     \
         *                63    624
         *                 \     /
         *                177  537
         */

        assertEquals("Unexpected node value", 199, utilities.getRoot().getData());

        assertEquals("Unexpected node value", 63, utilities.getRoot().getLeft().getData());
        assertEquals("Unexpected node value", 624, utilities.getRoot().getRight().getData());

        assertNull("Unexpected node value", utilities.getRoot().getLeft().getLeft());
        assertEquals("Unexpected node value", 177, utilities.getRoot().getLeft().getRight().getData());
        assertEquals("Unexpected node value", 537, utilities.getRoot().getRight().getLeft().getData());
        assertNull("Unexpected node value", utilities.getRoot().getRight().getRight());

        assertNull("Unexpected node value", utilities.getRoot().getLeft().getRight().getLeft());
        assertNull("Unexpected node value", utilities.getRoot().getLeft().getRight().getRight());
        assertNull("Unexpected node value", utilities.getRoot().getRight().getLeft().getLeft());
        assertNull("Unexpected node value", utilities.getRoot().getRight().getLeft().getRight());
    }

    @Test(timeout = TIMEOUT)
    public void test03Constructor3() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177}, 3);
        /*
         *                   199
         *                   / \
         *                  /   \
         *                 63  624
         */

        assertEquals("Unexpected node value", 199, utilities.getRoot().getData());

        assertEquals("Unexpected node value", 63, utilities.getRoot().getLeft().getData());
        assertEquals("Unexpected node value", 624, utilities.getRoot().getRight().getData());

        assertNull("Unexpected node value", utilities.getRoot().getLeft().getLeft());
        assertNull("Unexpected node value", utilities.getRoot().getLeft().getRight());
        assertNull("Unexpected node value", utilities.getRoot().getRight().getLeft());
        assertNull("Unexpected node value", utilities.getRoot().getRight().getRight());
    }

    @Test(timeout = TIMEOUT)
    public void test04Constructor4() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177}, 1, 4);
        /*
         *                   624
         *                  /
         *                 63
         *                  \
         *                  537
         */

        assertEquals("Unexpected node value", 624, utilities.getRoot().getData());

        assertEquals("Unexpected node value", 63, utilities.getRoot().getLeft().getData());
        assertNull("Unexpected node value", utilities.getRoot().getRight());

        assertNull("Unexpected node value", utilities.getRoot().getRight());
        assertEquals("Unexpected node value", 537, utilities.getRoot().getLeft().getRight().getData());

        assertNull("Unexpected node value", utilities.getRoot().getLeft().getRight().getLeft());
        assertNull("Unexpected node value", utilities.getRoot().getLeft().getRight().getRight());
    }

    @Test(timeout = TIMEOUT, expected = ArrayIndexOutOfBoundsException.class)
    public void test05Constructor4StartIndexOutOfBounds() {
        utilities = new BSTUtilities(new int[] {199}, -1, 0);
    }

    @Test(timeout = TIMEOUT, expected = ArrayIndexOutOfBoundsException.class)
    public void test06Constructor4EndIndexOutOfBounds() {
        utilities = new BSTUtilities(new int[] {199, 300}, 0, 3);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void test07Constructor4OverlapIndex() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177}, 2, 0);
    }

    @Test(timeout = TIMEOUT)
    public void test08DataSum() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177});
        assertEquals(1600, utilities.dataSum());
    }

    @Test(timeout = TIMEOUT)
    public void test09Size() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177});
        assertEquals(5, utilities.size());
    }

    @Test(timeout = TIMEOUT)
    public void test10ZigAdd() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177});
        assertEquals(-1076, utilities.zigAdd());
    }

    @Test(timeout = TIMEOUT)
    public void test11BaseMultiply() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177});
        assertEquals(34933, utilities.baseMultiply());
    }

    @Test(timeout = TIMEOUT)
    public void test12ThresholdCount() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});
        /*
         *                    199
         *                   /  \
         *                  /    \
         *                63     624
         *                / \     /
         *               /   \   /
         *             15  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *              113     550
         */
        assertEquals(8, utilities.thresholdCount(65));
    }

    @Test(timeout = TIMEOUT)
    public void test13ThresholdCountEfficiency() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});

        // This makes the structure of the tree incorrect,
        // but if this value is found, the method isn't O(log(n)).
        utilities.getRoot().getLeft().getLeft().setData(69);

        /*
         *                    199
         *                   /  \
         *                  /    \
         *                63     624
         *                / \     /
         *               /   \   /
         *              69  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *              113     550
         */
        assertEquals(
                "Make sure you're not checking parts of the tree you don't need to.",
                8, utilities.thresholdCount(65)
        );
    }

    @Test(timeout = TIMEOUT)
    public void test14GetMin() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});
        /*
         *               /    199
         *              /    /  \
         *             /    /    \
         *            /   63     624
         *           /   / \     /
         *          /   /   \   /
         *          -> 15  177  537
         *                   /    \
         *                  /      \
         *                120      611
         *                 /        /
         *                /        /
         *              113      550
         */
        assertEquals((Integer) 15, utilities.getMin());
    }

    @Test(timeout = TIMEOUT)
    public void test15GetMinEfficiency() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});

        // This makes the structure of the tree incorrect,
        // but if this value is found, the method isn't O(log(n)).
        utilities.getRoot().getRight().setData(1);

        /*
         *                    199
         *                   /  \
         *                  /    \
         *                63     1
         *                / \     /
         *               /   \   /
         *              15  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *              113     550
         */
        assertEquals(
                "Make sure you're not checking parts of the tree you don't need to.",
                new Integer(15), utilities.getMin()
        );
    }

    @Test(timeout = TIMEOUT)
    public void test16GetMax() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});
        /*
         *                    199    \
         *                   /  \     \
         *                  /    \     \
         *                63     624 <-
         *                / \     /
         *               /   \   /
         *             15  177  537
         *                   /    \
         *                  /      \
         *                120      611
         *                 /        /
         *                /        /
         *              113      550
         */

        assertEquals((Integer) 624, utilities.getMax());
    }

    @Test(timeout = TIMEOUT)
    public void test17GetMaxEfficiency() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});

        // This makes the structure of the tree incorrect,
        // but if this value is found, the method isn't O(log(n)).
        utilities.getRoot().getLeft().setData(9999);

        /*
         *                    199
         *                   /  \
         *                  /    \
         *               9999    624
         *                / \     /
         *               /   \   /
         *              15  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *              113     550
         */
        assertEquals(
                "Make sure you're not checking parts of the tree you don't need to.",
                new Integer(624), utilities.getMax()
        );
    }

    @Test(timeout = TIMEOUT)
    public void test18Height() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});
        /*
         *                    199
         *                   /  \
         *                  /    \
         *                63     624
         *                / \     /
         *               /   \   /
         *             15  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *              113     550
         */

        assertEquals(4, utilities.height());
    }

    @Test(timeout = TIMEOUT)
    public void test19ToString() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});
        /*
         *                    199
         *                   /  \
         *                  /    \
         *                63     624
         *                / \     /
         *               /   \   /
         *             15  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *              113     550
         */

        assertEquals("[199, [63, [15, [], []], [177, [120, [113, [], []], []], []]], [624, [537, [], [611, [550, [], []], []]], []]]",
                utilities.toString());
    }

    @Test(timeout = TIMEOUT)
    public void test20SumEvenLevels() {
        utilities = new BSTUtilities(new int[] {199, 624, 63, 537, 177, 15, 120, 611, 550, 113});
        /*
         *    -->             199
         *                   /  \
         *                  /    \
         *                63     624
         *                / \     /
         *               /   \   /
         *    -->      15  177  537
         *                   /    \
         *                  /     \
         *                120    611
         *                 /      /
         *                /      /
         *    -->       113     550
         */

        assertEquals(1591, utilities.sumEvenLevels());
    }
}