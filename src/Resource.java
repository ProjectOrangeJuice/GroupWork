public abstract class Resource {	
	protected int uniqueID;
	protected String title;
	protected int year;
	
	public Resource(int uniqueID, String title, int year) {
		this.uniqueID = uniqueID;
		this.title = title;
		this.year = year;
	}

	public int getUniqueID() {
		return uniqueID;
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
