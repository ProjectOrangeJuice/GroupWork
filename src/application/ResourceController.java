package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Book;
import model.DVD;
import model.Laptop;
import model.Resource;

public class ResourceController {

	@FXML
	VBox resourceBlock;
	
	
	private void setupBook() {
		Book book = (Book) ScreenManager.getCurrentResource();
		
		HBox titleBox = new HBox();
		Text titleText = new Text("Title");
		TextField titleField = new TextField (book.getTitle());
		titleBox.getChildren().addAll(titleText,titleField);
		
		HBox yearBox = new HBox();
		Text yearText = new Text("Year");
		TextField yearField = new TextField (String.valueOf(book.getYear()));
		yearBox.getChildren().addAll(yearText,yearField);
		
		//the rest are not from resource
		
		HBox authorBox = new HBox();
		Text authorText = new Text("Author");
		TextField authorField = new TextField (book.getAuthor());
		authorBox.getChildren().addAll(authorText,authorField);
		
		HBox publishBox = new HBox();
		Text publishText = new Text("Publish");
		TextField publishField = new TextField (book.getPublisher());
		publishBox.getChildren().addAll(publishText,publishField);
		
		HBox genreBox = new HBox();
		Text genreText = new Text("Genre");
		TextField genreField = new TextField (book.getGenre());
		genreBox.getChildren().addAll(genreText,genreField);
		
		HBox iBox = new HBox();
		Text iText = new Text("ISBN");
		TextField iField = new TextField (book.getISBN());
		iBox.getChildren().addAll(iText,iField);
		
		HBox languageBox = new HBox();
		Text languageText = new Text("Language");
		TextField languageField = new TextField (book.getLanguage());
		languageBox.getChildren().addAll(languageText,languageField);
		
		
		HBox imgBox = new HBox();
		Text imgText = new Text("Path to image");
		TextField imgField = new TextField ();
		imgBox.getChildren().addAll(imgText,imgField);
		
		
		Button button = new Button("Save");
		button.setOnAction(e -> {
			updateBook(titleField.getText(), yearField.getText(), authorField.getText(), publishField.getText(), genreField.getText(), iField.getText(), languageField.getText(), imgField.getText());
		});
		
		
		resourceBlock.getChildren().addAll(titleBox,yearBox,authorBox,publishBox,genreBox,iBox,languageBox,imgBox,button);
		
	}
	
	private void updateBook(String title, String year, String author, String publish, String genre, String ISBN, String language, String img){
		boolean goAhead = true;
		try {
			Integer.parseInt(year);
		}catch (NumberFormatException  e) {
			goAhead = false;
			alertDone("Year must be a number");
		}
		
		if (goAhead) {
			Book resource = (Book) ScreenManager.getCurrentResource();
			resource.setTitle(title);
			resource.setYear(Integer.parseInt(year));
			resource.setAuthor(author);
			resource.setPublisher(publish);
			resource.setGenre(genre);
			resource.setISBN(ISBN);
			resource.setLanguage(language);
			
			
		}
	}
	
	
	/**
	 * Generate a popup.
	 * @param text The text to be displayed.
	 */
	private void alertDone(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	@FXML
	 public void initialize() {
		Resource resource = ScreenManager.getCurrentResource();
		if(resource instanceof DVD) {
			
		}else if(resource instanceof Book) {
			setupBook();
		}else if(resource instanceof Laptop) {
			
		}
	}
	
	
	
}
