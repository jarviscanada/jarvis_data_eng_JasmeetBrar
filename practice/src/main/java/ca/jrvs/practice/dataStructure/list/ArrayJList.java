package ca.jrvs.practice.dataStructure.list;

public class ArrayJList<E> implements JList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    transient Object[] elementData;

    public ArrayJList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public ArrayJList(int initialCapacity) {
        if(initialCapacity > 0) {
            elementData = new Object[initialCapacity];
        }
        else {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
    }

    @Override
    public boolean add(E e) {
         return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public void clear() {

    }
}
