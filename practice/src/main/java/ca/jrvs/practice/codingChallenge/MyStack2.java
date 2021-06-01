package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-3861ab47a5304fdbb6b0b9871b6583c7
 */
public class MyStack2 {
    Queue<Integer> queue;

    public MyStack2() {
        queue = new ArrayDeque<>();
    }

    public void push(int x) {
        queue.add(x);
        int size = queue.size();

        for(int i = 0; i < size - 1; i++) {
            int item = queue.remove();
            queue.add(item);
        }
    }

    public int pop() {
        return queue.remove();
    }

    public Integer top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
