import javafx.scene.image.Image;

public class Person {
	private String userName;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String postcode;
	private Image avatar;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}
	
	
}
