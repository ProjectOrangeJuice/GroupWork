package model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
import javafx.scene.image.Image;

/**This class represents a resource that the library has to offer. A resource 
 * is a certain book, DVD, type of laptop, etc. and consists of multiple 
 * copies that can be borrowed or requested. It has a unique ID, a title, 
 * the year it came out and a thumbnail image.
 * @author Kane Miles
 * @author Alexandru Dascalu
 * @version 1.0
 * */
public abstract class Resource {
	
	/**A unique number that identifies this resource.*/
	protected final int uniqueID;
	
	/**The title of this resource.*/
	protected String title;
	
	/**The year this resource came out.*/
	protected int year;
	
	/**The thumbnail Image of this resource.*/
	protected Image thumbnail;
	
	/**A list of all the copies of this resource.*/
	private ArrayList<Copy> copyList;
	
	/**A list of all copies of this resource that are not currently borrowed.*/
	private LinkedList<Copy> freeCopies;
	
	/**A queue of all borrowed copies who have no due date set.*/
	private PriorityQueue<Copy> noDueDateCopies;
	
	/**A queue of user who have requested a copy of this resource but have not 
	 * gotten one because there is no free copy.*/
	private Queue<User> userRequest;
	
	private static ArrayList<Resource> resources = new ArrayList<>();
	
	public static  ArrayList<Resource> loadDatabaseResources() {
		Book.loadDatabaseBooks(resources);
		Laptop.loadDatabaseLaptops(resources);
		DVD.loadDatabaseDVDs(resources);
		
		return resources;
	}
	
	protected static void updateDbValue(String tableName, int resourceId, String field, String data) {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			stmt.executeQuery("update " + tableName + " set " + field + " = " + data + " where rID = " + resourceId); //Your sql goes here
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	/*private void loadFreeCopiesList() {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM freeCopies WHERE rID="+uniqueID); //Your sql goes here
			freeCopies.clear();
			
			while(rs.next()) {
				int copyID = rs.getInt("copyID");
				
				Copy currentCopy = null;
				for(Copy c: copyList) {
					if(c.getCOPY_ID()==copyID) {
						currentCopy=c;
						break;
					}
				}
				
				if(currentCopy==null) {
					throw new IllegalStateException("No copy in the copy List "+
							"with the ID from the freeCopies list.");
				} else {
					freeCopies.addFirst(currentCopy);
				}
			} 
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}*/
	
	/**
	 * Makes a new resource whose details are the given arguments.
	 * @param uniqueID The unique number that identifies this resource.
	 * @param title The title of this resource.
	 * @param year The year this resource appeared.
	 * @param thumbnail A small image of this resource.
	 */
	public Resource(int uniqueID, String title, int year, Image thumbnail) {
		this.uniqueID = uniqueID;
		this.title = title;
		this.year = year;
		this.thumbnail = thumbnail;
		
		/*just make new empty instances of the containers used for tracking
		 *  information about borrowing and returning copies*/
		copyList = new ArrayList<Copy>();
		freeCopies = new LinkedList<Copy>();
		noDueDateCopies = new PriorityQueue<Copy>();
		userRequest = new Queue<User>();
		
		loadCopyList();
		loadCopyPriorityQueue();
	} 
	
	public void addCopy(Copy copy) {
		copyList.add(copy);
		freeCopies.addFirst(copy);
		
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			
			stmt.executeUpdate("insert into copies values ("+copy.getCOPY_ID()+","+getUniqueID()+",null,null)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addCopies(Collection<Copy> copies) {
		copyList.addAll(copies);
		freeCopies.addAll(copies);
		
		for(Copy copy: copies) {
			try {
				Connection conn = DBHelper.getConnection(); //get the connection
				Statement stmt = conn.createStatement(); //prep a statement
				
				stmt.executeUpdate("insert into copies values ("+copy.getCOPY_ID()+","+getUniqueID()+",null,null)");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public void saveFreeCopies() {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			
			for(Copy c: freeCopies) {
				stmt.executeUpdate("insert into freeCopies values ("+c.getCOPY_ID()+","+uniqueID+")");
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}*/
	
	/**This method ensures a returned copy is marked a free copy or that it is
	 *  reserved for the user at the front of the request queue.
	 *  @param returnedCopy The copy being returned.*/
	public void processReturn(Copy returnedCopy) {
		/*If the user request queue is empty, add the copy to the list of free 
		 * copies and mark it as free.*/
		if(userRequest.isEmpty()) {
			freeCopies.add(returnedCopy);
			
			//Gets the user that returned the copy and removes it from
			//there withdrawn copies.
			returnedCopy.getBorrower().removeBorrowedCopy(returnedCopy);
			returnedCopy.setBorrower(null);
		} 
		/*If the are user in the queue, reserve this copy for the first user 
		 * in the queue and take that person out of the queue.*/
		else {
			User firstRequest = userRequest.peek();
			returnedCopy.setBorrower(firstRequest);
			userRequest.dequeue();
		}	
	}
	
	/**Ensures a copy is loaned to the given user if there are available 
	 * copies, else it adds the user to the request queue.
	 * @param user The user that wants to borrow a copy of this resource.
	 */
	public void loanToUser(User user) {	
		/*If there are free copies, mark a copy as borrowed and reserve 
		 * it for the user.*/
		if(!freeCopies.isEmpty()) {
			Copy copyToBorrow = freeCopies.removeFirst();
			copyToBorrow.setBorrower(user);
			user.addBorrowedCopy(copyToBorrow);
		} 
		/*Else, add the user to the request queue and set the due date
		 * of the borrowed copy with no due date that has been borrowed
		 * the longest.*/
		else {
			userRequest.enqueue(user);
			Copy firstCopy = noDueDateCopies.poll();
			firstCopy.setDueDate(null);
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
	
	public Copy getCopy(int copyID) {
		for(Copy c: copyList) {
			if(c.getCOPY_ID()==copyID) {
				return c;
			}
		}
		
		return null;
	}
	
	public static Resource getResource(int resourceID) {
		for(Resource r: resources) {
			if(r.getUniqueID()==resourceID) {
				return r;
			}
		}
		
		return null;
	}
	
	public String toString() {
		return "Title: "+title + "\nID: " + uniqueID + "\nYear: " + year;
	}
	
	private void loadCopyList() {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM copies WHERE rID="+uniqueID); //Your sql goes here
			copyList.clear();
			freeCopies.clear();
			
			while(rs.next()) {
				String userName=rs.getString("keeper");
		
				if(userName!=null) {
					User borrower;
					borrower=(User)Person.loadPerson(userName);
					copyList.add(new Copy(this, rs.getInt("copyID"),borrower));
				} else {
					Copy freeCopy=new Copy(this, rs.getInt("copyID"),null);
					freeCopies.add(freeCopy);
				}
			} 
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	private void loadCopyPriorityQueue() {
		noDueDateCopies.clear();
		
		for(Copy c: copyList) {
			if(c.getDueDate()==null && c.getBorrower()!=null) {
				noDueDateCopies.add(c);
			}
		}
	}
	
	public int getFreeCopies(){
		int size = freeCopies.size();
		return size;
	}

	/*public static void main(String args[]) {
		Resource r1 = new Resource(1,"Eduroam sucks",2018,null);
		Copy c1= new Copy(r1,1,null);
		Copy c2 = new Copy(r1,2,null);
		
		c1.setBorrowDate(new Date(100));
		c2.setBorrowDate(new Date(200));
		
		r1.noDueDateCopies.add(c1);
		r1.noDueDateCopies.add(c2);
		
		System.out.println(r1.noDueDateCopies.poll().getCOPY_ID());
		System.out.println(r1.noDueDateCopies.poll().getCOPY_ID());
	}*/
}
