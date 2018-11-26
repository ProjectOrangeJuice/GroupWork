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
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here
			
			rs = stmt.executeQuery("SELECT * FROM dvd");
			
			while(rs.next()) {
				int dvdID = rs.getInt("rID");
				ArrayList<String> subtitleLanguages = loadSubtitles(stmt, dvdID);
				
				resources.add(new DVD(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), null, rs.getString("director"), rs.getInt("runTime"), 
						rs.getString("language"), subtitleLanguages));
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
	
	public void setYear(String year) {
		updateDbValue("dvd", this.uniqueID, "year", year);
		super.setTitle(year);
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
			ResultSet languages = stmt.executeQuery("SELECT * FROM LANGUAGES WHERE rID="+dvdID);
			while(languages.next()) {
				subtitleLanguages.add(languages.getString("language"));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
		
		return subtitleLanguages;
	}
}
