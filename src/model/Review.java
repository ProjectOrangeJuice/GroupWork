package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            if(results.next()) {
            	connection.close();
            	return true;
            }else {
            	connection.close();
            	return false;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	
public static boolean hasReviewed(String username, int rId) {
		
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE username=? AND resourceId=?");
            statement.setString(1, username);
            statement.setInt(2, rId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
            	connection.close();
            	return true;
            }else {
            	connection.close();
            	return false;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	
	
	
	public static boolean hasReviews(int resourceId) {
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
            	connection.close();
            	return true;
            }else {
            	connection.close();
            	return false;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public static double getAvgStar(int resourceId) {
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT avg(star) FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
            	//connection.close();
            	return results.getDouble(1);
            }else {
            	connection.close();
            	return 0;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
	}
	
	public static void addReview(String username, int resource, int star, String text) {
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reviews(resourceId,username,star,review) VALUES(?,?,?,?)");
                statement.setInt(1, resource);
                statement.setString(2, username);
                statement.setInt(3, star);
                statement.setString(4, text);
          statement.execute();
          connection.close();
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public static ArrayList<String[]> getReviews(int resourceId){
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            ArrayList<String[]> reviews = new ArrayList<String[]>();
            while(results.next()) {
            	
            	//star, name,what,when
            	String[] re = {String.valueOf(results.getInt(4)),results.getString(3),results.getString(5),results.getString(6),String.valueOf(results.getInt(1))};
            	reviews.add(re);
            	
            }
            connection.close();
            return reviews;
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	
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
