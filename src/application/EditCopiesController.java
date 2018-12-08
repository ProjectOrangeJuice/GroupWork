package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Copy;
import model.DBHelper;
import model.Fine;
import model.Payment;
import model.Resource;
import model.Transactions;
import model.User;

public class EditCopiesController {

	private ArrayList<Copy> copies;
	private TableView<Copy> tableCopies= new TableView<>();
	ObservableList<Copy> copyData = FXCollections.observableArrayList();
	
	@FXML
	private TextField loanDur;
	
	
	@FXML
	private TableView copiesTable;
	
	
	private void setupCopy() {
		copies = ScreenManager.currentResource.getCopies();
		System.out.println("Current is "+ScreenManager.currentResource.getUniqueID());
		System.out.println(copies.size());
		

		copyData = FXCollections.observableArrayList();
		for (Copy copy : copies) {
			System.out.println("has found: "+copy.getCopyID());
			copyData.add(copy);
		}

		

		//create the table
		TableColumn<Copy, String> idCol = new TableColumn<Copy, String>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("copyID"));

		TableColumn<Copy, Number> loanDurCol = new TableColumn<Copy, Number>("Duration");
		loanDurCol.setCellValueFactory(new PropertyValueFactory<>("loanDuration"));
		
		

		
		copiesTable.setItems(copyData);
		
		copiesTable.setRowFactory( tv -> {
			TableRow<Copy> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
					Copy rowData = row.getItem();
					updateDuration(rowData);
				}
			});
			return row ;
		});

		copiesTable.getColumns().addAll(idCol,loanDurCol);
		copiesTable.autosize();

		

	}
	
	@FXML
	private void addCopy(ActionEvent event) {
		String duration = loanDur.getText();
		boolean goAhead = true;
		try {
			Integer.parseInt(duration);
		} catch (NumberFormatException e){
			goAhead = false;
		}
		
		if(goAhead) {
			System.out.println("Copy adding");
			int id = makeId();
			Copy copy = new Copy(ScreenManager.getCurrentResource(),id,null,Integer.parseInt(duration));
			ScreenManager.getCurrentResource().addCopy(copy);
			System.out.println("Copy.. "+copy.getCopyID());
			copiesTable = new TableView<>();
			setupCopy();
		}
	}

	private int makeId() {
		Random rand = new Random();
		boolean goAhead = false;
		int n = 0;
		while(!goAhead) {
			n = rand.nextInt(5000) + 1;
			checkId(n);
		}
		return n;
		
	}
	
	private boolean checkId(int id) {
		try {
			Connection connection = DBHelper.getConnection(); 
			PreparedStatement statement = connection.prepareStatement("SELECT * "
					+ "FROM copies WHERE copyId=?");
			statement.setInt(1,id);
			ResultSet results = statement.executeQuery(); 
			if(results.next()) {
				return false;
			}else {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
	}
		return false;
	}
	
	
	private void updateDuration(Copy copy){
		String duration = loanDur.getText();
		boolean goAhead = true;
		try {
			Integer.parseInt(duration);
		} catch (NumberFormatException e){
			goAhead = false;
		}
		
		if(goAhead) {
			int durationNumber = Integer.parseInt(duration);
			copy.setLoanDuration(durationNumber);
			copiesTable = new TableView<>();
			setupCopy();
		}
		
	}
	
	
	@FXML
	 public void initialize() {
		setupCopy();
	}
	
	
}
