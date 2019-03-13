package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Event {
	
	private int ID;
	private String title;
	private String details;
	private String date;
	private int maxAttending;

	private static ArrayList<Event> allEvents = new ArrayList<Event>();


	public Event(String title, String details, String date, int maxAttending) {
		
		this.title = title;
		this.details = details;
		this.date = date;
		this.maxAttending = maxAttending;
		
	}
	
	private static boolean checkFutureDate(String dateString) throws ParseException {
		
		LocalDate localDate = LocalDate.now();
		
		Date eventDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
		Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		return eventDate.after(currentDate) || eventDate.equals(currentDate);
		
		
	}
	
	public static void loadEvents() throws SQLException, ParseException {
		
		Connection connectionToDB = DBHelper.getConnection();
        
        Statement stmt = connectionToDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM events");

		while(rs.next()) {
			
			
			System.out.println("---------------------------------------------------- " + rs.getString(4));
			if(checkFutureDate(rs.getString(4))) {
				allEvents.add(new Event(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
				allEvents.get(allEvents.size()-1).setID(rs.getInt(1));
				System.out.println("hello");
			}
			
		}
		
		connectionToDB.close();
		
	}

	public static void addEvent(String title, String details, String date, int maxAllowed) {
		allEvents.add(new Event(title, details, date, maxAllowed));
	}
	
	

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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
	
	public static ArrayList<Event> getAllEvents() {
		return allEvents;
	}

	public static void setAllEvents(ArrayList<Event> newEvents) {
		allEvents = newEvents;
	}
	
	public static void updateEvent(Event event) throws SQLException {
		
		Connection connectionToDB = DBHelper.getConnection();
        Statement stmt = connectionToDB.createStatement();
        stmt.execute("UPDATE events SET title = '" + event.title + "', details = '" +
        event.details + "', date = '" + event.date + "', maxAllowed = " + event.maxAttending + " WHERE eID = " + event.ID);

	}

}
