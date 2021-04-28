package ca.jrvs.practice.dataStructure.set;

import java.util.HashMap;

public class JHashSet<E> implements JSet<E> {

    private final HashMap<E, Object> map = new HashMap<>();
    private static final Object PRESENT = new Object();

    public JHashSet() {

    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean contains(E o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        map.put(e, PRESENT);
        return true;
    }

    @Override
    public boolean remove(E o) {
        map.remove(o);
        return true;
    }

    @Override
    public void clear() {
        map.clear();
    }
}
