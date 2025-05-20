    

public class RedBlackTree implements SelfBalanceTreeInterface {
    // Red-Black Tree properties
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        String value;
        boolean color;
        Node left, right, parent;

        Node(String value) {
            this.value = value;
            this.color = RED; // New nodes are always red
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root;
    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean insert(String value){
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            
        } else {
            Node parent = null;
            Node current = root;

            while (current != null) {
                parent = current;
                if (newNode.value.compareTo(current.value) < 0) {
                    current = current.left;
                } else if (newNode.value.compareTo(current.value) > 0) {
                    current = current.right;
                }else {
                    return false; // Duplicate value
                }
            }

            newNode.parent = parent;

            if (newNode.value.compareTo(parent.value) < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }

        }
        fixViolation(newNode);
        size++;
        return true;
    }

    @Override
    public boolean delete(String value){
        Node nodeToDelete = searchNode(root, value);
        if (nodeToDelete == null) {
            return false; // Value not found
        }
        deleteNode(nodeToDelete);
        size--;
        return true;
    }
    @Override
    public boolean search(String value){
        return searchNode(root, value) != null;
    }
    @Override
    public void traverseInOrder(){
        inOrderTraversal(root);
        System.out.println();
        }
    @Override
    public void traversePreOrder(){
        preOrderTraversal(root);
        System.out.println();
        }
    @Override
    public void traversePostOrder(){
        postOrderTraversal(root);
        System.out.println();
    }

    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }
    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }
    private void fixViolation(Node node) {
        Node parent = null;
        Node grandParent = null;

        while (node != root && node.color == RED && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;

                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateRight(grandParent);
                    boolean tempColor = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = tempColor;
                    node = parent;
                }
            } else {
                Node uncle = grandParent.left;

                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }
                    rotateLeft(grandParent);
                    boolean tempColor = parent.color;
                    parent.color = grandParent.color;
                    grandParent.color = tempColor;
                    node = parent;
                }
            }
        }
        root.color = BLACK; // Ensure the root is always black  
}
    private Node searchNode(Node node, String value) {
        if (node == null || node.value.equals(value)) {
            return node;
        }
        if (value.compareTo(node.value) < 0) {
            return searchNode(node.left, value);
        }
        return searchNode(node.right, value);
    }

    private void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.value + " ");
            inOrderTraversal(node.right);
        }
    }
    private void preOrderTraversal(Node node) {
        if (node != null) {
            System.out.print(node.value + " ");
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }
    private Node getMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    // private Node getMaximum(Node node) {
    //     while (node.right != null) {
    //         node = node.right;
    //     }
    //     return node;
    // }
    
    private void postOrderTraversal(Node node) {
        if (node != null) {
            postOrderTraversal(node.left);
            postOrderTraversal(node.right);
            System.out.print(node.value + " ");
        }
    }
   private void deleteNode(Node node) {
        
        Node replacement = null;
        Node child = null;

        if (node.left != null && node.right != null) {
            replacement = getMinimum(node.right);
            node.value = replacement.value;
            node = replacement;
        }

        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }

        if (child != null) {
            child.parent = node.parent;
        }

        if (node.parent == null) {
            root = child;
        } else if (node == node.parent.left) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }

        if (node.color == BLACK) {
            fixAfterDeletion(child);
        }
    }
    private void fixAfterDeletion(Node node) {
        while (node != root && node.color == BLACK) {
            if (node == node.parent.left) {
                Node sibling = node.parent.right;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rotateLeft(node.parent);
                    sibling = node.parent.right;
                }
                if (sibling.left.color == BLACK && sibling.right.color == BLACK) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.right.color == BLACK) {
                        sibling.left.color = BLACK;
                        sibling.color = RED;
                        rotateRight(sibling);
                        sibling = node.parent.right;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    sibling.right.color = BLACK;
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                Node sibling = node.parent.left;
                if (sibling.color == RED) {
                    sibling.color = BLACK;
                    node.parent.color = RED;
                    rotateRight(node.parent);
                    sibling = node.parent.left;
                }
                if (sibling.right.color == BLACK && sibling.left.color == BLACK) {
                    sibling.color = RED;
                    node = node.parent;
                } else {
                    if (sibling.left.color == BLACK) {
                        sibling.right.color = BLACK;
                        sibling.color = RED;
                        rotateLeft(sibling);
                        sibling = node.parent.left;
                    }
                    sibling.color = node.parent.color;
                    node.parent.color = BLACK;
                    sibling.left.color = BLACK;
                    rotateRight(node.parent);
                    node = root;
                }
            }
        }
        node.color = BLACK;
    }
    public int getSize() {
        return size;
    }
    public Node getRoot() {
        return root;
    }
    public int getHeight() {
        return getHeight(root);
    }
    public int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }
}
