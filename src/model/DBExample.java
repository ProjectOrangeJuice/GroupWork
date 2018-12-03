package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				System.out.println("RID: "+rs.getInt("rId")+" Title:" +rs.getString("title") //The index is either a number of the name
				+ " Year: "+rs.getInt("year"));
			} //Think of this a bit like the file reader for the games project
				
			
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void displaySubtitles() {
		System.out.println("----Displaying dvd table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM subtitles"); //Your sql goes here
			while(rs.next()) {
				System.out.println("dvdID: "+rs.getInt("dvdID")+" Title:" +rs.getString("subtitleLanguage"));
			} //Think of this a bit like the file reader for the games project
				
			
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void displayDVDs() {
		System.out.println("----Displaying dvd table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM dvd"); //Your sql goes here
			while(rs.next()) {
				System.out.println("dvdID: "+rs.getInt("rID")+" Director:" +rs.getString("director")+
						"language: "+rs.getString("language")+" runtime: "+rs.getInt("runtime"));
			} //Think of this a bit like the file reader for the games project
				
			
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void insertIntoResource() { //This is a prepared statement. Much safer than creating the SQL string yourself

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO resource (rId,title,year) VALUES (4,\"Marea Unire\",1918)"); // "?" is a placeholder
			pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void removeAResource() {

		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM resource WHERE title=?");//A prepared statement can be used for anything
	            pstmt.setString(1, "Other Book"); //Including select
	            pstmt.executeUpdate();//Instead of update you'll use something else.. But what? ;)
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void testFreeCopiesDB() {
		DVD laptop1=new DVD(1,"Bookie",1998,null,"George Lucas",200);
		ArrayList<Copy> list = new ArrayList<>();
		list.add(new Copy(laptop1,1,null));
		list.add(new Copy(laptop1,2,null));
		list.add(new Copy(laptop1,3,null));
		
		laptop1.addCopies(list);
		
		laptop1.saveFreeCopies();
		
		System.out.println("----Displaying free copies table----");
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM freeCopies"); //Your sql goes here
			while(rs.next()) {
				System.out.println("copyID "+rs.getInt("copyID")+" rID " +rs.getInt("rID"));
			} //Think of this a bit like the file reader for the games project
				
			System.out.println("Load free copies from database");
			laptop1.loadCopyList();
			/*
			System.out.println("Copy List:");
			for(Copy c: laptop1.copyList) {
				System.out.println(c.getCOPY_ID());
			}
			
			laptop1.loadFreeCopiesList();
			System.out.println("Free Copy List:");
			for(Copy c: laptop1.freeCopies) {
				System.out.println(c.getCOPY_ID());
			}*/
		} catch (SQLException e) { //if your SQL is incorrect this will display it
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		DBHelper.forceUpdate();
		displayResourceTable();
		/*insertIntoResource();
		displayResourceTable();
		removeAResource();
		displayResourceTable();*/
		
		//displaySubtitles();
		//displayDVDs();
		
		testFreeCopiesDB();
	}

}
