import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<T extends Comparable<T>> implements SelfBalanceTreeInterface<T> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        T value;
        Node left, right, parent;
        boolean color;

        Node(T value) {
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;
    private int size = 0;

    @Override
    public boolean insert(T value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            root.color = BLACK;
            size++;
            return true;
        }

        Node current = root;
        Node parent = null;
        while (current != null) {
            parent = current;
            int cmp = value.compareTo(current.value);
            if (cmp == 0) return false; // Already exists
            current = (cmp < 0) ? current.left : current.right;
        }

        newNode.parent = parent;
        if (value.compareTo(parent.value) < 0) parent.left = newNode;
        else parent.right = newNode;

        size++;
        fixInsert(newNode);
        return true;
    }

    private void fixInsert(Node node) {
        while (node != root && node.parent.color == RED) {
            Node parent = node.parent;
            Node grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node uncle = grandparent.right;
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        rotateLeft(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    rotateRight(grandparent);
                }
            } else {
                Node uncle = grandparent.left;
                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rotateRight(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    rotateLeft(grandparent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) rightChild.left.parent = node;
        rightChild.parent = node.parent;

        if (node.parent == null) root = rightChild;
        else if (node == node.parent.left) node.parent.left = rightChild;
        else node.parent.right = rightChild;

        rightChild.left = node;
        node.parent = rightChild;
    }

    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) leftChild.right.parent = node;
        leftChild.parent = node.parent;

        if (node.parent == null) root = leftChild;
        else if (node == node.parent.right) node.parent.right = leftChild;
        else node.parent.left = leftChild;

        leftChild.right = node;
        node.parent = leftChild;
    }

    @Override
    public boolean delete(T value) {
        Node node = root;
        while (node != null) {
            int cmp = value.compareTo(node.value);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else break;
        }

        if (node == null) return false;
        deleteNode(node);
        size--;
        return true;
    }

    private void deleteNode(Node node) {
        // TODO: Implement the delete fix-up to maintain Red-Black properties
        if (node.left != null && node.right != null) {
            Node successor = getMinimum(node.right);
            node.value = successor.value;
            node = successor;
        }

        Node replacement = (node.left != null) ? node.left : node.right;

        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) root = replacement;
            else if (node == node.parent.left) node.parent.left = replacement;
            else node.parent.right = replacement;

            if (node.color == BLACK) {
                fixDelete(replacement);
            }
        } else if (node.parent == null) {
            root = null;
        } else {
            if (node.color == BLACK) {
                fixDelete(node);
            }
            if (node.parent != null) {
                if (node == node.parent.left) node.parent.left = null;
                else if (node == node.parent.right) node.parent.right = null;
            }
        }
    }

    private void fixDelete(Node node) {
        while (node != root && node.color == BLACK) {
            if (node == node.parent.left) {
                Node sibling = node.parent.right;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rotateLeft(node.parent);
                    sibling = node.parent.right;
                }

                if ((sibling.left == null || sibling.left.color == BLACK) &&
                    (sibling.right == null || sibling.right.color == BLACK)) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.right == null || sibling.right.color == BLACK) {
                        if (sibling.left != null) sibling.left.color = BLACK;
                        sibling.color = RED;
                        rotateRight(sibling);
                        sibling = node.parent.right;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    if (sibling.right != null) sibling.right.color = BLACK;
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                // Mirror logic for the right child
                // Implement similar logic as above
            }
        }
        node.color = BLACK;
    }

    private Node getMinimum(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    @Override
    public boolean search(T value) {
        Node node = root;
        while (node != null) {
            int cmp = value.compareTo(node.value);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int height() {
        return calculateHeight(root);
    }

    private int calculateHeight(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
    }

    @Override
    public List<T> traverseInOrder() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(Node node, List<T> result) {
        if (node != null) {
            inorderTraversal(node.left, result);
            result.add(node.value);
            inorderTraversal(node.right, result);
        }
    }

    @Override
    public List<T> traversePreOrder() {
        List<T> result = new ArrayList<>();
        preorderTraversal(root, result);
        return result;
    }

    private void preorderTraversal(Node node, List<T> result) {
        if (node != null) {
            result.add(node.value);
            preorderTraversal(node.left, result);
            preorderTraversal(node.right, result);
        }
    }

    @Override
    public List<T> traversePostOrder() {
        List<T> result = new ArrayList<>();
        postorderTraversal(root, result);
        return result;
    }

    private void postorderTraversal(Node node, List<T> result) {
        if (node != null) {
            postorderTraversal(node.left, result);
            postorderTraversal(node.right, result);
            result.add(node.value);
        }
    }
}
