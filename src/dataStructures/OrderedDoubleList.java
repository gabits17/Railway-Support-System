package dataStructures;

import java.io.Serial;

public class OrderedDoubleList < K extends Comparable <K>, V > implements Dictionary <K, V>{
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    DoubleListNode<Entry<K,V>> head;
    DoubleListNode<Entry<K,V>> tail;
    int size;

    public OrderedDoubleList()  {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty( ){
        return size == 0;
    }


    public int size( ){
        return size;
    }

    public V find( K key ){
        DoubleListNode<Entry<K,V>> node = findNode(key);
        if(node != null && node.getElement().getKey().compareTo(key) == 0)
            return node.getElement().getValue();
        return null;
    }

    public V insert( K key, V value ) {
        // chama findNode para encontrar um node com key <key>
        DoubleListNode<Entry<K, V>> node = findNode(key);


        // se node existir e tiver a mesma chave, substitui o valor
        if (node != null && node.getElement().getKey().compareTo(key) == 0) {
            //System.out.println(key);
            V v = node.getElement().getValue();
            node.setElement(new EntryClass<>(key, value));
            return v;
        }

        DoubleListNode<Entry<K, V>> newNode = new DoubleListNode<>(new EntryClass<>(key,value));

        // se estiver vazia
        if(isEmpty())
            head = tail = newNode; // o unico elemento é newNode

        else {
            if (node == head) { // se a posicao para o newNode for na head
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            }
            else if(node == null) { // se a poiscao para o newNode for na tail
                newNode.setPrevious(tail);
                tail.setNext(newNode);
                tail = newNode;
            }
            else { // se a posicao para o newNode for no meio da lista
                newNode.setNext(node);
                newNode.setPrevious(node.getPrevious());
                node.getPrevious().setNext(newNode);
                node.setPrevious(newNode);
            }
        }
        size++;
        return null;
    }

    public V remove( K key ){

        // verifica se existe um node com chave <key>
        DoubleListNode<Entry<K,V>> node = findNode(key);
        V value;

        // se node for null, node nao existe => impossivel remover
        if(node != null && node.getElement().getKey().compareTo(key) == 0) {
            value = node.getElement().getValue();
            if (node == head && node == tail) {
                tail = null;
                head = null;
            } else if (node == head) {
                head = head.getNext();
                head.setPrevious(null);
            } else if (node == tail) {
                tail = tail.getPrevious();
                tail.setNext(null);
            } else {
                node.getPrevious().setNext(node.getNext());
                node.getNext().setPrevious(node.getPrevious());
            }
            size--;
            return value;
        }
        return null;
    }

    public Iterator<Entry<K,V>> iterator(){
        return new DoubleListIterator<>(head,tail);
    }

    // percorre lista comparando as chaves até
    // - encontrar chave igual
    // - encontrar chave maior
    // - chegar ao fim da lista
    // devolve o nó onde parou (ou null quando chega ao fim)
    public DoubleListNode<Entry<K,V>> findNode(K key) {

        // procura o node com chave <key> começando pela head
        DoubleListNode<Entry<K, V>> cur = head;

        // percorre a lista, comparando as chaves até encontrar uma
        // maior ou igual do que <key>
        while (cur != null) {
            if(cur.getElement().getKey().compareTo(key) >= 0)
                return cur;
            cur = cur.getNext();
        }

        return null;
    }
}
