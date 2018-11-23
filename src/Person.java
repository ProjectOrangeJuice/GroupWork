import javafx.scene.image.Image;

/**This class represents a person from which both the user and librarian
 * classes are inherited from. It holds all the standard information to 
 * create a profile.
 * @author Charles Day
 * @version 1.0
 * */
public class Person {
	
	/**The persons user name.*/
	private final String userName; 
	
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
	public Person(String userName, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar) {
		this.userName = userName;
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
	public String getUserName() {
		return userName;
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
		this.avatar = avatar;
	}
	
	//
	//-------------------------------------------------------------------------
	//
	
	static public Person loadPerson (String userName) {
		
		return null;
	}
	
	//Remove Person
	//onUpdate(preparedStatement)

}