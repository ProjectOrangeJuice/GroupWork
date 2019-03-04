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
	
	public static double getAvgStar(int resourceId) {
		try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "SELECT avg(star) FROM reviews WHERE resourceId=?");
            statement.setInt(1, resourceId);
            ResultSet results = statement.executeQuery();
            if(results.next()) {
            	
            	return results.getDouble(1);
            }else {
            	return 0;
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return 0;
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
            	String[] re = {String.valueOf(results.getInt(4)),results.getString(3),results.getString(5),results.getString(6)};
            	reviews.add(re);
            	
            }
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
            	String[] re = {String.valueOf(results.getInt(0)),String.valueOf(results.getInt(3)),results.getString(5),results.getString(4)};
            	reviews.add(re);
            	
            }
          
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	
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
