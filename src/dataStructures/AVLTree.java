package dataStructures;

/**
 * AVL tree implementation
 *
 * @author AED team
 * @version 1.0
 *
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value
 */
public class AVLTree<K extends Comparable<K>, V>
        extends AdvancedBSTree<K,V> implements OrderedDictionary<K,V> {

    AVLTree(AVLNode<Entry<K, V>> node) {
        root = node;
    }

    public AVLTree() {
        this(null);
    }

    /**
     * Rebalance method called by insert and remove.  Traverses the path from
     * zPos to the root. For each node encountered, we recompute its height
     * and perform a trinode restructuring if it's unbalanced.
     * the rebalance is completed with O(log n) running time
     */
    void rebalance(AVLNode<Entry<K, V>> zPos) {
        if (zPos.isInternal())
            zPos.setHeight();
        // Improve if possible...
        while (zPos != null) {  // traverse up the tree towards the root
            zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
            if (zPos == null) //reached the root, stop.
                break;
            zPos.setHeight();
            if (!zPos.isBalanced()) {
                // perform a trinode restructuring at zPos's tallest grandchild
                //If yPos (zPos.tallerChild()) denote the child of zPos with greater height.
                //Finally, let xPos be the child of yPos with greater height
                AVLNode<Entry<K, V>> xPos = zPos.tallerChild().tallerChild();

                zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
                ((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight();  // recompute heights
                ((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
                zPos.setHeight();
            }
        }
    }


    @Override
    public V insert(K key, V value) {
        Entry<K, V> e = new EntryClass<>(key, value);
        AVLNode<Entry<K, V>> toInsert = new AVLNode<>(e);

        if (root == null) {
            root = toInsert; return null;
        }

        AVLNode<Entry<K, V>> curr = (AVLNode<Entry<K, V>>) root;
        AVLNode<Entry<K, V>> parent = null;

        while (curr != null) {
            parent = curr;
            if(key.compareTo(curr.element.getKey()) < 0)
                curr = (AVLNode<Entry<K, V>>) curr.getLeft();
            else if(key.compareTo(curr.element.getKey()) > 0)
                curr = (AVLNode<Entry<K, V>>) curr.getRight();
            else { // key already exists, key.compareTo(curr.element.getKey()) == 0
                V toRet = curr.element.getValue();
                curr.setElement(e);
                return toRet; // returns old value
            }
        }

        // links subtree after the insertion
        linkSubtreeInsert(toInsert,parent);
        rebalance(toInsert);
        return null;
    }

    @Override
    public V remove(K key) {
        AVLNode<Entry<K, V>> thisNode = (AVLNode<Entry<K, V>>) findNode(key);
        if (thisNode == null) return null;

        V toRemove = thisNode.getElement().getValue();
        AVLNode<Entry<K, V>> thisParent = (AVLNode<Entry<K, V>>) thisNode.getParent();
        AVLNode<Entry<K, V>> thisLeft = (AVLNode<Entry<K, V>>) thisNode.getLeft();
        AVLNode<Entry<K,V>> thisRight = (AVLNode<Entry<K, V>>) thisNode.getRight();

        if(thisLeft == null) // thisNode has only a right child
            linkSubtreeRemove(thisRight,thisParent,thisNode);
        else if(thisRight == null) // thisNode has only a left child
            linkSubtreeRemove(thisLeft,thisParent,thisNode);
        else { // thisNode has two children
            AVLNode<Entry<K,V>> min = (AVLNode<Entry<K, V>>) minNode(thisRight);
            thisNode.setElement(min.element);
            AVLNode<Entry<K,V>> minRight = (AVLNode<Entry<K, V>>) min.getRight();
            AVLNode<Entry<K,V>> minParent = (AVLNode<Entry<K, V>>) min.getParent();
            linkSubtreeRemove(minRight,minParent,min);
        }
        rebalance(thisNode);
        currentSize--;
        return toRemove;
    }

}
