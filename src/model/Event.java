package model;

public class Event {
	
	private String title;
	private String details;
	private String date;
	private int maxAttending;
	
	public Event(String title, String details, String date, int maxAttending) {

		this.title = title;
		this.details = details;
		this.date = date;
		this.maxAttending = maxAttending;
		
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getMaxAttending() {
		return maxAttending;
	}
	
	public void setMaxAttending(int maxAttending) {
		this.maxAttending = maxAttending;
	}

}
