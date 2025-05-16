public interface SelfBalanceTreeInterface {
    void insert(int value);
    boolean delete(int value);
    boolean search(int value);
    void traverseInOrder();
    void traversePreOrder();
    void traversePostOrder();
}