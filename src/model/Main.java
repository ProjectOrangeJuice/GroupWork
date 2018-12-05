package model;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Main {

	public static void main(String[] args) {
		DBHelper.forceUpdate();
		
		ArrayList<String> languages = new ArrayList<String>();
		
		languages.add("English");
		languages.add("French");
		languages.add("Romanian");
		
		Book Sapiens = new Book(0, "Sapiens", 2017, null, "Noah", "Penguin Books", "educational", "111222333", "English");
		Book TheMartian = new Book(1, "The Martian", 2015, null, "Andy Weir", "Kane Books", "sci-fi", "333444555", "English");
		DVD Interstellar = new DVD(2, "Interstellar", 2014, null, "Christopher Nolan", 169, "English", languages);
		
		System.out.println(Sapiens.getUniqueID());
		System.out.println(TheMartian.getUniqueID());
		System.out.println(Interstellar.getUniqueID());
		
		System.out.println("---Librarian & User Tesing---");
		
		//Loading a Librarian example
		Librarian testStaff = (Librarian)Librarian.loadPerson("staff");
		System.out.println(testStaff.getUsername());
		System.out.println(testStaff.getEmploymentDate());
		
		//Loading a User example
		User testUser = (User)User.loadPerson("test");
		System.out.println(testUser.getUsername());
		
		System.out.println(testUser.getAccountBalance());
		testUser.makePayment(2.66);
		System.out.println(testUser.getAccountBalance());
		
		System.out.println("---Copy Testing---");
		
		Copy testCopy = new Copy(Sapiens, 5, null);
		
		System.out.println(testCopy.getBorrower());
		testStaff.loanCopy(testCopy, testUser);
		System.out.println(testCopy.getBorrower());

	}
}
