package ca.jrvs.practice.codingChallenge;

public class Palindrome {

    public boolean isPalindrome1(String s) {
        String cleanedUpString = s.toLowerCase().replaceAll("/[^A-Za-z0-9]/", "");

        int i = 0;
        int j = cleanedUpString.length() - 1;

        while(i <= j) {
            if(cleanedUpString.charAt(i++) != cleanedUpString.charAt(j--)) {
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome2(String s) {
        String cleanedUpString = s.toLowerCase().replaceAll("/[^A-Za-z0-9]/", "");
        return _isPalindrome2(0, cleanedUpString.length() - 1, cleanedUpString);
    }

    private boolean _isPalindrome2(int low, int high, String s) {
        if(low >= high) {
            return true;
        }
        else if(s.charAt(low) != s.charAt(high)) {
            return false;
        }
        else {
            return _isPalindrome2(low + 1, high - 1, s);
        }
    }
}
