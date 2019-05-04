package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The users list of resources they follow object.
 * 
 * @author Oliver Harris.
 */
public class MyList {

	/** The resources. */
	private ArrayList<Resource> resources = new ArrayList<Resource>();

	/**
	 * Instantiates a new my list.
	 *
	 * @param resourceList the resource list.
	 */
	public MyList(String[] resourceList) {
		for (String resource : resourceList) {
			resources.add(Resource.getResource(Integer.parseInt(resource)));
		}
	}

	/**
	 * Gets the resources.
	 *
	 * @return the resources.
	 */
	public ArrayList<Resource> getResources() {
		return resources;
	}

	/**
	 * Gets the my list.
	 *
	 * @param username the username
	 * @return the my list
	 */
	public static MyList getMyList(String username) {
		MyList readingList = null;
		Connection connection;
		try {
			connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT group_concat(rId) as resources" +
			" FROM usersList WHERE username = ?");
			statement.setString(1, username);

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				try {
					readingList = new MyList(
							results.getString("resources").split(","));
				} catch (NullPointerException e) {

				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readingList;
	}

	/**
	 * Removes the resource from my list.
	 *
	 * @param username the username.
	 * @param id       the resource id.
	 */
	public static void removeFromMyList(String username, int id) {
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("DELETE " + "FROM usersList where"
							+ " username=? AND rId=?");
			statement.setString(1, username);
			statement.setInt(2, id);

			statement.execute();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Checks if a resource is in my list.
	 *
	 * @param username the username.
	 * @param id       the resource id.
	 * @return true, if is in my list.
	 */
	public static boolean isInMyList(String username, int id) {
		boolean output = false;
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT rId " + "FROM usersList where"
							+ " username=? AND rId=?");
			statement.setString(1, username);
			statement.setInt(2, id);

			ResultSet results = statement.executeQuery();
			if (results.next()) {
				System.out.println("Is in list!");
				output = true;
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}

	/**
	 * Adds the resource to my list.
	 *
	 * @param username the username.
	 * @param id       the resource id.
	 * @return true, if successful.
	 */
	public static boolean addToMyList(String username, int id) {
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT rId " + "FROM usersList where "
							+ "username=? AND rId=?");
			statement.setString(1, username);
			statement.setInt(2, id);

			ResultSet results = statement.executeQuery();
			if (results.next()) {
				System.out.println("Already exists..");
				return false;
			} else {
				statement = connection.prepareStatement("INSERT INTO usersList(" 
			+ "username,rId) VALUES(?,?)");
				statement.setString(1, username);
				statement.setInt(2, id);
				statement.execute();
				connection.close();
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
