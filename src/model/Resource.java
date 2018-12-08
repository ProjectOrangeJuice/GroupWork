package model;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @version 1.5
 * */
public abstract class Resource {
	
	private static final long MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
	
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
	private Queue<User> userRequestQueue;
	
	private ArrayList<User> pendingRequests;
	
	protected static ArrayList<Resource> resources = new ArrayList<>();
	
	public static  void loadDatabaseResources() {
		Book.loadDatabaseBooks();
		Laptop.loadDatabaseLaptops();
		DVD.loadDatabaseDVDs();
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
	
	protected static void updateDbValue(String tableName, int resourceId, String field, int data) {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			stmt.executeQuery("update " + tableName + " set " + field + " = " + data + " where rID = " + resourceId); //Your sql goes here
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Copy> getCopies(){
		return copyList;
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
		userRequestQueue = new Queue<User>();
		pendingRequests = new ArrayList<User>();
		
		loadCopyList();
		loadCopyPriorityQueue();
		loadUserQueue();
	} 
	
	public void addCopy(Copy copy) {
		copyList.add(copy);
		freeCopies.addFirst(copy);
		
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			
			stmt.executeUpdate("insert into copies values ("+copy.getCopyID()+","+getUniqueID()+",null,null)");
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
				
				stmt.executeUpdate("insert into copies values ("+copy.getCopyID()+","+getUniqueID()+",null,null)");
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
		applyFines(returnedCopy);
		
		/*If the user request queue is empty, add the copy to the list of free 
		 * copies and mark it as free.*/
		if(userRequestQueue.isEmpty()) {
			freeCopies.add(returnedCopy);
			
			//Gets the user that returned the copy and removes it from
			//there withdrawn copies.
			returnedCopy.getBorrower().removeBorrowedCopy(returnedCopy);
			returnedCopy.setBorrower(null);
			returnedCopy.resetDates();
		} 
		/*If the are user in the queue, reserve this copy for the first user 
		 * in the queue and take that person out of the queue.*/
		else {
			User firstRequest = userRequestQueue.peek();
			
			returnedCopy.resetDates();
			returnedCopy.setBorrower(firstRequest);
			returnedCopy.setBorrowDate(new Date());
			
			noDueDateCopies.add(returnedCopy);
			firstRequest.addBorrowedCopy(returnedCopy);
			userRequestQueue.dequeue();
		}	
	}
	
	private void applyFines(Copy copyToBeReturned) {
		Date today =  new Date();
		Date dueDate = copyToBeReturned.getDueDate();
		
		int daysOverDue = (int)((today.getTime()-dueDate.getTime())/MILLISECONDS_IN_DAY);
		if(daysOverDue>0) {
			int amount = daysOverDue * getDailyFineAmount();
			
			if(amount > getMaxFineAmount()) {
				amount = getMaxFineAmount();
			}
			
			try {
				Connection dbConnection = DBHelper.getConnection();
				PreparedStatement sqlStatement=dbConnection.prepareStatement("INSERT INTO fines (userName,rID,daysOver,amount,dateTime,paid) VALUES (?,?,?,?,?,?)");
				
				sqlStatement.setString(1, copyToBeReturned.getBorrower().getUsername());
				sqlStatement.setInt(2, uniqueID);
				sqlStatement.setInt(3, daysOverDue);
				sqlStatement.setDouble(4, amount);
				
				SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				sqlStatement.setString(5, normalDateFormat.format(today));
				sqlStatement.setInt(6, 0);
				
				sqlStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**Ensures a copy is loaned to the given user if there are available 
	 * copies, else it adds the user to the request queue.
	 * @param user The user that wants to borrow a copy of this resource.
	 * @return If the user has the copy or is in the queue (false)
	 */
	public boolean loanToUser(User user) {	
		/*If there are free copies, mark a copy as borrowed and reserve 
		 * it for the user.*/
		if(!freeCopies.isEmpty()) {
			Copy copyToBorrow = freeCopies.removeFirst();
			
			copyToBorrow.resetDates();
			copyToBorrow.setBorrower(user);
			copyToBorrow.setBorrowDate(new Date());
			
			noDueDateCopies.add(copyToBorrow);
			user.addBorrowedCopy(copyToBorrow);
			return true;
		} 
		/*Else, add the user to the request queue and set the due date
		 * of the borrowed copy with no due date that has been borrowed
		 * the longest.*/
		else {
			userRequestQueue.enqueue(user);
			Copy firstCopy = noDueDateCopies.poll();
			firstCopy.setDueDate();
			return false;
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
		updateDbValue("resource", uniqueID, "title", title);
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		updateDbValue("resource", uniqueID, "year", year);
		this.year = year;
	}
	
	public Copy getCopy(int copyID) {
		for(Copy c: copyList) {
			if(c.getCopyID()==copyID) {
				return c;
			}
		}
		
		return null;
	}
	
	public abstract int getDailyFineAmount();
	
	public abstract int getMaxFineAmount();
	
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
	
	/**
	 * Returns a string with the unique ID and availability of each copy of 
	 * this resource. The information of each copy is on each separate line.
	 * @return A string where each line of the unique ID and availability 
	 * of a copy of this resource.
	 */
	public String getCopyInformation() {
		String copiesInfo="";
		
		for(Copy c: copyList) {
			copiesInfo+=c.toString();
		}
		
		return copiesInfo;
	}
	
	private void loadCopyList() {
		try {
			Connection dbConnection = DBHelper.getConnection(); //get the connection
			Statement sqlStatement = dbConnection.createStatement(); //prep a statement
			ResultSet savedCopies = sqlStatement.executeQuery("SELECT * FROM copies WHERE rID="+uniqueID); //Your sql goes here
			
			copyList.clear();
			freeCopies.clear();
			
			while(savedCopies.next()) {
				String userName=savedCopies.getString("keeper");
	
				if(userName!=null) {
					User borrower;
					borrower=(User)Person.loadPerson(userName);
					
					Date borrowDate=null;
					Date lastRenewalDate=null;
					Date dueDate=null;
					
					try {
						SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						borrowDate= new SimpleDateFormat("dd/MM/yyyy").parse(savedCopies.getString("borrowDate"));
						
						String dbLastRenewal = savedCopies.getString("lastRenewal");
						if(dbLastRenewal!=null) {
							lastRenewalDate= normalDateFormat.parse(dbLastRenewal);
						}
						
						String dbDueDate = savedCopies.getString("dueDate");
						if(dbDueDate!=null) {
							dueDate = normalDateFormat.parse(dbDueDate);
						}
					} catch (ParseException e) {
						System.err.println("Failed to load a date for a copy from the database.");
					}
					
					copyList.add(new Copy(this, savedCopies.getInt("copyID"),
							borrower,savedCopies.getInt("loanDuration"),
							borrowDate, lastRenewalDate, dueDate));
				} else {
					Copy freeCopy=new Copy(this, savedCopies.getInt("copyID"),null,savedCopies.getInt("loanDuration"));
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
	
	private void loadUserQueue() {
		userRequestQueue.clean();
		try {
			Connection conn = DBHelper.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet userRequests=stmt.executeQuery("SELECT * FROM userRequests WHERE rID="+uniqueID+" ORDER BY orderNumber ASC");
			
			while(userRequests.next()) {
				String userName = userRequests.getString("userName");
				User userWithRequest=(User)Person.loadPerson(userName);
				userRequestQueue.enqueue(userWithRequest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void loadPendingRequests() {
		pendingRequests.clear();
		try {
			Connection dbConnection = DBHelper.getConnection();
			Statement sqlStatement = dbConnection.createStatement();
			ResultSet userRequests=sqlStatement.executeQuery("SELECT * FROM requestsToApprove WHERE rID="+uniqueID);
			
			while(userRequests.next()) {
				String userName = userRequests.getString("userName");
				User userWithRequest=(User)Person.loadPerson(userName);
				userRequestQueue.enqueue(userWithRequest);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveUserQueue() {
		LinkedList<User> orderedUsers = userRequestQueue.getOrderedList();
		try {
			Connection dbConnection = DBHelper.getConnection();
			PreparedStatement sqlStatement=dbConnection.prepareStatement("DELETE FROM userRequests");
			sqlStatement.executeUpdate();
			sqlStatement = dbConnection.prepareStatement("INSERT INTO userRequests VALUES ("+uniqueID+",?,?)");
			
			int orderNr=1;
			User current = orderedUsers.pollFirst();
			while(current!=null) {
				sqlStatement.setString(1, current.getUsername());
				sqlStatement.setInt(2, orderNr);
				sqlStatement.executeUpdate();
				current = orderedUsers.pollFirst();
				orderNr++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveCopiesToDB() {
		try {
			Connection dbConnection = DBHelper.getConnection();
			PreparedStatement sqlStatement=dbConnection.prepareStatement("DELETE FROM copies WHERE rID="+uniqueID);
			sqlStatement.executeUpdate();
			
			SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			sqlStatement = dbConnection.prepareStatement("INSERT INTO copies VALUES(?,?,?,?,?,?,?)");
			sqlStatement.setInt(2, uniqueID);
			
			for(Copy copy: copyList) {
				sqlStatement.setInt(1, copy.getCopyID());
				
				String userName = null;
				if(copy.getBorrower()!=null) {
					userName = copy.getBorrower().getUsername();
				}
				sqlStatement.setString(3, userName);
				sqlStatement.setInt(4, copy.getLoanDuration());
				sqlStatement.setString(5, normalDateFormat.format(copy.getBorrowDate()));
				sqlStatement.setString(6, normalDateFormat.format(copy.getLastRenewal()));
				sqlStatement.setString(7, normalDateFormat.format(copy.getDueDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Gets the number of copies that this resource has, wether free or borrowed.
	 * @return The nr of copies of this resource.
	 */
	public int getNrOfCopies() {
		return copyList.size();
	}
	
	public static ArrayList<Resource> getResources() {
		return resources;
	}
	
	public ArrayList<User> getPendingRequests() {
		return pendingRequests;
	}
	
	public void addPendingRequest(User requester) {
		pendingRequests.add(requester);
		
		try {
			Connection dbConnection = DBHelper.getConnection();
			PreparedStatement sqlStatement = dbConnection.prepareStatement("INSERT INTO requestsToApprove VALUES (?,?)");
			sqlStatement.setInt(1, uniqueID);
			sqlStatement.setString(2,requester.getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//public static void main(String args[]) {
		/*Resource r1 = new Resource(1,"Eduroam sucks",2018,null);
		Copy c1= new Copy(r1,1,null);
		Copy c2 = new Copy(r1,2,null);
		
		c1.setBorrowDate(new Date(100));
		c2.setBorrowDate(new Date(200));
		
		r1.noDueDateCopies.add(c1);
		r1.noDueDateCopies.add(c2);
		
		System.out.println(r1.noDueDateCopies.poll().getCOPY_ID());
		System.out.println(r1.noDueDateCopies.poll().getCOPY_ID());*/
		
		/*DBHelper.tableCheck();
		
		Laptop l = new Laptop(1,"VX15", 2017,null,"Acer","Aspire","Windows");
		Book b = new Book(2,"Great Expectations", 1880, null, "CharlesDickens", "Wanker");
		DVD d =  new DVD(3,"Iron Man", 2008, null, "Favreau", 200);
		
		User user = new User("Alexandru", "Alex","Dasc","0777777777","902 Kilvey", "SA2 8PU", null,0);
		Copy test =  new Copy(b, 1, user, 7);
		
		test.setDueDate(new Date(118,11,2));
		
		System.out.println("----Displaying resource table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM fines"); //Your sql goes here
			while(rs.next()) {
				System.out.println("ID: "+rs.getInt("fineId")+" username: " +rs.getString("username") //The index is either a number of the name
				+ " rID: "+rs.getInt("rID")+" daysOver: "+rs.getInt("daysOver")+
				" amount: "+rs.getDouble("amount")+" date and time: "+
				rs.getString("dateTime")+" paid: "+rs.getInt("paid"));
			} //Think of this a bit like the file reader for the games project
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		l.applyFines(test);
		
		System.out.println("----Displaying resource table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM fines"); //Your sql goes here
			while(rs.next()) {
				System.out.println("ID: "+rs.getInt("fineId")+" username: " +rs.getString("username") //The index is either a number of the name
				+ " rID: "+rs.getInt("rID")+" daysOver: "+rs.getInt("daysOver")+
				" amount: "+rs.getDouble("amount")+" date and time: "+
				rs.getString("dateTime")+" paid: "+rs.getInt("paid"));
			} //Think of this a bit like the file reader for the games project
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
	public boolean contains(String search) {
		if(title.toUpperCase().contains(search.toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}
}
