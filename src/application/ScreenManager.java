/**
 * 
 */
package application;

import java.util.ArrayList;

import model.Person;
import model.Resource;

/**
 * @author Kane
 *
 */
public class ScreenManager {
	
	private static Person currentUser;
	private static ArrayList<Resource> resources;
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
	
	

}
