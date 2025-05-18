import java.util.List;
/**
 * SelfBalanceTreeInterface.java
 * 
 * This interface defines the basic operations for a self-balancing tree data structure.
 * It includes methods for inserting, deleting, searching for elements, and traversing the tree.
 * 
 * @param <T> The type of elements stored in the tree, which must be comparable.
 */

public interface SelfBalanceTreeInterface<T extends Comparable<T>> {
    boolean insert(T value);
    boolean delete(T value);
    boolean search(T value);
    int size();
    int height();
    List<T> traverseInOrder();
    List<T> traversePreOrder();
    List<T> traversePostOrder();
}