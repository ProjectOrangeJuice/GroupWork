import javafx.scene.image.Image;

public abstract class Resource {	
	protected int uniqueID;
	protected String title;
	protected int year;
	protected Image thumbnail;
	
	public Resource(int uniqueID, String title, int year, Image thumbnail) {
		this.uniqueID = uniqueID;
		this.title = title;
		this.year = year;
		this.thumbnail = thumbnail;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
