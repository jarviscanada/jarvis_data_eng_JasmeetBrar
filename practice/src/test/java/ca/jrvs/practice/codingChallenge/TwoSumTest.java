package ca.jrvs.practice.codingChallenge;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumTest {

    private static TwoSum twoSum;

    private static int[] nums1;
    private static int target1;

    private static int[] nums2;
    private static int target2;

    private static int[] nums3;
    private static int target3;

    @BeforeClass
    public static void beforeClass() throws Exception {
        twoSum = new TwoSum();
        nums1 = new int[]{2,7,11,15};
        target1 = 9;

        nums2 = new int[]{3,2,4};
        target2 = 6;

        nums3 = new int[]{3,3};
        target3 = 6;
    }

    @Test
    public void twoSum1() {
        int[] result1 = twoSum.twoSum1(nums1, target1);

        assertEquals(result1[0], 0);
        assertEquals(result1[1], 1);

        int[] result2 = twoSum.twoSum1(nums2, target2);
        assertEquals(result2[0], 1);
        assertEquals(result2[1], 2);

        int[] result3 = twoSum.twoSum1(nums3, target3);
        assertEquals(result3[0], 0);
        assertEquals(result3[1], 1);
    }
}