import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BSTUtilitiesStudentTests {
    private static final int TIMEOUT = 200;
    private BSTUtilities utilities;
    private int[] data;

    @Before
    public void setup() {
        data = new int[] {199, 624, 63, 537, 177};
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorOverlapIndex() {
        utilities = new BSTUtilities(data, 2, 0);
    }

    @Test(timeout = TIMEOUT)
    public void testDataSum() {
        utilities = new BSTUtilities(data);
        assertEquals(1600, utilities.dataSum());
    }

    @Test(timeout = TIMEOUT)
    public void testSize() {
        utilities = new BSTUtilities(data);
        assertEquals(5, utilities.size());
    }

    @Test(timeout = TIMEOUT)
    public void testZigAdd() {
        utilities = new BSTUtilities(data);
        assertEquals(-1076, utilities.zigAdd());
    }

    @Test(timeout = TIMEOUT)
    public void testBaseMultiply() {
        utilities = new BSTUtilities(data);
        assertEquals(34933, utilities.baseMultiply());
    }

    @Test(timeout = TIMEOUT)
    public void testThresholdCount() {
        utilities = new BSTUtilities(data);
        assertEquals(4, utilities.thresholdCount(65));
    }

    @Test(timeout = TIMEOUT)
    public void testGetMin() {
        utilities = new BSTUtilities(data);
        assertEquals((Integer) 63, utilities.getMin());
    }

    @Test(timeout = TIMEOUT)
    public void testGetMax() {
        utilities = new BSTUtilities(data);
        assertEquals((Integer) 624, utilities.getMax());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        utilities = new BSTUtilities(data);
        assertEquals(2, utilities.height());
    }
    
    @Test(timeout = TIMEOUT)
    public void testToString() {
        utilities = new BSTUtilities(data);
        assertEquals("[199, [63, [], [177, [], []]], [624, [537, [], []], []]]",
                utilities.toString());
    }

    @Test(timeout = TIMEOUT)
    public void testSumEvenLevels() {
        utilities = new BSTUtilities(data);
        assertEquals(913, utilities.sumEvenLevels());
    }
}
