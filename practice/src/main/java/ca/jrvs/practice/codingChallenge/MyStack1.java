package ca.jrvs.practice.codingChallenge;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Stack-using-Queue-3861ab47a5304fdbb6b0b9871b6583c7
 */
public class MyStack1 {

    Queue<Integer> queue;

    public MyStack1() {
        queue = new ArrayDeque<>();
    }

    public void push(int x) {
        queue.add(x);
    }

    public Integer pop() {
        Queue<Integer> queue2 = new ArrayDeque<>();
        Integer result = null;
        while(!queue.isEmpty()) {
            int item = queue.remove();
            if(queue.isEmpty()) {
                result = item;
            }
            else {
                queue2.add(item);
            }
        }

        while(!queue2.isEmpty()) {
            int item = queue2.remove();
            queue.add(item);
        }

        return result;
    }

    public Integer top() {
        Queue<Integer> queue2 = new ArrayDeque<>();
        Integer result = null;
        while(!queue.isEmpty()) {
            int item = queue.remove();

            if(queue.isEmpty()) {
                result = item;
            }

            queue2.add(item);
        }

        while(!queue2.isEmpty()) {
            int item = queue2.remove();
            queue.add(item);
        }

        return result;
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
