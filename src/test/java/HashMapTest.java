/**
 * Project: Dictionary Implementation in java
 * File: HashMapTest [Test suite validates HaspMap structure]
 * @author Micah Olugbamila
 * Date: 02/29/2024
 */

import org.junit.jupiter.api.Test;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
public class HashMapTest extends DictionaryTest{
    @Override
    public ProjOneDictionary<Integer, String> newDictionary() {
        return new HashMapDict<Integer,String>();
    }

    @Test
    void testHashMapConstructor() {
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0, dict.getSize() , "Incorrect behavior of HashMap constructor");
    }
    @Test
    void testOverwrite() throws NullValueException {
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0, dict.getSize() , "Incorrect behavior of HashMap constructor");
        assertFalse(dict.insert(4, "apple"));
        assertTrue(dict.insert(4, "pineapple"));
        assertEquals("pineapple",dict.find(4));
    }

    /**
     * Test resizing in HashMap
     * @throws NullValueException
     */
    @Test
    void testCapacityIncrease() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0, dict.getSize() , "Incorrect behavior of HashMap constructor");

        ArrayList<Integer> testArray = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        for(Integer integer : testArray){
            dict.insert(integer, "Test");
        }

        for(Integer integer : testArray){
            dict.insert((integer * 2), "Test");
        }
        assertEquals(14, dict.getSize(), "Incorrect behavior of HashMap resize");
    }
    /**
     * TEST iterator()
     */
    //*** Test Iterator on empty HashMap
    @Test
    void testIteratorEmpty(){
        ProjOneDictionary<Integer, String> map = newDictionary();
        assertEquals(0,map.getSize());
        Iterator<Integer> iter = map.iterator();
        assertFalse(iter.hasNext());
    }
    //*** Test Iterator on single element in HashMap
    @Test
    void testIteratorSingle() throws NullValueException{
        ProjOneDictionary<Integer, String> map = newDictionary();
        assertEquals(0,map.getSize());
        assertFalse(map.insert(4, "apple"));
        Iterator<Integer> iter = map.iterator();
        assertTrue(iter.hasNext(),"Incorrect Iterator behavior for single element hasNext");
        assertEquals(4,iter.next(), "Incorrect Iterator behavior for single element next");
    }
    //*** Test Iterator on multiple element in HashMap
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
    @Test
    void testHashMapIteratorBroken1() throws NullValueException{
        ProjOneDictionary<Integer, String> dict = newDictionary();
        for(int i = 0; i<10; i++){
            if(i%2 == 0){continue;}
            assertFalse(dict.insert(i,"Test"));
        }
        dict.delete(7);
        dict.insert(6,"null");
        dict.insert(15,"test");
        dict.delete(5);
        dict.insert(5,"test");
        dict.delete(1);
        dict.insert(11,"Test");
        dict.insert(31,"Test");
        dict.delete(11);
        dict.insert(11, "Test");

        for(int i = 0; i<10; i++){
            if(i%2 == 0){continue;}
            assertFalse(dict.insert(i+17,"Test"));
        }
        Iterator<Integer> iter = dict.iterator();
        int count = 0;

        while(iter.hasNext()){
            Integer key = iter.next();
            assertNotNull(dict.find(key));
            count++;
        }
        assertEquals(dict.getSize(),count);

    }
}
