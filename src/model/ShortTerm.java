package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShortTerm {

	ArrayList<Resource> dvds;
	ArrayList<Resource> books;
	ArrayList<Resource> laptops;
	
	
	public ShortTerm() {
		//Program start up
		generateDVDs();
		generateBooks();
		generateLaptops();
	}
	
	
	private void generateBooks() {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here
			
			while(rs.next()) {
				books.add(new Book(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), null, rs.getString("author"),rs.getString("publisher"),
						rs.getString("genre"), rs.getString("ISBN"), rs.getString("language")));
			} 
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	private void generateLaptops(){
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here
			
			rs = stmt.executeQuery("SELECT * FROM laptop");
			
			while(rs.next()) {
				laptops.add(new Laptop(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), null, rs.getString("manufacturer"), rs.getString("Model"),
						rs.getString("os")));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
	}
	
	private void generateDVDs() {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here	
			while(rs.next()) {
				int dvdID = rs.getInt("rID");
				ArrayList<String> subtitleLanguages = DVD.loadSubtitles(stmt, dvdID);
				
				dvds.add(new DVD(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), null, rs.getString("director"), rs.getInt("runTime"), 
						rs.getString("language"), subtitleLanguages));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		
	}
	
	
}
