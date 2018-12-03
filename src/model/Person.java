package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.image.Image;

/**This class represents a person from which both the user and librarian
 * classes are inherited from. It holds all the standard information to 
 * create a profile.
 * @author Charles Day
 * @version 1.0
 * */
public abstract class Person {
	
	/**The persons user name.*/
	private final String username; 
	
	/**The persons first name.*/
	private String firstName; 
	
	/**The persons last name.*/
	private String lastName; 
	
	/**The persons phone number.*/
	private String phoneNumber; 
	
	/**The persons street address.*/
	private String address; 
	
	/**The persons post code.*/
	private String postcode; 
	
	/**The persons chosen avatar image.*/
	private Image avatar; 

	/**
	 * Creates a new Person object from the given arguments.
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param address
	 * @param postcode
	 * @param avatar
	 */
	public Person(String username, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.postcode = postcode;
		this.avatar = avatar;
	}
	

	/**
	 * Returns the user name of the person.
	 * @return userName String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the first name of the person.
	 * @return firstName String
	 */
	public String getFirstName() {
		return firstName;
	}

	/** 
	 * Sets the first name of the person.
	 * @param firstName String
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		Person.updateDatabase(this.getUsername(), "firstName", firstName);
	}

	/**
	 * Returns the last name of the person.
	 * @return lastName String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the person.
	 * @param lastName String
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
		Person.updateDatabase(this.getUsername(), "lastName", lastName);
	}

	/**
	 * Returns the phone number of the person.
	 * @return phoneNumber String
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number of the person.
	 * @param phoneNumber String
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		Person.updateDatabase(this.getUsername(), "telephone", phoneNumber);
	}

	/**
	 * Returns the address of the person.
	 * @return address String
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address of the person.
	 * @param address String
	 */
	public void setAddress(String address) {
		this.address = address;
		Person.updateDatabase(this.getUsername(), "address", address);
	}

	/**
	 * Returns the post code of the person.
	 * @return postcode String
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Sets the post code of the person
	 * @param postcode String
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
		Person.updateDatabase(this.getUsername(), "postcode", postcode);
	}

	/**
	 * Returns the avatar the person has chosen.
	 * @return avatar Image
	 */
	public Image getAvatar() {
		return avatar;
	}

	/**
	 * Sets the avatar the person has chosen.
	 * @param avatar Image
	 */
	public void setAvatar(String avatarPath) {
		this.avatar = loadAvatar(avatarPath);
		Person.updateDatabase(this.getUsername(), "avatarPath", avatarPath);
	}
	
	//TEMPORARY
	public static Image loadAvatar(String avatarPath) {
		return null; //TODO: Make actual image loader for avatars.
	}
	
	//
	//-------------------------------------------------------------------------
	//
	
	static public Person loadPerson(String username) {
		try {
			//Declaring necessary variables
			Connection conn = DBHelper.getConnection();
			String result = "";
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE users.username = ?;");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
			
			if (rs.getInt(1) == 1) {
				
				pstmt = conn.prepareStatement("SELECT COUNT (*) FROM staff WHERE username = ?;");
	            pstmt.setString(1, username);
	            rs = pstmt.executeQuery();
	            
	            if (rs.getInt(1) == 1) {
	            	
	            	pstmt = conn.prepareStatement("SELECT * FROM users, staff WHERE users.username = staff.username and users.username = ?;");
		            pstmt.setString(1, username);
		            rs = pstmt.executeQuery();
		            
		            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						if (i == rs.getMetaData().getColumnCount()) {
							result += rs.getString(i);
						} else {
							result += rs.getString(i) + ",";
						}
					}
		            
		            String[] parts = result.split(",");

					conn.close();
					return new Librarian(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], loadAvatar(parts[6]), parts[8], 0);
	            } else {

	            	pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?;");
		            pstmt.setString(1, username);
		            rs = pstmt.executeQuery();
		            
		            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
						if (i == rs.getMetaData().getColumnCount()) {
							result += rs.getString(i);
						} else {
							result += rs.getString(i) + ",";
						}
					}
		            
		            String[] parts = result.split(",");

					conn.close();
					return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], loadAvatar(parts[6]), Double.parseDouble(parts[7]));
	            }
				
			} else {
				System.out.println("Error: Either too many or not enough rows returned."); //Raise error
				conn.close();
			}
			
		//Catch most other errors!
		} catch (SQLException e) {
			System.out.println(e);
		}
		//By default return null.
		return null;
	}
	
	protected static boolean updateDatabase(String username, String field, String data) {
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET " + field +" = ? WHERE username = ?");
            pstmt.setString(1, data);
            pstmt.setString(2, username);
            if (pstmt.executeUpdate() == 1) {
            	conn.close();
            	return true;
            }
            conn.close();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	//Remove Person
}
