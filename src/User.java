import javafx.scene.image.Image;

public class User extends Person {
	
	/**The current account balance for this User.*/
	private int accountBalance;

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
	public User(String userName, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar, int accountBalance) {
		super(userName, firstName, lastName, phoneNumber, address, postcode, avatar);
		this.accountBalance = accountBalance;
	}

	/**
	 * @param copy
	 */
	public void addBorrowedCopy(Copy copy) {
		
	}
	
	/**
	 * @param amount
	 */
	public void makePayment (int amount) {
		this.accountBalance += amount;
	}

	/**
	 * @return
	 */
	public int getAccountBalance() {
		return accountBalance;
	}
	
}
