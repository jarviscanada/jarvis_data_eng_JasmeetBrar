package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-bf81ad1d3dcd48599ad07dedbfb032c6
 */
public class MyQueue1 {

    Stack<Integer> stack;

    public MyQueue1() {
        stack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
    }

    public int pop() {
        Stack<Integer> stack2 = new Stack<Integer>();

        while(!stack.empty()) {
            stack2.push(stack.pop());
        }

        Integer item = stack2.pop();

        while(!stack2.empty()) {
            stack.push(stack2.pop());
        }

        return item;
    }

    public int peek() {
        Stack<Integer> stack2 = new Stack<Integer>();

        while(!stack.empty()) {
            stack2.push(stack.pop());
        }

        Integer item = stack2.peek();

        while(!stack2.empty()) {
            stack.push(stack2.pop());
        }

        return item;
    }

    public boolean empty() {
        return stack.empty();
    }
}
