package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Time Complexity: O(nlogn)
     * Justification: In the very beginning, we decide to take the list and sort it.
     * The fastest sorting algorithm that can sort any arbitrary list of numbers have
     * the worst-case runtime of O(nlogn). Then afterwards, we loop through each number,
     * and then perform a binary search on finding the other number that would add to it
     * in order to get the sum to be result. The runtime complexity of a binary search is
     * O(logn), and in the worst case we have to do it for every number in the list, which
     * would make the time complexity to be O(nlogn) for that portion in the algorithm.
     * Overall, there isn't anything worse than what we have found, thus the time
     * complexity of this algorithm is O(nlogn).
     */
    public int[] twoSum2(int[] nums, int target) {

        List<Integer> numsCopy = Arrays.stream(nums).boxed().collect(Collectors.toList());
        int[] numsClone = nums.clone();
        Arrays.sort(numsClone);

        for(int i = 0; i < numsClone.length - 1; i++) {
            int num = numsClone[i];
            int diff = target - num;

            if(binarySearch(diff, Arrays.copyOfRange(numsClone, i + 1, numsClone.length))) {
                int index1 = numsCopy.indexOf(num);
                int index2 = numsCopy.lastIndexOf(diff);
                return new int[]{index1, index2};
            }
        }

        return null;
    }

    private boolean binarySearch(int num, int[] numArray) {
        int middleIndex = numArray.length / 2;

        if(numArray.length == 0) {
            return false;
        }

        if(numArray[middleIndex] == num) {
            return true;
        }
        else if (num < numArray[middleIndex]) {
            if(middleIndex == 0) {
                return false;
            }
            return binarySearch(num, Arrays.copyOfRange(numArray, 0, middleIndex));
        }
        else {
            return binarySearch(num, Arrays.copyOfRange(numArray, middleIndex + 1, numArray.length));
        }
    }

    /**
     * Time Complexity: O(n)
     * Justification: We loop through the nums array only once, and store previously seen
     * numbers in a hashmap, along with their index. For each iteration of the loop, we check
     * if the required number to add to the current number is in the hashmap, and if it is, then
     * we just return their indices. A key lookup in a hashmap can be done in constant time, which
     * is O(1). And the most amount of work we would ever do is to perform this constant work for
     * all elements in the nums array. Thus the runtime complexity is O(n).
     */
    public int[] twoSum3(int[] nums, int target) {
        // Store previously seen numbers and their indices
        Map<Integer, Integer> numsIndex = new HashMap<>();

        for(int i = 0; i < nums.length; i++) {
            if(numsIndex.containsKey(target - nums[i])) {
                return new int[]{numsIndex.get(target - nums[i]), i};
            }
            numsIndex.put(nums[i], i);
        }
        
        return null;
    }
}
