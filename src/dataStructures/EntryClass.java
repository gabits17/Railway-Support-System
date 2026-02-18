package dataStructures;


import java.io.Serial;

public class EntryClass<K extends Comparable<K>,V> implements Entry<K,V> {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;
    /**
     * Key.
     */
    protected K key;
    /**
     * Value.
     */
    protected V value;

    /**
     * EntryClass Constructor.
     * @param key Key.
     * @param value Value.
     */
    public EntryClass(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public int compareTo(Entry<K,V> other) {
        return key.compareTo(other.getKey());
    }
}
