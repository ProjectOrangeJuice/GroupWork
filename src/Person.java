import javafx.scene.image.Image;

/**This class represents a person from which both the user and librarian
 * classes are inherited from. It holds all the standard information to 
 * create a profile.
 * @author Charles Day
 * @version 1.0
 * */
public class Person {
	
	/**The persons user name.*/
	private String userName; 
	
	/**The persons first name.*/
	private String firstName; 
	
	/**The persons last name.*/
	private String lastName; 
	
	/**The persons phone number.*/
	private String phoneNumber; 
	
	/**The persons street address.*/
	private String address; 
	
	/**The persons postcode.*/
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
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.postcode = postcode;
		this.avatar = avatar;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return
	 */
	public Image getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 */
	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

}