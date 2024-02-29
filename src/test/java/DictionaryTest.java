/**
 * @author Micah Olugbamila
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class DictionaryTest {
    public abstract ProjOneDictionary<Integer,String> newDictionary();

    @Test
    public void testSize(){
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize());
    }

    @Test
    public void testNullValue(){
        ProjOneDictionary<Integer, String> dict = newDictionary();
        try{
            dict.insert(0,null);
            fail();
        } catch (NullValueException e){
            return;
        }
    }
    /**
     * test constructs an empty dictionary
     */
    @Test
    void testEmpty()  {
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize());

    }
    /**
     * TEST insert()
     */
    @Test
    void testEmptyInsert() throws NullValueException {
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize(), "Incorrect size for no insert on empty dict");
        dict.insert(4,"apple");
        assertEquals(1, dict.getSize(), "Incorrect size for single insert on empty dict");
    }
    //*** Test SingleInsert
    @Test
    void testSingleInsert() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        dict.insert(4,"apple");
        dict.insert(1, "pear");
        assertEquals(2, dict.getSize(), "Incorrect size for single insert");
    }
    //*** Test multipleInsert
    @Test
    void testMultipleInsert() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        for(int i=0; i<10; i++){
            dict.insert(i, "TEST");
        }
        assertEquals(10, dict.getSize(), "Incorrect size for multiple insert");
    }
    /**
     * TEST find()
     */
    //*** TestFind on empty
    @Test
    void testEmptyFind(){
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertNull(dict.find(4), "Incorrect behavior on find for empty");
    }
    //*** TestFind on single dict when key exist
    @Test
    void testFindSingleCase1() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        dict.insert(4,"apple");
        assertNotNull(dict.find(4), "Incorrect find() behavior on single dictionary");
        assertEquals("apple", dict.find(4), "Incorrect find() behavior on single dictionary");
    }
    //*** TestFind on single dictionary when key does not exist
    @Test
    void testFindSingleCase2() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        dict.insert(4,"apple");
        assertNull(dict.find(6), "Incorrect find() behavior on single dictionary");
    }
    //*** TestFind on multiple elements in dictionary with and without Key
    @Test
    void testMultipleFind() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        ArrayList<Integer> testArray = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        for(Integer integer : testArray){
            assertFalse(dict.insert(integer, "value"));
        }
        assertEquals(testArray.size(),dict.getSize(), "Incorrect multiple insert behavior");
        for(Integer integer : testArray){
            assertNotNull(dict.find(integer), "Incorrect multiple find() behavior with multiple elements");
            assertEquals("value", dict.find(integer), "Incorrect multiple find() behavior with multiple elements");
            assertNull(dict.find(integer + 100));
        }
    }
    /**
     * TEST delete()
     */
    //*** Test EmptyDelete
    @Test
    void testEmptyDelete() {
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize());
        assertFalse(dict.delete(6),"Incorrect behavior for delete on empty dictionary");

    }
    //*** Test SingleDelete on dict with size of 1
    @Test
    void testSingleDelete() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize());
        dict.insert(4, "apple");
        assertTrue(dict.delete(4),"Incorrect behavior for delete on singular dictionary");
    }
    //*** Test multipleDelete
    @Test
    void testMultipleDelete() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        for(int i=0; i<10; i++){
            dict.insert(i, "TEST");
        }

        assertEquals(10, dict.getSize());
        for(int i=0; i<10; i++){
            assertTrue(dict.delete(i), "Incorrect behavior for multiple delete");
        }
    }
    //*** Test Delete on edge cases
    @Test
    void testDeleteEdgeCase1() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize());

        ArrayList<Integer> testArray = new ArrayList<>(Arrays.asList(1,8,5,10,15,7,9,8));
        for(Integer integer : testArray){
            dict.insert(integer, "value");
        }

        assertTrue(dict.delete(7));
        assertTrue(dict.delete(10));
        testArray.remove((Integer) 7); testArray.remove((Integer) 10);

        for(Integer integer : testArray){
            assertEquals("value", dict.find(integer), "Incorrect behavior for delete edgeCase1");
        }

    }
    //*** Test Delete for edge case 2
    @Test
    void testDeleteEdgeCase2() throws NullValueException{

        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0,dict.getSize());

        ArrayList<Integer> testArray = new ArrayList<>(Arrays.asList(2,3,4,6,7,1));
        for(Integer integer: testArray){
            dict.insert(integer, "value");
        }
        assertEquals(testArray.size(), dict.getSize());
        assertTrue(dict.delete(6));
        testArray.remove((Integer) 6);

        for(Integer integer : testArray){
            assertEquals("value", dict.find(integer), "Incorrect behavior for delete edgeCase2");
        }

    }
}
