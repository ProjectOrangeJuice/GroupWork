package application;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reserve;
import model.ReserveFeature;
import model.Resource;
import model.User;

public class ReserveController {

	@FXML
	TableView table;
	
	@FXML
	public void approve(ActionEvent e) {
		
		Reserve r = (Reserve) table.getSelectionModel().getSelectedItem();
		if(r != null) {
			Instant instant = Instant.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
			Date dateFull = Date.from(instant);
			  SimpleDateFormat normal = new SimpleDateFormat("dd/MM/yyyy");
		       String date = normal.format(dateFull);
		      
		    if(!date.equals( r.getDate())) {
		    	AlertBox.showErrorAlert("Not reserved for today!");
		    }else {
		    	
			Resource resource = Resource.getResource(r.getRId());
			int free = resource.loanToUser(((User)User.loadPerson(r.getUsername())));
			if(free==0) {
				AlertBox.showErrorAlert("No free copies! Reserve for another time");
			}
			if(free == 1) {
				AlertBox.showInfoAlert("Approved");
			}else {
				AlertBox.showInfoAlert("Approved for "+free+" days");
			}
			
			r.cancel();
			rebuildTable();
		    }
		}
		
		
		
	}
	
	@FXML
	public void cancel(ActionEvent e) {
		Reserve r = (Reserve) table.getSelectionModel().getSelectedItem();
		if(r != null) {
			r.cancel();
			rebuildTable();
		}
	}
	
	
	private void rebuildTable() {
		
		table.getItems().clear();
		table.getColumns().clear();
		
		TableColumn<String, Reserve> column1 = new TableColumn<>("Username");
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));


        TableColumn<String, Reserve> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<String, Reserve> column3 = new TableColumn<>("CopyId");
        column3.setCellValueFactory(new PropertyValueFactory<>("copyId"));

        TableColumn<String, Reserve> column4 = new TableColumn<>("When");
        column4.setCellValueFactory(new PropertyValueFactory<>("date"));


        table.getColumns().addAll(column1,column2,column3,column4);
  

	
	
	ArrayList<Reserve> r = ReserveFeature.getReserves();
	for(Reserve reserve : r) {
		table.getItems().add(reserve);
		System.out.println("we're on reserve.. "+reserve.getTitle());
	}
	
	}
	
	@FXML
	public void initialize() {
		ReserveFeature.checkForLate();
		
		rebuildTable();
		 
		
	}
}
