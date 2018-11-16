import java.util.ArrayList;

public class DVD extends Resource {
	String director;
	int runtime;
	String language;
	ArrayList<String> subtitleLanguages;
	
	public DVD(String title, int year, String director, int runtime, String language, ArrayList<String> subtitleLanguages) {
		super(title, year);
		
		this.director = director;
		this.runtime = runtime;
		this.language = language;
		this.subtitleLanguages = subtitleLanguages;
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
