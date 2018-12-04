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
public class Copy {
	
	private Resource resource;
	private User borrower;
	private final int COPY_ID;
	private int duration;
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
		this.COPY_ID = copyID;
	}
    
	/**
	 * Sets the borrow variable by inserting the values into the borrowrecords table, then updates the keeper column in the copies table
	 * @param firstRequest 
	 */
	public void setBorrower(User user){
		if (user != null) {
			try {
				Connection conn = DBHelper.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrowRecords (borrowId, copyId, userId, description)"
						+ " VALUES ('?','?','?')");
	            pstmt.setInt(1, this.getCOPY_ID());
	            pstmt.setString(2, user.getUsername());
	            pstmt.setString(2, "Not sure what to put in description.");
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
            pstmt.setInt(1, this.getCOPY_ID());
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
	public int getDuration(){
		return duration;
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
	public int getCOPY_ID(){
		return COPY_ID;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate=dueDate;
	}

	public User getBorrower() {
		return this.borrower;
	}

}
