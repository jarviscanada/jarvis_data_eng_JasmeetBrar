package ca.jrvs.practice.dataStructure.tree;

/**
 * Jarvis Tree (JTree)
 *
 * @param <E> the type of the item
 */
public interface JTree<E> {

    /**
     * Insert an object into the tree.
     *
     * @param object item to be inserted
     * @return inserted item
     * @throws IllegalArgumentException if the object already exists
     */
    E insert(E object);

    /**
     * Search and return an object, return null if not found
     *
     * @param object to be found
     * @return the object if exists or null if not
     */
    E search(E object);

    /**
     * Remove an object from the tree.
     *
     * @param object to be removed
     * @return removed object
     * @throws IllegalArgumentException if the object not exists
     */
    E remove(E object);

    /**
     * traverse the tree recursively
     *
     * @return all objects in pre-order
     */
    E[] preOrder();

    /**
     * traverse the tree recursively
     *
     * @return all objects in-order
     */
    E[] inOrder();

    /**
     * traverse the tree recursively
     *
     * @return all objects pre-order
     */
    E[] postOrder();

    /**
     * traverse through the tree and find out the tree height
     * @return height
     * @throws NullPointerException if the BST is empty
     */
    int findHeight();
}