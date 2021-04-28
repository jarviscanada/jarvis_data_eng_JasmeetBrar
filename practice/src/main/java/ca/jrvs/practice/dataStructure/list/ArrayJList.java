package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ArrayJList<E> implements JList<E> {

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer.
     */
    transient Object[] elementData; // non-private to simplify nested class access
    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ArrayJList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayJList() {
        this(DEFAULT_CAPACITY);
    }


    /**
     * Appends the specified element to the end of this list (optional
     * operation).
     *
     * Double elementData size if elementData is full.
     */
    @Override
    public boolean add(E e) {
        if(size == elementData.length) {
            elementData = Arrays.copyOf(elementData, 2 * size);
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public Object[] toArray() {
        return elementData;
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
        OptionalInt result = IntStream.range(0, size).filter(i -> elementData[i] == o).findFirst();
        return result.orElse(-1);
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public E get(int index) {
        if(size == 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }

        return (E) elementData[index];
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        if(index >= size) {
            throw new IndexOutOfBoundsException("Index is out of range");
        }

        E item = (E) elementData[index];
        size--;
        Object[] newArray = new Object[size];

        System.arraycopy(elementData, 0, newArray, 0, index);
        System.arraycopy(elementData, index + 1, newArray, index, size - index);
        elementData = newArray;

        return item;
    }

    @Override
    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }
}
