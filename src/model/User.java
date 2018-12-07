package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**This class represents a user of the library, allowing them to borrow copies
 * of recourses and make payments towards any fines against them. It has all
 * the attributes and methods of the Person class with the inclusion of the
 * account balance behaviour.
 * @author Charles Day
 * @version 1.0
 * */
public class User extends Person {
	
	/**The current account balance for this User.*/
	private double accountBalance;
	
	/**All of the copies the user has taken out.*/
	private ArrayList<Copy> copiesList = new ArrayList<Copy>();
	
	/**
	 * Creates a new User object from the given arguments.
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param address
	 * @param postcode
	 * @param avatar
	 * @param accountBalance
	 */
	public User(String username, String firstName, String lastName, String phoneNumber, String address, String postcode, String avatarPath, double accountBalance) {
		super(username, firstName, lastName, phoneNumber, address, postcode, avatarPath);
		this.accountBalance = accountBalance;
	}
	

	/**
	 * Adds a copy of a resource that the user has withdrawn.
	 * @param copy Copy
	 */
	public void addBorrowedCopy(Copy copy) {
		this.copiesList.add(copy);
		//Updater not needed as copy already updates the database.
	}
	
	/**
	 * Returns all copies that the user has currently withdrawn.
	 * @return copiesList ArrayList
	 */
	public ArrayList<Copy> getBorrowedCopies () {
		return copiesList;
	}
	
	/**
	 * Removes a copy from the list of copies withdrawn.
	 * @param copy Copy
	 */
	public void removeBorrowedCopy(Copy copy) {
		copiesList.remove(copy);
		//Updater not needed as copy already updates the database.
	}
	
	/**
	 * Allows payments to be added to the account balance.
	 * @param amount The amount the User has payed in pounds.
	 */
	public void makePayment (double amount) {
		this.accountBalance += amount;
		Person.updateDatabase("users",this.getUsername(), "accountBalance", Double.toString(this.accountBalance));
	}

	/**
	 * Returns the current account balance.
	 * @return accountBalance The current account balance in pounds.
	 */
	public double getAccountBalance() {
		return accountBalance;
	}
	
	
	public static boolean addBalance(String username, float value) {
		Connection conn;
		try {
			conn = DBHelper.getConnection();
		
		PreparedStatement pstmt2 = conn.prepareStatement("UPDATE users set accountBalance = accountBalance + ? WHERE username=?");
		pstmt2.setFloat(1,value);
		pstmt2.setString(2,username);
	
		int updates = pstmt2.executeUpdate();
		return (updates >= 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	} 
	
	public void loadUserCopies() {
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM copies WHERE keeper = ?");
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	copiesList.add(Resource.getResource(rs.getInt("rID")).getCopy(rs.getInt("copyID")));
            }
		} catch (SQLException e) { 
			System.out.println("Failed to load copies into user.");
			e.printStackTrace();
		}
	}
	
	public boolean hasOutstandingFines() {
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM fines WHERE username = ? AND paid = 0;");
            pstmt.setString(1, this.getUsername());
            ResultSet rs = pstmt.executeQuery();
            conn.close();
            
            if (rs.getInt(1) == 0) {
            	return false;
            } else {
            	return true;
            }
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return false;
	}
}