package dataStructures;

import java.io.Serial;

public class TreeSetClass<E extends Comparable<E>> implements TreeSet<E>{

    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    protected BSTNode<E> root;
    protected int currSize;
    protected Comparator<E> comp;

    /**
     * Static Class that saves the last step
     * @param <E> Generic Element.
     */
    protected static class PathStep<E> {
        /**
         * Parent of the node.
         */
        public BSTNode<E> parent;

        /**
         * Node is left or right child of parent
         * This boolean determines the "direction" of the child.
         */
        public boolean isLeftChild;

        public PathStep(BSTNode<E> parent, boolean left) {
            this.parent = parent;
            this.isLeftChild = left;
        }
        public void set(BSTNode<E> parent, boolean left) {
            this.parent = parent;
            this.isLeftChild = left;
        }
    }

    public TreeSetClass(Comparator<E> comp) {
        this.comp = comp;
        this.root = null;
        currSize = 0;
    }

    protected BSTNode<E> findNode(E e, PathStep<E> lastStep) {
        BSTNode<E> node = root;
        while(node != null) {
            int compResult = e.compareTo(node.getElement());
            if(compResult == 0) return node;
            else if(compResult < 0) {
                lastStep.set(node,true);
                node = node.getLeft();
            } else {
                lastStep.set(node,false);
                node = node.getRight();
            }
        }
        return null;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public E insert(E e) {
        PathStep<E> lastStep = new PathStep<>(root,false);
        BSTNode<E> node = this.findNode(e,lastStep);
        if(node == null) {
            BSTNode<E> newNode = new BSTNode<>(e);
            this.linkSubtree(newNode, lastStep);
            currSize++;
            return null;
        }
        else {
            E old = node.getElement();
            node.setElement(e);
            return old;
        }
    }

    protected void linkSubtree(BSTNode<E> node, PathStep<E> lastStep) {
        if(lastStep.parent == null) root = node;
        else {
            if(lastStep.isLeftChild) lastStep.parent.setLeft(node);
            else lastStep.parent.setRight(node);
        }
    }

    /**
     * Returns the node with the smallest key in the tree rooted at the specified node.
     * Moreover, stores the last step of the path in lastStep.
     * Requires: theRoot != null
     * @param theRoot Root of the tree
     * @param lastStep Refers to the parent of the theRoot
     * @return Node that has the minimum key.
     */
    protected BSTNode<E> minNode(BSTNode<E> theRoot, PathStep<E> lastStep) {
        BSTNode<E> node = theRoot;
        while(node.getLeft() != null) {
            lastStep.set(node,true);
            node = node.getLeft();
        }
        return node;
    }

    @Override
    public E remove(E e) {
        PathStep<E> lastStep = new PathStep<>(null,false);
        BSTNode<E> node = this.findNode(e,lastStep);
        if(node == null) return null;
        else {
            E old = node.getElement();
            if(node.getLeft() == null) // The left subtree is empty.
                this.linkSubtree(node.getRight(),lastStep);
            else if(node.getRight() == null) // The right subtree is empty
                this.linkSubtree(node.getLeft(),lastStep);
            else { // Node has 2 children. Replace the node's element with the minNode of the right subtree.
                lastStep.set(node, false);
                BSTNode<E> min = this.minNode(node.getRight(),lastStep);
                node.setElement(min.getElement());
                this.linkSubtree(min.getRight(),lastStep);
            }
            currSize--;
            return old;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeSetIterator<>(root);
    }
}
