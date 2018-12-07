package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class DVD extends Resource {
	
	private static final int MAX_FINE_AMOUNT=25;
	private static final int DAILY_FINE_AMOUNT=2;
	
	private String director;
	private int runtime;
	private String language;
	private ArrayList<String> subtitleLanguages;
	
	public static void loadDatabaseDVDs() {
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
	
	public DVD(int uniqueID, String title, int year, Image thumbnail, String director, int runtime, String language, ArrayList<String> subtitleList) {
		super(uniqueID, title, year, thumbnail);
		this.director = director;
		this.runtime = runtime;
		this.language = language;
		
		loadSubtitles();
		
		for(String subtitle: subtitleList) {
			addSubtitle(subtitle);
		}
	}
	
	public DVD(int uniqueID, String title, int year, Image thumbnail, String director, int runtime) {
		super(uniqueID, title, year, thumbnail);
		this.director = director;
		this.runtime = runtime;
		
		loadSubtitles();
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

	public String getSubtitleLanguages() {
		String result="";
		
		for(String s: subtitleLanguages) {
			result+=s+"\n";
		}
		
		return result;
	}

	public void addSubtitle(String language) {
		//go through the list and make sure the language is not in there already
		for(String s: subtitleLanguages) {
			if(s.equals(language)) {
				return;
			}
		}
		
		subtitleLanguages.add(language);
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			stmt.executeUpdate("INSERT INTO subtitles VALUES ("+uniqueID+","+language+")");
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public int getDailyFineAmount() {
		return DAILY_FINE_AMOUNT;
	}
	
	public int getMaxFineAmount() {
		return MAX_FINE_AMOUNT;
	}

	private void loadSubtitles() {
		if(subtitleLanguages!=null) {
			subtitleLanguages.clear();
		} else {
			subtitleLanguages=new ArrayList<String>();
		}
		
		
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet subtitles = stmt.executeQuery("SELECT * FROM SUBTITLES WHERE rID="+uniqueID);
			while(subtitles.next()) {
				subtitleLanguages.add(subtitles.getString("subtitleLanguage"));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
	}
}
