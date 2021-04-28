package ca.jrvs.practice.dataStructure.stackQueue;

import java.util.HashMap;

public class LinkedJListJDeque<E> implements JDeque<E> {

    private Node<E> head = null;
    private int size = 0;

    public LinkedJListJDeque() {

    }

    @Override
    public boolean add(E e) {
        if(head == null) {
            head = new Node<>(e, null, null);
        }
        else {
            Node<E> node = new Node<>(e, null, head);
            head.prev = node;
            head = node;
        }

        size++;
        return true;
    }

    @Override
    public E remove() {
        if(head == null) {
            throw new IllegalStateException("Cannot remove from an empty LinkedList");
        }

        Node<E> node = head;
        head = head.next;
        head.prev = null;
        size--;
        return node.item;
    }

    @Override
    public E pop() {
        Node<E> node = getLastNode();

        if(node == null) {
            throw new IllegalStateException("Cannot pop from an empty LinkedList");
        }

        node.prev.next = null;
        size--;
        return node.item;
    }

    @Override
    public void push(E e) {
        Node<E> node = getLastNode();

        if(node == null) {
            head = new Node<>(e, null, null);
        }
        else {
            node.next = new Node<>(e, node, null);
        }

        size++;
    }

    @Override
    public E peek() {
        Node<E> node = getLastNode();
        return node == null ? null : node.item;
    }

    private Node<E> getLastNode() {
        if(size == 0) {
            return null;
        }

        Node<E> node = head;

        while(node.next != null) {
            node = node.next;
        }

        return node;
    }

    private static class Node<E> {
        E item;
        Node<E> prev;
        Node<E> next;

        public Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
