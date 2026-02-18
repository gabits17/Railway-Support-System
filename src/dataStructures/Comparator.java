package dataStructures;

import java.io.Serializable;

/**
 * Comparator interface.
 * @param <E> Generic element.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface Comparator<E> extends Serializable {

    int compare(E o1, E o2);
}
