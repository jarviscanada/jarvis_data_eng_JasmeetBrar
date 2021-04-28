package ca.jrvs.practice.dataStructure.stackQueue;

import java.util.NoSuchElementException;

/**
 *
 * This is a simplified version of java.util.Stack (legacy)
 *
 * @param <E> the type of elements held in this collection
 */
public interface JStack<E> {

    /**
     * Pops an element from the stack represented by this deque. In other
     * words, removes and returns the first element of this deque.
     *
     * @return the element at the front of this deque (which is the top
     *         of the stack represented by this deque)
     * @throws NoSuchElementException if this deque is empty
     */
    E pop();

    /**
     * Pushes an element onto the stack represented by this deque (in other
     * words, at the head of this deque) if it is possible to do so
     * immediately without violating capacity restrictions
     *
     * @param e the element to push
     * @throws NullPointerException if the specified element is null and this
     *         deque does not permit null elements
     */
    void push(E e);


    /**
     * Retrieves, but does not remove, the head of the queue represented by
     * this deque (in other words, the first element of this deque), or
     * returns {@code null} if this deque is empty.
     *
     * @return the head of the queue represented by this deque, or
     *         {@code null} if this deque is empty
     */
    E peek();

}
