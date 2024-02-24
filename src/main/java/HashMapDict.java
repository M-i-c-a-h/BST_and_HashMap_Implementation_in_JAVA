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



    @Override
    public boolean insert(K key, V value) throws NullValueException {
        if (value == null) {throw new NullValueException();}

        // determine if key exist in hashArray
        boolean found = (this.find(key) != null);

        // create new Node key-Value pair
        Node newNode = new Node(key, value);

        // if hashArray is empty insert Node
        if(size == 0){
            hashArray = (Node[]) Array.newInstance(Node.class, capacity);
            // hash Node and insert in hashArray
            hash(newNode);
        }
        // if key exist in array, overwrite existing value
        else if(found){
            overwriteKey(key, value);
        }
        else{
            // add newNode into hashArray
            hash(newNode);
        }
        // increase size if key does not exist
        if(!found) {size++;}

        // maintain load factor of hashArray
        if((size + 1) * 0.5 >= capacity){
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

        int index = key.hashCode() % capacity;
        int count = 0;

        // iterate through array to find element,
        // because of soft-deletion search while we have not seen all elements
        while(count <= size){
            if(hashArray[index] != null && hashArray[index].key.equals(key)){
                hashArray[index].value = value;
                return;
            }
            index = (index + 1) % capacity;
            count++;
        }
    }

    @Override
    public V find(K key) {
        if (size == 0 ){return null;}
        int index = key.hashCode() % capacity;
        int count = 0;

        // iterate through array to find element,
        // because of soft-deletion search while we have not seen all elements
        while(count <= size){
            if(hashArray[index] != null && hashArray[index].key.equals(key)){
                return hashArray[index].value;
            }
            index = (index + 1) % capacity;
            count++;
        }

        return null;
    }

    @Override
    public boolean delete(K key) {
        if(size == 0 || this.find(key) == null){ return false;}

        // soft-delete Node
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
