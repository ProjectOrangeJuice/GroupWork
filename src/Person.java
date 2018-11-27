import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;

/**This class represents a person from which both the user and librarian
 * classes are inherited from. It holds all the standard information to 
 * create a profile.
 * @author Charles Day
 * @version 1.0
 * */
public class Person {
	
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
		this.firstName = firstName;//TODO: Updater required
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
		this.lastName = lastName;//TODO: Updater required
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
		this.phoneNumber = phoneNumber;//TODO: Updater required
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
		this.address = address;//TODO: Updater required
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
		this.postcode = postcode;//TODO: Updater required
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
	public void setAvatar(Image avatar) {
		this.avatar = avatar; //TODO: Updater required
	}
	
	//
	//-------------------------------------------------------------------------
	//
	
	static public Person loadPerson (String username) {
		try {
			//Declaring necessary variables
			Connection conn = DBHelper.getConnection();
			Statement stmt = conn.createStatement();
			String result = "";
			String sql = "";
			
			//Checks whether the person exists in the Database
			sql = "SELECT COUNT(*) FROM users WHERE `username` = '" + username + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.getInt(1) == 1) {
			
				sql = "SELECT * FROM users WHERE `username` = '" + username + "';";
				rs = stmt.executeQuery(sql);
				
				//Iterates through every column in the result set
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (i == rs.getMetaData().getColumnCount()) {
						result += rs.getString(i);
					} else {
						result += rs.getString(i) + ",";
					}
				}
				//Splits the CSV string into a string array
				String[] parts = result.split(",");
				
				//Checks whether to make a Librarian or User
				switch (parts[9]) {
				case "staff":
					return new Librarian(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], getAvatar(parts[6]), parts[8]);
				case "user":
					return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], getAvatar(parts[6]), Float.parseFloat(parts[7]));
				default:
					System.out.println("Error: Incorrect user type loaded."); //Raise error
					return null;
				}
			} else {
				System.out.println("Error: Either too many or not enough rows returned."); //Raise error
				return null;
			}
			
		//Catch most other errors!
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//By default return null.
		return null;
	}
	
	//Temporary, to be replaced with image loader from James?
	public static Image getAvatar(String path) {
		return null;
	}
	
	public static boolean updateDatabase(String SQL) {
		try {
			Connection conn = DBHelper.getConnection();
			Statement stmt = conn.createStatement();
			 if (stmt.executeUpdate(SQL) == 1) {
				 return true;
			 } 
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return false;
	}
	
	//Remove Person
}