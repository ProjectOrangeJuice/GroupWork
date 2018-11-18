import javafx.scene.image.Image;

public class User extends Person {
	private int accountBalance;

	public User(String userName, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar, int accountBalance) {
		super(userName, firstName, lastName, phoneNumber, address, postcode, avatar);
		this.accountBalance = accountBalance;
	}

	public void addBorrowedCopy(Copy copy) {
		
	}
	
	public void makePayment (int amount) {
		this.accountBalance += amount;
	}

	public int getAccountBalance() {
		return accountBalance;
	}
	
}
