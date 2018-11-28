package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Laptop extends Resource {
	private String manufacturer;
	private String model;
	private String OS;
	
	public static void loadDatabaseLaptops(ArrayList<Resource> resources) {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here
			
			rs = stmt.executeQuery("SELECT * FROM laptop");
			
			while(rs.next()) {
				resources.add(new Laptop(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), null, rs.getString("manufacturer"), rs.getString("Model"),
						rs.getString("os")));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
	}
	
	public Laptop(int uniqueID, String title, int year, Image thumbnail, String manufacturer, String model, String OS) {
		super(uniqueID, title, year, thumbnail);
		this.manufacturer = manufacturer;
		this.model = model;
		this.OS = OS;
	}
	
	public void setTitle(String title) {
		updateDbValue("laptop", this.uniqueID, "title", title);
		super.setTitle(title);
	}
	
	public void setYear(String year) {
		updateDbValue("laptop", this.uniqueID, "year", year);
		super.setTitle(year);
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
		updateDbValue("laptop", this.uniqueID, "manufacturer", manufacturer);
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
		updateDbValue("laptop", this.uniqueID, "model", model);
	}

	public String getOS() {
		return OS;
	}

	public void setOS(String OS) {
		this.OS = OS;
		updateDbValue("laptop", this.uniqueID, "OS", OS);
		
	}

	
}
