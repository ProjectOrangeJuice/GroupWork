package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * The reserve data structure.
 * 
 * @author Oliver Harris.
 */
public class Reserve {

	
	/** The username. */
	private String username;
	
	/** The date. */
	private String date;
	
	/** The title. */
	private String title;
	
	/** The copy id. */
	private int copyId;
	
	/** The id. */
	private int id;
	
	/** The r id. */
	private int rId;
	
	/**
	 * Instantiates a new reserve.
	 *
	 * @param username the username.
	 * @param title the title.
	 * @param date the date.
	 * @param copy the copy.
	 * @param rId the resource id.
	 * @param id the reserve id.
	 */
	public Reserve(String username,String title, String date, int copy,int rId, int id) {
		this.username = username;
		this.title = title;
		this.date = date;
		this.copyId = copy;
		this.id = id;
		this.rId = rId;
	}
	

	
	/**
	 * Gets the resource id.
	 *
	 * @return the resource id.
	 */
	public int getRId() {
		return rId;
	}
	
	
/**
 * Cancel this reserve.
 */
public  void cancel() {
	try {
		 Connection connection = DBHelper.getConnection();


		 PreparedStatement statement = connection.prepareStatement("DELETE FROM reserve WHERE"
		 		+ " id=?");
	       statement.setInt(1, id);


	        statement.execute();
	      
	       connection.close();



	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
	
	/**
	 * Gets the title.
	 *
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date.
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Gets the copy id.
	 *
	 * @return the copy id.
	 */
	public int getCopyId() {
		return copyId;
	}
	
}
