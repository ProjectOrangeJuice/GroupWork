package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadingList {
	ArrayList<Resource> resources = new ArrayList<Resource>();
	String name;
	
	public ReadingList(String[] resourceList, String name) {
		this.name = name;
		for(String resource : resourceList) {
			resources.add(Resource.getResource(Integer.parseInt(resource)));
		}
	}

	public static ArrayList<ReadingList> myLists(String username){
		ArrayList<ReadingList> readingList = new ArrayList<ReadingList>();
		 Connection connection;
		try {
			connection = DBHelper.getConnection();
		
        PreparedStatement statement = connection.prepareStatement("SELECT group_concat(rId) as resources" + 
        " FROM usersList WHERE username = ? group by username");
        statement.setString(1, username);
        
        ResultSet results = statement.executeQuery();
        while (results.next()) {
       	 readingList.add(new ReadingList(results.getString("resources").split(","),""));
        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readingList;
	}
	
	
	public static String[] followedList(String username) {
		
		 Connection connection;
		try {
			connection = DBHelper.getConnection();
		
       PreparedStatement statement = connection.prepareStatement("SELECT group_concat(rId) as lists" + 
       " FROM usersFollows WHERE username = ? group by username");
       statement.setString(1, username);
       
       ResultSet results = statement.executeQuery();
       while (results.next()) {
      	 return results.getString("lists").split(",");
       }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<ReadingList> databaseReader() {
		ArrayList<ReadingList> readingList = new ArrayList<ReadingList>();
		 Connection connection;
		try {
			connection = DBHelper.getConnection();
		
         PreparedStatement statement = connection.prepareStatement("SELECT name,group_concat(rId) as resources" + 
         " FROM readingList group by name");
         
         ResultSet results = statement.executeQuery();
         while (results.next()) {
        	 readingList.add(new ReadingList(results.getString("resources").split(","),results.getString("name")));
         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readingList;
	}
	
	
}
