import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Alice Lew
 * @version 1.3
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code STARTING_SIZE}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(STARTING_SIZE);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    @Override
    public V add(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Your key can't be null.");
        } else if (value == null) {
            throw new IllegalArgumentException("Your value can't be null.");
        }
        
        // Regrowing the backing array if it goes over the load factor
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            MapEntry<K, V>[] newTable =
                    (MapEntry<K, V>[]) new MapEntry[(2 * table.length) + 1];
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null && !table[i].isRemoved()) {
                    int hashValue = Math.abs(
                            table[i].getKey().hashCode()) % newTable.length;
                    while (newTable[hashValue] != null) {
                        hashValue = (hashValue + 1) % newTable.length;
                    }
                    newTable[hashValue] = table[i];
                }
            }
            table = newTable;
        }
        
        // The implementation of adding a new map entry
        int hashValue = Math.abs(key.hashCode()) % table.length;
        
        int eligibleIndex = -1;
        int count = 0;
        while (table[hashValue] != null && count < table.length) {
            if (!table[hashValue].isRemoved()
                    && table[hashValue].getKey().equals(key)) {
                V returnVal = table[hashValue].getValue();
                table[hashValue].setKey(key);
                table[hashValue].setValue(value);
                return returnVal;
            } else if (table[hashValue].isRemoved() && eligibleIndex == -1) {
                eligibleIndex = hashValue;
            }
            hashValue = (hashValue + 1) % table.length;
            count++;
        }
        
        if (eligibleIndex != -1) {
            table[eligibleIndex].setKey(key);
            table[eligibleIndex].setValue(value);
            table[eligibleIndex].setRemoved(false);
        } else {
            table[hashValue] = new MapEntry<K, V>(key, value);
        }
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Your key can't be null.");
        }
        
        int hashValue = Math.abs(key.hashCode()) % table.length;
        int counter = table.length;
        while (table[hashValue] != null && counter >= 0) {
            if (table[hashValue].getKey().equals(key)
                    && !table[hashValue].isRemoved()) {
                table[hashValue].setRemoved(true);
                size--;
                return table[hashValue].getValue();
            }
            hashValue = (hashValue + 1) % table.length;
            counter--;
        }
        throw new java.util.NoSuchElementException("The key isn't in the map.");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Your key can't be null.");
        }
        
        int hashValue = Math.abs(key.hashCode()) % table.length;
        int counter = table.length;
        while (table[hashValue] != null && counter >= 0) {
            if (table[hashValue].getKey().equals(key)
                    && !table[hashValue].isRemoved()) {
                return table[hashValue].getValue();
            }
            hashValue = (hashValue + 1) % table.length;
            counter--;
        }
        throw new java.util.NoSuchElementException("The key isn't in the map.");
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Your key can't be null.");
        }
        
        int hashValue = Math.abs(key.hashCode()) % table.length;
        int counter = table.length;
        while (table[hashValue] != null && counter >= 0) {
            if (table[hashValue].getKey().equals(key)
                    && !table[hashValue].isRemoved()) {
                return true;
            }
            hashValue = (hashValue + 1) % table.length;
            counter--;
        }
        return false;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();
        for (int index = 0; index < table.length; index++) {
            if (table[index] != null && !table[index].isRemoved()) {
                returnSet.add(table[index].getKey());
            }
        }
        return returnSet;
    }

    @Override
    public List<V> values() {
        List<V> returnList = new ArrayList<>();
        for (int index = 0; index < table.length; index++) {
            if (table[index] != null && !table[index].isRemoved()) {
                returnList.add(table[index].getValue());
            }
        }
        return returnList;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException("Length cannot be non-positive"
                    + " or smaller than the total number of items.");
        }
        
        MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                int hashValue = Math.abs(
                        table[i].getKey().hashCode()) % newTable.length;
                while (newTable[hashValue] != null) {
                    hashValue = (hashValue + 1) % newTable.length;
                }
                newTable[hashValue] = table[i];
            }
        }
        table = newTable;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
