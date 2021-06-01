package ca.jrvs.practice.codingChallenge;

public class RotateString {

    public boolean rotateString(String s, String goal) {
        return (s + s).contains(goal);
    }
}
