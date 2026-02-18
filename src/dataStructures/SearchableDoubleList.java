package dataStructures;

/**
 * SearchableDoubleList interface.
 * Has an additional method that justifies the name of this ADT.
 * @param <E> Generic Element
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface SearchableDoubleList<E> extends List<E>{
    /**
     * Searches for the Element E in the List.
     * @param element element para procurar
     * @return E if found, null otherwise.
     */
    E findEquals(E element);
}
