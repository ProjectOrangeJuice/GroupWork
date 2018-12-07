package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * This class holds all the payment details for a transaction.
 * @author Oliver Harris.
 *
 */
public class Payment {
	private int transactionId;
	private String username;
	private double amount;
	private String dateTime;

	/**
	 * Constructor for payment.
	 * @param transactionId The transactionId from the database.
	 * @param username The username for this payment.
	 * @param amount The amount paid.
	 * @param dateTime The date time stamp.
	 */
	public Payment(int transactionId, String username, double amount, 
			String dateTime) {
		this.transactionId = transactionId;
		this.username = username;
		this.amount = amount;
		this.dateTime = dateTime;
	}
	
	
	/**
	 * Make a payment.
	 * @param username The username the fine belongs to.
	 * @param amount The amount being paid.
	 * @param fine The fine id.
	 * @param full If the fine is being paid in full.
	 * @return The payment object.
	 */
	public static Payment makePayment(String username, double amount, int fine,
			boolean full) {
		Date date = new Date();

	     
	      try {
	    	  	ResultSet results = updateFine(fine,amount,full);
	    	  	if(results.next()) {
	    	  		int id = results.getInt(1);
	    	  		insertTransaction(username,amount,date.toString());
	    	  		
	    	  		return new Payment(id,username,amount,date.toString());
	    	  		
	    	  		
	    	  	}else {
	    	  		return null;
	    	  	}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
		}
	      return null;
	}

	
	/**
	 * Add payment to transaction database.
	 * @param username The username the transaction belongs to.
	 * @param amount The amount that was paid.
	 * @param date The date it was paid.
	 * @throws SQLException The database did not update.
	 */
	private static void insertTransaction(String username, double amount, 
			String date) throws SQLException {

		Connection connection = DBHelper.getConnection(); 
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "transactions(username,paid,dateTime) VALUES (?,?,?) ");
		statement.setString(1,username);
		statement.setDouble(2,amount);
		statement.setString(3, date);
		
		statement.executeUpdate(); 
	}
	
	
	/**
	 * Update the fine in the database.
	 * @param fine The fine id.
	 * @param amount The amount paid.
	 * @param full If the amount is the full amount.
	 * @return The fineId from the database.
	 * @throws SQLException The database did not update.
	 */
	private static ResultSet updateFine(int fine,double amount,boolean full) 
			throws SQLException {
		Connection connection = DBHelper.getConnection(); 
		if(full) {
		PreparedStatement statement = connection.prepareStatement("UPDATE fines "
				+ "set paid=1, amount=0 WHERE fineId=? AND amount=?");
		statement.setInt(1,fine);
		statement.setDouble(2,amount);
	
		statement.executeUpdate(); 
		return statement.getGeneratedKeys();
		}else {
			PreparedStatement statement = connection.prepareStatement("UPDATE "
					+ "fines set paid=0, amount=? WHERE fineId=?");
			statement.setInt(2,fine);
			statement.setDouble(1,amount);
		
			statement.executeUpdate(); 
			return statement.getGeneratedKeys();
		}
	}

	/**
	 * Get the amount paid.
	 * @return The amount paid.
	 */
	public double getAmount() {
		return amount;
	}
	
	
	

	/**
	 * The date and time the payment was made.
	 * @return The date time stamp.
	 */
	public String getStamp() {
		return dateTime;
	}

	
	/**
	 * Get the database transaction id.
	 * @return The transaction id.
	 */
	public int getTransactionId() {
		return transactionId;
	}

	/**
	 * The username.
	 * @return The username.
	 */
	public String getusername() {
		return username;
	}
	
	/**
	 * Check if the date time stamp contains a search value.
	 * @param search The search value.
	 * @return If the date time stamp contains the value.
	 */
	public boolean contains(String search) {
		if(this.getStamp().contains(search)) {
			return true;
		}else {
			return false;
		}
	}
}
