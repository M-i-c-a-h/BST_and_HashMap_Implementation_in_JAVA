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
        dict.insert(4, "apple");
        assertEquals(1, dict.getSize());
        assertNotNull(dict.find(4));
        assertEquals("apple", dict.find(4));
        dict.insert(4, "banana");
        assertEquals("banana", dict.find(4));
        assertEquals(1, dict.getSize());
    }
}
