package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBExample {

	//remove me at the end :(
	
	
	
	//Display a table
	private static void displayBookTable() {
		System.out.println("----Displaying resource table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here
			while(rs.next()) {
				System.out.println("RID: " + rs.getInt("rId") + " Author: " + rs.getString("author") + " Genre: " + rs.getString("genre"));
			} //Think of this a bit like the file reader for the games project
				
			
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void insertIntoBook() { //This is a prepared statement. Much safer than creating the SQL string yourself

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO book (author,publisher,genre,ISBN,language,rId) VALUES (?,?,?,?,?,?)"); // "?" is a placeholder
	            pstmt.setString(1, "Andy Weir");//Make sure you get the types correct (String, int..)
	            pstmt.setString(2, "penguin books");//Make sure you get the types correct (String, int..)
	            pstmt.setString(3, "sci-fi");//Make sure you get the types correct (String, int..)
	            pstmt.setString(4, "00110011");//Make sure you get the types correct (String, int..)
	            pstmt.setString(5, "English");//Make sure you get the types correct (String, int..)
	            pstmt.setInt(6, 0);//Make sure you get the types correct (String, int..)
	            pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void removeABook() {

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM book WHERE author=?");//A prepared statement can be used for anything
	            pstmt.setString(1, "Andy Weir"); //Including select
	            pstmt.executeUpdate();//Instead of update you'll use something else.. But what? ;)
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		DBHelper.forceUpdate();
		displayBookTable();
		insertIntoBook();
		displayBookTable();
		removeABook();
		displayBookTable();

	}

}
