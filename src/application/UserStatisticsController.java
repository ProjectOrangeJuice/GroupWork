package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextFlow;
import model.DBHelper;
import model.Person;

public class UserStatisticsController {

	
	Person person = ScreenManager.getCurrentUser();
	String username = person.getUsername();

	/**
	 * Initialises the controller
	 * @throws SQLException 
	 */
	public void initialize() throws SQLException {
		
		this.initializeMonthlyStatsGraph();
		this.initializeWeeklyStatsGraph();
		this.initializeDailyStatsGraph();

	

	}
	


	public void initializeMonthlyStatsGraph() throws SQLException {
		String month1;
		String month2;
		String day1;
		String day2;
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		int month1T = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
		int year1  = c.get(Calendar.YEAR);
		int day1T = c.get(Calendar.DAY_OF_MONTH)-1;
		if(day1T<10) {
			day1 = "0"+day1T;
		}else {
			day1 = String.valueOf(day1T);
		}
		if(month1T<10) {
			month1 = "0"+month1T;
		}else {
			month1 = String.valueOf(month1T);
		}
		c.add(Calendar.MONTH, -1);
		int month2T = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
		int year2  = c.get(Calendar.YEAR);
		int day2T = c.get(Calendar.DAY_OF_MONTH)-1;
		
		if(day1T<10) {
			day2 = "0"+day2T;
		}else {
			day2 = String.valueOf(day2T);
		}
		
		if(month1T<10) {
			month2 = "0"+month2T;
		}else {
			month2 = String.valueOf(month2T);
		}
		
		String date1 = year1+"-"+month1+"-"+day1+" 23:59:59";
		String date2 = year2+"-"+month2+"-"+day2+" 23:59:59";

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username,
				date2,date1);
		System.out.println("This month : "+monthStat);
	}
		
		
		public void initializeWeeklyStatsGraph() {
			String month1;
			String month2;
			String day1;
			String day2;
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 1);
			dt = c.getTime();
			int month1T = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
			int year1  = c.get(Calendar.YEAR);
			int day1T = c.get(Calendar.DAY_OF_MONTH)-1;
			if(day1T<10) {
				day1 = "0"+day1T;
			}else {
				day1 = String.valueOf(day1T);
			}
			if(month1T<10) {
				month1 = "0"+month1T;
			}else {
				month1 = String.valueOf(month1T);
			}
			c.add(Calendar.DAY_OF_MONTH, -7);
			int month2T = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
			int year2  = c.get(Calendar.YEAR);
			int day2T = c.get(Calendar.DAY_OF_MONTH)-1;
			
			if(day1T<10) {
				day2 = "0"+day2T;
			}else {
				day2 = String.valueOf(day2T);
			}
			
			if(month1T<10) {
				month2 = "0"+month2T;
			}else {
				month2 = String.valueOf(month2T);
			}
			
			String date1 = year1+"-"+month1+"-"+day1+" 23:59:59";
			String date2 = year2+"-"+month2+"-"+day2+" 23:59:59";
			
			
			
			
			
			System.out.println(date1.toString());
			System.out.println(date1.toString());
			int monthStat = model.Statistics.totalBorrow(username,
					date2,date1);
			System.out.println("This Week : "+monthStat);
		}
		
		
		public void initializeDailyStatsGraph() {
			String month1;
			String month2;
			String day1;
			String day2;
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 1);
			dt = c.getTime();
			int month1T = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
			int year1  = c.get(Calendar.YEAR);
			int day1T = c.get(Calendar.DAY_OF_MONTH)-1;
			if(day1T<10) {
				day1 = "0"+day1T;
			}else {
				day1 = String.valueOf(day1T);
			}
			if(month1T<10) {
				month1 = "0"+month1T;
			}else {
				month1 = String.valueOf(month1T);
			}
			c.add(Calendar.DAY_OF_MONTH, -1);
			int month2T = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
			int year2  = c.get(Calendar.YEAR);
			int day2T = c.get(Calendar.DAY_OF_MONTH)-1;
			
			if(day1T<10) {
				day2 = "0"+day2T;
			}else {
				day2 = String.valueOf(day2T);
			}
			
			if(month1T<10) {
				month2 = "0"+month2T;
			}else {
				month2 = String.valueOf(month2T);
			}
			
			String date1 = year1+"-"+month1+"-"+day1+" 23:59:59";
			String date2 = year2+"-"+month2+"-"+day2+" 23:59:59";
			
			
			
			
			
			System.out.println(date1.toString());
			System.out.println(date1.toString());
			int monthStat = model.Statistics.totalBorrow(username,
					date2,date1);
			System.out.println("24hours : "+monthStat);
		}

	}



