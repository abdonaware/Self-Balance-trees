import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    private SelfBalanceTreeInterface selfBalanceTree;

    public Main(SelfBalanceTreeInterface selfBalanceTree) {
        this.selfBalanceTree = selfBalanceTree;
    }

    public void readFromFile(String filePath) {
        int count = 0;
        int existingCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (selfBalanceTree.insert(line.trim())) {
                    count++;
                } else {
                    existingCount++;
                }
            }
            System.out.println("Inserted: " + count + ", Already exists: " + existingCount);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    public void deleteFromFile(String filePath) {
        int count = 0;
        int existingCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (selfBalanceTree.delete(line.trim())) {
                    count++;
                } else {
                    existingCount++;
                }
            }
            System.out.println("Deleted: " + count + ", Not found: " + existingCount);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the type of tree you want to create \n1-AVL\n2-RedBlack: ");
        int choice = scanner.nextInt();

        SelfBalanceTreeInterface selfBalanceTree;

        switch (choice) {
            case 1:
                selfBalanceTree = new AVLTree();
                break;
            case 2:
                selfBalanceTree = new RedBlackTree();
                break;
            default:
                System.out.println("Invalid choice");
                scanner.close();
                return;
        }

       while (true) {
            System.out.println("please enter the Number of operations you want to perform: ");
            System.out.println("1. Insert a value");
            System.out.println("2. batch insert from file");
            System.out.println("3. Delete a value");
            System.out.println("4. batch delete from file");
            System.out.println("5. Search for a value");
            System.out.println("6. Traverse the tree");
            System.out.println("7. Get the height of the tree");
            System.out.println("8. Get the size of the tree");
            System.out.println("9. Exit");
            int operation = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (operation) {
                case 1:
                    System.out.println("Enter value to insert: ");
                    String insertValue = scanner.nextLine();
                    if (selfBalanceTree.insert(insertValue)) {
                        System.out.println(GREEN + "Inserted: " + insertValue + RESET);
                    } else {
                        System.out.println(RED + "Failed to insert: " + insertValue + RESET);
                    }
                    break;
                case 2:
                    System.out.println("Enter file path for batch insert: ");
                    String insertFilePath = scanner.nextLine();
                    new Main(selfBalanceTree).readFromFile(insertFilePath);
                    break;
                case 3:
                    System.out.println("Enter value to delete: ");
                    String deleteValue = scanner.nextLine();
                    if (selfBalanceTree.delete(deleteValue)) {
                        System.out.println(GREEN + "Deleted: " + deleteValue + RESET);
                    } else {
                        System.out.println(RED + "Failed to delete: " + deleteValue + RESET);
                    }
                    break;
                case 4:
                    System.out.println("Enter file path for batch delete: ");
                    String deleteFilePath = scanner.nextLine();
                    new Main(selfBalanceTree).deleteFromFile(deleteFilePath);
                    break;
                case 5:
                    System.out.println("Enter value to search: ");
                    String searchValue = scanner.nextLine();
                    if (selfBalanceTree.search(searchValue)) {
                        System.out.println(GREEN + "Found: " + searchValue + RESET);
                    } else {
                        System.out.println(RED + "Not found: " + searchValue + RESET);
                    }
                    break;
                case 6:
                System.out.println("chose the type of traversal you want to perform: ");
                    System.out.println("1. InOrder");
                    System.out.println("2. PreOrder");  
                    System.out.println("3. PostOrder");
                    int traversalType = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (traversalType) {
                        case 1:
                            System.out.println("InOrder Traversal: ");
                            selfBalanceTree.traverseInOrder();
                            break;
                        case 2:
                            System.out.println("PreOrder Traversal: ");
                            selfBalanceTree.traversePreOrder();
                            break;
                        case 3:
                            System.out.println("PostOrder Traversal: ");
                            selfBalanceTree.traversePostOrder();
                            break;
                        default:
                            System.out.println("Invalid traversal type");
                    }
                   
                    break;
                case 7:
                    System.out.println("Height of the tree: " + selfBalanceTree.getHeight());
                    break;
                case 8:
                    System.out.println("Size of the tree: " + selfBalanceTree.getSize());
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    break;
                default:
                    System.out.println("Invalid operation");
            }
        }
       
    }
}
