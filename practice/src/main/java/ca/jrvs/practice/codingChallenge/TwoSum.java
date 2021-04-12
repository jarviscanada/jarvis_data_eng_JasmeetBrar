package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Two-Sum-c7391b288e244e0fb91701eb7c5b87f9
 */
public class TwoSum {

    /**
     * Time Complexity: O(n^2)
     * Justification: Two loops are used in this implementation, where
     * we are iterating over each number, and then for each number,
     * iterate through the rest of the list to find a valid match. Thus
     * the time complexity is O(n^2).
     */
    public int[] twoSum1(int[] nums, int target) {
        for(int i = 0; i < nums.length - 1; i++) {
            for(int j = i + 1; j < nums.length; j++) {
                if(nums[i] + nums[j] == target) {
                    return new int[]{i ,j};
                }
            }
        }

        return null;
    }
}
