package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-bf81ad1d3dcd48599ad07dedbfb032c6
 */
public class MyQueue2 {
    Stack<Integer> stack;
    Stack<Integer> stack2;

    Boolean popForFirstStack = true;

    public MyQueue2() {
        stack = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        Stack<Integer> currentStack = stack.size() <= stack2.size() ? stack : stack2;

        if(currentStack.empty()) {
            currentStack.push(x);
        }
        else {
            int lastItem = currentStack.pop();
            currentStack.push(x);
            currentStack.push(lastItem);
        }
    }

    public int pop() {
        Stack<Integer> currentStack = popForFirstStack ? stack : stack2;
        int result = currentStack.pop();

        if(currentStack.size() >= 2) {
            int lastItem = currentStack.pop();
            int lastLastItem = currentStack.pop();
            currentStack.push(lastItem);
            currentStack.push(lastLastItem);
        }

        popForFirstStack = stack2.empty() || !popForFirstStack;
        return result;
    }

    public int peek() {
        Stack<Integer> currentStack = popForFirstStack ? stack : stack2;
        return currentStack.peek();
    }

    public boolean empty() {
        return stack.empty();
    }
}
