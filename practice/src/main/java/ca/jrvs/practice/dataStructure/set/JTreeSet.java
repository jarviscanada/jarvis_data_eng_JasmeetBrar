package ca.jrvs.practice.dataStructure.set;

import java.util.Comparator;
import java.util.TreeMap;

public class JTreeSet<E> implements JSet<E> {

    private final TreeMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public JTreeSet(Comparator<E> comparator) {
        map = new TreeMap<>(comparator);
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
