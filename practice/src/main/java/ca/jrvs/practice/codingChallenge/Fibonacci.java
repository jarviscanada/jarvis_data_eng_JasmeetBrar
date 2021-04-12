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

    /**
     * Time Complexity: O(n)
     * Justification: We are using dynamic programming along with the bottom-up
     * approach, where we start off with our base case and build our way up till
     * the nth Fibonacci number. Since we are just simply going through each case
     * once without needing to duplicate any work, the time complexity is O(n).
     */
    public int fib2(int n) {
        if(n < 2) {
            return n;
        }

        int first = 0;
        int second = 1;

        for(int i = 2; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }

        return second;
    }
}
