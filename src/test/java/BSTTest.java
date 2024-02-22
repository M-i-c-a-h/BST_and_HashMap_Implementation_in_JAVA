import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class BSTTest extends DictionaryTest {
    @Override
    public ProjOneDictionary<Integer, String> newDictionary() {
        return new BinarySearchTreeDict<Integer,String>();
    }

    /**
     * TEST delete()
     */
    //*** Test DeleteLeafNode
    @Test
    void testDeleteLeafNode() throws NullValueException{
        ProjOneDictionary<Integer, String> tree = newDictionary();
        assertEquals(0,tree.getSize());
        tree.insert(4, "apple");
        tree.insert(3, "pie");
        tree.insert(5, "pineapple");
        assertEquals(3, tree.getSize());

        tree.delete(5);
        assertEquals(2, tree.getSize());
        assertEquals("pie",tree.find(3) ,"Incorrect behavior for delete leafNode ");
        assertEquals("apple", tree.find(4), "Incorrect behavior for delete leafNode");


    }
    //*** Test Delete rootNode simple
    @Test
    void testDeleteRootSimple() throws NullValueException{
        ProjOneDictionary<Integer, String> tree = newDictionary();
        assertEquals(0,tree.getSize());
        tree.insert(4, "apple");
        tree.insert(3, "pie");
        tree.insert(5, "pineapple");
        assertEquals(3, tree.getSize());

        assertTrue(tree.delete(4), "Incorrect behavior for delete root node");
        assertEquals(2, tree.getSize());
        assertEquals("pie", tree.find(3), "Incorrect behavior for delete root node");
        assertEquals("pineapple", tree.find(5), "Incorrect behavior for delete root node");

    }
    /**
     * TEST iterator()
     */
    //*** Test Iterator on empty BST
    @Test
    void testIteratorEmpty(){
        ProjOneDictionary<Integer, String> tree = newDictionary();
        assertEquals(0,tree.getSize());
        Iterator<Integer> iter = tree.iterator();
        assertFalse(iter.hasNext());
    }
    //*** Test Iterator on single element in BST
    @Test
    void testIteratorSingle() throws NullValueException{
        ProjOneDictionary<Integer, String> tree = newDictionary();
        assertEquals(0,tree.getSize());
        assertFalse(tree.insert(4, "apple"));
        Iterator<Integer> iter = tree.iterator();
        assertTrue(iter.hasNext(),"Incorrect Iterator behavior for single element hasNext");
        assertEquals(4,iter.next(), "Incorrect Iterator behavior for single element next");
    }
    //*** Test Iterator on multiple element in BST
    @Test
    void testIteratorMultiple() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        dict.insert(3,"three");
        dict.insert(4, "four");
        dict.insert(5, "five");
        dict.insert(6, "six");
        dict.insert(7, "seven");
        int size = dict.getSize();
        int count = 0;
        int val = 3;

        Iterator<Integer> iter = dict.iterator();
        while(iter.hasNext()){
            count++;
            assertEquals(val, iter.next(), "Incorrect Iterator next() behavior with multiple element");
            val++;
        }
        assertEquals(size, count);
        assertFalse(iter.hasNext(), "Incorrect Iterator behavior with multiple element");
    }
}
