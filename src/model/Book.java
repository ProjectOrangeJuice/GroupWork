package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;

public class Book extends Resource {

    private static final int MAX_FINE_AMOUNT = 25;
    private static final int DAILY_FINE_AMOUNT = 2;

    private String author;
    private String publisher;
    private String genre;
    private String isbn;
    private String language;

    /**
     * Method that loads the details of a book from the book table
     * 
     * @param resources
     */
    public static void loadDatabaseBooks() {
        try {

            Connection conn = DBHelper.getConnection(); // get the connection
            Statement stmt = conn.createStatement(); // prep a statement
            ResultSet rs = stmt.executeQuery(
                "SELECT resource.rID, resource.year, resource.title, resource.thumbnail, author, publisher," +
                    "genre, ISBN, language FROM book, resource WHERE book.rID = resource.rID"); // Your sql
                                                                                                // goes here

            while (rs.next()) {
                Image resourceImage = new Image(rs.getString("thumbnail"), true);
                // Image resourceImage=null;
                resources.add(new Book(rs.getInt("rID"), rs.getString("title"), rs.getInt("year"), resourceImage,
                    rs.getString("author"), rs.getString("publisher"), rs.getString("genre"), rs.getString("ISBN"),
                    rs.getString("language")));

                System.out.println("New book added!");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Book constructor with all the fields
     * 
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
    public Book(int uniqueID, String title, int year, Image thumbnail, String author, String publisher, String genre,
            String ISBN, String language) {
        super(uniqueID, title, year, thumbnail);
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.isbn = ISBN;
        this.language = language;
    }

    /**
     * Book constructor that constructs the optional fields
     * 
     * @param uniqueID
     * @param title
     * @param year
     * @param thumbnail
     * @param author
     * @param publisher
     */
    public Book(int uniqueID, String title, int year, Image thumbnail, String author, String publisher) {
        super(uniqueID, title, year, thumbnail);
        this.author = author;
        this.publisher = publisher;
    }

    /**
     * Get genre method
     * 
     * @return genre of book
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre variable from database
     * 
     * @param genre of book
     */
    public void setGenre(String genre) {
        this.genre = genre;
        updateDbValue("book", this.uniqueID, "genre", genre);
    }

    /**
     * Get author method
     * 
     * @return author of book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author variable from database.
     * 
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
        updateDbValue("book", this.uniqueID, "author", author);
    }

    /**
     * Get publisher method
     * 
     * @return publisher of book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Set publisher variable from database
     * 
     * @param publisher of book
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
        updateDbValue("book", this.uniqueID, "publisher", publisher);
    }

    /**
     * Get ISBN method
     * 
     * @return ISBN of a book
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * Set ISBN variable from database
     * 
     * @param ISBN of a book
     */
    public void setISBN(String ISBN) {
        this.isbn = ISBN;
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

    public int getLikenessScore(Resource otherResource) {
        int score = 0;

        if (otherResource.getClass() == Book.class) {

            Book otherBook = (Book) otherResource;

            if (author.equals(otherBook.getAuthor())) {
                score++;
            }

            if (publisher.equals(otherBook.getPublisher())) {
                score++;
            }

            if (genre != null) {
                if (genre.equals(otherBook.getGenre())) {
                    score++;
                }
            }

            if (isbn != null) {
                if (isbn.equals(otherBook.getISBN())) {
                    score++;
                }
            }

            if (language != null) {
                if (language.equals(otherBook.getLanguage())) {
                    score++;
                }
            }
        }

        score += super.getLikenessScore(otherResource);
        return score;
    }
}
