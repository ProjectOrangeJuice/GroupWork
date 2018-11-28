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
	private int borrower;
	private final int COPY_ID;
	private Date duration;
	
	/**
	 * Class Constructor
	 * @param resource
	 * @param copyID
	 * @param borrower
	 */
	public Copy(Resource resource, int copyID, int borrower) {
		this.resource = resource;
		this.borrower = borrower;
		this.COPY_ID = copyID;
	}
    
	/**
	 * Sets the borrow variable by inserting the values into the borrowrecords table, then updates the keeper column in the copies table
	 * @param borrower 
	 */
	public void setBorrower(int borrower) { //This is a prepared statement. Much safer than creating the SQL string yourself

		
		this.borrower = borrower;
		
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO borrowRecords (borrowId, copyId, userId, description)"
					+ " VALUES ('?', '?', '?', '?')"); // "?" is a placeholder
	            pstmt.setInt(1, 1);//Make sure you get the types correct (String, int..)
	            pstmt.setInt(2, 1);
	            pstmt.setInt(3, 1);
	            pstmt.setString(4, "Brown hair, smells good");
	            pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE copies SET keeper = ?"); // "?" is a placeholder
	            pstmt.setInt(1, 1);//Make sure you get the types correct (String, int..)
	            pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Method that gets the duration variable
	 * @return duration
	 */
	public Date getDuration(){
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
	
	public void duration(){
		this.duration = duration;
	}

}
