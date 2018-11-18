import java.util.ArrayList;

import javafx.scene.image.Image;

public class DVD extends Resource {
	private String director;
	private int runtime;
	private String language;
	private ArrayList<String> subtitleLanguages;
	
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ArrayList<String> getSubtitleLanguages() {
		return subtitleLanguages;
	}

	public void setSubtitleLanguages(ArrayList<String> subtitleLanguages) {
		this.subtitleLanguages = subtitleLanguages;
	}

}
