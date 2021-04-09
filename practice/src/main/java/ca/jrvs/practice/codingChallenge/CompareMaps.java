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

    /**
     * Time Complexity: O(n)
     * Justification: It is the same in the first method; the worst case scenario after checking the
     * trivial cases is to check every single key-value pairs in the first map and see if they match
     * with the second. This would require us to visit each key-value pair once, hence the runtime is
     * linear.
     */
    public <K,V> boolean compareMaps2(Map<K,V> m1, Map<K,V> m2){
        if(m1 == m2) {
            return true;
        }

        if(m1.size() != m2.size()) {
            return false;
        }

        for(K key: m1.keySet()) {
            if(!m1.get(key).equals(m2.get(key))) {
                return false;
            }
        }

        return true;
    }
}
