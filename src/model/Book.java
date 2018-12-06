package model;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Book extends Resource {
	
	private static final int MAX_FINE_AMOUNT=25;
	private static final int DAILY_FINE_AMOUNT=2;
	
	private String author;
	private String publisher;
	private String genre;
	private String ISBN;
	private String language;
	
	/**
	 * Method that loads the details of a book from the book table
	 * @param resources
	 */
	public static void loadDatabaseBooks() {
		try {
			
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT resource.rID, resource.year, resource.title, resource.thumbnail, author, publisher,"
					+ "genre, ISBN, language FROM book, resource WHERE book.rID = resource.rID"); //Your sql goes here
			
			while(rs.next()) {
				
				Image resourceImage = new Image(rs.getString("thumbnail"), true);
				
				resources.add(new Book(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), resourceImage, rs.getString("author"),rs.getString("publisher"),
						rs.getString("genre"), rs.getString("ISBN"), rs.getString("language")));
				
				System.out.println("New book added!");
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Book constructor with all the fields
	 * @param uniqueID
	 * @param title
	 * @param year
	 * @param thumbnail
	 * @param author
	 * @param publisher
	 * @param genre
	 * @param ISBN
	 * @param language
	 */
	public Book (int uniqueID, String title, int year, Image thumbnail, String author, String publisher, String genre, String ISBN, String language) {
		super(uniqueID, title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;
	}
	
	/**
	 * Book constructor that constructs the optional fields
	 * @param uniqueID
	 * @param title
	 * @param year
	 * @param thumbnail
	 * @param author
	 * @param publisher
	 */
	public Book (int uniqueID, String title, int year, Image thumbnail, String author, String publisher) {
		super(uniqueID, title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
	}
	
	/**
	 * 
	 */
	public void setTitle(String title) {
		updateDbValue("book", this.uniqueID, "title", title);
		super.setTitle(title);
	}
	
	public void setYear(int year) {
		String yearString = Integer.toString(year);
		updateDbValue("book", this.uniqueID, "year", yearString);
		super.setTitle(yearString);
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
		updateDbValue("book", this.uniqueID, "genre", genre);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
		updateDbValue("book", this.uniqueID, "author", author);
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
		updateDbValue("book", this.uniqueID, "publisher", publisher);
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
		updateDbValue("book", this.uniqueID, "ISBN", ISBN);
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		updateDbValue("book", this.uniqueID, "language", language);
	}
	
	public int getDailyFineAmount() {
		return DAILY_FINE_AMOUNT;
	}
	
	public int getMaxFineAmount() {
		return MAX_FINE_AMOUNT;
	}
}
