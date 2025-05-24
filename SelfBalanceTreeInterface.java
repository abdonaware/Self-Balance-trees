public interface SelfBalanceTreeInterface<T extends Comparable<T>> {
    boolean insert(T value);
    boolean delete(T value);
    boolean search(T value);
    void traverseInOrder();
    void traversePreOrder();
    void traversePostOrder();
    int getHeight();
    int getSize();
}