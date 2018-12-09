package model;

/**
 * Represents an object that encapsulates an element it store and a reference to 
 * the next Queue Element.
 * 
 * @param <E> Type of element to be stored.
 * @author Alexandru Dascalu
 */
public class QueueElement<E> {
    private E element; // the element contained in this linked list
    private QueueElement<E> next; // the next element of the linked list

    /**
     * Makes a new queue element with the given element and next queue element.
     * @param element The element to be stored.
     * @param next The next QueueElement in the queue.
     */
    public QueueElement(E element, QueueElement<E> next) {
        this.element = element;
        this.next = next;
    }

    /**
     * Method to set the element.
     * @param element The new value of the element.
     */
    public void setElement(E element) {
        this.element = element;
    }

    /**
     * Method to set the next linked list element.
     * @param next The new next element.
     */
    public void setNext(QueueElement<E> next) {
        this.next = next;
    }

    /**
     * Method to get the element.
     * @return The element being stored.
     */
    public E getElement() {
        return element;
    }

    /**
     * Method to get the next linked list element.
     * @return The next element in the queue.
     */
    public QueueElement<E> getNext() {
        return next;
    }

}
