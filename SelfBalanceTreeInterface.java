public interface SelfBalanceTreeInterface {
    boolean insert(String value);
    boolean delete(String value);
    boolean search(String value);
    void traverseInOrder();
    void traversePreOrder();
    void traversePostOrder();
    int getHeight();
    int getSize();
}