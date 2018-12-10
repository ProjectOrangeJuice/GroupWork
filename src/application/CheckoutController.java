package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.Fine;
import model.Payment;
import model.Request;
import model.Transactions;
import model.User;
/**
 * CheckoutController is a class that controls the checkout system GUI
 * and the functions behind it.
 *@author Oliver Harriss
 *
 */
public class CheckoutController {

	@FXML
	private VBox checkoutVbox; //vbox that holds the checkoutsearch textfield 

	private TableView<Request> tableRequest = new TableView<>(); //request to view the table

	@FXML
	private TextField checkoutSearch; //the search bar that allows the person to search.

	ObservableList<Request> requestData = FXCollections.observableArrayList();
	FilteredList<Request> requestList = new FilteredList<>(requestData);

	/**
	 * Loads requests when the program starts up.
	 */
	@FXML
	public void initialize() {

		loadRequests();

	}

	/**
	 * Performs the search that the user wants to do.
	 * 
	 * @param event the search being confirmed.
	 */
	@FXML
	public void checkoutDoSearch(KeyEvent event) {
		requestList.setPredicate(s -> s.contains(checkoutSearch.getText()));
	}

	/**
	 * A method that makes an alert to make the user confirm the request of a resource.
	 * @param request the request to get a copy
	 */
	private void confirmRequest(Request request) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Taking " + request.getResourceName());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			takeOut(request);
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	/**
	 * A method that makes the takeout request alert which pops up after a request is accepted.
	 * @param request the request to take a copy.
	 */
	private void takeOut(Request request) {

		// make the user

		User user = (User) User.loadPerson(request.getUsername());
		if (request.getResource().loanToUser(user)) {
			alertDone("Copy given");
		} else {
			alertDone("In queue");
		}

		checkoutVbox.getChildren().remove(tableRequest);
		requestData = FXCollections.observableArrayList();
		requestList.removeAll();
		tableRequest = new TableView<>();
		loadRequests();

	}

	/**
	 * Generate a popup.
	 * 
	 * @param text
	 *            The text to be displayed.
	 */
	private void alertDone(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}

	/**
	 * A method that will load the array of requests and get the username and the resource they requested,
	 */
	@SuppressWarnings("unchecked")
	private void loadRequests() {
		ArrayList<Request> requests = Request.loadRequests();

		//adds a request to the array list
		for (Request request : requests) {
			requestData.add(request);
		}

		//creates the request table
		TableColumn<Request, String> userCol = new TableColumn<Request,
				String>("User");
		userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

		TableColumn<Request, String> resourceCol = new TableColumn<Request,
				String>("Resource");
		resourceCol.setCellValueFactory(new PropertyValueFactory<>("resourceName"));

		tableRequest.setItems(requestList);

		tableRequest.setRowFactory(tv -> {
			TableRow<Request> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Request rowData = row.getItem();
					confirmRequest(rowData);
				}
			});
			return row;
		});

		//adds all the usernames and resources to the table and resizes it.
		tableRequest.getColumns().addAll(userCol, resourceCol);
		tableRequest.autosize();
		checkoutVbox.getChildren().add(tableRequest);

	}

}
