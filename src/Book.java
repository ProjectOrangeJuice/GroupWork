import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Book extends Resource {
	private String author;
	private String publisher;
	private String genre;
	private String ISBN;
	private String language;
	
	public static void loadDatabaseBooks(ArrayList<Resource> resources) {
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM book"); //Your sql goes here
			
			while(rs.next()) {
				resources.add(new Book(rs.getInt("rID"), rs.getString("title"), 
						rs.getInt("year"), null, rs.getString("author"),rs.getString("publisher"),
						rs.getString("genre"), rs.getString("ISBN"), rs.getString("language")));
			} 
		} catch (SQLException e) { 
			e.printStackTrace();
		}
	}
	public Book (int uniqueID, String title, int year, Image thumbnail, String author, String publisher, String genre, String ISBN, String language) {
		super(uniqueID, title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;
	}
	
	public Book (int uniqueID, String title, int year, Image thumbnail, String author, String publisher) {
		super(uniqueID, title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
	}
	
	public void setTitle(String title) {
		updateDbValue("book", this.uniqueID, "title", title);
		super.setTitle(title);
	}
	
	public void setYear(String year) {
		updateDbValue("book", this.uniqueID, "year", year);
		super.setTitle(year);
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

}
