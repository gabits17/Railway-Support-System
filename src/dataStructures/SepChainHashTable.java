package dataStructures;

import java.io.Serial;

/**
 * Separate Chaining Hash table implementation
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value 
 */

public class SepChainHashTable<K extends Comparable<K>, V> 
    extends HashTable<K,V> 
{
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

	/**
	 * The array of dictionaries.
	 */
    protected Dictionary<K,V>[] table;

    /**
     * Constructor of an empty separate chaining hash table,
     * with the specified initial capacity.
     * Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     * @param capacity defines the table capacity.
     */
    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity )
    {
        int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
        table = (Dictionary<K,V>[]) new Dictionary[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = new OrderedDoubleList<>();
        maxSize = capacity;
        currentSize = 0;
    }                                      


    public SepChainHashTable( )
    {
        this(DEFAULT_CAPACITY);
    }                                                                

    /**
     * Returns the hash value of the specified key.
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash( K key )
    {
        return Math.abs(key.hashCode()) % table.length;
    }

    @Override
    public V find( K key ) {
        return table[this.hash(key)].find(key);
    }

    @Override
    public V insert( K key, V value ) {
        if (this.isFull()) rehash();
        int idx = hash(key);
        V old = table[idx].insert(key, value);
        if(old == null) currentSize++;
        return old;
    }

    @Override
    public V remove( K key )
    {
        V value = table[hash(key)].remove(key);
        if (value != null) currentSize--;
        return value;
    }


    @Override
    public Iterator<Entry<K,V>> iterator( ) {
        return new OpenHashTableIterator<>(table, table.length);
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        int cap = maxSize * 2;
        int newSize = HashTable.nextPrime((int) (1.1 * cap));
        Dictionary<K, V>[] newTable = (Dictionary<K, V>[]) new Dictionary[newSize];

        for (int i = 0; i < newSize; i++)
            newTable[i] = new OrderedDoubleList<>();
        Iterator<Entry<K, V>> it = iterator();
        table = newTable;
        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
            insert(entry.getKey(), entry.getValue());
        }
        maxSize = cap;
    }
}
































