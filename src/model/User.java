package model;
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
	public User(String username, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar, double accountBalance) {
		super(username, firstName, lastName, phoneNumber, address, postcode, avatar);
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
	public ArrayList getAllCopies () {
		return this.copiesList;
	}
	
	/**
	 * Removes a copy from the list of copies withdrawn.
	 * @param copy Copy
	 */
	public void removeBorrowedCopy(Copy copy) {
		this.copiesList.remove(copy);
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
	
	public void loadUserCopies() {
		//Get copies by username
		//Get resources by copies ID
		//Load resources by resouces ID
		//Load copies by username
		//TODO: loadUserCopies
	}
}