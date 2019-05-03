package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reserve {

	
	private String username;
	private String date;
	private String title;
	private int copyId;
	private int id;
	private int rId;
	
	public Reserve(String username,String title, String date, int copy,int rId, int id) {
		this.username = username;
		this.title = title;
		this.date = date;
		this.copyId = copy;
		this.id = id;
		this.rId = rId;
	}
	

	
	public int getRId() {
		return rId;
	}
	
	
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
	
	public String getTitle() {
		return title;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getDate() {
		return date;
	}
	
	public int getCopyId() {
		return copyId;
	}
	
}
