package dataStructures;


import java.io.Serial;

public class SearchableDoubleListClass<E> extends DoubleList<E> implements SearchableDoubleList<E> {

    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    public SearchableDoubleListClass() {
        super();
    }

    @Override
    public E findEquals(E element) {
        Iterator<E> it = this.iterator();
        while(it.hasNext()) {
            E e = it.next();
            if(e.equals(element))
                return e;
        }
        return null;
    }
}
