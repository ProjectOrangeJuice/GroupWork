public abstract class Resource {
	
	static int nextIdValue = 0;
	
	int uniqueID;
	String title;
	int year;
	
	public Resource(String title, int year) {
		
		this.uniqueID = nextIdValue;
		this.title = title;
		this.year = year;
		
		nextIdValue++;
		
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
