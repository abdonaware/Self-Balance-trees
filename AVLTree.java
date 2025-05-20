public class AVLTree implements SelfBalanceTreeInterface {
    class Node {
        String  value;
        Node left;
        Node right;
        int height;

        public Node(String  value) {
            this.value = value;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }
    private Node root = null;
    private int size = 0;
   public int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
   public int balance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.right) - height(node.left);
    }
   public void updateHeight(Node node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }
   public Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        updateHeight(y);
        updateHeight(x);
        return x;
    }
   public Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        updateHeight(x);
        updateHeight(y);
        return y;
    }
    // balance=height(right subtree)-height(left subtree)
   public Node balanceTree(Node root) {
        updateHeight(root);
        int balance = balance(root);
        if (balance > 1){ // Right Heavy (balance > 1)
            if (balance(root.right) < 0){//RL
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }else{ //RR
                return leftRotate(root);
            }
        }
        if (balance < -1)//Left Heavy (balance < -1)
        {
            if (balance(root.left) > 0){ //LR
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }else {//LL
                return rightRotate(root);
            }
        }
        return root;
    }
    @Override
    public boolean insert(String value) {
        if (search(value)) return false; // Avoid duplicates
        root = insert(root, value);
        size++;
        return true;
    }
private Node insert(Node current, String value) {
    if (current == null) {
        return new Node(value);
    }
    if (value.compareTo(current.value) < 0) {
        current.left = insert(current.left, value);
    } else if (value.compareTo(current.value) > 0) {
        current.right = insert(current.right, value);
    }
    return balanceTree(current);
 }

    public boolean search(String value){
        return search(root, value) != null;
    }
    private Node search(Node current, String value) {
        if (current == null || value.equals(current.value)) {
            return current;
        }
        if (value.compareTo(current.value) < 0) {
            return search(current.left, value);
        } else {
            return search(current.right, value);   
        }
    }
    private Node getSuccessor(Node root) {
        while (root.left != null)
            root = root.left;
        return root;
    }
    private Node delete(Node root, String key) {
    if (root == null){
        return null;
    }
    if (key.compareTo(root.value) < 0) {
        root.left = delete(root.left, key);
    } else if (key.compareTo(root.value) > 0) {
        root.right = delete(root.right, key);
    } else {
        // Node with one or no child
        if (root.left == null){
            return root.right;
        }
        else if (root.right == null){
            return root.left;
        }
        // Node with two children
        Node temp = getSuccessor(root.right);
        root.value = temp.value;
        root.right = delete(root.right, temp.value);
    }
     return balanceTree(root);
   }
    public boolean delete(String value) {
        if (!search(value)) return false;
        root = delete(root, value);
        size--;
        return true;
    }
    public void traverseInOrder() {
    traverseInOrder(root);
    System.out.println(); 
    }
    private void traverseInOrder(Node node) {
    if (node == null) return;
    // Traverse left subtree, visit node, then traverse right subtree   
    traverseInOrder(node.left);
    System.out.print(node.value + " ");
    traverseInOrder(node.right);
    }
    public void traversePreOrder() {
        traversePreOrder(root);
        System.out.println(); 
    }
    private void traversePreOrder(Node node) {
        if (node == null) return;
        // Visit node, traverse left subtree, then traverse right subtree
        System.out.print(node.value + " ");
        traversePreOrder(node.left);
        traversePreOrder(node.right);
    }
    public void traversePostOrder() {
        traversePostOrder(root);
        System.out.println(); 
    }
    private void traversePostOrder(Node node) {
        if (node == null) return;
        // Traverse left subtree, traverse right subtree, then visit node
        traversePostOrder(node.left);
        traversePostOrder(node.right);
        System.out.print(node.value + " ");
    }
    public int getHeight() {
        return height(root);
    }
    public int getSize() {
        return size;
    }

    
   
}

