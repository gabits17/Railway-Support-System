package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

import java.io.Serial;

/**
 * Values iterator of a Dictionary<K,V>.
 * @param <K> Generic Key Object.
 * @param <V> Generic Value Object.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public class VIterator<K,V> implements Iterator<V> {

    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Entries iterator of a Dictionary<K,V>
     */
    private final Iterator<Entry<K,V>> it;

    /**
     * Constructor of the Value Iterator
     * @param it Iterator of entries.
     * @pre it != null
     */
    public VIterator(Iterator<Entry<K,V>> it) {
        this.it = it;
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public V next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        return it.next().getValue();
    }

    @Override
    public void rewind() {
        it.rewind();
    }
}
