package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ValidAnagram {

    public boolean isAnagram1(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }

        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();

        Arrays.sort(sChars);
        Arrays.sort(tChars);

        return Arrays.equals(sChars, tChars);
    }

    public boolean isAnagram2(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> characterCount = new HashMap<>();

        for(Character c: s.toCharArray()) {
            if(characterCount.containsKey(c)) {
                characterCount.put(c, characterCount.get(c) + 1);
            }
            else {
                characterCount.put(c, 1);
            }
        }

        for(Character c: t.toCharArray()) {
            if(characterCount.containsKey(c)) {
                characterCount.put(c, characterCount.get(c) - 1);
            }
            else {
                return false;
            }
        }

        return characterCount.values().stream().reduce(0, Integer::sum) == 0;
    }
}
