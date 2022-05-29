/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 2.2
 Description: Node class that can be used to create singly and doubly linked lists of type T items
 */

package clock;

/**
 * Node class to store an item and a link to the next/previous node
 * Can be used as a singly or doubly linked list
 *
 * @param <T> The type of object stored
 */
public class Node<T> {

    private T item;
    private Node<T> next;
    private Node<T> previous;

    public Node(T item) {
        this.item = item;
    }

    public Node(T item, Node<T> next) {
        this.item = item;
        this.next = next;
    }

    /**
     * @return item stored in the node
     */
    public T getItem() {
        return item;
    }


    /**
     * Sets the pointer to a given node
     * @param next - Pointer to the next node
     */
    public void setNext(Node<T> next) {this.next = next;}


    /**
     * @return the next node in the list
     */
    public Node<T> getNext() {
        return next;
    }


    /**
     * Sets the pointer to a given node
     * @param previous - Pointer to the previous node
     */
    public void setPrevious(Node<T> previous) {this.previous = previous;}


    /**
     * @return the previous node in the list
     */
    public Node<T> getPrevious() {
        return previous;
    }

}

