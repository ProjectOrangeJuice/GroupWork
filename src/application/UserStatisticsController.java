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
	private final String FORMAT_YMDHMS = new String("yyyy-MM-dd HH:mm:ss");

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
		
		
//}
//
//	public void initializeWeeklyStatsGraph() throws SQLException {
//	
//	    
//		Calendar cal = Calendar.getInstance();
//		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//		int dayInt = dayOfMonth;//the day in int format
//		String dayString = Integer.toString(dayInt); // converting the day into a String for easier use
//		int monthInt = Calendar.getInstance().get(Calendar.MONTH)+1; //the current month in int and adding 1 because i am working with 1 index
//	
//		int dayInt2 = dayInt-;//the day in int format
//		String dayString2 = Integer.toString(dayInt); // converting the day into a String for easier use
//		
//		
//		String curMonthString = Integer.toString(monthInt); //the current month in String
//		int yearInt = Calendar.getInstance().get(Calendar.YEAR); //the current year in int
//		String curYearString = Integer.toString(yearInt); //the current year in String
//		
//		//setting desiredDate 1 and 2
//		try {
//			desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+nextMonth+"/"+curYearString+" 00:01");
//			desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 23:59");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//	    
//	    Date date = new Date();
//	    Calendar c = Calendar.getInstance();
//	    c.setTime(date);
//	    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
//	    c.add(Calendar.DATE, -i - 7);
//	    Date start = c.getTime();
//	    c.add(Calendar.DATE, 6);
//	    //getting the dates of the days that are in the current week
//	    int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
//	    now.add(Calendar.DAY_OF_MONTH, delta-7 );
//	    for (int i = 0; i < 7; i++)
//	    {
//	    	daysOfTheWeek[i] = formatDMY.format(now.getTime());
//	        now.add(Calendar.DAY_OF_MONTH, 1);
//	    }
//		
//		int weekStat = model.Statistics.totalBorrow(username,
//				desiredDate1.toString(),desiredDate2.toString());
//		System.out.println("Week stat: "+weekStat);
//		
//		
//	    
//	   
//	    
//	  
//		
//		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
//		
//		//looping though the days
//		for(int i=0;i<7;i++){
//			String day = daysOfTheWeek[i];
//			
//			//setting desiredDate 1 and 2
//			try {
//				desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(day+" 00:01");
//				desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(day+" 23:59");
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			
//			
//			
//			int borrowedThisDate = model.Statistics.totalBorrow(username,
//					desiredDate1.toString(),desiredDate2.toString());
//		
//			//set the graph data
//			series.getData().add(new XYChart.Data<String, Number>(this.daysOfTheWeek[i], borrowedThisDate));
//			
//			//finding max sold listing in a week sales
//			if(maxWeekBorrows<borrowedThisDate){
//				maxWeekBorrows=borrowedThisDate;
//			}
//		}
//		//set the graph data
//		weeklyStatsGraph.getData().add(series);
//	
//	}
//
//	public void initializeDailyStatsGraph() throws SQLException {
//		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
//		
//		try {
//			desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 00:01");
//			desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 23:59");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//			int borrowedThisHour = model.Statistics.totalBorrow(username,
//					desiredDate1.toString(),desiredDate2.toString());
//	
//			
//			series.getData().add(new XYChart.Data<Number, Number>(0, borrowedThisHour));
//		}
	}
}


