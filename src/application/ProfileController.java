package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import model.Copy;
import model.DBHelper;
import model.DVD;
import model.Fine;
import model.Laptop;
import model.Librarian;
import model.Person;
import model.Resource;
import model.User;

public class ProfileController {

	@FXML
	private HBox resourceImages;//hbox that holds the resource images

	@FXML
	private ScrollPane scrollPane;//allows the user to scroll through the images
	
	@FXML
	private VBox vResourceBox;//vbox that contains the hbox of resources
	
	@FXML
	private TextField searchTextBox;//allows the user to search for resources
	
	@FXML
	private LoginController TextField;//log out link
	
	//User Profile
	@FXML
	private Label userLabel;//label displaying "Username"
	@FXML
	private Label fullnameLabel;//label displaying "Full name"
	@FXML
	private Label phoneLabel;//label displaying "Phone number"
	@FXML
	private Label addressLabel;//label displaying "Address"
	@FXML
	private Label postcodeLabel;//label displaying "Post code"
	@FXML
	private Label balanceLabel;//label displaying "Balance"
	
	//Staff Profile
	@FXML
	private Label userLabel1;//label displaying the username from the database 
	@FXML
	private Label fullnameLabel1;//label displaying the fullanme of the user from the database 
	@FXML
	private Label phoneLabel1;//label displaying the phone number of the user from the database 
	@FXML
	private Label addressLabel1;//label displaying the address of the user from the database 
	@FXML
	private Label postcodeLabel1;//label displaying the postcode of the user from the database 
	@FXML
	private Label dateLabel1;//label displaying the employment date of the staff from the database 
	@FXML
	private Label staffIDLabel1;//label displaying the staff id from the database 	
	
	@FXML
	private Tab userProfileTab;//my profile tab
	
	@FXML
	private Tab resourcesTab;// library resources tab
	
	@FXML
	private Tab transactionTab;//transaction tab
	
	@FXML
	private Tab staffProfileTab;//staff profile tab
	
	@FXML
	private TabPane tabs;//tab pane that holds the tabs
	
	@FXML
	private Label accountBalance;//label that displays an users balance
	
	@FXML
	private Button userEditProfileButton;//allows user to edit their profile
	
	@FXML
	private Button staffEditProfileButton;//allows staff to edit their profile
	
	@FXML
	private Button staffEditAvatar;//allows staff to edit avatar
	@FXML
	private Button userEditAvatar;//allows user to edit avatar
	
	//check boxes
	@FXML
	private CheckBox dvdCheck;
	@FXML
	private CheckBox bookCheck;
	@FXML
	private CheckBox laptopCheck;
	
	//Copies Explorer
	@FXML
	private Button staffOverdueFilter;
	@FXML
	private Button staffRequestedFilter;
	@FXML
	private Button staffAllFilter;
	@FXML
	private Button staffHistoryFind;
	@FXML
	private Button staffApproveCopy;
	@FXML
	private Button staffReturnCopy;
	@FXML
	private TextField staffCopyIDField;
	@FXML
	private TableView staffCopiesExplorerTable;
	
	//Manage Users
	@FXML
	private TableView<Person> staffUsersTable;
	@FXML
	private Label selectedUserLabel;
	@FXML
	private TextField staffAmountField;
	
	@FXML
	private VBox leftVbox;
	
	//Avatars
	@FXML
	private ImageView staffAvatarView;
	@FXML
	private ImageView userAvatarView;
	
	//may remove fixed size resource images
	//when dealing with window resizing.
	private final int RES_IMG_WIDTH = 150;
	private final int RES_IMG_HEIGHT = 250;
	
	private final int COPY_IMG_WIDTH = 195;
	private final int COPY_IMG_HEIGHT = 325;
	
	private Person currentUser;
	private ArrayList<Resource> resources;

	/**
	 * Sets new scene on stage within program using fxml file provided.
	 * @param sceneFXML
	 */
	public void changeScene(MouseEvent event, String sceneFXML) {
		try {
			//create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@FXML
	private void searchBarSwitch (MouseEvent event) {
		//tabPane.getSelectionModel().select(2);
	}
	
	/**
	 * Method that searches for resources
	 * @param event when something is typed into the search box
	 */
	@FXML  
    void searchThis(Event event) {
		tabs.getSelectionModel().select(resourcesTab);
		vResourceBox.getChildren().clear();
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		vResourceBox.getChildren().add(hbox);
		loadResourceImages();
	}
	

	/**
	 * Called when logout link is clicked.
	 * @param event Passed when mouse event occurs
	 */
	@FXML
	public void logoutAction(MouseEvent event) {
		changeScene(event, "/fxml/loginScene.fxml");
	}
	
	
	
	
	/**
	 * Loads user information from Screen Manager class, so that
	 * it can be displayed within the UI.
	 */
	private void loadUserInformation() {
		if (ScreenManager.getCurrentUser() instanceof User) {

			//change text in labels to appropriate user information.
			userLabel.setText(currentUser.getUsername());
			fullnameLabel.setText("Full Name: " + currentUser.getFirstName() + " "
			+ currentUser.getLastName());
			addressLabel.setText("Address: " + currentUser.getAddress());
			postcodeLabel.setText("Post Code: " + currentUser.getPostcode());
			phoneLabel.setText("Phone Number: " + currentUser.getPhoneNumber());
			
			Double userBalance = ((User) currentUser).getAccountBalance();
			accountBalance.setText("£" + Double.toString(userBalance));
			
			userAvatarView.setImage(new Image(currentUser.getAvatar()));
		}else {
			//get all information in about user from ScreenManager class.
			Librarian staff = (Librarian) currentUser;
			String fullname = staff.getFirstName() + " " + staff.getLastName();
			
			userLabel1.setText(staff.getUsername());
			fullnameLabel1.setText(fullnameLabel1.getText() + " " + fullname);
			addressLabel1.setText(addressLabel1.getText() + " " + staff.getAddress());
			phoneLabel1.setText(phoneLabel1.getText() + " " + staff.getPhoneNumber());
			postcodeLabel1.setText(postcodeLabel1.getText() + " " + staff.getPostcode());
			dateLabel1.setText(dateLabel1.getText() + " " + staff.getEmploymentDate());
			staffIDLabel1.setText(staffIDLabel1.getText() + " " + staff.getStaffID());
			
			staffAvatarView.setImage(new Image(currentUser.getAvatar()));
		}
	}
	
	/**
	 * Event handler that handles when a resource is clicked.
	 */
	final EventHandler<MouseEvent> enterHandler = event -> {
		StackPane currentPane = (StackPane) event.getSource();
		currentPane.getChildren().get(4).setVisible(true);
		currentPane.getChildren().get(3).setVisible(true);
	};
	
	/**
	 * Event handler that handles when a resource is clicked.
	 */
	final EventHandler<MouseEvent> exitHandler = event -> {
		StackPane currentPane = (StackPane) event.getSource();
		currentPane.getChildren().get(4).setVisible(false);
		currentPane.getChildren().get(3).setVisible(false);
	};
	
	final EventHandler<MouseEvent> clickHandler = event -> {
		
		//find the resource that was clicked.
		for(Resource resource : resources) {
			if(resource.getUniqueID() == Integer.parseInt(((StackPane) event.getSource()).getId())) {
				ScreenManager.setCurrentResource(resource);
			}
		}
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/copyScene.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Resource Information");
            stage.setScene(new Scene(root1));  
            stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	};
	
	/**
	 * Makes the image and resource text for the resource
	 * @param copyResource the copy of the resource 
	 * @param width width of image
	 * @param height height of image
	 * @return the image pane
	 */
	private StackPane createImage(Resource copyResource, int width, int height) {
		
		//create stackpane to add image layers to.
		StackPane imagePane = new StackPane();
		
		//create white backround just in case image is small.
		Rectangle background = new Rectangle();
		background.setWidth(width);
		background.setHeight(height);
		background.setFill(Color.WHITE);
		
		//create text containing resource information
		//this text only shows when mouse enters image.
		Text resourceText = new Text();
		resourceText.setFont(Font.font("Arial", 20));
		resourceText.setStyle("-fx-font-weight: bold");
		resourceText.setFill(Color.WHITE);
		resourceText.setText("ID: " + copyResource.getUniqueID() + "\n" +
		copyResource.getTitle() + "\n" + copyResource.getYear());
		resourceText.setVisible(false);
		resourceText.setTextAlignment(TextAlignment.CENTER);
		resourceText.setWrappingWidth(width);
		
		//create imageview containg resource image.
		ImageView image = new ImageView();
		image.setFitWidth(width);
		image.setFitHeight(height);
		image.setImage(copyResource.getThumbnail());
		
		//if image is of a laptop, keep aspect ratio.
		if(copyResource instanceof Laptop) {
			image.setPreserveRatio(true);
		}
		
		//make image as smooth as possible.
		image.setCache(true);
		image.setCacheHint(CacheHint.SCALE);
		image.setSmooth(true);
		
		//add black colour overlay
		//only shows when mouse is in image.
		Rectangle rect = new Rectangle();
		rect.setWidth(width);
		rect.setHeight(height);
		rect.setFill(Color.BLACK);
		rect.setOpacity(0.7);
		rect.setVisible(false);
		
		//add all elements to stack pane.
		imagePane.getChildren().add(background);
		imagePane.getChildren().add(image);
		imagePane.getChildren().add(new ImageView());
		imagePane.getChildren().add(rect);
		imagePane.getChildren().add(resourceText);
		
		//set id of imagePane to it's index so it can be accessed
		//within the event handler.
		imagePane.setId(String.valueOf(copyResource.getUniqueID()));
		
		return imagePane;
	}
	
	/**
	 * Search resources and checks to see if the checkboxes are selected which filter the search
	 * @param i loop to get the resources
	 * @return the search results or false
	 */
	private boolean search(int i ) {
		//get the resource
		Resource r = resources.get(i);
		String searchText = searchTextBox.getText();
		
		if(bookCheck.isSelected() && r instanceof Book) {
			return r.contains(searchText);
		}
		if(dvdCheck.isSelected() && r instanceof DVD) {
			return r.contains(searchText);
		}
		if(laptopCheck.isSelected() && r instanceof Laptop) {
			return r.contains(searchText);
		}
		
		return false;

	}
	
	
	@FXML
	private void  reloadVisuals(Event e) {

		vResourceBox.getChildren().clear();
		vResourceBox.getChildren().add(new HBox());
		resourceImages.getChildren().clear();
		
		Resource.loadDatabaseResources();
		loadResourceImages();
		loadUserInformation();
		loadCopies();
		loadRequested();
		loadBorrowHistory();
		
	}
	
	
	/**
	 * Loads resource images from Resource class, so that they can
	 * be displayed within the UI.
	 */
	private void loadResourceImages() {
		if (ScreenManager.getCurrentUser() instanceof Librarian) {
			staffProfileTab.setDisable(false);
			userProfileTab.setDisable(true);
		} else {
			staffProfileTab.setDisable(true);
			userProfileTab.setDisable(false);
		}
		//get resources
		
		resources = Resource.getResources();
				
		
		System.out.println(resources.size());
		ScreenManager.setResources(resources);
		
		//for each resource in resources array
		for(int i = 0; i < resources.size(); i++) {
			if(search(i)) {
			StackPane imagePane = createImage(resources.get(i), RES_IMG_WIDTH, RES_IMG_HEIGHT);
			
			//get last image in last resource HBox.
			HBox latestHBox = (HBox) vResourceBox.getChildren().get(vResourceBox.getChildren().size() - 1);
			latestHBox.setSpacing(5);
			
			//if there is at least one image in last resource HBox
			if(!latestHBox.getChildren().isEmpty()) {
				//if the number of resources in resource HBox is more than
				//the width of the resource VBox / the width of a resource image
				if(latestHBox.getChildren().size() > (vResourceBox.getPrefWidth()
				- RES_IMG_WIDTH) / RES_IMG_WIDTH) {
					//create new HBox below last HBox
					HBox hResourceBox = new HBox();
					hResourceBox.setSpacing(5);
					//add image to new HBox
					hResourceBox.getChildren().add(imagePane);
					vResourceBox.getChildren().add(hResourceBox);
				} else {
					latestHBox.getChildren().add(imagePane); //add new image to last HBox
				}
			} else {
				latestHBox.getChildren().add(imagePane); //add new image to last HBox
			}
			
			imagePane.setOnMouseEntered(enterHandler);
			imagePane.setOnMouseExited(exitHandler);
			imagePane.setOnMouseClicked(clickHandler);
			
		}
	}
		
	}
	
	private void loadCopyImages(ArrayList<Resource> resources, String bannerName) {

		for(Resource resource : resources) {
			
			StackPane imagePane = createImage(resource, COPY_IMG_WIDTH, COPY_IMG_HEIGHT);
			
			((ImageView) imagePane.getChildren().get(2)).setFitWidth(COPY_IMG_WIDTH);
			((ImageView) imagePane.getChildren().get(2)).setImage(new Image("/graphics/" + bannerName));
			((ImageView) imagePane.getChildren().get(2)).setPreserveRatio(true);
			
			resourceImages.getChildren().add(imagePane);
			
			imagePane.setOnMouseEntered(enterHandler);
			imagePane.setOnMouseExited(exitHandler);
			imagePane.setOnMouseClicked(clickHandler);
				
			}
	}
	
	/**
	 * Method that loads copies that the user is currently borrowing
	 */
	private void loadCopies() {
		
		if(currentUser instanceof User) {
			((User) currentUser).loadUserCopies();
			ArrayList<Copy> userCopies = ((User) currentUser).getBorrowedCopies();
			ArrayList<Resource> copyResources = new ArrayList<Resource>();
			
			for(Copy copy : userCopies) {
				copyResources.add(copy.getResource());
			}
			
			loadCopyImages(copyResources, "borrowed.png");
		}
		

	}
	
	/**
	 * Loads the resources that have been requested so the librarian can confirm then
	 */
	@FXML
	private void loadRequested() {
		if(currentUser instanceof User) {
			ArrayList<Resource> requestedResources = ((User) currentUser).getRequestedResources();
			loadCopyImages(requestedResources, "requested.png");
		}
		
	}
	
	@FXML
	private void loadBorrowHistory() {
		if(currentUser instanceof User) {
			ArrayList<Resource> borrowHistory = ((User) currentUser).loadUserHistory();
			loadCopyImages(borrowHistory, "returned.png");
		}
	}

	/**
	 * intialize method that starts when the scene is intialized
	 */
	@FXML
	 public void initialize() {
		
		currentUser = ScreenManager.getCurrentUser();
		resources = ScreenManager.getResources();

		loadUserTableColumns();
		displayAll();
		
		loadResourceImages();

		scrollPane.setHvalue(0.5);
	
	 }
	
	//
	//Staff Profile -----------------------------------------------------------
	//
	
	//
	//Staff: Copies Explorer
	//
	
	@FXML
	private void displayAll() {
		staffHistoryFind.setDisable(false);
		staffApproveCopy.setDisable(true);
		staffReturnCopy.setDisable(false);
		
		loadExplorerTableColumns("all");
		ObservableList<ExplorerRow> copiesList = FXCollections.observableArrayList();

		try {
			Connection conn = DBHelper.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM copies, resource WHERE copies.rID = resource.rID");
			
			while(rs.next()) {
				copiesList.add(new ExplorerRow(
						rs.getString(9),
						rs.getString(3),
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7)
						));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		staffCopiesExplorerTable.setItems(copiesList);
		staffCopiesExplorerTable.autosize();		
	}
	
	/**
	 * Displays what is overdue 
	 */
	@FXML
	private void displayOverdue() {
		staffHistoryFind.setDisable(false);
		staffApproveCopy.setDisable(true);
		staffReturnCopy.setDisable(false);
		
		loadExplorerTableColumns("overdue");
		ObservableList<ExplorerRow> copiesList = FXCollections.observableArrayList();

		try {
			Connection conn = DBHelper.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM fines, resource, copies WHERE fines.rID = resource.rID AND copies.rID = resource.rID AND copies.keeper = fines.username");
			
			while(rs.next()) {
				copiesList.add(new ExplorerRow(
						rs.getString(9),
						rs.getString(2),
						rs.getInt(12),
						rs.getInt(3),
						rs.getString(16),
						rs.getString(18)
						));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		staffCopiesExplorerTable.setItems(copiesList);
		staffCopiesExplorerTable.autosize();
	}
	
	/**
	 * Displays what is requested
	 */
	@FXML
	private void displayRequested() {
		staffHistoryFind.setDisable(true);
		staffApproveCopy.setDisable(false);
		staffReturnCopy.setDisable(true);
		
		loadExplorerTableColumns("requested");
		ObservableList<ExplorerRow> copiesList = FXCollections.observableArrayList();

		try {
			Connection conn = DBHelper.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM requestsToApprove, users where requestsToApprove.userName = users.username");
			
			while(rs.next()) {
				copiesList.add(new ExplorerRow(
						rs.getString(2),
						rs.getInt(1)
						));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		staffCopiesExplorerTable.setItems(copiesList);
		staffCopiesExplorerTable.autosize();
	}
	
	/**
	 * prints display history
	 */
	@FXML
	private void displayHistory() {
		String copyID = staffCopyIDField.getText();
		if (copyID.equals("")) {
			System.out.println("No copyID entered!");
		
		} else {
			if (checkCopyID(copyID) == true) {
				loadExplorerTableColumns("history");
				ObservableList<ExplorerRow> historyList = FXCollections.observableArrayList();

				try {
					Connection conn = DBHelper.getConnection();
					PreparedStatement sqlStatement = conn.prepareStatement("SELECT * FROM borrowRecords WHERE copyID = ?");
					sqlStatement.setInt(1, Integer.parseInt(copyID));
					ResultSet rs = sqlStatement.executeQuery();
					
					while(rs.next()) {

						historyList.add(new ExplorerRow(
								rs.getInt(2),
								rs.getString(3),
								rs.getString(4)));
					}
						
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				staffCopiesExplorerTable.setItems(historyList);
				staffCopiesExplorerTable.autosize();
				
			} else {
				System.out.println("Copy not found!");
				staffCopyIDField.setText("");
			}
		}
	}
	
	/**
	 * prints out approval of the copy
	 */
	@FXML
	private void approveCopy() {
		ExplorerRow row  = (ExplorerRow) staffCopiesExplorerTable.getSelectionModel().getSelectedItem();
		int resourceID = row.getResourceID();
		String username = row.getKeeper();
		
		User user = (User)Person.loadPerson(username);
		Resource.getResource(resourceID).loanToUser(user);
		
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement sqlStatement = conn.prepareStatement("DELETE FROM requestsToApprove WHERE rID = ? AND userName = ?");
			sqlStatement.setInt(1, resourceID);
			sqlStatement.setString(2, username);
			sqlStatement.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Approved copy!");
		displayRequested();
	}
	
	/**
	 * 
	 */
	@FXML
	private void returnCopy() {
		ExplorerRow row  = (ExplorerRow) staffCopiesExplorerTable.getSelectionModel().getSelectedItem();
		int copyID = row.getCopyID();
		
		for (Resource res : Resource.getResources()) {
			for (Copy copy : res.getCopies()) {
				if (copy.getCopyID() == copyID) {
					res.processReturn(copy);
				}
			}
		}
		
		
		System.out.println("Returned copy!");
		displayAll();
	}
	
	private boolean checkCopyID(String copyID) {
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement sqlStatement = conn.prepareStatement("SELECT COUNT(*) FROM copies WHERE copyID = ?");
			sqlStatement.setString(1, copyID);
			ResultSet rs = sqlStatement.executeQuery();
            if (rs.getInt(1) == 1) {
            	return true;
            }
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Opens the profile editor when the button is clicked
	 * @param event button being clicked
	 */
	@FXML
	private void explorerTableClicked(MouseEvent event) {
		ExplorerRow row  = (ExplorerRow) staffCopiesExplorerTable.getSelectionModel().getSelectedItem();
		staffCopyIDField.setText(Integer.toString(row.getCopyID()));
	}
	
	//
	//Staff: Profile
	//
	
	
	@FXML
	void pickAvatar(Event e) {
		  FileChooser chooser = new FileChooser();
		    FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
			chooser.getExtensionFilters().add(extentionFilter);
		    chooser.setTitle("Open File");    
		    File file = chooser.showOpenDialog(new Stage());
		    System.out.println(file.getAbsolutePath());
		 
		 
		    ScreenManager.getCurrentUser().setAvatar(new File(file.getAbsolutePath()).toURI().toString());
		    userAvatarView.setImage(new Image(currentUser.getAvatar()));
		    staffAvatarView.setImage(new Image(currentUser.getAvatar()));
	
	}
	
	@FXML
	private void openProfileEditor(MouseEvent event) {
		System.out.println("Launch staff editing profile.");
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/staffEdit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Resource Information");
            stage.setScene(new Scene(root1));  
            stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens the avatar drawing scene when the button is clicked
	 * @param event button being clicked
	 */
	@FXML
	private void openAvatarEditor(MouseEvent event) {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/drawAvatar.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Avatar!");
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//AvatarDrawingController avatarDrawingController = new AvatarDrawingController();
		//avatarDrawingController.setPrevScene("profile");
		System.out.println("Launch avatar editor.");
		
		//changeScene(event,"/fxml/drawAvatar.fxml");
	}
	

	//Integer.toString(cd.getValue().getLoanDuration())
	
	/**
	 * Loads the user table so the staff can manage the users.
	 */
	private void loadExplorerTableColumns(String tableToLoad) {
		
		TableColumn<ExplorerRow, String> titleCol = new TableColumn<ExplorerRow, String>("Title");
		titleCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(cd.getValue().getResourceTitle()));
		
		TableColumn<ExplorerRow, String> keeperCol = new TableColumn<ExplorerRow, String>("Keeper");
		keeperCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(cd.getValue().getKeeper()));
		
		TableColumn<ExplorerRow, String> copyIDCol = new TableColumn<ExplorerRow, String>("Copy ID");
		copyIDCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(Integer.toString(cd.getValue().getCopyID())));
		
		TableColumn<ExplorerRow, String> resourceIDCol = new TableColumn<ExplorerRow, String>("Resource ID");
		resourceIDCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(Integer.toString(cd.getValue().getResourceID())));
		
		TableColumn<ExplorerRow, String> loanDurationCol = new TableColumn<ExplorerRow, String>("Loan Duration");
		loanDurationCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(Integer.toString(cd.getValue().getLoanDuration())));
		
		TableColumn<ExplorerRow, String> orderNumberCol = new TableColumn<ExplorerRow, String>("Order Number");
		orderNumberCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(Integer.toString(cd.getValue().getOrderNumber())));
		
		TableColumn<ExplorerRow, String> borrowDateCol = new TableColumn<ExplorerRow, String>("Borrow Date");
		borrowDateCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(cd.getValue().getBorrowDate()));
		
		TableColumn<ExplorerRow, String> lastRenewalCol = new TableColumn<ExplorerRow, String>("Last Renewal");
		lastRenewalCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(cd.getValue().getLastRenewal()));
		
		TableColumn<ExplorerRow, String> dueDateCol = new TableColumn<ExplorerRow, String>("Due Date");
		dueDateCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(cd.getValue().getDueDate()));
		
		TableColumn<ExplorerRow, String> historyCol = new TableColumn<ExplorerRow, String>("History");
		historyCol.setCellValueFactory(cd -> 
		new SimpleStringProperty(cd.getValue().getHistory()));
		
		switch (tableToLoad) {				
			case "all":
				staffCopiesExplorerTable.getColumns().clear();
				staffCopiesExplorerTable.getColumns().addAll(
						copyIDCol,resourceIDCol,titleCol,keeperCol,loanDurationCol,
						borrowDateCol,lastRenewalCol,dueDateCol);
				break;
			case "overdue":
				staffCopiesExplorerTable.getColumns().clear();
				staffCopiesExplorerTable.getColumns().addAll(
						titleCol,keeperCol,copyIDCol,resourceIDCol,borrowDateCol,
						dueDateCol);
				break;
				
			case "requested":
				staffCopiesExplorerTable.getColumns().clear();
				staffCopiesExplorerTable.getColumns().addAll(
						keeperCol,resourceIDCol);
				break;
				
			case "history":
				staffCopiesExplorerTable.getColumns().clear();
				staffCopiesExplorerTable.getColumns().addAll(
						copyIDCol,keeperCol,historyCol);
		}
		
	}
	
	//
	//Staff: Manage Users
	//
	
	private void loadUserTableColumns() {
		TableColumn<Person, String> usernameCol = new TableColumn<Person, String>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		
		TableColumn<Person, String> firstnameCol = new TableColumn<Person, String>("Firstname");
		firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		
		TableColumn<Person, String> lastnameCol = new TableColumn<Person, String>("Lastname");
		lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		
		TableColumn<Person, Integer> telephoneCol = new TableColumn<Person, Integer>("Telephone");
		telephoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		TableColumn<Person, String> addressCol = new TableColumn<Person, String>("Address");
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

		TableColumn<Person, String> postcodeCol = new TableColumn<Person, String>("Postcode");
		postcodeCol.setCellValueFactory(new PropertyValueFactory<>("postcode"));
		
		TableColumn<Person, String> avatarCol = new TableColumn<Person, String>("Avatar Path");
		avatarCol.setCellValueFactory(new PropertyValueFactory<>("avatarPath"));
		
		TableColumn<Person, String> accountBalanceCol = new TableColumn<Person, String>("accountBalance");
		accountBalanceCol.setCellValueFactory(new PropertyValueFactory<>("accountBalance"));
		
		staffUsersTable.getColumns().clear();
		staffUsersTable.getColumns().addAll(usernameCol,firstnameCol,
				lastnameCol,addressCol,postcodeCol,accountBalanceCol);
	}
	/**
	 * loads all the users from the table which aren't staff when the button is clicked
	 * @param event the button being clicked
	 */
	@FXML
	private void loadUsersTable(MouseEvent event) {
		ObservableList<Person> usersList = FXCollections.observableArrayList();
		try {
			Connection conn = DBHelper.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT username FROM users WHERE username NOT IN (SELECT username FROM staff);");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	usersList.add(Person.loadPerson(rs.getString(1)));
            }
            
            staffUsersTable.setItems(usersList);
            staffUsersTable.autosize();
            
            
		} catch (SQLException e) { 
			System.out.println("Error: Failed to load users from Database.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method that shows the table of the selected user when button is clicked
	 * @param event when the button is clicked
	 */
	@FXML
	private void userTableClicked(MouseEvent event) {
		Person selectedUser = staffUsersTable.getSelectionModel().getSelectedItem();
		selectedUserLabel.setText(selectedUser.getUsername());
		//System.out.println("Cell clicked?");
	}
	
	/**
	 * Deletes a user from the database when the button is clicked
	 * @param event the button being clicked
	 */
	@FXML
	private void userDeleteButton(MouseEvent event) {
		if (selectedUserLabel.getText().equals("-")) {
			System.out.println("No user selected!");
		} else {
			System.out.println("Delete User: " + selectedUserLabel.getText());
			if(selectedUserLabel.getText().equals(ScreenManager.getCurrentUser().getUsername())) {
				alertDone("You can't delete yourself!");
			}else {
				if(Person.removePerson(selectedUserLabel.getText())) {
					
				}else {
					alertDone("They can't be deleted at the moment");
				}
			}
			//Delete user
			selectedUserLabel.setText("-");
			staffUsersTable.getItems().clear();
			loadUsersTable(null);
		}
		
	}
	
	//
	// End of Staff Tab
	//
	
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
	
	/**
	 * Method that allows librarian to add funds to a user when the button is clicked
	 * @param event the button being pressed
	 */
	@FXML
	private void userAddFundsButton(MouseEvent event) {
		if (selectedUserLabel.getText().equals("-")) {
			System.out.println("No user selected!");
		} else {
			try {
				double amount = Float.parseFloat(staffAmountField.getText());
				if (amount > 0.01) {
					System.out.println("Add: " + amount + " to " + selectedUserLabel.getText());
					User.addBalance(selectedUserLabel.getText(), amount);
				}
			} catch (RuntimeException e) {
				System.out.println("User entered non-float.");
			} finally {
				staffAmountField.setText("");
				staffUsersTable.getItems().clear();
				loadUsersTable(null);
			}
		}
		
	}

}