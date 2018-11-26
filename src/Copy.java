import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sun.corba.se.pept.transport.Connection;

public class Copy {
	
	private Resource resource;
	private int borrower;
	private final int COPY_ID;
	
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
	 * 
	 * @param borrower
	 */
	private static void setBorrower() { //This is a prepared statement. Much safer than creating the SQL string yourself

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE borrowRecords SET borrowId = ?, copyId = ?, userId = ?, description = ?"); // "?" is a placeholder
	            pstmt.setInt(1, 1);//Make sure you get the types correct (String, int..)
	            pstmt.setInt(2, 1);
	            pstmt.setInt(3, 1);
	            pstmt.setString(4, "Brown hair, smells good");
	            pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 */
	public void setDueDate() {
		
	}
	
	/**
	 * Method that gets the duedate variable
	 * @return duedate
	 */
	public Date getDueDate(){
		return duedate;
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
	 */
	public Resource getResource(){
		return resource;
	}
	
	/**
	 * Method that gets the COPY_ID final variable
	 * @return COPY_ID
	 */
	public int getCOPY_ID(){
		return COPY_ID;
	}
	
	private static void setResource() { //This is a prepared statement. Much safer than creating the SQL string yourself

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE resource SET RId = ?,type = ?, title =?, description= ?  "); // "?" is a placeholder
			    pstmt.setInt(1, 1);
	            pstmt.setString(2, "Book");//Make sure you get the types correct (String, int..)
	            pstmt.setString(3, "Oliver Twist");
	            pstmt.setString(4, "Book about some poor boy")
	            pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
