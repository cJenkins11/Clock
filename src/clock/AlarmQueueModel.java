package clock;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class AlarmQueueModel<T> extends Observable implements PriorityQueue<T> {

    private Node head;
    //private Node tail;
    private int size;

    Dimension PREF_SIZE = new Dimension(300, 400);
    Color PREF_COLOUR = Color.CYAN;

    public AlarmQueueModel() {
        head = null;

        //Changes
        //tail = null;
    }

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

    public Node headNode() throws  QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        return head;
    }

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        return (T) head.getItem();
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        head = head.getNext();
    }

    public void remove(T alarm) throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        }
        //Node next = ((Node) alarm).getNext();

        Node temp = head;
        Node previous = null;

        PriorityItem item = (PriorityItem)temp.getItem();

        //Find alarm position in list
        int count = 0;
        while (temp != null && item.getItem() != alarm) {
            /*System.out.println("in loop");
            System.out.println("item: " + item.getItem());
            System.out.println("Target: " + alarm);*/

            /*System.out.println("Previous: " + previous);
            System.out.println("\nTempItem: " + temp.getItem() + "\n");
            System.out.println("\nTempItem Priority item: " + item.getItem() + "\n");
            System.out.println("Temp: " + temp + "\n");*/

            /*if (item.getItem() == alarm) {
                System.out.println("Found it");
            }*/

            previous = temp;
            temp = temp.getNext();
            item = (PriorityItem) temp.getItem();
            count++;
        }

        if (item.getItem() == alarm) {
            System.out.println("Found it");
        }
        System.out.println(count);

        System.out.println("Previous: " + previous);
        System.out.println("Temp: " + temp);

        //Item not found
        if (temp == null) {

            System.out.println("alarm not found");

        } else {

            //If searched item is first in the list
            if (previous == null) {

                System.out.println("Alarm was first in the queue");
                head = temp.getNext();

            } else {

                System.out.println("Alarm was in the queue");
                previous.setNext(temp.getNext());

            }


        }

        /*for (Node node = head; node != null; node = node.getNext()) {

            if (node == alarm) {

                previous.setNext(node.getNext());


            } else {
                previous = node;
            }


            *//*if (node != head) {
                result += ", ";
                //System.out.println("Node was not the head node");
            }
            result += node.getItem();*//*
            //System.out.println("Node item added to result string");
        }*/

        /*previous.setNext(next);
        next.setPrevious(previous);*/
        //((Node) alarm).setNext(null);


    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString(){
        String result = "[";

        for (Node node = head; node != null; node = node.getNext()) {
            if (node != head) {
                result += ", ";
                //System.out.println("Node was not the head node");
            }
            result += node.getItem();
            //System.out.println("Node item added to result string");
        }

        result = result + "]";

        return result;
    }

    public void setAlarm(String setTime, String am_pm, String name, int daysRepeating) {
    }

    public void editAlarm(Object item) {
    }

    public int getSize() {
        size = 0;
        for (Node<T> node = head; node != null; node = node.getNext()) {
            size += 1;
        }
        return size;
    }

}
