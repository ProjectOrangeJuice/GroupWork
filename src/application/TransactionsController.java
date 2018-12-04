package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.DBHelper;
import model.Payment;
import model.Person;
import model.Transactions;


public class TransactionsController {
	
	private Transactions transactions;
	private TableView table = new TableView();
	
	@FXML
	private SplitPane transactionsSplit;
	

	
	@FXML  
    void transactionSearch(KeyEvent event) {
        
        }
    


	
	@FXML
	 public void initialize() {
		Person user = ScreenManager.currentUser;
		transactions = Transactions.createTransactions(user.getUsername());
		
		
		

        ObservableList<Payment> data = FXCollections.observableArrayList();
	for (Payment p : transactions.getPayments()) {
		data.add(p);
		
		
	}
		
		//create the table
		 TableColumn transCol = new TableColumn("Transaction");
		 transCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		 
	        TableColumn amountCol = new TableColumn("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        
	        TableColumn whenCol = new TableColumn("When");
	        whenCol.setCellValueFactory(new PropertyValueFactory<>("stamp"));
	        
	        table.setItems(data);
	        
	        table.getColumns().addAll(transCol,amountCol,whenCol);
	        table.autosize();
		
		transactionsSplit.getItems().add(table);
		
	 }    
}
