package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import model.DVD;
import model.Game;
import model.Laptop;
import model.MyList;
import model.ReadingList;
import model.ReserveFeature;
import model.Resource;
import model.Review;
import model.User;

/**
 * The gui that appears when a resource is clicked, shows the information about
 * a resource and allows the user to request a copy if there is a free copy
 * available.
 * 
 * @author Joe Wright
 * @author Oliver Harris
 */
public class CopyController {


	private static final int RES_IMG_WIDTH = 200;
	private static final int RES_IMG_HEIGHT = 200;

	private static final double ROUND = 100.0;
	private static final double REVIEW_SPACING = 8;

	// star, name,what,when
	private static final int STAR_INDEX = 0;
	private static final int NAME_INDEX = 1;
	private static final int REVIEW_INDEX = 2;
	private static final int WHEN_INDEX = 3;

	
	@FXML
	private Text rError;

	@FXML
	private Button reserveButton;

	@FXML
	private BorderPane borderpane1;// borderpane

	@FXML
	private DatePicker datepicker;

	@FXML
	private AnchorPane leftanchor;// anchor for left side of borderpane

	@FXML
	private VBox leftvbox;// vbox in left anchor

	@FXML
	private ImageView resourceimage;// resource image holder

	@FXML
	private AnchorPane centeranchor;// anchor pane in the center of borderpane

	@FXML
	private VBox centervbox;// vbox in center anchor

	@FXML
	private TextArea centertextarea;// textarea in center anchor

	@FXML
	private AnchorPane bottomanchor;// anchor for bottom of borderpane

	@FXML
	private Button requestbutt;// request copy button

	@FXML
	private Button viewTrailerButton;

	@FXML
	private VBox leftVbox;// vbox in left anchor pane

	@FXML
	private Label copytext;// textbox showing copies available

	@FXML
	private Label resourceName;// resources name

	private Resource currentResource;// current instance of resource

	@FXML
	private Text overLimit;

	@FXML
	private Button addListButton;

	@FXML
	private VBox seeReviews;

	/**
	 * Initialize the window.
	 */
	@FXML
	public void initialize() {
		
		if (ScreenManager.getCurrentUser() instanceof User) {
			checkIfBorrowed();
			setupLimit();
			checkCanReserve();
			if (MyList.isInMyList(ScreenManager.getCurrentUser().getUsername(),
					ScreenManager.getCurrentResource().getUniqueID())) {
				addListButton.setText("Remove from list");
			}
		} else {
			requestbutt.setDisable(true);
			rError.setVisible(false);
			setupStaffButtons();

		}
		loadResourceImage();
		loadResourceInformation();

		if (!(ScreenManager.currentResource.getClass() == DVD.class
				|| ScreenManager.currentResource.getClass() == Game.class)) {
			viewTrailerButton.setDisable(true);
		}

		// disable dates
		setupDatePicker();
		

	}

	/**
	 * Sets new scene on stage within program using fxml file provided.
	 *
	 * @param sceneFXML The scene location.
	 * @param event     The event.
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
	 * Add resource to list
	 */
	@FXML
	public void doListButton() {
		if (ScreenManager.getCurrentUser() instanceof User) {
			String user = ScreenManager.getCurrentUser().getUsername();
			int id = ScreenManager.getCurrentResource().getUniqueID();
			if (MyList.isInMyList(user, id)) {
				MyList.removeFromMyList(user, id);
				addListButton.setText("Add to list");
			} else {
				MyList.addToMyList(user, id);
				addListButton.setText("Remove from list");
			}
		} else {

			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("What reading list?");
			dialog.setHeaderText("Enter reading list name");
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> {
				if (name.length() > 1) {
					ReadingList.addToReadingList(
							ScreenManager.getCurrentResource().getUniqueID(),
							name);
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setContentText("The name was too short");

					alert.showAndWait();
				}
			});

		}

	}

	/**
	 * When the button is clicked, it will send a request to a librarian and they
	 * can either decline or accept said request.
	 *
	 * @param event button being pressed.
	 */
	@FXML
	public void requestCopy(MouseEvent event) {
		ScreenManager.currentResource.addPendingRequest(
				(User) ScreenManager.getCurrentUser());
		AlertBox.showInfoAlert("Requested!");
		// ScreenManager.currentResource.loanToUser((User)ScreenMana
		// ger.getCurrentUser());
	}

	/**
	 * The method that gets called every time the View Trailer button is clicked. It
	 * opens a new window showign an embedded youtube video of a trailer for the
	 * selected DVD or video game.
	 * 
	 * @param actionEvent The event that triggers the call of this method.
	 */
	@FXML
	public void showTrailerWindow(ActionEvent actionEvent) {
		Resource currentResource = ScreenManager.currentResource;

		if (currentResource.getClass() == DVD.class) {
			DVD currentMovie = (DVD) currentResource;

			String title = currentMovie.getTitle();

			MovieTrailerView trailerView = new MovieTrailerView(title);

			if (trailerView.getWebView() != null) {
				Scene trailerScene = new Scene(trailerView.getWebView(),
						trailerView.getPrefViewWidth(),
						trailerView.getPrefViewHeight());

				Stage trailerWindow = new Stage();
				trailerWindow.setTitle(trailerView.getTrailerDescription().getName());

				trailerWindow.setOnHidden(e -> {
					trailerView.stop();
				});

				trailerWindow.setScene(trailerScene);
				trailerWindow.show();
			}
		} else if (currentResource.getClass() == Game.class) {
			Game currentGame = (Game) currentResource;

			String title = currentGame.getTitle();

			GameTrailerView trailerView = new GameTrailerView(title);

			if (trailerView.getWebView() != null) {
				Scene trailerScene = new Scene(trailerView.getWebView(), 
						trailerView.getPrefViewWidth(),
						trailerView.getPrefViewHeight());

				Stage trailerWindow = new Stage();
				trailerWindow.setTitle(trailerView.getVideoName());

				trailerWindow.setOnHidden(e -> {
					trailerView.stop();
				});

				trailerWindow.setScene(trailerScene);
				trailerWindow.show();
			}
		}
	}

	/**
	 * This checks if the current user is currently borrowing the resource, if they
	 * have then it will disable the request copy button.
	 */
	private void checkIfBorrowed() {
		User user = (User) ScreenManager.getCurrentUser();

		if (user.isBorrowing(ScreenManager.currentResource)) {

			requestbutt.setDisable(true);
		}
	}

	/**
	 * These are only buttons that appear when the user is a staff, these buttons
	 * allow the librarian to manage the resources.
	 */
	private void setupStaffButtons() {
		reserveButton.setDisable(true);
		Button editCopies = new Button("Edit copies");
		editCopies.setOnAction(e -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/fxml/editCopies.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				// stage.initStyle(StageStyle.UNDECORATED);
				stage.setTitle("Copies");
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		Button editResource = new Button("Edit resource");
		editResource.setOnAction(e -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/fxml/editResource.fxml"));
				Parent root1 = (Parent) fxmlLoader.load();
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				// stage.initStyle(StageStyle.UNDECORATED);
				stage.setTitle("Resource");
				stage.setScene(new Scene(root1));
				stage.show();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		leftVbox.getChildren().addAll(editCopies, editResource);
	}

	/**
	 * Disable buttons if the user cannot borrow the resource due to limits.
	 */
	private void setupLimit() {
		User user = (User) ScreenManager.getCurrentUser();
		if (user.exceedLimit(ScreenManager.getCurrentResource())) {
			requestbutt.setDisable(true);
			overLimit.setVisible(true);

		}
	}

	/**
	 * Reserve resource on selected date.
	 * 
	 * @param event The action event.
	 */
	@FXML
	public void reserveOn(ActionEvent event) {
		LocalDate dateSelected = datepicker.getValue();
		if (dateSelected == null) {
			AlertBox.showErrorAlert("No date selected");
		} else {
			Resource r = ScreenManager.getCurrentResource();
			ReserveFeature.reserve(
					r.getUniqueID(), ScreenManager.getCurrentUser().getUsername(),
					dateSelected);
			AlertBox.showInfoAlert("Reserved!");
		}
		checkCanReserve();
	}

	/**
	 * Blanks out days that are unavailable.
	 */
	private void setupDatePicker() {
		datepicker.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();

				setDisable(empty || date.compareTo(today) < 0);

				if (ReserveFeature.getFreeCopy(
						ScreenManager.getCurrentResource().getUniqueID(),
						date) == 0) {
					setDisable(true);
				}
			}
		});
	}

	/**
	 * Method that deals with reviews and if the user is staff, a remove button
	 * appears.
	 */
	private void dealWithReviews() {
		int resourceId = ScreenManager.currentResource.getUniqueID();
		boolean hasReviews = model.Review.hasReviews(resourceId);

		if (hasReviews) {

			HBox avg = new HBox(); // ready for images
			Text avgText = new Text("Rating: " + 
			Math.round(model.Review.getAvgStar(resourceId) * ROUND) / ROUND);
			avgText.setStyle("-fx-font: 24 arial;");
			avg.getChildren().add(avgText);
			seeReviews.getChildren().add(avg);

			// Now for the actual reviews
			for (String[] review : Review.getReviews(resourceId)) {

				VBox topV = new VBox();
				HBox topH = new HBox();
				// star, name,what,when
				Text title = new Text(" from " + review[NAME_INDEX]);
				Text when = new Text(" [" + review[WHEN_INDEX] + "]");
				when.setStyle("-fx-font:12 arial;");
				Text star = new Text("Rating: " + review[STAR_INDEX]);
				star.setFill(Color.GREEN);
				Text reviewText = new Text(review[REVIEW_INDEX]);
				reviewText.setStyle("-fx-font:15 arial;");
				topH.getChildren().addAll(star, title, when);

				// if staff , add button.
				if (ScreenManager.getCurrentUser() instanceof model.Librarian) {

					Button removeReview = new Button("Remove");
					removeReview.setOnAction((event) -> {
						Review.removeReview(Integer.valueOf(review[4]));
						seeReviews.getChildren().clear();

						dealWithReviews();
					});
					topV.getChildren().addAll(topH, reviewText, removeReview);
				} else {
					topV.getChildren().addAll(topH, reviewText);
				}
				seeReviews.getChildren().add(topV);

			}
			seeReviews.setSpacing(REVIEW_SPACING);

		} else {
			Text avgText = new Text("No reviews yet!");
			seeReviews.getChildren().add(avgText);
		}
	}

	/**
	 * Loads resource information from Screen Manager class, so that it can be
	 * displayed within the UI. Shows different information depending on the
	 * resource.
	 */
	private void loadResourceInformation() {

		// Gets the common attributes between each resource
		int uniqueId = ScreenManager.currentResource.getUniqueID();
		String title = ScreenManager.currentResource.getTitle();
		int year = ScreenManager.currentResource.getYear();
		dealWithReviews();

		resourceName.setText(title);
		resourceName.setWrapText(true);
		// Adds all the common attributes to the text area
		centertextarea.appendText(
				"UniqueID: " + Integer.toString(uniqueId) + "\nTitle: " + title
				+ "\nYear: " + Integer.toString(year));

		// If the resource is a Book, it will add the book attributes to the
		// text area.
		if (ScreenManager.currentResource instanceof Book) {
			ScreenManager.currentBook = (Book) ScreenManager.currentResource;
			String author = ScreenManager.currentBook.getAuthor();
			String publisher = ScreenManager.currentBook.getPublisher();
			String genre = ScreenManager.currentBook.getGenre();
			String isbn = ScreenManager.currentBook.getISBN();
			String language = ScreenManager.currentBook.getLanguage();

			centertextarea.appendText("\nAuthor: " + author + "\nPublisher: " 
			+ publisher + "\nGenre: " + genre
					+ "\nISBN: " + isbn + "\nLanguage: " + language);

			// If the resource is a Laptop, it will add the laptop attributes to
			// the text area.
		} else if (ScreenManager.currentResource instanceof Laptop) {
			ScreenManager.currentLaptop = (Laptop) ScreenManager.currentResource;
			String manufacturer = ScreenManager.currentLaptop.getManufacturer();
			String model = ScreenManager.currentLaptop.getModel();
			String operatingSystem = ScreenManager.currentLaptop.getOS();

			centertextarea
			.appendText("\nManufacturer: " + manufacturer + "\nModel: " + model 
					+ "\nOS: " + operatingSystem);

			// If the resource is a DVD, it will add the attributes of a dvd to
			// the text area.
		} else if (ScreenManager.currentResource instanceof DVD) {
			ScreenManager.currentDVD = (DVD) ScreenManager.currentResource;
			String director = ScreenManager.currentDVD.getDirector();
			int runtime = ScreenManager.currentDVD.getRuntime();
			String language = ScreenManager.currentDVD.getLanguage();

			centertextarea.appendText(
					"\nDirector: " + director + "\nRuntime: " 
			+ Integer.toString(runtime) + "\nLanguage: " + language);

			// If the resource is a Game, it will add the attributes of a Game
			// to
			// the text area.
		} else if (ScreenManager.currentResource instanceof Game) {
			ScreenManager.currentGame = (Game) ScreenManager.currentResource;

			String publisher = ScreenManager.currentGame.getPublisher();
			String genre = ScreenManager.currentGame.getGenre();
			String rating = ScreenManager.currentGame.getRating();
			String multiplayer = ScreenManager.currentGame.getMultiplayerSupport();

			centertextarea.appendText("\nPublisher: " + publisher + "\nGenre: " 
			+ genre + "\nRating: " + rating
					+ "\nHas Mulitplayer? " + multiplayer);
		}

		// This sets the textbox depending if the number of copies is equal to 0
		// or not.
		if (ScreenManager.currentResource.getNrOfCopies() == 0) {
			copytext.setText("All Copies are currently being borrowed.");
		} else {
			copytext.setText("Copies: " + Integer.toString(
					ScreenManager.currentResource.getNrOfCopies()));
		}

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

	/**
	 * Checks if the user can reserve this item.
	 */
	private void checkCanReserve() {
		String date = ReserveFeature.canReserve(
				ScreenManager.getCurrentResource().getUniqueID(),
				ScreenManager.getCurrentUser().getUsername());
		if (date.equals("")) {
			rError.setVisible(false);
			reserveButton.setDisable(false);
		} else {
			rError.setText("Reserved for " + date);
			reserveButton.setDisable(true);
			rError.setVisible(true);
		}
	}

}
