package application;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Person;

/**
 * Users statistics display.
 * 
 * @author James
 *
 */
public class UserStatisticsController {

	@FXML
	private BorderPane border;

	@FXML
	private BarChart<?, ?> borrowChart;

	@FXML
	private Label dayLabel;

	@FXML
	private TextField borrowTodayTxt;

	@FXML
	private TextField borrowWeekTxt;

	@FXML
	private Label monthLabel;

	@FXML
	private TextField borrowMonthTxt;

	@FXML
	private Label weekLabel;

	Person person = ScreenManager.getCurrentUser();
	String username = person.getUsername();

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

		XYChart.Series series = new XYChart.Series();

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username, date2, date1);
		System.out.println("This month : " + monthStat);
		borrowMonthTxt.setText(""+monthStat);
		series.getData().add(new XYChart.Data("Monthly", monthStat));
		borrowChart.getData().add(series);
	}

	/**
	 * Sets up the weekly stats.
	 */
	public void initializeWeeklyStatsGraph() {

		XYChart.Series series = new XYChart.Series();

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username, date2, date1);
		System.out.println("This Week : " + monthStat);
		borrowWeekTxt.setText(""+monthStat);
		series.getData().add(new XYChart.Data("Weekly", monthStat));
		borrowChart.getData().add(series);
	}

	/**
	 * Sets up the daily stats.
	 */
	public void initializeDailyStatsGraph() {

		XYChart.Series series = new XYChart.Series();

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		System.out.println(date1.toString());
		System.out.println(date1.toString());
		int monthStat = model.Statistics.totalBorrow(username, date2, date1);
		System.out.println("24hours : " + monthStat);
		borrowTodayTxt.setText(""+monthStat);
		series.getData().add(new XYChart.Data("Daily", monthStat));
		borrowChart.getData().add(series);
	}

	/**
	 * Generate a string with a date.
	 * 
	 * @param daysBackwards
	 *            The number of days to go back.
	 * @param monthsBackwards
	 *            The number of months to go back.
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

		String date1 = year2 + "-" + month2 + "-" + day2 + " " + END_HOUR;
		return date1;
	}

}
