package ca.jrvs.practice.codingChallenge;

import com.sun.tools.javac.util.List;

import java.util.HashSet;
import java.util.Set;

public class Atoi {

    /**
     * Time Complexity: O(n)
     * Justification: Internally, Integer.parseInt() will at the bare minimum need to read every character of the given
     * String to determine int value of it. Thus it has a linear time complexity.
     */
    public int atoi1(String s) {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException e) {
            throw new RuntimeException("Provided string is not an integer", e);
        }
    }

    /**
     * Time Complexity: O(n)
     * Justification: The method has to loop through every character once to determine the String's int value. Thus it
     * has a linear time complexity.
     */
    public int atoi2(String s) {
        Set<Character> intChars = new HashSet<>(List.of(
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
        ));

        int startIndex = 0;
        boolean isNegative = false;

        if(s.charAt(0) == '+') {
            startIndex = 1;
        }
        else if(s.charAt(0) == '-') {
            isNegative = true;
            startIndex = 1;
        }

        int result = 0;
        int power = 1;

        for(int i = s.length() - 1; i >= startIndex; i--) {
            if(!intChars.contains(s.charAt(i))) {
                throw new RuntimeException("Provided string is not a integer");
            }

            // The decimal value for 0 in ASCII is 48
            result += (((int) s.charAt(i)) - 48) * power;
            power *= 10;
        }

        return isNegative ? -result : result;
    }
}
