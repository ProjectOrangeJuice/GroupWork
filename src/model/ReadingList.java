package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadingList {
	ArrayList<Resource> resources = new ArrayList<Resource>();
	String name;
	String description;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReadingList(String[] resourceList, String name,String description) {
		this.name = name;
		this.description = description;
		for(String resource : resourceList) {
			System.out.println("Converting.,.. "+resource);
			resources.add(Resource.getResource(Integer.parseInt(resource)));
		}
	}

	public static ReadingList myList(String username){
		ReadingList readingList =null;
		 Connection connection;
		try {
			connection = DBHelper.getConnection();
		
        PreparedStatement statement = connection.prepareStatement("SELECT group_concat(rId) as resources" + 
        " FROM usersList WHERE username = ?");
        statement.setString(1, username);
        
        ResultSet results = statement.executeQuery();
        while (results.next()) {
        	try {
       	 readingList = new ReadingList(results.getString("resources").split(","),"","");
        	}catch(NullPointerException e) {
        		
        	}
        }
        connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readingList;
	}
	
	
	public static boolean addToMyList(String username, int id) {
		try {
			 Connection connection = DBHelper.getConnection();
		
       PreparedStatement statement = connection.prepareStatement("SELECT rId "
       		+ "FROM usersList where username=? AND rId=?");
       statement.setString(1, username);
       statement.setInt(2, id);
       
       ResultSet results = statement.executeQuery();
       if(results.next()) {
    	   System.out.println("Already exists..");
    	   return false;
       }else {
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

	public static void removeFromMyList(String username, int id) {
		try {
			 Connection connection = DBHelper.getConnection();
		
      PreparedStatement statement = connection.prepareStatement("DELETE "
      		+ "FROM usersList where username=? AND rId=?");
      statement.setString(1, username);
      statement.setInt(2, id);
      
     statement.execute();
     connection.close();
     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static boolean isInMyList(String username, int id) {
		boolean output = false;
		try {
			 Connection connection = DBHelper.getConnection();
		
      PreparedStatement statement = connection.prepareStatement("SELECT rId "
      		+ "FROM usersList where username=? AND rId=?");
      statement.setString(1, username);
      statement.setInt(2, id);
      
      ResultSet results = statement.executeQuery();
      if(results.next()) {
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
	
	
	@SuppressWarnings("resource")
	public static void changeFollow(String username, String list) {
		System.out.println("name! - "+list);
		try {
			 Connection connection = DBHelper.getConnection();
		
      PreparedStatement statement = connection.prepareStatement("SELECT listId "
      		+ "FROM userFollows where username=? AND listId=?");
      statement.setString(1, username);
      statement.setString(2, list);
      
      ResultSet results = statement.executeQuery();
      if(results.next()) {
    	  statement = connection.prepareStatement("DELETE FROM userFollows "
    	  		+ "WHERE username=? and listId=?");
    	   	       statement.setString(1, username);
    	   	       statement.setString(2, list);
    	   	       statement.execute();
    	   	       connection.close();
   	   
      }else {
   	   statement = connection.prepareStatement("INSERT INTO userFollows("
   	   		+ "username,listId) VALUES(?,?)");
   	       statement.setString(1, username);
   	       statement.setString(2, list);
   	       statement.execute();
   	       connection.close();
   	       
      }
     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean follows(String username, String list) {
		try {
			 Connection connection = DBHelper.getConnection();
		
     PreparedStatement statement = connection.prepareStatement("SELECT listId "
     		+ "FROM userFollows where username=? AND listId=?");
     statement.setString(1, username);
     statement.setString(2, list);
     
     ResultSet results = statement.executeQuery();
     if(results.next()) {
    	 connection.close();
  	  return true;
  	   
     }else {
    	 connection.close();
  	 return false;
  	       
     }
    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
	
	public static void addToReadingList(int r, String name) {
		try {
			 Connection connection = DBHelper.getConnection();
		
      PreparedStatement statement = connection.prepareStatement("SELECT rId "
      		+ "FROM readingList where name=? and rId=?");
      statement.setString(1, name);
      statement.setInt(2, r);
      
      ResultSet results = statement.executeQuery();
      if(results.next()) {
   	  
      }else {
   	   statement = connection.prepareStatement("INSERT INTO readingList("
   	   		+ "name,rId) VALUES(?,?)");
   	       statement.setString(1, name);
   	       statement.setInt(2, r);
   	       statement.execute();
   	       connection.close();
 
      }
     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Resource> getResources() {
		return resources;
	}

	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ArrayList<ReadingList> databaseReader() {
		ArrayList<ReadingList> readingList = new ArrayList<ReadingList>();
		 Connection connection;
		try {
			connection = DBHelper.getConnection();
		
         PreparedStatement statement = connection.prepareStatement("SELECT name,group_concat(rId) as resources,description" + 
         " FROM readingList group by name");
         
         ResultSet results = statement.executeQuery();
         while (results.next()) {
        	 readingList.add(new ReadingList(results.getString("resources").split(","),results.getString("name"),results.getString("description")));
         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readingList;
	}
	
	
}
