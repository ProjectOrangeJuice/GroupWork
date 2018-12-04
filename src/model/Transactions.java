package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transactions {

	private String username;
	private ArrayList<Payment> payments;



	public Transactions(String username, ArrayList<Payment> payments) {
		this.username = username;
		this.payments = payments;

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public void addPayment(Payment payment) {
		payments.add(payment);
		String stamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transactions (username,paid,dateTime) VALUES (?,?,?)");
	            pstmt.setString(1, username);
	            pstmt.setFloat(2, payment.getAmount());
	            pstmt.setString(3, stamp);
	            pstmt.executeUpdate();



		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Transactions getTransactions(String username){
		ArrayList<Payment> p = new ArrayList();
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM transactions WHERE username=?");
			pstmt.setString(1,username);
			ResultSet rs = pstmt.executeQuery(); //Your sql goes here
			while(rs.next()) {
				int a = rs.getInt("transactionId");
				String b = rs.getString("username");
				float c = rs.getFloat("paid");
				String d = rs.getString("dateTime");
				System.out.println(a + b + c + d);

				p.add(new Payment(a,b,c,d));

			}

			return new Transactions(username,p);
		} catch (SQLException e) {
			e.printStackTrace();
	}

		return null;
	}
	
	







}
