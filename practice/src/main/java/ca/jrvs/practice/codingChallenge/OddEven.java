package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-001fa31c48a74d1282f60b61613fe149
 */
public class OddEven {

    /**
     * Time Complexity: O(1)
     * Justification: It performs just some simple arithmetic operation on one value
     */
    public String oddEvenMod(int i) {
        if(i % 2 == 0) {
            return "even";
        }
        else {
            return "odd";
        }
    }

    /**
     * Time Complexity: O(1)
     * Justification: It performs just some simple bitwise operation on one value
     */
    public String oddEvenBit(int i) {
        return (i & 1) == 0 ? "even" : "odd";
    }

}
