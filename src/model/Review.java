package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Review object class
 * 
 * @author Oliver Harris
 *
 */
public class Review {

	public static boolean hasRead(String username, int rId) {

		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM copies,borrowRecords,resource WHERE copies.rID = resource.rID AND borrowRecords.copyId = copies.copyID AND borrowRecords.username = ?"
							+ " AND copies.rID=?");
			statement.setString(1, username);
			statement.setInt(2, rId);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean hasReviewed(String username, int rId) {

		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM reviews WHERE username=? AND resourceId=?");
			statement.setString(1, username);
			statement.setInt(2, rId);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Method that returns true or false whether a resource has reviews or not
	 * 
	 * @param resourceId
	 *            id of the resource
	 * @return true or false
	 */
	public static boolean hasReviews(int resourceId) {
		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM reviews WHERE resourceId=?");
			statement.setInt(1, resourceId);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				connection.close();
				return true;
			} else {
				connection.close();
				return false;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get method that gets the average rating/star of a resource
	 * 
	 * @param resourceId
	 *            id of resource
	 * @return average rating/star of a resource
	 */

	public static double getAvgStar(int resourceId) {
		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("SELECT avg(star) FROM reviews WHERE resourceId=?");
			statement.setInt(1, resourceId);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				// connection.close();
				return results.getDouble(1);
			} else {
				connection.close();
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void addReview(String username, int resource, int star, String text) {
		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO reviews(resourceId,username,star,review) VALUES(?,?,?,?)");
			statement.setInt(1, resource);
			statement.setString(2, username);
			statement.setInt(3, star);
			statement.setString(4, text);
			statement.execute();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get method for the written reviews people have left on a specfic resource
	 * 
	 * @param resourceId
	 *            id of resource
	 * @return arraylist of reviews on a certain resource
	 */
	public static ArrayList<String[]> getReviews(int resourceId) {
		try {
			Connection connection = DBHelper.getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM reviews WHERE resourceId=?");
			statement.setInt(1, resourceId);
			ResultSet results = statement.executeQuery();
			ArrayList<String[]> reviews = new ArrayList<String[]>();
			while (results.next()) {

				// star, name,what,when
				String[] re = { String.valueOf(results.getInt(4)), results.getString(3), results.getString(5),
						results.getString(6), String.valueOf(results.getInt(1)) };

				reviews.add(re);

			}
			connection.close();
			return reviews;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get method of all reviews on all resources
	 * @return arralist of all reviews
	 */
	public static ArrayList<String[]> getReviews(){

		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews");
          
            ResultSet results = statement.executeQuery();
            ArrayList<String[]> reviews = new ArrayList<String[]>();
            while(results.next()) {
            	String[] re = {String.valueOf(results.getInt(1)),String.valueOf(results.getInt(3)),results.getString(5),results.getString(4)};
            	reviews.add(re);

            }

          return reviews;

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}


	/**
	 * Void method that removes a review from the database
	 * @param reviewId id of a review
	 */
	public static void removeReview(int reviewId) {
		System.out.println("to remove.. "+reviewId);
		try {
            Connection connection = DBHelper.getConnection();

            PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM reviews WHERE reviewId=?");
            statement.setInt(1, reviewId);
            statement.execute();
            connection.close();

        }
	     catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
	}
