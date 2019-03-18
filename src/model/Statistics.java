package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Database queries.
 * @author James 
 * @author Oliver Harris
 * @author Lee Zack
 */

public class Statistics {

	/**
	 * Get the total borrowed between a date by a user.
	 * @param username The username the statistic is being generated for.
	 * @param date1 The first date.
	 * @param date2 The second date.
	 * @return The total borrowed between the dates.
	 */
	public static int totalBorrow(String username, String date1, String date2) {
		Connection con;
		int borrowedTotal = 0;
		try {
			con = DBHelper.getConnection();
		
		String getBorrows = "SELECT COUNT(username) FROM borrowRecords WHERE username = ? AND timestamp BETWEEN ? AND ?";
		PreparedStatement pstmt = con.prepareStatement(getBorrows);
		pstmt.setString(1,username);
		pstmt.setString(2,date1);
		pstmt.setString(3,date2);
		ResultSet borrowSet = pstmt.executeQuery();
		
		borrowedTotal = borrowSet.getInt(1);
		con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrowedTotal;
		
	}
	
	/**
	 * Get the most popular book between two dates.
	 * @param date1 The first date.
	 * @param date2 The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularBook(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getBooks = "SELECT * FROM  majorStat, resource,book "
					+ "WHERE majorStat.resource=resource.rID AND book.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getBooks);
			pstmt.setString(1,date1);
			pstmt.setString(2,date2);
			ResultSet bookSet = pstmt.executeQuery();
			if (!bookSet.isBeforeFirst() ) {    
			    System.out.println("No data"); 
			    con.close();
			    return -1;
			} else {
				
				int bookId = bookSet.getInt(1);
			
				
			
				con.close();
				return bookId;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Get the most popular DVD between two dates.
	 * @param date1 The first date.
	 * @param date2 The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularDVD(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getBooks = "SELECT * FROM  majorStat, resource,dvd"
					+ "WHERE majorStat.resource=resource.rID AND dvd.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getBooks);
			pstmt.setString(1,date1);
			pstmt.setString(2,date2);
			ResultSet bookSet = pstmt.executeQuery();
			if (!bookSet.isBeforeFirst() ) {    
			    System.out.println("No data"); 
			    con.close();
			    return -1;
			} else {
				
				int bookId = bookSet.getInt(1);
			
				
			
				con.close();
				return bookId;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Get the most popular laptop between two dates.
	 * @param date1 The first date.
	 * @param date2 The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularLaptop(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getBooks = "SELECT * FROM  majorStat, resource,laptop "
					+ "WHERE majorStat.resource=resource.rID AND laptop.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getBooks);
			pstmt.setString(1,date1);
			pstmt.setString(2,date2);
			ResultSet bookSet = pstmt.executeQuery();
			if (!bookSet.isBeforeFirst() ) {    
			    System.out.println("No data"); 
			    con.close();
			    return -1;
			} else {
				
				int bookId = bookSet.getInt(1);
			
				
			
				con.close();
				return bookId;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Get the most popular game between two dates.
	 * @param date1 The first date.
	 * @param date2 The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularGame(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getBooks = "SELECT * FROM  majorStat, resource,game "
					+ "WHERE majorStat.resource=resource.rID AND game.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getBooks);
			pstmt.setString(1,date1);
			pstmt.setString(2,date2);
			ResultSet bookSet = pstmt.executeQuery();
			if (!bookSet.isBeforeFirst() ) {    
			    System.out.println("No data"); 
			    con.close();
			    return -1;
			} else {
				
				int bookId = bookSet.getInt(1);
			
				
			
				con.close();
				return bookId;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
}
