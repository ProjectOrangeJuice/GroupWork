package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Book;
import model.DVD;
import model.Laptop;
import model.Resource;
import model.User;

/**
 * 
 * @author Joe Wright
 *
 */
public class CopyController {

	@FXML
	private BorderPane borderpane1;

	@FXML
	private AnchorPane leftanchor;

	@FXML
	private VBox leftvbox;

	@FXML
	private ImageView resourceimage;

	@FXML
	private AnchorPane centeranchor;

	@FXML
	private VBox centervbox;

	@FXML
	private TextArea centertextarea;

	@FXML
	private AnchorPane bottomanchor;

	@FXML
	private Button requestbutt;

	@FXML
	private Button closewindow;
	
	@FXML
	private Label copytext;
	
	@FXML
	private Label resourceName;
	
	private Resource currentResource;

	private int RES_IMG_WIDTH = 200;
	private int RES_IMG_HEIGHT = 200;


	/**
	 * Sets new scene on stage within program using fxml file provided.
	 * 
	 * @param sceneFXML
	 */
	public void changeScene(MouseEvent event, String sceneFXML) {
		try {
			// create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Called when close button is clicked.
	 * 
	 * @param event
	 *            Passed when mouse event occurs
	 */
	@FXML
	public void closeWindow(MouseEvent event) {
		changeScene(event, "/fxml/profileScene.fxml");
	}

	/**
	 * Loads resource information from Screen Manager class, so that it can be
	 * displayed within the UI.
	 */
	private void loadResourceInformation() {

		int uniqueId = ScreenManager.currentResource.getUniqueID();
		String title = ScreenManager.currentResource.getTitle();
		int year = ScreenManager.currentResource.getYear();
		
		resourceName.setText(title);
		
		centertextarea.appendText("uniqueID: " + Integer.toString(uniqueId) + "\ntitle: " + title + "\nyear: " + Integer.toString(year));

		if (ScreenManager.currentResource instanceof Book) {
			ScreenManager.currentBook = (Book) ScreenManager.currentResource;
			String author = ScreenManager.currentBook.getAuthor();
			String publisher = ScreenManager.currentBook.getPublisher();
			String genre = ScreenManager.currentBook.getGenre();
			String ISBN = ScreenManager.currentBook.getISBN();
			String language = ScreenManager.currentBook.getLanguage();
			
			centertextarea.appendText("\nauthor: " + author + "\npublisher: " + publisher + "\ngenre: " + genre + "\nISBN: " + ISBN + "\nlanguage: "
			+ language);
			
		} else if (ScreenManager.currentResource instanceof Laptop) {
			ScreenManager.currentLaptop = (Laptop) ScreenManager.currentResource;
			String manufacturer = ScreenManager.currentLaptop.getManufacturer();
			String model = ScreenManager.currentLaptop.getModel();
			String OS = ScreenManager.currentLaptop.getOS();
			
			centertextarea.appendText("\nmanufacturer: " + manufacturer + "\nmodel: " + model + "\nOS: " + OS);
			
		} else if (ScreenManager.currentResource instanceof DVD) {
			ScreenManager.currentDVD = (DVD) ScreenManager.currentResource;
			String director = ScreenManager.currentDVD.getDirector();
			int runtime = ScreenManager.currentDVD.getRuntime();
			String language = ScreenManager.currentDVD.getDirector();
			
			centertextarea.appendText("\ndirector: " + director + "\nruntime: " + Integer.toString(runtime) + "\nlanguage: " + language);

		}
		
	/*	 if(ScreenManager.currentResource.getFreeCopies() == 0){
			copytext.setText("All Copies are currently being borrowed.");
		}else{
			copytext.setText(Integer.toString(ScreenManager.currentResource.getFreeCopies()));
		}*/

	}

	/**
	 * Loads resource image from Resource class, so that they can be displayed
	 * within the UI.
	 */
	private void loadResourceImage() {
			// create new resource image to be added.
			resourceimage.setFitWidth(RES_IMG_WIDTH);
			resourceimage.setFitHeight(RES_IMG_HEIGHT);
			resourceimage.setImage(ScreenManager.currentResource.getThumbnail());
	}
	
	@FXML
	public void requestCopy(MouseEvent event) {
		ScreenManager.currentResource.loanToUser((User)ScreenManager.getCurrentUser());
	}
	
	@FXML
	 public void initialize() {
		
		
		loadResourceImage();
		loadResourceInformation();
		
	
	 }

}
