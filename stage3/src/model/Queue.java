package model;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A class that implements a queue. It encapsulates the head an tail of linked 
 * structure. The linked element are QueueElement objects. Provides all operations
 * a queue should provide.
 * @param <E> The type of element contained by this queue.
 * @author Alexandru Dascalu
 */
public class Queue<E> {

    /**
     * The head of the queue, represented by a QueueElement object.
     */
    private QueueElement<E> head;

    /**
     * The tail of the queue, represented by a QueueElement object.
     */
    private QueueElement<E> tail;

    /**
     * Constructs an empty Queue.
     */
    public Queue() {
        // queue is empty so it has no head and no tail
        head = null;
        tail = null;
    }

    /**
     * Returns true if the queue is empty.
     * @return True if the queue is empty, false if not.
     */
    public boolean isEmpty() {
        if (head == null && tail == null) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the element at the head of the queue.
     * @return element at the head of the queue.
     * 
     * @throws NoSuchElementException If the queue is empty.
     */
    public E peek() throws NoSuchElementException {
        if (!isEmpty()) {
            return head.getElement();
        }
        // throw exception if the queue is empty
        else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Removes the front element of the queue.
     * 
     * @throws NoSuchElementException If the queue is empty.
     */
    public void dequeue() throws NoSuchElementException {
        if (!isEmpty()) {
            if (head != tail) {
                head = head.getNext();
            }
            // make sure to set both tail and head to null if we dequeue a queue
            // with one element
            else {
                head = head.getNext();
                tail = null;
            }
        }
        // throw exception if the queue is empty
        else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Puts an element on the back of the queue.
     * @param element The element to be enqueued.
     */
    public void enqueue(E element) {
        QueueElement<E> newTail = new QueueElement<>(element, null);
        if (!isEmpty()) {
            tail.setNext(newTail);
            tail = newTail;
        }
        // a queue of one element has its head and tail as that element
        else {
            tail = newTail;
            head = newTail;
        }

    }

    /**
     * Method to print the full contents of the queue in order from head to
     * tail.
     */
    public void print() {
        if (!isEmpty()) {
            QueueElement<E> nextElement = head;
            while (nextElement != null) {
                System.out.println(nextElement.getElement().toString() + ",");
                nextElement = nextElement.getNext();
            }
            System.out.print("\n");
        }
        else {
            System.out.println("The queue is empty.");
        }
    }

    /**
     * Empties this queue of all elements.
     */
    public void clean() {
        head = null;
        tail = null;
    }

    /**
     * Builds an returns a linked list of elements contained by the QueueElements
     * of the queue, in the order they are in the queue.
     * @return a linked list with the elements in the order they are in the queue.
     */
    public LinkedList<E> getOrderedList() {
        LinkedList<E> orderedList = new LinkedList<E>();
        QueueElement<E> current = head;
        while (current != null) {
            orderedList.add(current.getElement());
            current = current.getNext();
        }

        return orderedList;
    }
}
