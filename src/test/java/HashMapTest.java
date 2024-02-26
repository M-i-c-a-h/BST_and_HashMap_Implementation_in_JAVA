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
}
