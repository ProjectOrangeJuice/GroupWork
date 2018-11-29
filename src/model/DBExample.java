package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBExample {

	//remove me at the end :(
	
	
	
	//Display a table
	private static void displayResourceTable() {
		System.out.println("----Displaying resource table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM resource"); //Your sql goes here
			while(rs.next()) {
				System.out.println("RID: "+rs.getInt("rId")+" Type:" +rs.getString("type") //The index is either a number of the name
				+ " Title: "+rs.getString("title"));
			} //Think of this a bit like the file reader for the games project
				
			
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void insertIntoResource() { //This is a prepared statement. Much safer than creating the SQL string yourself

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO resource (type,title) VALUES (?,?)"); // "?" is a placeholder
	            pstmt.setString(1, "Test");//Make sure you get the types correct (String, int..)
	            pstmt.setString(2, "Oliver Twist");
	            pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void removeAResource() {

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM resource WHERE type=?");//A prepared statement can be used for anything
	            pstmt.setString(1, "Test"); //Including select
	            pstmt.executeUpdate();//Instead of update you'll use something else.. But what? ;)
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		DBHelper.forceUpdate();
		displayResourceTable();
		insertIntoResource();
		displayResourceTable();
		removeAResource();
		displayResourceTable();

	}

}
