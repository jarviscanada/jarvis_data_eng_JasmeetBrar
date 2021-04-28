package ca.jrvs.practice.dataStructure.tree;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A simplified BST implementation
 *
 * @param <E> type of object to be stored
 */
public class JBSTree<E> implements JTree<E> {

    /**
     * The comparator used to maintain order in this tree map
     * Comparator cannot be null
     */
    private Comparator<E> comparator;

    private Node<E> head = null;

    /**
     * Create a new BST
     *
     * @param comparator the comparator that will be used to order this map.
     * @throws IllegalArgumentException if comparator is null
     */
    public JBSTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * Insert an object into the BST.
     * Please review the BST property.
     *
     * @param object item to be inserted
     * @return inserted item
     * @throws IllegalArgumentException if the object already exists
     */
    @Override
    public E insert(E object) {
        if(head == null) {
            head = new Node<>(object, null);
        }
        else {
            Node<E> curr = head;
            Node<E> curr_next;
            int cmp;
            while(true) {
                cmp = comparator.compare(curr.value, object);
                if(cmp < 0) {
                    curr_next = curr.right;
                }
                else {
                    curr_next = curr.left;
                }

                if(curr_next == null) {
                    break;
                }
                else {
                    curr = curr_next;
                }
            }

            Node<E> node = new Node<>(object, curr);

            if(cmp < 0) {
                curr.right = node;
            }
            else {
                curr.left = node;
            }
        }

        return object;
    }

    /**
     * Search and return an object, return null if not found
     *
     * @param object to be found
     * @return the object if exists or null if not
     */
    @Override
    public E search(E object) {
        return findNode(head, object).map(eNode -> eNode.value).orElse(null);
    }

    /**
     * Remove an object from the tree.
     *
     * @param object to be removed
     * @return removed object
     * @throws IllegalArgumentException if the object not exists
     */
    @Override
    public E remove(E object) {
        Optional<Node<E>> node = findNode(head, object);

        if(!node.isPresent()) {
            throw new IllegalArgumentException("Object does not exist");
        }

        Node<E> curr = node.get();

        int cmp = comparator.compare(curr.parent.value, curr.value);

        if(cmp < 0) {
            curr.parent.right = null;
        }
        else {
            curr.parent.left = null;
        }

        return curr.value;
    }

    private Optional<Node<E>> findNode(Node<E> root, E object) {
        if(root == null) {
            return Optional.empty();
        }

        int cmp = comparator.compare(root.value, object);

        if(cmp == 0) {
            return Optional.of(root);
        }
        else if(cmp < 0) {
            return findNode(root.right, object);
        }
        else {
            return findNode(root.left, object);
        }
    }

    /**
     * traverse the tree recursively
     *
     * @return all objects in pre-order
     */
    @Override
    public E[] preOrder() {
        List<E> list = _preOrder(head);
        return (E[]) list.toArray();
    }

    private List<E> _preOrder(Node<E> root) {
        List<E> list = new ArrayList<>();

        if(root == null) {
            return list;
        }

        list.add(root.value);
        list.addAll(_preOrder(root.left));
        list.addAll(_preOrder(root.right));

        return list;
    }

    /**
     * traverse the tree recursively
     *
     * @return all objects in-order
     */
    @Override
    public E[] inOrder() {
        List<E> list = _inOrder(head);
        return (E[]) list.toArray();
    }

    private List<E> _inOrder(Node<E> root) {
        List<E> list = new ArrayList<>();

        if(root == null) {
            return list;
        }

        list.addAll(_preOrder(root.left));
        list.add(root.value);
        list.addAll(_preOrder(root.right));

        return list;
    }

    /**
     * traverse the tree recursively
     *
     * @return all objects pre-order
     */
    @Override
    public E[] postOrder() {
        List<E> list = _postOrder(head);
        return (E[]) list.toArray();
    }

    private List<E> _postOrder(Node<E> root) {
        List<E> list = new ArrayList<>();

        if(root == null) {
            return list;
        }

        list.addAll(_preOrder(root.left));
        list.addAll(_preOrder(root.right));
        list.add(root.value);

        return list;
    }

    /**
     * traverse through the tree and find out the tree height
     * @return height
     * @throws NullPointerException if the BST is empty
     */
    @Override
    public int findHeight() {
        return _findHeight(head);
    }

    private int _findHeight(Node<E> root) {
        if(root == null) {
            return 0;
        }

        return Math.max(1 + _findHeight(root.left), 1 + _findHeight(root.right));
    }

    static final class Node<E> {

        E value;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E value, Node<E> parent) {
            this.value = value;
            this.parent = parent;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return getValue().equals(node.getValue()) &&
                    Objects.equals(getLeft(), node.getLeft()) &&
                    Objects.equals(getRight(), node.getRight()) &&
                    getParent().equals(node.getParent());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getValue(), getLeft(), getRight(), getParent());
        }
    }

}