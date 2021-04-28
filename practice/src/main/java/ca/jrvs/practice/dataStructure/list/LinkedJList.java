package ca.jrvs.practice.dataStructure.list;

public class LinkedJList<E> implements JList<E> {

    private Node<E> head = null;
    int size = 0;

    public LinkedJList() {

    }

    @Override
    public boolean add(E e) {
        if(head == null) {
            head = new Node<>(e, null, null);
        }
        else {
            Node<E> parent = head;

            while(parent.next != null) {
                parent = parent.next;
            }

            parent.next = new Node<>(e, parent, null);
        }

        size++;
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];

        if(size == 0) {
            return arr;
        }

        Node<E> curr = head;

        for(int i = 0; i < size; i++) {
            arr[i] = curr.item;
            curr = curr.next;
        }

        return arr;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int indexOf(Object o) {
        if(size == 0) {
            return -1;
        }

        Node<E> curr = head;

        for(int i = 0; i < size; i++) {
            if(curr.item == o) {
                return i;
            }
            curr = curr.next;
        }

        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public E get(int index) {
        return getNode(index).item;
    }

    @Override
    public E remove(int index) {
        Node<E> node = getNode(index);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.item;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    private Node<E> getNode(int index) {
        if(size == 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range");
        }

        Node<E> curr = head;

        for(int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr;
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


