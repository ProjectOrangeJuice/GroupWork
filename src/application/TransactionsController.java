package application;

import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import model.DBHelper;
import model.Fine;
import model.Librarian;
import model.Payment;
import model.Person;
import model.Resource;
import model.Transactions;


public class TransactionsController {
	
	private Transactions transactions;
	private ArrayList<Fine> fines;
	private TableView<Payment> tableTransaction = new TableView<>();
	private TableView<Fine> tableFine = new TableView<>();
	Person user = ScreenManager.currentUser;
	private boolean isStaff = false;
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
		if(isStaff) {
			ffilteredList.setPredicate(s -> s.containsUser(dateSearch.getText()));
			
		}else {
			ffilteredList.setPredicate(s -> s.contains(dateSearch.getText()));
			tfilteredList.setPredicate(s -> s.contains(dateSearch.getText()));
		}

        }
    
	@SuppressWarnings("unchecked")
	private void setupFines() {
		
		if(isStaff) {
			fines = Fine.getFines();
		}else {
			fines = Fine.getFines(user.getUsername());
		}
		
		
		

         fdata = FXCollections.observableArrayList();
	for (Fine f : fines) {
		fdata.add(f);
	}
	
	ffilteredList = new FilteredList<>(fdata);
		
		//create the table
		 TableColumn<Fine, String> fineCol = new TableColumn<Fine, String>("Fine");
		 fineCol.setCellValueFactory(new PropertyValueFactory<>("fineId"));
		 
		 TableColumn<Fine, String> personCol = new TableColumn<Fine, String>("Person");
		 personCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		 
	        TableColumn<Fine, String> amountCol = new TableColumn<Fine, String>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	        
	        TableColumn<Fine, String> forCol = new TableColumn<Fine, String>("For");
	        forCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getResourceName()));
	        
	        
	        TableColumn<Fine, String> overCol = new TableColumn<Fine, String>("Days over");
	        overCol.setCellValueFactory(new PropertyValueFactory<>("daysOver"));
	        
	        TableColumn<Fine, String> whenCol = new TableColumn<Fine, String>("When");
	        whenCol.setCellValueFactory(new PropertyValueFactory<>("stamp"));
	        
	        TableColumn<Fine, String> paidCol = new TableColumn<Fine, String>("Paid");
	        paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
	        
	        tableFine.setItems(ffilteredList);
	        
	        if(isStaff) {
	        tableFine.setRowFactory( tv -> {
	            TableRow<Fine> row = new TableRow<>();
	            row.setOnMouseClicked(event -> {
	                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	                    Fine rowData = row.getItem();
	                    confirmPay(rowData);
	                }
	            });
	            return row ;
	        });
	        }
	        
	        tableFine.getColumns().addAll(fineCol,personCol,amountCol,overCol,forCol,whenCol,paidCol);
	        tableFine.autosize();
		
		finesSplit.getItems().add(tableFine);
		
	}
	
	
	private void confirmPay(Fine f) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Pay fine for "+f.getUsername()+"?");
		alert.setContentText("For the amount of "+f.getAmount()+" due to "+f.getDaysOver()+" days late");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    if(Payment.makePayment(f.getUsername(), f.getAmount(),f.getFineId()) != null){
		    	alert.close();
		    	alertDone("Fine has been paid");
		    	finesSplit.getItems().remove(tableFine);
				tableFine = new TableView<>();
		    	setupFines();
		    }else {
		    	alertDone("Fine was not able to be paid");
		    }
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
	
	private void alertDone(String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);

		alert.showAndWait();
	}
	
	
	@SuppressWarnings("unchecked")
	private void setupTransactions() {
		
		transactions = Transactions.getTransactions(user.getUsername());
		
		
		

        
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
		//isStaff = user instanceof Librarian;
		//System.out.println("Is staff? "+isStaff);
		//if(!isStaff) {
		//setupTransactions();
		//}else {
			//Resource.loadDatabaseResources(); // remove me later
		//}
		//setupFines();
	 }    
}
