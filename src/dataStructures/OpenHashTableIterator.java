package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

import java.io.Serial;

/**
 * Iterator of a Hashtable.
 * @param <K> Generic Key object.
 * @param <V> Generic Value object.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public class OpenHashTableIterator<K extends Comparable<K>, V> implements Iterator<Entry<K,V>>
{
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;
    /**
     * Current table idx (each bucket, not inside the lists)
     */
    private int currIdx;

    /**
     * Counts the number of elements
     */
    private final int size;

    /**
     * Current iterator
     */
    private Iterator<Entry<K,V>> currIterator;

    /**
     * Hashtable "address"
     */
    private final Dictionary<K,V>[] hashTable;

    public OpenHashTableIterator(Dictionary<K,V>[] hashTable, int size) {
        this.hashTable = hashTable;
        this.size = size;
        this.currIdx = 0;
        this.currIterator = null;
        skipEmptyBuckets();
    }

    // counter must be < size of hashtable
    @Override
    public boolean hasNext() {
        return currIterator != null;
    }

    // gets next entry of the hashtable
    // if currIterator.next is null, skips to the next iterator of the next bucket
    @Override
    public Entry<K,V> next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        Entry<K,V> e = currIterator.next();
        if(!currIterator.hasNext()) {
            currIdx++;
            skipEmptyBuckets(); // skips every empty bucket until it finds a non-empty bucket
        }
        return e;
    }

    // restarts the iteration
    @Override
    public void rewind() {
        this.currIdx = 0;
        this.currIterator = null;
        skipEmptyBuckets();
    }

    // skips idx of the hashtable that don't have any entry
    // if currIterator is empty, goes to next
    // increments currIdx
    private void skipEmptyBuckets() {
        while(currIdx < size && (hashTable[currIdx].isEmpty()))
            currIdx++;

        if(currIdx < size)
            currIterator = hashTable[currIdx].iterator();

        else currIterator = null;
    }
}
