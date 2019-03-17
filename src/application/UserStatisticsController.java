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

	// date range for charts
	private Date desiredDate1 = null;
	private Date desiredDate2 = null;

	
	private final String daysOfTheWeek[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
			"Sunday" };

	// the format of a date used to set the desiredDate 1 and 2
	private final String FORMAT_DMYHM = new String("dd/MM/yyyy HH:mm");

	int borrowed;

	DBHelper helper = new DBHelper();
	Person person = ScreenManager.getCurrentUser();
	String username = person.getUsername();

	/**
	 * Initialises the controller
	 * @throws SQLException 
	 */
	public void initialize() throws SQLException {
		
		this.initializeMonthlyStatsGraph();
	//	this.initializeWeeklyStatsGraph();
		//this.initializeDailyStatsGraph();

	

	}
	


	public void initializeMonthlyStatsGraph() throws SQLException {
		
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int dayInt = dayOfMonth;//the day in int format
		String dayString = Integer.toString(dayInt); // converting the day into a String for easier use
		int monthInt = Calendar.getInstance().get(Calendar.MONTH)+1; //the current month in int and adding 1 because i am working with 1 index
		int nextMonthInt = monthInt+1;
		String nextMonth = Integer.toString(nextMonthInt); 
		String curMonthString = Integer.toString(monthInt); //the current month in String
		int yearInt = Calendar.getInstance().get(Calendar.YEAR); //the current year in int
		String curYearString = Integer.toString(yearInt); //the current year in String
		
		//setting desiredDate 1 and 2
		try {
			desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+nextMonth+"/"+curYearString+" 00:01");
			desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 23:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int monthStat = model.Statistics.totalBorrow(username,
				desiredDate1.toString(),desiredDate2.toString());
		System.out.println("This month : "+monthStat);
		
		
}

	public void initializeWeeklyStatsGraph() throws SQLException {
	
	    
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int dayInt = dayOfMonth;//the day in int format
		String dayString = Integer.toString(dayInt); // converting the day into a String for easier use
		int monthInt = Calendar.getInstance().get(Calendar.MONTH)+1; //the current month in int and adding 1 because i am working with 1 index
	
		int dayInt2 = dayInt-;//the day in int format
		String dayString2 = Integer.toString(dayInt); // converting the day into a String for easier use
		
		
		String curMonthString = Integer.toString(monthInt); //the current month in String
		int yearInt = Calendar.getInstance().get(Calendar.YEAR); //the current year in int
		String curYearString = Integer.toString(yearInt); //the current year in String
		
		//setting desiredDate 1 and 2
		try {
			desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+nextMonth+"/"+curYearString+" 00:01");
			desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 23:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	    
	    Date date = new Date();
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    c.add(Calendar.DATE, -i - 7);
	    Date start = c.getTime();
	    c.add(Calendar.DATE, 6);
	    //getting the dates of the days that are in the current week
	    int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
	    now.add(Calendar.DAY_OF_MONTH, delta-7 );
	    for (int i = 0; i < 7; i++)
	    {
	    	daysOfTheWeek[i] = formatDMY.format(now.getTime());
	        now.add(Calendar.DAY_OF_MONTH, 1);
	    }
		
		int weekStat = model.Statistics.totalBorrow(username,
				desiredDate1.toString(),desiredDate2.toString());
		System.out.println("Week stat: "+weekStat);
		
		
	    
	   
	    
	  
		
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		
		//looping though the days
		for(int i=0;i<7;i++){
			String day = daysOfTheWeek[i];
			
			//setting desiredDate 1 and 2
			try {
				desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(day+" 00:01");
				desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(day+" 23:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			
			int borrowedThisDate = model.Statistics.totalBorrow(username,
					desiredDate1.toString(),desiredDate2.toString());
		
			//set the graph data
			series.getData().add(new XYChart.Data<String, Number>(this.daysOfTheWeek[i], borrowedThisDate));
			
			//finding max sold listing in a week sales
			if(maxWeekBorrows<borrowedThisDate){
				maxWeekBorrows=borrowedThisDate;
			}
		}
		//set the graph data
		weeklyStatsGraph.getData().add(series);
	
	}

	public void initializeDailyStatsGraph() throws SQLException {
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		
		try {
			desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 00:01");
			desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 23:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}

			int borrowedThisHour = model.Statistics.totalBorrow(username,
					desiredDate1.toString(),desiredDate2.toString());
	
			
			series.getData().add(new XYChart.Data<Number, Number>(0, borrowedThisHour));
		}
	}


