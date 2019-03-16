package application;

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
import model.Person;

public class UserStatisticsController {

	@FXML
	private BorderPane border;

	@FXML
	private ScatterChart<Number, Number> dailyStatsGraph;

	@FXML
	private HBox hbox;

	@FXML
	private Tab dailyTab;

	@FXML
	private TextFlow monthlyStatsText;

	@FXML
	private TabPane tabs;

	@FXML
	private TextFlow dailyStatsText;

	@FXML
	private LineChart<Number, Number> monthlyStatsGraph;

	@FXML
	private AnchorPane dailyAnchor;

	@FXML
	private TextFlow weeklyStatsText;

	@FXML
	private LineChart<String, Number> weeklyStatsGraph;

	// variables used to format the line charts by setting axises
	@FXML
	private NumberAxis dayXAxis;
	@FXML
	private NumberAxis dayYAxis;
	@FXML
	private NumberAxis monthXAxis;
	@FXML
	private NumberAxis monthYAxis;
	@FXML
	private NumberAxis weekYAxis;
	// used to set the upper bound of the Y axis of the line charts
	private int maxDayBorrows = 0;
	private int maxMonthBorrows = 0;
	private int maxWeekBorrows = 0;

	// date range for charts
	private Date desiredDate1 = null;
	private Date desiredDate2 = null;

	private final String daysOfTheWeek[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
			"Sunday" };

	// the format of a date used to set the desiredDate 1 and 2
	private final String FORMAT_DMYHM = new String("dd/MM/yyyy HH:mm");

	int borrowed;

	/**
	 * Initialises the controller
	 */
	public void initialize() {

		Person person = ScreenManager.getCurrentUser();

		// formating month graph
		monthXAxis.setAutoRanging(false);
		monthXAxis.setLowerBound(1);
		monthXAxis.setUpperBound(31);
		monthXAxis.setTickUnit(1);
		monthYAxis.setAutoRanging(false);
		monthYAxis.setLowerBound(0);
		monthYAxis.setUpperBound(maxMonthBorrows);
		monthYAxis.setTickUnit(1);

		// formating week graph
		weekYAxis.setAutoRanging(false);
		weekYAxis.setLowerBound(0);
		weekYAxis.setUpperBound(maxWeekBorrows);
		weekYAxis.setTickUnit(1);

		// formating day graph
		dayXAxis.setAutoRanging(false);
		dayXAxis.setLowerBound(1);
		dayXAxis.setUpperBound(24);
		dayXAxis.setTickUnit(1);
		dayYAxis.setAutoRanging(false);
		dayYAxis.setLowerBound(0);
		dayYAxis.setUpperBound(maxDayBorrows);
		dayYAxis.setTickUnit(1);

	}

	public void initializeMonthlyStatsGraph() {
		
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		
		int numOfDaysInAMonth =0;
		int curMonthInt = Calendar.getInstance().get(Calendar.MONTH)+1; //the current month in int and adding 1 because i am working with 1 index
		int curYearInt = Calendar.getInstance().get(Calendar.YEAR); //the current year in int
		GregorianCalendar cal1 = new GregorianCalendar(); // a calendar to check for leap years
		
		//setting the graph with different number of days according to the current month
		if(curMonthInt == 1 ||
			curMonthInt == 3 ||
			curMonthInt == 5 ||
			curMonthInt == 7 ||
			curMonthInt == 8 ||
			curMonthInt == 10 ||
			curMonthInt == 12 ) {
			numOfDaysInAMonth = 31;
		}else if(curMonthInt == 4 ||
				curMonthInt == 6 ||
				curMonthInt == 9 ||
				curMonthInt == 11){
			numOfDaysInAMonth = 30;
		}else if(curMonthInt == 2 && cal1.isLeapYear(curYearInt)){
			numOfDaysInAMonth = 29;
		}else {
			numOfDaysInAMonth = 28;
		}
		
		//looping though the days
		for(int i=1;i<numOfDaysInAMonth;i++){
			
			int dayInt = i;//the day in int format
			String dayString = Integer.toString(dayInt); // converting the day into a String for easier use
			int monthInt = Calendar.getInstance().get(Calendar.MONTH)+1; //the current month in int and adding 1 because i am working with 1 index
			String curMonthString = Integer.toString(monthInt); //the current month in String
			int yearInt = Calendar.getInstance().get(Calendar.YEAR); //the current year in int
			String curYearString = Integer.toString(yearInt); //the current year in String
			
			//setting desiredDate 1 and 2
			try {
				desiredDate1 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 00:01");
				desiredDate2 = new SimpleDateFormat(FORMAT_DMYHM).parse(dayString+"/"+curMonthString+"/"+curYearString+" 23:59");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			//getting the number of sales made between desiredDate 1 and 2
			int borrowedThisDate = (desiredDate1, desiredDate2);//TODO get data from database, #of things borrowed between 2 dates
			
			//adding points on the line chart
			series.getData().add(new XYChart.Data<Number, Number>(i, borrowedThisDate));
			
			//finding max sold listing in a month sales
			if(maxMonthBorrows<borrowedThisDate){
				maxMonthBorrows=borrowedThisDate;
			}
		}
		
		//adding points on the line chart
		monthlyStatsGraph.getData().add(series);
		
}

	public void initializeWeeklyStatsGraph() {
		Calendar now = Calendar.getInstance();
	    SimpleDateFormat formatDMY = new SimpleDateFormat("dd/MM/yyyy");
	    
	    //getting the dates of the days that are in the current week
	    int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
	    now.add(Calendar.DAY_OF_MONTH, delta-7 );
	    for (int i = 0; i < 7; i++)
	    {
	    	daysOfTheWeek[i] = formatDMY.format(now.getTime());
	        now.add(Calendar.DAY_OF_MONTH, 1);
	    }
		
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
			
			int borrowedThisDate = (desiredDate1, desiredDate2);//TODO get data from database, #of things borrowed between 2 dates
			
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

	public void initializeDailyStatsGraph() {
		for(int i=0;i<24;i++){
			
			int borrowedThisHour = ();//TODO get data from database, #of things borrowed this hour
			
			series.getData().add(new XYChart.Data<Number, Number>(i, borrowedThisHour));
		}
	}

}
