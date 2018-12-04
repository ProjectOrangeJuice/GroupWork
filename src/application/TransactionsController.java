package application;

import java.util.ArrayList;

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
import model.Fine;
import model.Payment;
import model.Person;
import model.Transactions;


public class TransactionsController {
	
	private Transactions transactions;
	private ArrayList<Fine> fines;
	private TableView<Payment> tableTransaction = new TableView<>();
	private TableView<Fine> tableFine = new TableView<>();
	Person user = ScreenManager.currentUser;
	
	@FXML
	private SplitPane transactionsSplit;
	
	@FXML
	private SplitPane finesSplit;

	
	@FXML  
    void transactionSearch(KeyEvent event) {
        
        }
    
	@SuppressWarnings("unchecked")
	private void setupFines() {
		fines = Fine.createFines(user.getUsername());
		
		

        ObservableList<Fine> data = FXCollections.observableArrayList();
	for (Fine f : fines) {
		data.add(f);
	}
		
		//create the table
		 TableColumn<Fine, String> fineCol = new TableColumn<Fine, String>("Fine");
		 fineCol.setCellValueFactory(new PropertyValueFactory<>("fineId"));
		 
	        TableColumn<Fine, String> amountCol = new TableColumn<Fine, String>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        
	        TableColumn<Fine, String> forCol = new TableColumn<Fine, String>("For");
	        forCol.setCellValueFactory(new PropertyValueFactory<>("copy"));
	        
	        TableColumn<Fine, String> overCol = new TableColumn<Fine, String>("Days over");
	        overCol.setCellValueFactory(new PropertyValueFactory<>("datsOver"));
	        
	        TableColumn<Fine, String> paidCol = new TableColumn<Fine, String>("Paid");
	        paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
	        
	        tableFine.setItems(data);
	        
	        tableFine.getColumns().addAll(fineCol,amountCol,forCol,paidCol);
	        tableFine.autosize();
		
		finesSplit.getItems().add(tableFine);
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void setupTransactions() {
		
		transactions = Transactions.createTransactions(user.getUsername());
		
		
		

        ObservableList<Payment> data = FXCollections.observableArrayList();
	for (Payment p : transactions.getPayments()) {
		data.add(p);
		
		
	}
		
		//create the table
		 TableColumn<Payment, String> transCol = new TableColumn<Payment, String>("Transaction");
		 transCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		 
	        TableColumn<Payment, String> amountCol = new TableColumn<Payment, String>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        
	        TableColumn<Payment, String> whenCol = new TableColumn<Payment, String>("When");
	        whenCol.setCellValueFactory(new PropertyValueFactory<>("stamp"));
	        
	        tableTransaction.setItems(data);
	        
	        tableTransaction.getColumns().addAll(transCol,amountCol,whenCol);
	        tableTransaction.autosize();
		
		transactionsSplit.getItems().add(tableTransaction);
		
	}
	
	@FXML
	 public void initialize() {
		setupTransactions();
		setupFines();
	 }    
}
