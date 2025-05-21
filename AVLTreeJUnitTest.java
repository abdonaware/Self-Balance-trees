import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import static org.junit.Assert.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AVLTreeJUnitTest {
    
    private static final String RESULTS_FILE = "test_results.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Rule
    public TestRule watcher = new TestWatcher() {
        private long startTime;
        
        @Override
        protected void starting(Description description) {
            startTime = System.currentTimeMillis();
            writeToFile("Starting test: " + description.getMethodName());
        }
        
        @Override
        protected void succeeded(Description description) {
            long duration = System.currentTimeMillis() - startTime;
            writeToFile(String.format("Test %s PASSED (%d ms)", 
                description.getMethodName(), duration));
        }
        
        @Override
        protected void failed(Throwable e, Description description) {
            long duration = System.currentTimeMillis() - startTime;
            writeToFile(String.format("Test %s FAILED (%d ms) - Reason: %s", 
                description.getMethodName(), duration, e.getMessage()));
        }
        
        private void writeToFile(String message) {
            try (FileWriter writer = new FileWriter(RESULTS_FILE, true)) {
                String timestamp = DATE_FORMAT.format(new Date());
                writer.write(timestamp + " - " + message + "\n");
            } catch (IOException e) {
                System.err.println("Error writing to results file: " + e.getMessage());
            }
        }
    };

    @Test
    public void testInsertSingleElement_AVL() {
        AVLTree tree = new AVLTree();
        assertTrue(tree.insert("Apple"));
        assertEquals(1, tree.getSize());
        assertTrue(tree.search("Apple"));
    }

    @Test
    public void testInsertDuplicateElement_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("Banana");
        assertFalse(tree.insert("Banana"));
        assertEquals(1, tree.getSize());
    }

    @Test
    public void testSearchEmptyTree_AVL() {
        AVLTree tree = new AVLTree();
        assertFalse(tree.search("Cherry"));
    }

    @Test
    public void testDeleteFromSingleElementTree_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("Date");
        assertTrue(tree.delete("Date"));
        assertEquals(0, tree.getSize());
        assertFalse(tree.search("Date"));
    }

    @Test
    public void testLeftRotation_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("A");
        tree.insert("B");
        tree.insert("C"); // Should trigger left rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testRightRotation_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("C");
        tree.insert("B");
        tree.insert("A"); // Should trigger right rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testLeftRightRotation_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("C");
        tree.insert("A");
        tree.insert("B"); // Should trigger LR rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testRightLeftRotation_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("A");
        tree.insert("C");
        tree.insert("B"); // Should trigger RL rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testMultipleInsertions_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("E");
        tree.insert("C");
        tree.insert("G");
        tree.insert("B");
        tree.insert("D");
        tree.insert("F");
        tree.insert("H");
        assertEquals(7, tree.getSize());
        assertTrue(tree.getHeight() <= 3); // AVL property ensures height is logarithmic
    }

    @Test
    public void testDeleteLeafNode_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("B");
        tree.insert("A");
        tree.insert("C");
        assertTrue(tree.delete("A"));
        assertEquals(2, tree.getSize());
        assertFalse(tree.search("A"));
    }

    @Test
    public void testDeleteNodeWithOneChild_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("B");
        tree.insert("A");
        tree.insert("C");
        tree.insert("D");
        assertTrue(tree.delete("C"));
        assertEquals(3, tree.getSize());
        assertTrue(tree.search("D")); // D should still exist
    }

    @Test
    public void testDeleteNodeWithTwoChildren_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("D");
        tree.insert("B");
        tree.insert("F");
        tree.insert("A");
        tree.insert("C");
        tree.insert("E");
        tree.insert("G");
        assertTrue(tree.delete("D")); // Should be replaced by E
        assertEquals(6, tree.getSize());
        assertTrue(tree.search("E"));
    }

    @Test
    public void testTreeHeightAfterDeletions_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("D");
        tree.insert("B");
        tree.insert("F");
        tree.insert("A");
        tree.insert("C");
        tree.insert("E");
        tree.insert("G");
        tree.delete("A");
        tree.delete("C");
        assertEquals(5, tree.getSize());
        assertTrue(tree.getHeight() <= 3);
    }

    @Test
    public void testComplexInsertDeleteSequence_AVL() {
        AVLTree tree = new AVLTree();
        tree.insert("M");
        tree.insert("N");
        tree.insert("O");
        tree.insert("L");
        tree.insert("K");
        tree.insert("Q");
        tree.insert("P");
        tree.insert("H");
        tree.insert("I");
        tree.insert("A");
        
        assertEquals(10, tree.getSize());
        
        tree.delete("H");
        tree.delete("K");
        tree.delete("N");
        
        assertEquals(7, tree.getSize());
        assertEquals(3, tree.getHeight());
    }

    @Test
    public void testEmptyTreeProperties_AVL() {
        AVLTree tree = new AVLTree();
        assertEquals(0, tree.getSize());
        assertEquals(0, tree.getHeight());
        assertFalse(tree.delete("Any"));
    }

    @Test
    public void testInsertSingleElement_RB() {
        RedBlackTree tree = new RedBlackTree();
        assertTrue(tree.insert("Apple"));
        assertEquals(1, tree.getSize());
        assertTrue(tree.search("Apple"));
    }

    @Test
    public void testInsertDuplicateElement_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("Banana");
        assertFalse(tree.insert("Banana"));
        assertEquals(1, tree.getSize());
    }

    @Test
    public void testSearchEmptyTree_RB() {
        RedBlackTree tree = new RedBlackTree();
        assertFalse(tree.search("Cherry"));
    }

    @Test
    public void testDeleteFromSingleElementTree_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("Date");
        assertTrue(tree.delete("Date"));
        assertEquals(0, tree.getSize());
        assertFalse(tree.search("Date"));
    }

    @Test
    public void testLeftRotation_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("A");
        tree.insert("B");
        tree.insert("C"); // Should trigger left rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testRightRotation_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("C");
        tree.insert("B");
        tree.insert("A"); // Should trigger right rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testLeftRightRotation_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("C");
        tree.insert("A");
        tree.insert("B"); // Should trigger LR rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testRightLeftRotation_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("A");
        tree.insert("C");
        tree.insert("B"); // Should trigger RL rotation
        assertEquals(3, tree.getSize());
        assertEquals(2, tree.getHeight());
    }

    @Test
    public void testMultipleInsertions_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("E");
        tree.insert("C");
        tree.insert("G");
        tree.insert("B");
        tree.insert("D");
        tree.insert("F");
        tree.insert("H");
        assertEquals(7, tree.getSize());
        assertTrue(tree.getHeight() <= 3); // AVL property ensures height is logarithmic
    }

    @Test
    public void testDeleteLeafNode_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("B");
        tree.insert("A");
        tree.insert("C");
        assertTrue(tree.delete("A"));
        assertEquals(2, tree.getSize());
        assertFalse(tree.search("A"));
    }

    @Test
    public void testDeleteNodeWithOneChild_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("B");
        tree.insert("A");
        tree.insert("C");
        tree.insert("D");
        assertTrue(tree.delete("C"));
        assertEquals(3, tree.getSize());
        assertTrue(tree.search("D")); // D should still exist
    }

    @Test
    public void testDeleteNodeWithTwoChildren_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("D");
        tree.insert("B");
        tree.insert("F");
        tree.insert("A");
        tree.insert("C");
        tree.insert("E");
        tree.insert("G");
        assertTrue(tree.delete("D")); // Should be replaced by E
        assertEquals(6, tree.getSize());
        assertTrue(tree.search("E"));
    }

    @Test
    public void testTreeHeightAfterDeletions_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("D");
        tree.insert("B");
        tree.insert("F");
        tree.insert("A");
        tree.insert("C");
        tree.insert("E");
        tree.insert("G");
        tree.delete("A");
        tree.delete("C");
        assertEquals(5, tree.getSize());
        assertTrue(tree.getHeight() <= 3);
    }

    @Test
    public void testComplexInsertDeleteSequence_RB() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert("M");
        tree.insert("N");
        tree.insert("O");
        tree.insert("L");
        tree.insert("K");
        tree.insert("Q");
        tree.insert("P");
        tree.insert("H");
        tree.insert("I");
        tree.insert("A");
        
        assertEquals(10, tree.getSize());
        
        tree.delete("H");
        tree.delete("K");
        tree.delete("N");
        
        assertEquals(7, tree.getSize());
        assertEquals(3, tree.getHeight());
    }

    @Test
    public void testEmptyTreeProperties_RB() {
        RedBlackTree tree = new RedBlackTree();
        assertEquals(0, tree.getSize());
        assertEquals(0, tree.getHeight());
        assertFalse(tree.delete("Any"));
    }
}