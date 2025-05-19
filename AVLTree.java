public class AVLTree implements SelfBalanceTreeInterface {
    class Node {
        int value;
        Node left;
        Node right;
        int height;

        public Node(int value) {
            this.value = value;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }
    private Node root = null;
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
    public void insert(int value) {
        root = insert(root, value);
    }
private Node insert(Node current, int value) {
    if (current == null) {
        return new Node(value);
    }
    if (value < current.value) {
        current.left = insert(current.left, value);
    } else if (value > current.value) {
        current.right = insert(current.right, value);
    }
    return balanceTree(current);
 }
 
    public boolean search(int value){
        return search(root, value) != null;
    }
    private Node search(Node current, int value) {
        if (current == null || value == current.value) {
            return current;
        }
        if (value < current.value) {
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
    private Node delete(Node root, int key) {
    if (root == null){
        return null;
    }
    if (key < root.value) {
        root.left = delete(root.left, key);
    } else if (key > root.value) {
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
    public boolean delete(int value) {
        if (!search(value)) return false;
        root = delete(root, value);
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

    
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Test insertions
        System.out.println("Inserting values...");
        int[] valuesToInsert = {10, 20, 30, 40, 50, 25};
        for (int val : valuesToInsert) {
            tree.insert(val);
            System.out.println("Inserted: " + val);
        }

        // Test traversals
        System.out.println("\nIn-order Traversal (should be sorted):");
        tree.traverseInOrder();

        System.out.println("Pre-order Traversal:");
        tree.traversePreOrder();

        System.out.println("Post-order Traversal:");
        tree.traversePostOrder();

        // Test search
        System.out.println("\nSearching for 25: " + tree.search(25));  // true
        System.out.println("Searching for 100: " + tree.search(100)); // false

        // Test deletion
        System.out.println("\nDeleting 25...");
        System.out.println("Deleted: " + tree.delete(25)); // true

        System.out.println("Deleting 100...");
        System.out.println("Deleted: " + tree.delete(100)); // false

        // Traversals after deletion
        System.out.println("\nIn-order Traversal after deletion:");
        tree.traverseInOrder();

        System.out.println("Pre-order Traversal after deletion:");
        tree.traversePreOrder();
    }
}

