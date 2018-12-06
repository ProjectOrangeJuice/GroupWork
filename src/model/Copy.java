package model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @author Joe Wright
 *
 */
public class Copy implements Comparable<Copy>{
	
	private Resource resource;
	private User borrower;
	private final int copyID;
	private int loanDuration;
	private Date borrowDate;
	private Date lastRenewal;
	private Date dueDate;
	
	/**
	 * Class Constructor
	 * @param resource
	 * @param copyID
	 * @param borrower
	 */
	public Copy(Resource resource, int copyID, User borrower) {
		this.resource = resource;
		this.borrower = borrower;
		this.copyID = copyID;
		
		loanDuration=7;
		borrowDate=null;
		lastRenewal=null;
		dueDate=null;
	}
    
	/**
	 * Sets the borrow variable by inserting the values into the borrowrecords table, then updates the keeper column in the copies table
	 * @param user
	 */
	public void setBorrower(User user){
		if (user != null) {
			try {
				Connection conn = DBHelper.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrowRecords (borrowId, copyId, userId, description)"
						+ " VALUES ('?','?','?')");
	            pstmt.setInt(2, this.getCopyID());
	            pstmt.setString(3, user.getUsername());
	            pstmt.setString(4, "Not sure what to put in description.");
	            pstmt.executeUpdate();
	            conn.close();
			} catch (SQLException e) { 
				System.out.println("Failed to add copy borrow to borrowRecoreds.");
				e.printStackTrace();
			}
		}
		
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE copies SET keeper = ? WHERE copyID = ?");
			if (user == null) {
				pstmt.setString(1, null);
			} else {
				pstmt.setString(1, user.getUsername());
			}
            pstmt.setInt(2, this.getCopyID());
            pstmt.executeUpdate();
            conn.close();
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
		}
	}
	
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
}
