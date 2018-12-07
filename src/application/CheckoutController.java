package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.Fine;
import model.Payment;
import model.Request;
import model.Transactions;

public class CheckoutController {

	
	@FXML
	private VBox checkoutVbox;
	
	private TableView<Request> tableRequest = new TableView<>();
	
	@FXML
	private TextField checkoutSearch;


	ObservableList<Request> requestData = FXCollections.observableArrayList();
	FilteredList<Request> requestList = new FilteredList<>(requestData);
	
	@FXML
	public void initialize() {
		
		loadRequests();
		
	}
	
	
	@FXML
	public void checkoutDoSearch(KeyEvent event) {
		requestList.setPredicate(s -> s.contains(checkoutSearch.getText()));
	}
	
	
	@SuppressWarnings("unchecked")
	private void loadRequests() {
		ArrayList<Request> requests = Request.loadRequests();
		
		for (Request request : requests) {
			requestData.add(request);
		}
		
		

		//create the table
		TableColumn<Request, String> orderCol = 
				new TableColumn<Request, String>("Order");
		orderCol.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

		TableColumn<Request, String> userCol = 
				new TableColumn<Request, String>("User");
		userCol.setCellValueFactory(new PropertyValueFactory<>("username"));

		TableColumn<Request, String> resourceCol = 
				new TableColumn<Request, String>("Resource");
		resourceCol.setCellValueFactory(new PropertyValueFactory<>("resourceName"));

		tableRequest.setItems(requestList);

		tableRequest.getColumns().addAll(orderCol,userCol,resourceCol);
		tableRequest.autosize();
		checkoutVbox.getChildren().add(tableRequest);


		
	}
	
	
	
	
	
}
