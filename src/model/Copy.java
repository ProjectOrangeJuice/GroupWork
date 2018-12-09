package model;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * 
 * @author Joe Wright
 *
 */
public class Copy implements Comparable<Copy>{
	
	private final Resource resource;
	private User borrower;
	private final int copyID;
	private int loanDuration;
	private Date borrowDate;
	private Date lastRenewal;
	private Date dueDate;
	
	private static void updateDBValue(int copyID, String field, String data) {
		try {
			Connection connectionToDB = DBHelper.getConnection();
			PreparedStatement sqlStatement = connectionToDB.prepareStatement("UPDATE copies "
					+ "set " + field + " = ? WHERE copyID=?");
			sqlStatement.setString(1,data);
			sqlStatement.setInt(2,copyID);
			sqlStatement.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private static void updateDBValue(int copyID, String field, int data) {
		try {
			Connection connectionToDB = DBHelper.getConnection();
			PreparedStatement sqlStatement = connectionToDB.prepareStatement("UPDATE copies "
					+ "set " + field + " = ? WHERE copyID=?");
			sqlStatement.setInt(1,data);
			sqlStatement.setInt(2,copyID);
			sqlStatement.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public Copy(Resource resource, int copyID, User borrower, int loanDuration, 
			Date borrowDate, Date lastRenewal, Date dueDate) {
		this.resource = resource;
		this.borrower = borrower;
		this.copyID = copyID;
		
		this.loanDuration=loanDuration;
		this.borrowDate=borrowDate;
		this.lastRenewal=lastRenewal;
		this.dueDate=dueDate;
	}
	
	/**
	 * Class Constructor
	 * @param resource
	 * @param copyID
	 * @param borrower
	 */
	public Copy(Resource resource, int copyID, User borrower, int loanDuration) {
		this.resource = resource;
		this.borrower = borrower;
		this.copyID = copyID;
		
		this.loanDuration=loanDuration;
		borrowDate=null;
		lastRenewal=null;
		dueDate=null;
	}
    
	/**
	 * Sets the borrow variable by inserting the values into the borrowrecords table, then updates the keeper column in the copies table
	 * @param firstRequest 
	 */
	public void setBorrower(User user){
		if (user != null) {
			try {
				Connection dbConnection = DBHelper.getConnection();
				PreparedStatement preparedUpdateStatement = dbConnection.prepareStatement("INSERT INTO borrowRecords (copyId, username, description)"
						+ " VALUES (?,?,?)");
	            preparedUpdateStatement.setInt(1, getCopyID());
	            preparedUpdateStatement.setString(2, user.getUsername());
	            preparedUpdateStatement.setString(3, "Not sure what to put in description.");
	            preparedUpdateStatement.executeUpdate();
	            dbConnection.close();
			} catch (SQLException e) { 
				System.out.println("Failed to add copy borrow to borrowRecoreds.");
				e.printStackTrace();
			}
		}
		
		try {
			Connection dbConnection = DBHelper.getConnection();
			PreparedStatement preparedUpdateStatement = dbConnection.prepareStatement("UPDATE copies SET keeper = ? WHERE copyID = ?");
			if (user == null) {
				preparedUpdateStatement.setString(1, null);
			} else {
				preparedUpdateStatement.setString(1, user.getUsername());
			}
            preparedUpdateStatement.setInt(2, this.getCopyID());
            preparedUpdateStatement.executeUpdate();
            dbConnection.close();
		} catch (SQLException e) { 
			System.out.println("Failed to update copies database.");
			e.printStackTrace();
		}
		
		this.borrower = user; //Do this last just in case the queries haven't worked.
	}
	
	/**
	 * Method that gets the duration variable
	 * @return duration
	 */
	public int getLoanDuration(){
		return loanDuration;
	}
	
	/**
	 * Method that gets the resource variable
	 * @return resource
	 * 
	 */
	public Resource getResource(){
		return resource;
	}
	
	/**
	 * Method that gets the COPY_ID final variable
	 * @return COPY_ID
	 * @author Joe Wright
	 */
	public int getCopyID(){
		return copyID;
	}
	
	public void setLoanDuration(int duration){
		updateDBValue(copyID,"loanDuration", duration);
		this.loanDuration = duration;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate() {
		if(dueDate==null) {
			Date afterBorrowDuration=(Date)borrowDate.clone();
			afterBorrowDuration.setDate(afterBorrowDuration.getDate()+loanDuration);
			Date today = new Date(System.currentTimeMillis());
			
			if(afterBorrowDuration.after(today)) {
				dueDate=afterBorrowDuration;
			} else {
				today.setDate(today.getDate()+1);
				dueDate=today;
			}
			
			SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			updateDBValue(copyID,"dueDate",normalDateFormat.format(dueDate));
		}
	}
	
	/*
	public void setDueDate(Date d) {
		dueDate=d;
	}*/
	
	public boolean checkRenewal() {
		if(dueDate==null) {
			Date nextRenewal;
			
			if(lastRenewal!=null) {
				nextRenewal=(Date)lastRenewal.clone();
			} else {
				nextRenewal=(Date)borrowDate.clone();
			}
			
			nextRenewal.setDate(nextRenewal.getDate()+loanDuration);
			Date today=new Date(System.currentTimeMillis());
			
			if(nextRenewal.before(today)) {
				lastRenewal=nextRenewal;
				
				SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				updateDBValue(copyID,"lastRenewal", normalDateFormat.format(lastRenewal));
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Date getBorrowDate() {
		return borrowDate;
	}
	
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
		
		SimpleDateFormat normalDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		updateDBValue(copyID,"borrowDate", normalDateFormat.format(borrowDate));
	}

	public User getBorrower() {
		return this.borrower;
	}
	
	public boolean isBorrowed() {
		return (borrower!=null);
	}
	
	public Date getLastRenewal() {
		return lastRenewal;
	}
	
	public void resetDates() throws IllegalArgumentException {
		if(borrower==null) {
			borrowDate=null;
			dueDate=null;
			lastRenewal=null;
			
			updateDBValue(copyID,"borrowDate",null);
			updateDBValue(copyID,"dueDate",null);
			updateDBValue(copyID,"lastRenewal",null);
		} else {
			throw new IllegalStateException("You are trying to reset borrow,"+
					" due and last renewal dates while this copy is still borrowed!");
		}
		
	}
	
	/**
	 * Returns a string representation of this copy suitable to display to any 
	 * user browsing the library. For this reason, it only says the copy ID 
	 * and whether it is available or not and leaves the rest of the 
	 * information, which should not be publicly accessible.
	 * @return Shortened representation of this copy suitable for public 
	 * viewing.
	 */
	public String toString() {
		String copy="CopyID: "+copyID+", Available: ";
		
		String available;
		if(borrower==null) {
			available="yes.";
		} else {
			available="no.";
		}
		
		copy+=available+"\n";
		return copy;
	}
	
	public int compareTo(Copy otherCopy) {
		if(borrowDate.before(otherCopy.getBorrowDate())) {
			return -1;
		}
		else if(borrowDate.after(otherCopy.getBorrowDate())) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public String getBorrowerIDSafely() {
		if (this.borrower == null) {
			return null;
		} else {
			return this.borrower.getUsername();
		}
	}
}
