package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Copy;
import model.Fine;
import model.Payment;
import model.Transactions;

public class EditCopiesController {

	private ArrayList<Copy> copies;
	private TableView<Copy> tableCopies= new TableView<>();
	ObservableList<Copy> copyData = FXCollections.observableArrayList();
	
	
	@FXML
	private TableView copiesTable;
	
	
	private void setupCopy() {
		copies = ScreenManager.currentResource.getCopies();
		

		copyData = FXCollections.observableArrayList();
		for (Copy copy : copies) {
			copyData.add(copy);
		}

		

		//create the table
		TableColumn<Copy, String> idCol = new TableColumn<Copy, String>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("copyID"));

		TableColumn<Copy, String> loanDurCol = new TableColumn<Copy, String>("Duration");
		loanDurCol.setCellValueFactory(new PropertyValueFactory<>("loanDuration"));

		
		copiesTable.setItems(copyData);
		
		

		copiesTable.getColumns().addAll(idCol,loanDurCol);
		copiesTable.autosize();

		

	}

	
	
	
	@FXML
	 public void initialize() {
		setupCopy();
	}
	
	
}
