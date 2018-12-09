package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DBHelper;
import model.Resource;

public class ResourceAdderController {

	@FXML
	public void addBook(Event event) {
		Connection connection;
		try {
			connection = DBHelper.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "resource(thumbnail) values('/graphics/logo.png') ");
		statement.executeUpdate(); 
		ResultSet result = statement.getGeneratedKeys();
		addActualBook(result.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	
	
	
	@FXML
	public void addDVD(Event event) {
		Connection connection;
		try {
			connection = DBHelper.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "resource(thumbnail) values('/graphics/logo.png') ");
		statement.executeUpdate(); 
		ResultSet result = statement.getGeneratedKeys();
		addActualDVD(result.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	@FXML
	public void addLaptop(Event event) {
		Connection connection;
		try {
			connection = DBHelper.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "resource(thumbnail) values('/graphics/logo.png') ");
		statement.executeUpdate(); 
		ResultSet result = statement.getGeneratedKeys();
		addActualLaptop(result.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	
	private void addActualBook(int ID) {
		Connection connection;
		try {
			connection = DBHelper.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "book(rID) values(?) ");
		statement.setInt(1,ID);
		statement.executeUpdate(); 

		Resource.loadDatabaseResources();
		
		ScreenManager.setCurrentResource(Resource.getResource(ID));
		
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
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	
	private void addActualLaptop(int ID) {
		Connection connection;
		try {
			connection = DBHelper.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "laptop(rID) values(?) ");
		statement.setInt(1,ID);
		statement.executeUpdate(); 

		Resource.loadDatabaseResources();
		
		ScreenManager.setCurrentResource(Resource.getResource(ID));
		
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
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
	private void addActualDVD(int ID) {
		Connection connection;
		try {
			connection = DBHelper.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("INSERT INTO "
				+ "dvd(rID) values(?) ");
		statement.setInt(1,ID);
		statement.executeUpdate(); 

		Resource.loadDatabaseResources();
		
		ScreenManager.setCurrentResource(Resource.getResource(ID));
		
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
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	}
	
}
