/**
 * 
 */
package application;

import java.util.ArrayList;

import model.Book;
import model.Copy;
import model.DVD;
import model.Laptop;
import model.Librarian;
import model.Person;
import model.Resource;

/**
 * @author Kane
 *
 */
public class ScreenManager {
	
	private static Person currentUser;
	private static ArrayList<Resource> resources;
	private static Librarian currentLibrarian;
	
	public static Resource currentResource;
	public static Book currentBook;
	public static Laptop currentLaptop;
	public static DVD currentDVD;
	public static Copy currentCopy;
	
	public static Person getCurrentUser() {
		return currentUser;
	}
	
	public static Resource getCurrentResource() {
		return currentResource;
	}
	
	public static void setCurrentResource(Resource resource) {
		currentResource = resource;
	}
	public static void setCurrentUser(Person currentUser) {
		ScreenManager.currentUser = currentUser;
	}
	public static ArrayList<Resource> getResources() {
		return resources;
	}
	public static void setResources(ArrayList<Resource> resources) {
		ScreenManager.resources = resources;
	}

}
