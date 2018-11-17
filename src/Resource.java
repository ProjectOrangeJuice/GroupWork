import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javafx.scene.image.Image;

public abstract class Resource {	
	protected int uniqueID;
	protected String title;
	protected int year;
	protected Image thumbnail;
	
	private ArrayList<Copy> copyList;
	private LinkedList<Copy> freeCopies;
	private PriorityQueue<Copy> noDueDateCopies;
	private Queue<User> userRequest;
	
	public Resource(int uniqueID, String title, int year, Image thumbnail) {
		this.uniqueID = uniqueID;
		this.title = title;
		this.year = year;
		this.thumbnail = thumbnail;
		
		copyList = new ArrayList<Copy>();
		freeCopies = new LinkedList<Copy>();
		noDueDateCopies = new PriorityQueue<Copy>();
		userRequest = new Queue<User>();
		
	}
	
	public void loanToUser(User user) 
	{	
		if(!freeCopies.isEmpty()) 
		{
			Copy copyToBorrow = freeCopies.removeFirst();
			copyToBorrow.setBorrower(user);
			user.addBorrowedCopy(copyToBorrow);
		} 
		else 
		{
			userRequest.enqueue(user);
			Copy firstCopy = noDueDateCopies.poll();
			firstCopy.setDueDate();
		}
		
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
