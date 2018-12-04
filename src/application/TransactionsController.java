package application;

import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
	private TextField dateSearch;
	
	
	 ObservableList<Fine> fdata = FXCollections.observableArrayList();
		FilteredList<Fine> ffilteredList = new FilteredList<>(fdata);
		
		 ObservableList<Payment> tdata = FXCollections.observableArrayList();
			FilteredList<Payment> tfilteredList = new FilteredList<>(tdata);
	
	@FXML
	private SplitPane transactionsSplit;
	
	@FXML
	private SplitPane finesSplit;

	
	@FXML  
    void transactionSearch(KeyEvent event) {
		//System.out.println(dateSearch.getText());
		ffilteredList.setPredicate(s -> s.contains(dateSearch.getText()));
		tfilteredList.setPredicate(s -> s.contains(dateSearch.getText()));
        }
    
	@SuppressWarnings("unchecked")
	private void setupFines() {
		fines = Fine.createFines(user.getUsername());
		
		

         fdata = FXCollections.observableArrayList();
	for (Fine f : fines) {
		fdata.add(f);
	}
	
	ffilteredList = new FilteredList<>(fdata);
		
		//create the table
		 TableColumn<Fine, String> fineCol = new TableColumn<Fine, String>("Fine");
		 fineCol.setCellValueFactory(new PropertyValueFactory<>("fineId"));
		 
	        TableColumn<Fine, String> amountCol = new TableColumn<Fine, String>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        
	        TableColumn<Fine, String> forCol = new TableColumn<Fine, String>("For");
	        forCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getResourceName()));
	        // forCol.setCellValueFactory(new PropertyValueFactory<>("resource"));
	        
	        TableColumn<Fine, String> overCol = new TableColumn<Fine, String>("Days over");
	        overCol.setCellValueFactory(new PropertyValueFactory<>("daysOver"));
	        
	        TableColumn<Fine, String> whenCol = new TableColumn<Fine, String>("When");
	        whenCol.setCellValueFactory(new PropertyValueFactory<>("stamp"));
	        
	        TableColumn<Fine, String> paidCol = new TableColumn<Fine, String>("Paid");
	        paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
	        
	        tableFine.setItems(ffilteredList);
	        
	        
	        
	        tableFine.getColumns().addAll(fineCol,amountCol,overCol,forCol,whenCol,paidCol);
	        tableFine.autosize();
		
		finesSplit.getItems().add(tableFine);
		
	}
	
	
	@SuppressWarnings("unchecked")
	private void setupTransactions() {
		
		transactions = Transactions.createTransactions(user.getUsername());
		
		
		

        
	for (Payment p : transactions.getPayments()) {
		tdata.add(p);
		
		
	}
		
		//create the table
		 TableColumn<Payment, String> transCol = new TableColumn<Payment, String>("Transaction");
		 transCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		 
	        TableColumn<Payment, String> amountCol = new TableColumn<Payment, String>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        
	        TableColumn<Payment, String> whenCol = new TableColumn<Payment, String>("When");
	        whenCol.setCellValueFactory(new PropertyValueFactory<>("stamp"));
	        
	        tableTransaction.setItems(tfilteredList);
	        
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
