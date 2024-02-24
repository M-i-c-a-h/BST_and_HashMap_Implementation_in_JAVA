import org.w3c.dom.traversal.NodeIterator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class HashMapDict<K,V> implements ProjOneDictionary<K,V> {

    /**
     * Node class- contains Key and Value pair
     * Key- uniquely identifies a value
     * value- object stored
     */
    private class Node{
        K key;
        V value;

        Node(K newKey, V newValue){
            this.key = newKey;
            this.value = newValue;
        }
    }
    private Node [] hashArray;
    private int size;
    private int capacity = 10; // default capacity

    /**
     * HashMapDict Constructor initialises a HashArray with default capacity 10
     */
    HashMapDict(){
        hashArray = (Node[]) Array.newInstance(Node.class, capacity);
    }
    @Override
    public boolean insert(K key, V value) throws NullValueException {
        if (value == null) {throw new NullValueException();}

        // determine if key exist in hashArray
        boolean found = (this.find(key) != null);

        // create new Node key-Value pair
        Node newNode = new Node(key, value);

        // if key exist in array, overwrite existing value
        if(found){
            overwriteKey(key, value);
        }
        else{
            // add newNode into hashArray
            hash(newNode);
            size++;
        }

        // maintain load factor of hashArray
        if((size * 2) == capacity){
            resizeHashArray();
        }
        return found;
    }

    /**
     * hash() -> finds the index to place key in []
     * @param nodeToAdd new node to be inserted into hashArray
     * return void
     */
    private void hash(Node nodeToAdd){
        // determine hashCode of key
        int index = nodeToAdd.key.hashCode() % capacity;

        // resolve collusion by linear probing
        while(hashArray[index] != null){
            index = (index + 1) % capacity;
        }
        hashArray[index] = nodeToAdd;
    }

    /**
     * resizeHashArray() -> doubles the capacity of current hashArray,
     *                      inserts previous element into new Array.
     *                      update hashArray and capacity
     * return void
     */
    private void resizeHashArray(){
        System.out.println("Capacity threshold met with \nsize: " + size + "\ncapacity :" + capacity);
        int newCapacity = capacity * 2;
        Node [] temp = (Node[]) Array.newInstance(Node.class, newCapacity);

        for(int i=0; i<capacity; i++){
            if(hashArray[i] != null){
                int index = hashArray[i].key.hashCode() % newCapacity;

                // resolve collusion by linear probing
                while(temp[index] != null){
                    index = (index + 1) % newCapacity;
                }
                temp[index] = hashArray[i];
            }
        }
        // update capacity and hasArray
        capacity = newCapacity;
        hashArray = temp;
    }
    private void overwriteKey(K key, V value){
        // find index location
        int index = getIndexPosition(key);
        // overwrite value
        hashArray[index].value = value;
    }
    private int getIndexPosition(K key){
        int index = key.hashCode() % capacity;

        // search until finding either an empty cell or a cell whose stored key is x
        while(hashArray[index] != null){
            if(hashArray[index].key.equals(key)){
                return index;
            }
            index = (index + 1) % capacity;
        }
        return -1;
    }

    @Override
    public V find(K key) {
        if(size == 0){ return null;}

        // get index position of key, index -1 is returned if key is not found
        int index = getIndexPosition(key);
        if (index == -1){return null;}

        return hashArray[index].value;
    }

    @Override
    public boolean delete(K key) {
        if(size == 0 || this.find(key) == null){ return false;}

        // delete Node at index position
        int index = getIndexPosition(key);
        hashArray[index] = null;
        size--;

        // rehash keys,
        // move keys up the table if a prior index exist for hashcode
        for(index = (index + 1) % capacity; hashArray[index] != null; index = (index + 1) % capacity){
            Node rehash = hashArray[index];
            hashArray[index] = null;
            hash(rehash);
        }
        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    /**
     * HashMap_Iterator linearly searches hashArray
     */
    private class HashMap_Iterator implements Iterator<K>{

        int curr_index = -1; // default start out of range

        HashMap_Iterator(){
            locate();
        }
        void locate(){
            curr_index++;      // set counter to 0
            while(curr_index < capacity && hashArray[curr_index] == null){
                curr_index++;
            }

        }
        @Override
        public boolean hasNext() {
            return (curr_index < capacity-1);
        }

        @Override
        public K next() {
            K to_return = hashArray[curr_index].key;
            locate();
            return to_return;
        }
    }
    @Override
    public Iterator<K> iterator() {
        return new HashMap_Iterator();
    }
}
