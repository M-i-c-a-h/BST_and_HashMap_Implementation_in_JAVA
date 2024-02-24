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
    void testHashMapConstructor() throws NullValueException {
        ProjOneDictionary<Integer, String> dict = newDictionary();
        assertEquals(0, dict.getSize() , "Incorrect behavior of HashMap constructor");
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
}
