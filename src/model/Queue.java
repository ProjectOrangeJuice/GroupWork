package model;
import java.util.NoSuchElementException;

/**
 * 
 * A class that implements a queue.  It is your job to complete this class.  Your queue
 * will use a linked list constructed by LinkedListElements.  However, your queue must be general and allow
 * setting of any type of Object.  Also you cannot use ArrayLists or arrays (you will get zero).  
 * @author you
 *
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
	    //queue is empty so it has no head and no tail
		head=null;
		tail=null;
	}
	
	/**
	 * Returns true if the queue is empty
	 */
	public boolean isEmpty() {
		if(head==null && tail==null){
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Returns the element at the head of the queue
	 * @throws NoSuchElementException
	 */
	public E peek() throws NoSuchElementException {
		if(!isEmpty()){
			return head.getElement();
		}
		//throw exception if the queue is empty
		else {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Removes the front element of the queue.
	 * @throws NoSuchElementException
	 */
	public void dequeue() throws NoSuchElementException 
	{
		if(!isEmpty()){
			if(head!=tail){
				head=head.getNext();
			}
			//make sure to set both tail and head to null if we dequeue a queue
			//with one element
			else {
				head=head.getNext();
				tail=null;
			}
		}
		//throw exception if the queue is empty
		else {
			throw new NoSuchElementException();
		}
	}
	
	/**
	 * Puts an element on the back of the queue.
	 */
	public void enqueue(E element) {
		QueueElement<E> newTail=new QueueElement<>(element, null);
		if(!isEmpty()) {
			tail.setNext(newTail);
			tail=newTail;
		}
		// a queue of one element has its head and tail as that element
		else {
			tail=newTail;
			head=newTail;
		}
		
	}
	
	/**
	 * Method to print the full contents of the queue in order from head to tail.
	 */
	public void print() {
		if(!isEmpty()) {
			QueueElement<E> nextElement=head;
			while(nextElement!=null)
			{
				System.out.println(nextElement.getElement().toString()+",");
				nextElement=nextElement.getNext();
			}
			System.out.print("\n");
		} else {
			System.out.println("The queue is empty.");
		}
	}
}
