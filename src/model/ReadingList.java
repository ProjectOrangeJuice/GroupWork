package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.Image;


/**
 * The Class ReadingList that handles the readinglist data.
 * 
 * @author Oliver Harris.
 */
public class ReadingList {
	
	/** The resources. */
	ArrayList<Resource> resources = new ArrayList<Resource>();
	
	/** The name. */
	String name;
	
	/** The description. */
	String description;
	
	/** The image. */
	Image image;
	
	/** The image default. */
	static String IMAGE_DEFAULT = "/graphics/readinglist.jpg";
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * Sets the description.
	 *
	 * @param description the new description.
	 */
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

	/**
	 * Instantiates a new reading list.
	 *
	 * @param resourceList the resource list.
	 * @param name the name.
	 * @param description the description.
	 * @param image the image.
	 */
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

	/**
	 * Gets the image.
	 *
	 * @return the image.
	 */
	public Image getImage() {
		return image;
	}


	/**
	 * Sets the image.
	 *
	 * @param image the new image.
	 */
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



	
	/**
	 * Change if the user is following a reading list.
	 *
	 * @param username the username.
	 * @param list the list.
	 */
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
	
	/**
	 * Removes the resource from list.
	 *
	 * @param name the name.
	 * @param rid the resource id.
	 */
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
	
	
	/**
	 * check if a user Follows the reading list.
	 *
	 * @param username the username.
	 * @param list the list name.
	 * @return true, if they are following
	 	 */
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
	
	/**
	 * get the lists the user is following.
	 *
	 * @param username the username.
	 * @return the reading lists.
	 */
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
	
	/**
	 * Adds the resource to reading list.
	 *
	 * @param r the resource id.
	 * @param name the name of list to add to.
	 */
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
	
	/**
	 * Gets the resources.
	 *
	 * @return the resources
	 */
	public ArrayList<Resource> getResources() {
		return resources;
	}

	/**
	 * Sets the resources.
	 *
	 * @param resources the new resources
	 */
	public void setResources(ArrayList<Resource> resources) {
		this.resources = resources;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Read reading lists.
	 *
	 * @return the array list
	 */
	public static ArrayList<ReadingList> readReadingLists() {
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
	
	
	
	/**
	 * Contains.
	 *
	 * @param search the search
	 * @return true, if successful
	 */
	public boolean contains(String search) {
		if(getName().toLowerCase().contains(search.toLowerCase())) {
			return true;
		}else {
			return false;
		}
	}
	
}
