import java.util.Iterator;

public class HashMapDict<K,V> implements ProjOneDictionary<K,V> {
    @Override
    public boolean insert(K key, V value) throws NullValueException {
        if (value == null) {throw new NullValueException();}
        return false;
    }

    @Override
    public V find(K key) {
        return null;
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
