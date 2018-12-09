package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class DVD extends Resource {
	
	private static final int MAX_FINE_AMOUNT=25;
	private static final int DAILY_FINE_AMOUNT=2;
	
	private String director;
	private int runTime;
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
				
				//Image resourceImage = new Image(rs.getString("thumbnail"), true);
				Image resourceImage=null;
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
		this.runTime = runtime;
		this.language = language;
		
		loadSubtitles();
		
		if(subtitleList!=null) {
			for(String subtitle: subtitleList) {
				addSubtitle(subtitle);
			}
		}
	}
	
	public DVD(int uniqueID, String title, int year, Image thumbnail, String director, int runtime) {
		super(uniqueID, title, year, thumbnail);
		this.director = director;
		this.runTime = runtime;
		
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
		return runTime;
	}

	public void setRuntime(int runtime) {
		this.runTime = runtime;
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

	public void addSubtitle(String subtitleLanguage) {
		//go through the list and make sure the language is not in there already
		for(String s: subtitleLanguages) {
			if(s.equals(subtitleLanguage)) {
				return;
			}
		}
		
		subtitleLanguages.add(subtitleLanguage);
		try {
			Connection connectionToDB = DBHelper.getConnection(); //get the connection
			Statement sqlStatement = connectionToDB.createStatement(); //prep a statement
			sqlStatement.executeUpdate("INSERT INTO subtitles VALUES ("+uniqueID+","+subtitleLanguage+")");
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	
	public void deleteSubtitle(String subtitleLanguage) {
		int languageIndex=-1;
		
		for(int i=0; i<subtitleLanguages.size(); i++) {
			if(subtitleLanguages.get(i).equals(subtitleLanguage)) {
				languageIndex=i;
			}
		}
		
		if(languageIndex != -1) {
			subtitleLanguages.remove(languageIndex);
			
			try {
				Connection connectionToDB = DBHelper.getConnection(); //get the connection
				PreparedStatement sqlStatement = connectionToDB.prepareStatement
						("DELETE FROM subtitles WHERE dvdID=? and subtitleLanguage=?"); //prep a statement
				
				sqlStatement.setInt(1, uniqueID);
				sqlStatement.setString(2, subtitleLanguage);
				sqlStatement.executeUpdate();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		}
	}
	
	public int getDailyFineAmount() {
		return DAILY_FINE_AMOUNT;
	}
	
	public int getMaxFineAmount() {
		return MAX_FINE_AMOUNT;
	}
	
	public int getLikenessScore(Resource otherResource) {
		int score=0;
		
		if(otherResource.getClass()==DVD.class) {
			
			DVD otherDVD=(DVD)otherResource;
			
			if(director.equals(otherDVD.getDirector())) {
				score++;
			}
		
			if(runTime==otherDVD.getRuntime()) {
				score++;
			}
			
			if(language!=null) {
				if(language.equals(otherDVD.getLanguage())) {
					score++;
				}
			}
		}
		
		score+=super.getLikenessScore(otherResource);
		return score;
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
			ResultSet subtitles = stmt.executeQuery("SELECT * FROM SUBTITLES WHERE dvdID="+uniqueID);
			while(subtitles.next()) {
				subtitleLanguages.add(subtitles.getString("subtitleLanguage"));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
		} 
	}
}
