package dataStructures;
import java.io.Serializable;

/**
 * TreeSet Interface.
 * Same implementation as an BSTree, but with just one element, instead of a pair key-value.
 * @param <E> Generic Element.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface TreeSet<E extends Comparable<E>> extends Serializable {

    /**
     * @return True if the stack is empty; False otherwise.
     */
    boolean isEmpty();

    /**
     * @return Number of elements of the TreeSet.
     */
    int size();

    /**
     * Inserts an element in the TreeSet.
     * @param e Element being inserted.
     * @return Old element in the position where it is inserted; Null if it is inserted in a new position.
     */
    E insert(E e);

    /**
     * Removes an element from the TreeSet.
     * @param e Element being removed.
     * @return Element that is being removed; Null if the element doesn't exist.
     */
    E remove(E e);

    /**
     * @return Iterator of the elements of the TreeSet.
     */
    Iterator<E> iterator();
}
