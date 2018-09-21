import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * SSTests
 *
 * @author Shashank Singh
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SSTests {

    //region #VARIABLES
    private BSTUtilities treeUtils;
    public static final int TIMEOUT = 200;
    //endregion

    //region #CONSTRUCTORS
    @Test(timeout = TIMEOUT)
    public void t01ThreeValidArgs() {
        int[] vals = {};
        treeUtils = new BSTUtilities(vals, 0, 0);
        assertEquals(null, treeUtils.getRoot());
        vals = new int[] {1, 2, 3};
        treeUtils = new BSTUtilities(vals, 0, 0);
        assertEquals(null, treeUtils.getRoot());
        treeUtils = new BSTUtilities(vals, 0, 3);
        assertTreeInorder(treeUtils.getRoot(), new int[] {1, 2, 3});
        assertTreePostorder(treeUtils.getRoot(), new int[] {3, 2, 1});
        treeUtils = new BSTUtilities(vals, 1, 2);
        assertTreeInorder(treeUtils.getRoot(), new int[] {2});
        treeUtils = new BSTUtilities(vals, 2, 2);
        assertTreeInorder(treeUtils.getRoot(), new int[] {});
    }

    @Test(timeout = TIMEOUT, expected = ArrayIndexOutOfBoundsException.class)
    public void t02ThreeArgsStartLessThan0() {
        int[] vals = {1, 2, 3};
        treeUtils = new BSTUtilities(vals, -1, 2);
    }

    @Test(timeout = TIMEOUT, expected = ArrayIndexOutOfBoundsException.class)
    public void t03ThreeArgsEndOverLength() {
        int[] vals = {1, 2, 3};
        treeUtils = new BSTUtilities(vals, 0, 4);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void t04ThreeArgsStartOverEnd() {
        int[] vals = {1, 2, 3};
        treeUtils = new BSTUtilities(vals, 2, 1);
    }

    @Test(timeout = TIMEOUT)
    public void t05TwoArgs() {
        int[] vals = {100, 65, 78};
        treeUtils = new BSTUtilities(vals, 2);
        assertTreeInorder(treeUtils.getRoot(), new int[] {65, 100});
        assertTreePreorder(treeUtils.getRoot(), new int[] {100, 65});
    }

    @Test(timeout = TIMEOUT)
    public void t06OneArgValid() {
        int[] vals = {70, 85, 67, 75, 32, 89, 79, 85};
        treeUtils = new BSTUtilities(vals);
        assertTreeInorder(treeUtils.getRoot(),
                new int[] {32, 67, 70, 75, 79, 85, 89});
        assertTreePostorder(treeUtils.getRoot(),
                new int[] {32, 67, 79, 75, 89, 85, 70});
    }

    @Test(timeout = TIMEOUT)
    public void t07NoArgs() {
        treeUtils = new BSTUtilities();
        assertEquals(null, treeUtils.getRoot());
    }
    //endregion

    //region #METHODS
    @Test(timeout = TIMEOUT)
    public void t08dataSum() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(0, treeUtils.dataSum());

        // only root
        treeUtils.add(500);
        assertEquals(500, treeUtils.dataSum());

        // 0, 1, and 2 children
        int[] vals = {500, 246, 100, 800, 600, 1008};
        int sum = 0;
        for (int e : vals) {
            sum += e;
        }
        treeUtils = new BSTUtilities(vals);
        assertEquals(sum, treeUtils.dataSum());
    }

    @Test(timeout = TIMEOUT)
    public void t09size() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(0, treeUtils.size());

        // only root
        treeUtils.add(500);
        assertEquals(1, treeUtils.size());

        // 0, 1, and 2 children
        // be careful to only add unique values or change vals.length to
        // a hardcoded value
        int[] vals = {500, 246, 100, 800, 600, 1008};
        treeUtils = new BSTUtilities(vals);
        assertEquals(vals.length, treeUtils.size());
    }

    @Test(timeout = TIMEOUT)
    public void t10zigAdd() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(0, treeUtils.zigAdd());

        // only root
        treeUtils.add(500);
        assertEquals(500, treeUtils.zigAdd());

        // 0, 1, and 2 children
        int[] vals = {500, 246, 100, 800, 600, 1008};
        treeUtils = new BSTUtilities(vals);
        assertEquals(454, treeUtils.zigAdd());
    }

    @Test(timeout = TIMEOUT)
    public void t11baseMultiply() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(0, treeUtils.baseMultiply());

        // only root
        treeUtils.add(500);
        assertEquals(500, treeUtils.baseMultiply());

        // 0, 1, and 2 children
        int[] vals = {500, 246, 100, 800, 600, 1008};
        treeUtils = new BSTUtilities(vals);
        assertEquals(85768, treeUtils.baseMultiply());
    }

    @Test(timeout = TIMEOUT)
    public void t12thresholdCount() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(0, treeUtils.thresholdCount(40));

        // only root
        treeUtils.add(500);
        assertEquals(0, treeUtils.thresholdCount(600));
        assertEquals(0, treeUtils.thresholdCount(500));
        assertEquals(1, treeUtils.thresholdCount(400));

        // complete tree
        int[] vals = {500, 246, 100, 800, 600, 1008, 300, 50, 101, 299, 302,
                      599, 601, 1007, 1009};
        treeUtils = new BSTUtilities(vals);
        assertEquals(15, treeUtils.thresholdCount(10));
        assertEquals(14, treeUtils.thresholdCount(50));
        assertEquals(0, treeUtils.thresholdCount(2000));
        assertEquals(0, treeUtils.thresholdCount(1009));
        assertEquals(3, treeUtils.thresholdCount(900));
        assertEquals(3, treeUtils.thresholdCount(800));
        assertEquals(6, treeUtils.thresholdCount(599));
        assertEquals(7, treeUtils.thresholdCount(598));
        assertEquals(13, treeUtils.thresholdCount(100));
        assertEquals(12, treeUtils.thresholdCount(101));
        assertEquals(11, treeUtils.thresholdCount(246));
        assertEquals(9, treeUtils.thresholdCount(301));
        assertEquals(8, treeUtils.thresholdCount(302));
    }

    @Test(timeout = TIMEOUT)
    public void t13sumEvenLevels() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(0, treeUtils.sumEvenLevels());

        // only root
        treeUtils.add(500);
        assertEquals(500, treeUtils.sumEvenLevels());

        // complete tree
        int[] vals = {500, 246, 100, 800, 600, 1008, 300, 50, 101, 299, 302,
                      599, 601, 1007, 1009};
        treeUtils = new BSTUtilities(vals);
        assertEquals(2508, treeUtils.sumEvenLevels());

        // 2 levels
        vals = new int[] {3, 1, 5};
        treeUtils = new BSTUtilities(vals);
        assertEquals(3, treeUtils.sumEvenLevels());

        // 3 levels
        treeUtils.add(0);
        treeUtils.add(2);
        treeUtils.add(4);
        treeUtils.add(6);
        assertEquals(15, treeUtils.sumEvenLevels());

        // only left children
        vals = new int[] {100, 99, 98, 97, 96, 95, 94};
        treeUtils = new BSTUtilities(vals);
        assertEquals(388, treeUtils.sumEvenLevels());

        // only right children
        vals = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        treeUtils = new BSTUtilities(vals);
        assertEquals(16, treeUtils.sumEvenLevels());
    }

    @Test(timeout = TIMEOUT)
    public void t14getMin() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(null, treeUtils.getMin());

        // only root
        treeUtils.add(500);
        assertEquals(500, (int) treeUtils.getMin());

        // complete tree
        int[] vals = {500, 246, 100, 800, 600, 1008, 300, 50, 101, 299, 302,
                      599, 601, 1007, 1009};
        treeUtils = new BSTUtilities(vals);
        assertEquals(50, (int) treeUtils.getMin());

        // only left children
        vals = new int[] {100, 99, 98, 97, 96, 95, 94};
        treeUtils = new BSTUtilities(vals);
        assertEquals(94, (int) treeUtils.getMin());

        // only right children
        vals = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        treeUtils = new BSTUtilities(vals);
        assertEquals(1, (int) treeUtils.getMin());
    }

    @Test(timeout = TIMEOUT)
    public void t15getMax() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(null, treeUtils.getMax());

        // only root
        treeUtils.add(500);
        assertEquals(500, (int) treeUtils.getMax());

        // complete tree
        int[] vals = {500, 246, 100, 800, 600, 1008, 300, 50, 101, 299, 302,
                      599, 601, 1007, 1009};
        treeUtils = new BSTUtilities(vals);
        assertEquals(1009, (int) treeUtils.getMax());

        // only left children
        vals = new int[] {100, 99, 98, 97, 96, 95, 94};
        treeUtils = new BSTUtilities(vals);
        assertEquals(100, (int) treeUtils.getMax());

        // only right children
        vals = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
        treeUtils = new BSTUtilities(vals);
        assertEquals(8, (int) treeUtils.getMax());
    }

    @Test(timeout = TIMEOUT)
    public void t16height() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals(-1, treeUtils.height());

        // only root
        treeUtils.add(500);
        assertEquals(0, treeUtils.height());

        // complete tree
        int[] vals = {500, 246, 100, 800, 600, 1008, 300, 50, 101, 299, 302,
                      599, 601, 1007, 1009};
        treeUtils = new BSTUtilities(vals);
        assertEquals(3, treeUtils.height());

        // zig zag
        vals = new int[] {100, 99, 98, 97, 96, 95, 94, 1, 2, 3, 4, 5, 6, 7, 8};
        treeUtils = new BSTUtilities(vals);
        assertEquals(14, treeUtils.height());

        vals = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94};
        treeUtils = new BSTUtilities(vals);
        assertEquals(14, treeUtils.height());
    }

    @Test(timeout = TIMEOUT)
    public void t17toString() {
        // empty tree
        treeUtils = new BSTUtilities();
        assertEquals("[]", treeUtils.toString());

        // only root
        treeUtils.add(500);
        assertEquals("[500, [], []]", treeUtils.toString());

        // complete tree
        int[] vals = {500, 246, 100, 800, 600, 1008, 300, 50, 101, 299, 302,
                      599, 601, 1007, 1009};
        treeUtils = new BSTUtilities(vals);
        assertEquals("[500, [246, [100, [50, [], []], [101, [], []]], [300, "
                + "[299, [], []], [302, [], []]]], [800, [600, [599, [], []],"
                + " [601, [], []]], [1008, [1007, [], []], [1009, [], []]]]]",
                treeUtils.toString());
    }
    //endregion

    //region #HELPER METHODS

    /**
     * Helper method for asserting that tree is correct inorder
     * @param root root of tree
     * @param inorder expected values
     */
    private void assertTreeInorder(BSTNode root, int[] inorder) {
        List<Integer> inorderList = new LinkedList<>();
        listInorder(inorderList, root);
        assertEquals(inorder.length, inorderList.size());
        for (int i : inorder) {
            int j = inorderList.remove(0);
            assertEquals(i, j);
        }
    }

    /**
     * Helper method for converting tree into an inorder list
     * @param list list to add values to
     * @param node node of subtree to list inorder
     */
    private void listInorder(List<Integer> list, BSTNode node) {
        if (node == null) {
            return;
        }
        listInorder(list, node.getLeft());
        list.add(node.getData());
        listInorder(list, node.getRight());
    }

    /**
     * Helper method for asserting that tree is correct preorder
     * @param root root of tree
     * @param preorder expected values
     */
    private void assertTreePreorder(BSTNode root, int[] preorder) {
        List<Integer> preorderList = new LinkedList<>();
        listPreorder(preorderList, root);
        assertEquals(preorder.length, preorderList.size());
        for (int i : preorder) {
            int j = preorderList.remove(0);
            assertEquals(i, j);
        }
    }

    /**
     * Helper method for converting tree into a preorder list
     * @param list list to add values to
     * @param node node of subtree to list preorder
     */
    private void listPreorder(List<Integer> list, BSTNode node) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        listPreorder(list, node.getLeft());
        listPreorder(list, node.getRight());
    }

    /**
     * Helper method for asserting that tree is correct postorder
     * @param root root of tree
     * @param postorder expected values
     */
    private void assertTreePostorder(BSTNode root, int[] postorder) {
        List<Integer> postorderList = new LinkedList<>();
        listPostorder(postorderList, root);
        assertEquals(postorder.length, postorderList.size());
        for (int i : postorder) {
            int j = postorderList.remove(0);
            assertEquals(i, j);
        }
    }

    /**
     * Helper method for converting tree into a postorder list
     * @param list list to add values to
     * @param node node of subtree to list postorder
     */
    private void listPostorder(List<Integer> list, BSTNode node) {
        if (node == null) {
            return;
        }
        listPostorder(list, node.getLeft());
        listPostorder(list, node.getRight());
        list.add(node.getData());
    }
    //endregion
}