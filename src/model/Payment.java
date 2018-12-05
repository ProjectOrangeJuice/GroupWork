package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Payment {
	private int transactionId;
	private String username;
	private float amount;
	private String stamp;

	public Payment(int transactionId, String username, float amount, String stamp) {
		this.transactionId = transactionId;
		this.username = username;
		this.amount = amount;
		this.stamp = stamp;
	}

	public float getAmount() {
		return amount;
	}
	
	public static Payment makePayment(String username, float amount, int fine) {
		Date date = new Date();
	     
	      try {
	    	  	ResultSet rs = updateFine(fine,amount);
	    	  	if(rs.next()) {
	    	  		int id = rs.getInt(1);
	    	  		insertTransaction(username,amount,date.toString());
	    	  		
	    	  		return new Payment(id,username,amount,date.toString());
	    	  		
	    	  		
	    	  	}else {
	    	  		//failed to update
	    	  		return null;
	    	  	}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
		}
	      return null;
	}

	
	private static void insertTransaction(String username, float amount, String date) throws SQLException {

		Connection conn = DBHelper.getConnection(); //get the connection
		PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO transactions(username,paid,dateTime) VALUES (?,?,?) ");
		pstmt2.setString(1,username);
		pstmt2.setFloat(2,amount);
		pstmt2.setString(3, date);
		
		pstmt2.executeUpdate(); //Your sql goes here
	}
	
	private static ResultSet updateFine(int fine,float amount) throws SQLException {
		Date date = new Date();
		Connection conn = DBHelper.getConnection(); //get the connection
		PreparedStatement pstmt2 = conn.prepareStatement("UPDATE FINES set paid=1 WHERE fineId=? AND amount=?");
		pstmt2.setInt(1,fine);
		pstmt2.setFloat(2,amount);
	
		int updates = pstmt2.executeUpdate(); //Your sql goes here
		return pstmt2.getGeneratedKeys();
	}
	

	public String getStamp() {
		return stamp;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public String getusername() {
		return username;
	}
	
	public boolean contains(String search) {
		if(this.getStamp().contains(search)) {
			return true;
		}else {
			return false;
		}
	}
}
