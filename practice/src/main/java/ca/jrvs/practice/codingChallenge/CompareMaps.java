package ca.jrvs.practice.codingChallenge;

import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/How-to-compare-two-maps-8bf9269629874880a93c6bd509731120
 */
public class CompareMaps {

    /**
     * Time Complexity: O(n)
     * Justification: In the worst-case scenario, the method would have to go through every entry set
     * in the first map, and check if they are equal with the second map. This would require looping
     * through the collection once, hence the runtime is linear.
     */
    public <K,V> boolean compareMaps1(Map<K,V> m1, Map<K,V> m2){
        return m1.equals(m2);
    }
}
