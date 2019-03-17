package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Database queries
 * @author James 
 */

public class Statistics {

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
	
	public static Resource getMostPopularBook(String date1, String date2) {
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
			    return null;
			} else {
				
				int bookId = bookSet.getInt(1);
			
				Resource bookR = Resource.getResource(bookId);
			
				con.close();
				return bookR;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
		
	}
}
