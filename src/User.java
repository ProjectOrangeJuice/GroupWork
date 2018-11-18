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
	private float accountBalance;

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
	public User(String userName, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar, float accountBalance) {
		super(userName, firstName, lastName, phoneNumber, address, postcode, avatar);
		this.accountBalance = accountBalance;
	}

	/**
	 * Needs clarification.
	 * @param copy The copy that the user has borrowed.
	 */
	public void addBorrowedCopy(Copy copy) {
		
	}
	
	/**
	 * Allows payments to be added to the account balance.
	 * @param amount The amount the User has payed in pounds.
	 */
	public void makePayment (float amount) {
		this.accountBalance += amount;
	}

	/**
	 * Returns the current account balance.
	 * @return accountBalance The current account balance in pounds.
	 */
	public float getAccountBalance() {
		return accountBalance;
	}
	
}