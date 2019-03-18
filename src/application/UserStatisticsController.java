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

/**
 * Users statistics display.
 * 
 * @author James
 *
 */
public class UserStatisticsController {

	Person person = ScreenManager.getCurrentUser();
	String username = person.getUsername();
	XYChart.Series monthData = new XYChart.Series();
	XYChart.Series dayData = new XYChart.Series();
	XYChart.Series monthData = new XYChart.Series();
	
	private static final String END_HOUR = "23:59:59";

	/**
	 * Initialises the controller
	 * 
	 * @throws SQLException
	 */
	public void initialize() throws SQLException {

		this.initializeMonthlyStatsGraph();
		this.initializeWeeklyStatsGraph();
		this.initializeDailyStatsGraph();

	}

	
	/**
	 * Setups the values for the monthly stats.
	 */
	public void initializeMonthlyStatsGraph() {

		String date1 = dateFormat(0,0);
		String date2 = dateFormat(0,1);

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username, date2, date1);
		System.out.println("This month : " + monthStat);
	}

	/**
	 * Sets up the weekly stats.
	 */
	public void initializeWeeklyStatsGraph() {
		String date1 = dateFormat(0,0);
		String date2 = dateFormat(7,0);

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username, date2, date1);
		System.out.println("This Week : " + monthStat);
	}

	/**
	 * Sets up the daily stats.
	 */
	public void initializeDailyStatsGraph() {
		String date1 = dateFormat(0,0);
		String date2 = dateFormat(0,1);

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username, date2, date1);
		System.out.println("24hours : " + monthStat);
	}
	
	/**
	 * Generate a string with a date.
	 * @param daysBackwards The number of days to go back.
	 * @param monthsBackwards The number of months to go back.
	 * @return A string with the date.
	 */
	private String dateFormat(int daysBackwards, int monthsBackwards) {
		String month1;
		String month2;
		String day1;
		String day2;
		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();

		c.add(Calendar.DAY_OF_MONTH, -1);
		int month2T = c.get(Calendar.MONTH) + 1 - monthsBackwards; // beware of month indexing from zero
		int year2 = c.get(Calendar.YEAR);
		int day2T = c.get(Calendar.DAY_OF_MONTH) - daysBackwards;

		if (day2T < 10) {
			day2 = "0" + day2T;
		} else {
			day2 = String.valueOf(day2T);
		}

		if (month2T < 10) {
			month2 = "0" + month2T;
		} else {
			month2 = String.valueOf(month2T);
		}
		
		String date1 = year2 + "-" + month2 + "-" + day2 + " "+END_HOUR;
	}

}
