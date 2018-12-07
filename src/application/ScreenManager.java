/**
 * 
 */
package application;

import java.util.ArrayList;

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
	
	public static Person getCurrentUser() {
		return currentUser;
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
	
	public static void setCurrentLibrarian(Librarian currentLibrarian) {
		ScreenManager.currentLibrarian = currentLibrarian;
	}
	public static Librarian getCurrentLibrarian() {
		return currentLibrarian;
	}

}
