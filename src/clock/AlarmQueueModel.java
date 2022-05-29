/**
 *Created by: Callum Jenkins
 Student Number: 15012241
 Version: 1.2
 Description: Model for the alarm queue view. Handles the priority queue of alarms
 */

package clock;

import java.awt.*;
import java.util.Observable;

public class AlarmQueueModel<T> extends Observable implements PriorityQueue<T> {

    private Node head;
    private int size;

    Dimension PREF_SIZE = new Dimension(400, 400);
    Color PREF_COLOUR = Color.CYAN;

    /**
     * New empty alarm queue
     */
    public AlarmQueueModel() {
        head = null;
    }

    /**
     * Add new item with given priority to the queue
     * @param item - Item to be added
     * @param priority - int priority of the item
     * @throws StringIndexOutOfBoundsException
     */
    @Override
    public void add(Object item, int priority) throws StringIndexOutOfBoundsException {
        PriorityItem newItem = new PriorityItem(item, priority);

        Node newNode = new Node(newItem);

        if (head == null) {
            head = newNode;
            return;
        }

        Node temp = head;
        Node previous = null;


        while (temp != null && ((PriorityItem)temp.getItem()).getPriority() <= priority) {

            previous = temp;
            temp = temp.getNext();
        }

        //If no items had a priority greater (lower number), add new node to the end
        //Previous in this case is the last non-NULL element
        if (temp == null) {
            previous.setNext(newNode);

        } else {

            //If previous is NULL (ie first item had a lower priority (higher number))
            //Add new node as the head
            if (previous == null) {
                newNode.setNext(head);
                head = newNode;

            }
            //Else add new node between temp and previous nodes
            else {
                newNode.setNext(temp);
                previous.setNext(newNode);
            }
        }
    }

    /**
     * @return - Node of the highest priority (lowest number)
     * @throws QueueUnderflowException - If empty, queue has no head
     */
    public Node headNode() throws  QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        return head;
    }

    /**
     *
     * @return - T of the highest priority
     * @throws QueueUnderflowException - If empty, queue has no head
     */
    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        return (T) head.getItem();
    }

    /**
     * Removes the highest priority item from the queue
     * @throws QueueUnderflowException - If empty, nothing to remove
     */
    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        head = head.getNext();
    }

    /**
     * Removes a specified alarm from the queue
     * @param alarm - Alarm to be removed
     * @throws QueueUnderflowException - If empty, nothing to remove
     */
    public void remove(T alarm) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }

        Node temp = head;
        Node previous = null;

        PriorityItem item = (PriorityItem)temp.getItem();

        //Find alarm position in list
        while (temp != null && item.getItem() != alarm) {
            previous = temp;
            temp = temp.getNext();
            item = (PriorityItem) temp.getItem();
        }

        //Item not found
        if (temp == null) {

        } else {

            //If searched item is first in the list
            if (previous == null) {
                head = temp.getNext();

            } else {

                previous.setNext(temp.getNext());
            }
        }
    }

    /**
     * @return - true if head is null
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * @return String representation of the alarm queue
     */
    @Override
    public String toString(){
        String result = "[";

        for (Node node = head; node != null; node = node.getNext()) {
            if (node != head) {
                result += ", ";
            }
            result += node.getItem();
        }
        result = result + "]";

        return result;
    }

    /**
     * @return - int of the number of items in the queue
     */
    public int getSize() {
        size = 0;
        for (Node<T> node = head; node != null; node = node.getNext()) {
            size += 1;
        }
        return size;
    }
}
