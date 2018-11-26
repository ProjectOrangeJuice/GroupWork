import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transactions {

	private int username;
	private ArrayList<Payment> payments;

	
	
	public Transactions(int username, ArrayList<Payment> payments) {
		this.username = username;
		this.payments = payments;

	}
	
	public int getUsername() {
		return username;
	}
	
	public void setUsername(int username) {
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
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transactions (userId,paid,dateTime) VALUES (?,?,?)"); 
	            pstmt.setInt(1, username);
	            pstmt.setFloat(2, payment.getAmount());
	            pstmt.setString(3, stamp);
	            pstmt.executeUpdate();

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
