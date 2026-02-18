package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

import java.io.Serial;

public class TreeSetIterator<E extends Comparable<E>> implements Iterator<E> {

    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Stack that contains all nodes sorted.
     */
    protected Stack<BSTNode<E>> stack;
    /**
     * Root node.
     */
    protected BSTNode<E> root;

    /**
     * TreeSetIterator constructor.
     * @param root Root node.
     * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
     * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
     */
    public TreeSetIterator(BSTNode<E> root) {
        this.root = root;
        rewind();
    }

    /**
     * Sorts the nodes in stack.
     * @param node Root node.
     */
    private void pushPathToMinimum(BSTNode<E> node) {
        BSTNode<E> next =node;
        while (next != null) {
            stack.push(next);
            next = next.getLeft();
        }

    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public E next() throws NoSuchElementException {
        if(!hasNext()) throw new NoSuchElementException();
        BSTNode<E> next = stack.pop();
        pushPathToMinimum(next.getRight());
        return next.getElement();
    }

    @Override
    public void rewind() {
        stack = new StackInList<>();
        pushPathToMinimum(root);
    }
}
