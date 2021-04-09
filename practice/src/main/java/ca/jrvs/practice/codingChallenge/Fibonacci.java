package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-933a5eeebe96472e8f68c102a93c8bc3
 */
public class Fibonacci {

    /**
     * Time Complexity: O(2^n)
     * Justification: In the worst case scenario, every call to fib1 would
     * produce 2 recursive calls to fib1, and this would repeat until the
     * calls would reach the trivial case. The total number of calls to fib1
     * doubles when going from n to (n + 1) case, so the number of calls is
     * in O(2^n). And the work done in each call excluding the recursive call
     * is O(1). Altogether it would give us the worst-case time complexity of
     * O(2^n).
     *
     */
    public int fib1(int n) {
        if(n < 2) {
            return n;
        }

        return fib1(n - 1) + fib1(n - 2);
    }
}
