package dataStructures;
import dataStructures.exceptions.NoSuchElementException;

import java.io.Serial;

class BSTKeyOrderIterator<K,V> implements Iterator<Entry<K,V>> {
	/**
	 * Serial Version UID of the Class
	 */
	@Serial
	private static final long serialVersionUID = 0L;
	
	protected BSTNode<Entry<K,V>> root;

	protected Stack<BSTNode<Entry<K,V>>> p;


	BSTKeyOrderIterator(BSTNode<Entry<K,V>> root){
		this.root = root;
		rewind();
	}
	
	private void pushPathToMinimum(BSTNode<Entry<K,V>> node) {
		BSTNode<Entry<K,V>> next =node;
		while (next != null) {
			p.push(next);
			next = next.getLeft();

		}

	}

	//O(1) para todos os casos
	public boolean hasNext(){
		 return !p.isEmpty();
	 }


    public Entry<K,V> next( ) throws NoSuchElementException {
    	if (!hasNext()) throw new NoSuchElementException();
    	else {
    		BSTNode<Entry<K,V>> next = p.pop();
			pushPathToMinimum(next.getRight());
			return next.getElement();
    	}
    }

    public void rewind( ){
		p = new StackInList<>();
    	pushPathToMinimum(root);
    }
}
