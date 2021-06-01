package ca.jrvs.practice.codingChallenge;

import java.util.*;

public class ValidParenthesis {

    private static final Map<Character, Character> symbolPairs = new HashMap<>();

    static {
        symbolPairs.put(')', '(');
        symbolPairs.put(']', '[');
        symbolPairs.put('}', '{');
    }

    public boolean isValid(String s) {
        Stack<Character> openingSymbols = new Stack<>();

        for(Character c: s.toCharArray()) {
            switch(c) {
                case '(':
                case '[':
                case '{':
                    openingSymbols.push(c);
                    break;
                case ')':
                case ']':
                case '}':
                    Character requiredSymbol = symbolPairs.get(c);
                    if(openingSymbols.empty() || !openingSymbols.pop().equals(requiredSymbol)) {
                        return false;
                    }
                    break;
            }
        }

        return openingSymbols.empty();
    }
}
