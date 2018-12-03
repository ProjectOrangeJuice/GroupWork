package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class DVD extends Resource {
	private String director;
	private int runtime;
	private String language;
	private ArrayList<String> subtitleLanguages;
	
	public static void loadDatabaseDVDs(ArrayList<Resource> resources) {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT resource.rID, resource.title, resource.year, resource.thumbnail,"
					+ "director, runTime, language FROM dvd, resource WHERE dvd.rID = resource.rID");
			
			while(rs.next()) {
				
				//ArrayList<String> subtitleLanguages = loadSubtitles(stmt, rs.getInt("rID"));
				
				Image resourceImage = new Image(rs.getString("thumbnail"), true);
				
				resources.add(new DVD(rs.getInt("rID"), rs.getString("title"), rs.getInt("year"),
						resourceImage, rs.getString("director"), rs.getInt("runTime"), rs.getString("language"), null)); //NEED TO FIX
				
				System.out.println("New DVD added!");
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public DVD(int uniqueID, String title, int year, Image thumbnail, String director, int runtime, String language, ArrayList<String> subtitleLanguages) {
		super(uniqueID, title, year, thumbnail);
		this.director = director;
		this.runtime = runtime;
		this.language = language;
		this.subtitleLanguages = subtitleLanguages;
	}
	
	public DVD(int uniqueID, String title, int year, Image thumbnail, String director, int runtime) {
		super(uniqueID, title, year, thumbnail);
		this.director = director;
		this.runtime = runtime;
	}
	
	public void setTitle(String title) {
		updateDbValue("dvd", this.uniqueID, "title", title);
		super.setTitle(title);
	}
	
	public void setYear(int year) {
		String yearString = Integer.toString(year);
		updateDbValue("dvd", this.uniqueID, "year", yearString);
		super.setTitle(yearString);
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
		updateDbValue("dvd", this.uniqueID, "director", director);
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
		updateDbValue("dvd", this.uniqueID, "runtime", Integer.toString(runtime));
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		updateDbValue("dvd", this.uniqueID, "language", language);
	}

	public ArrayList<String> getSubtitleLanguages() {
		return subtitleLanguages;
	}

	public void setSubtitleLanguages(ArrayList<String> subtitleLanguages) {
		this.subtitleLanguages = subtitleLanguages;
		//TO-DO update subtitle languages
	}

	private static ArrayList<String> loadSubtitles(Statement stmt, int dvdID) {
		ArrayList<String> subtitleLanguages = new ArrayList<>();
		
		try {
			ResultSet subtitles = stmt.executeQuery("SELECT * FROM SUBTITLES WHERE rID="+dvdID);
			while(subtitles.next()) {
				subtitleLanguages.add(subtitles.getString("subtitleLanguage"));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		
		return subtitleLanguages;
	}
}
