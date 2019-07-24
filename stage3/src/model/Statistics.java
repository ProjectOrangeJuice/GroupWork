package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database queries.
 * @author James Finlayson
 * @author Oliver Harris
 * @author Zhing Hang Lee
 */
public class Statistics {

	/**
	 * Get the total borrowed between a date by a user.
	 * 
	 * @param username
	 *            The username the statistic is being generated for.
	 * @param date1
	 *            The first date.
	 * @param date2
	 *            The second date.
	 * @return The total borrowed between the dates.
	 */
	public static int totalBorrow(String username, String date1, String date2) {
		Connection con;
		int borrowedTotal = 0;
		try {
			con = DBHelper.getConnection();

			String getBorrows = "SELECT COUNT(username) FROM borrowRecords WHERE username = ? AND timestamp BETWEEN ? AND ?";
			PreparedStatement pstmt = con.prepareStatement(getBorrows);
			pstmt.setString(1, username);
			pstmt.setString(2, date1);
			pstmt.setString(3, date2);
			ResultSet borrowSet = pstmt.executeQuery();

			borrowedTotal = borrowSet.getInt("rID");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return borrowedTotal;

	}

	/**
	 * Get the most popular book between two dates.
	 * 
	 * @param date1
	 *            The first date.
	 * @param date2
	 *            The second date.
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
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet bookSet = pstmt.executeQuery();
			if (!bookSet.isBeforeFirst()) {
				System.out.println("No data");
				con.close();
				return -1;
			} else {

				int bookId = bookSet.getInt("rID");

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
	 * 
	 * @param date1
	 *            The first date.
	 * @param date2
	 *            The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularDVD(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getDVD = "SELECT * FROM  majorStat, resource,dvd "
					+ "WHERE majorStat.resource=resource.rID AND dvd.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getDVD);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet DVDSet = pstmt.executeQuery();
			if (!DVDSet.isBeforeFirst()) {
				System.out.println("No data");
				con.close();
				return -1;
			} else {

				int DVDId = DVDSet.getInt("rID");

				con.close();
				return DVDId;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Get the most popular laptop between two dates.
	 * 
	 * @param date1
	 *            The first date.
	 * @param date2
	 *            The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularLaptop(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getLaptop = "SELECT * FROM  majorStat, resource,laptop "
					+ "WHERE majorStat.resource=resource.rID AND laptop.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getLaptop);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet laptopSet = pstmt.executeQuery();
			if (!laptopSet.isBeforeFirst()) {
				System.out.println("No data");
				con.close();
				return -1;
			} else {

				int laptopId = laptopSet.getInt("rID");
				System.out.println("most pop laptop: "+laptopId);

				con.close();
				return laptopId;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Get the most popular game between two dates.
	 * 
	 * @param date1
	 *            The first date.
	 * @param date2
	 *            The second date.
	 * @return The resourceId of the most popular. -1 if there are none.
	 */
	public static int getMostPopularGame(String date1, String date2) {
		try {
			Connection con = DBHelper.getConnection();
			String getGame = "SELECT * FROM  majorStat, resource,game "
					+ "WHERE majorStat.resource=resource.rID AND game.rID=resource.rID "
					+ "AND majorStat.timestamp BETWEEN ? AND ? "
					+ "GROUP BY majorStat.resource ORDER BY COUNT (majorStat.resource) DESC LIMIT 1";
			PreparedStatement pstmt = con.prepareStatement(getGame);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet gameSet = pstmt.executeQuery();
			if (!gameSet.isBeforeFirst()) {
				System.out.println("No data");
				con.close();
				return -1;
			} else {

				int gameId = gameSet.getInt("rID");

				con.close();
				return gameId;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Get number of fines between 2 dates.
	 * 
	 * @param date1
	 *            The first date
	 * @param date2
	 *            The second date
	 * @return the total fines between 2 dates
	 */
	public static int getMostFine(String date1, String date2) {
		Connection con;
		int fineTotal = 0;
		try {
			con = DBHelper.getConnection();

			String getFine = "SELECT COUNT(fineID) FROM fines WHERE timestamp BETWEEN ? AND ?";
			PreparedStatement pstmt = con.prepareStatement(getFine);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet fineSet = pstmt.executeQuery();

			fineTotal = fineSet.getInt(1);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fineTotal;
	}
	
	/**
	 * Get the average fines between two dates.
	 * 
	 * @param date1
	 *            The first date
	 * @param date2
	 *            The second date
	 * @return the total fines between 2 dates
	 */
	public static double getAvgFine(String date1, String date2) {
		Connection con;
		double fineTotal = 0;
		try {
			con = DBHelper.getConnection();

			String getFine = "SELECT SUM(amount) FROM fines WHERE timestamp BETWEEN ? AND ?";
			PreparedStatement pstmt = con.prepareStatement(getFine);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			ResultSet fineSet = pstmt.executeQuery();

			fineTotal = fineSet.getDouble(1);
			System.out.println("total "+fineTotal);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fineTotal;
	}

}