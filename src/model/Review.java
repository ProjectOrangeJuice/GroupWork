package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Review object class
 * @author Oliver Harris
 *
 */
public class Review {
	
	/**
	 * Method that returns true or false whether a resource has reviews or not
	 * @param resourceId id of the resource
	 * @return true or false
	 */
	public static boolean hasReviews(int resourceId) {
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
            	return true;
            }else {
            	return false;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	/**
	 * Get method that gets the average rating/star of a resource
	 * @param resourceId id of resource
	 * @return average rating/star of a resource
	 */
	 	 
	public static double getAvgStar(int resourceId) {
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT avg(start) FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
            	return results.getDouble(0);
            }else {
            	return 0;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
	}
	
	/**
	 * Get method for the written reviews people have left on a specfic resource
	 * @param resourceId id of resource
	 * @return arraylist of reviews on a certain resource
	 */
	public static ArrayList<String[]> getReviews(int resourceId){
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            ArrayList<String[]> reviews = new ArrayList<String[]>();
            while(results.next()) {
            	String[] re = {String.valueOf(results.getInt(3)),results.getString(5),results.getString(4)};
            	reviews.add(re);
            	
            }
          
        }
        catch (SQLException e) {
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
            	String[] re = {String.valueOf(results.getInt(0)),String.valueOf(results.getInt(3)),results.getString(5),results.getString(4)};
            	reviews.add(re);
            	
            }
          
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
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "DELETE * FROM reviews WHERE reviewId=?");
            statement.setInt(1, reviewId);
            ResultSet results = statement.executeQuery();
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
	}


}
