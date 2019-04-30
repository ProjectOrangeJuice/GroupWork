package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class ReadingList {
	ArrayList<Resource> resources = new ArrayList<Resource>();
	String name;
	String description;
	Image image;
	static String IMAGE_DEFAULT = "/graphics/readinglist.jpg";
	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	
		 try {
			 Connection connection = DBHelper.getConnection();
		
       PreparedStatement statement = connection.prepareStatement("SELECT name"
       		+ " FROM listDesc where name=?");
       statement.setString(1, name);
       
       ResultSet results = statement.executeQuery();
       if(results.next()) {
    	   statement = connection.prepareStatement("UPDATE "
    	       		+ " listDesc SET desc=? WHERE name=?");
    	   statement.setString(2, name);
    	   statement.setString(1, description);
    	   statement.execute();
       }else {
    	   statement = connection.prepareStatement("INSERT INTO listDesc("
    	   		+ "name,desc,image) VALUES(?,?,?)");
    	   statement.setString(1, name);
    	       statement.setString(2, description);
    	       statement.setString(3, IMAGE_DEFAULT);
    	       statement.execute();
    	      
       }
       connection.close();
 
      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public ReadingList(String[] resourceList, String name,String description,String image) {
		this.name = name;
		this.description = description;
		for(String resource : resourceList) {
			System.out.println("Converting.,.. "+resource);
			resources.add(Resource.getResource(Integer.parseInt(resource)));
		}
		if(image == null || image.equals("")) {
			this.image = new Image(IMAGE_DEFAULT);
			
		}else {
			System.out.println("Attempting with image: "+image);
			this.image = new Image(image);
		}
	}

	public Image getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = new Image(image);
		 try {
			 Connection connection = DBHelper.getConnection();
		
       PreparedStatement statement = connection.prepareStatement("SELECT name"
       		+ " FROM listDesc where name=?");
       statement.setString(1, name);
       
       ResultSet results = statement.executeQuery();
       if(results.next()) {
    	   statement = connection.prepareStatement("UPDATE "
    	       		+ " listDesc SET image=? WHERE name=?");
    	   statement.setString(2, name);
    	   statement.setString(1, image);
    	   statement.execute();
       }else {
    	   statement = connection.prepareStatement("INSERT INTO listDesc("
    	   		+ "name,image) VALUES(?,?)");
    	       statement.setString(1, name);
    	       statement.setString(2, image);
    	       statement.execute();
    	      
       }
       connection.close();
 
      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
       	 readingList = new ReadingList(results.getString("resources").split(","),"","","");
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
	
	public static void removeFromList(String name, int rid) {
		try {
			 Connection connection = DBHelper.getConnection();
		
     PreparedStatement statement = connection.prepareStatement("DELETE FROM readingList "
     		+ "WHERE name=? AND rId=?");
     statement.setString(1, name);
     statement.setInt(2, rid);
     
      statement.executeUpdate();
       connection.close();
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
	
	public static ArrayList<ReadingList> followedList(String username) {
		ArrayList<ReadingList> list = new ArrayList<ReadingList>();
		 Connection connection;
		try {
			connection = DBHelper.getConnection();
		
       PreparedStatement statement = connection.prepareStatement("SELECT group_concat(readingList.rId) as resources,readingList.name, IFNULL(listDesc.desc,'') as desc, "
       		+ " listDesc.image as image FROM userFollows, readingList LEFT JOIN listDesc on listDesc.name = readingList.name WHERE userFollows.listId = readingList.name "
       		+ "AND userFollows.username=? group by readingList.name");
       statement.setString(1, username);
       
       ResultSet results = statement.executeQuery();
       while (results.next()) {
      	 list.add(new ReadingList(results.getString("resources").split(","),results.getString("name"),results.getString("desc"),results.getString("image")));
       }
       connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
   	  connection.close();
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
		
         PreparedStatement statement = connection.prepareStatement("SELECT readingList.name,group_concat(rId) as resources,IFNULL(listDesc.desc,'') as desc,listDesc.image as image" + 
         " FROM readingList LEFT JOIN listDesc on readingList.name = listDesc.name group by readingList.name");
         
         ResultSet results = statement.executeQuery();
         while (results.next()) {
        	 readingList.add(new ReadingList(results.getString("resources").split(","),results.getString("name"),results.getString("desc"),results.getString("image")));
         }
         connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readingList;
	}
	
	
	
	public boolean contains(String search) {
		if(getName().toLowerCase().contains(search.toLowerCase())) {
			return true;
		}else {
			return false;
		}
	}
	
}
