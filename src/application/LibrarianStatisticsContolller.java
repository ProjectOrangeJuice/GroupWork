package application;

import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import model.Person;
import model.Resource;
import model.Statistics;

public class LibrarianStatisticsContolller {

	@FXML
	private ImageView bookImg;

	@FXML
	private ImageView bookImg1;

	@FXML
	private Tab dayTab11;

	@FXML
	private ImageView dvdImg11;

	@FXML
	private ImageView gameImg11;

	@FXML
	private ImageView dvdImg;

	@FXML
	private Tab overallTab1;

	@FXML
	private ImageView laptopImg;

	@FXML
	private ImageView gameImg;

	@FXML
	private ImageView gameImg1;

	@FXML
	private Tab dayTab;

	@FXML
	private ImageView bookImg11;

	@FXML
	private TextArea descBox1;

	@FXML
	private ImageView dvdImg1;

	@FXML
	private TextArea descBox;

	@FXML
	private ImageView laptopImg11;

	@FXML
	private Tab dayTab1;

	@FXML
	private TextArea descBox11;

	@FXML
	private ImageView laptopImg1;
	
	@FXML
    private LineChart<?, ?> fineChart;

	private static final String END_HOUR = "23:59:59";

	Person person = ScreenManager.getCurrentUser();

	public void initialize() {
		this.weeklyBook();
		this.weeklyDVD();
		this.weeklyLaptop();
		this.weeklyGame();
		this.monthlyBook();
		this.monthlyDVD();
		this.monthlyGame();
		this.monthlyLaptop();
		this.allTimeBook();
		this.allTimeDVD();
		this.allTimeGame();
		this.allTimeLaptop();
		this.initialiseFineChart();
	}

	public void weeklyBook() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int Book = Statistics.getMostPopularBook(date2, date1);
		if(Book!=-1) {
			bookImg.setImage(findResource(Book).getThumbnail());
		}
		else {
			bookImg.setImage(null);
		}

	}

	public void monthlyBook() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int Book = Statistics.getMostPopularBook(date2, date1);
		if(Book!=-1) {
			bookImg1.setImage(findResource(Book).getThumbnail());
		}
		else {
			bookImg1.setImage(null);
		}

	}

	public void allTimeBook() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int Book = Statistics.getMostPopularBook(date2, date1);
		if(Book!=-1) {
			bookImg11.setImage(findResource(Book).getThumbnail());
		}
		else {
			bookImg11.setImage(null);
		}

	}

	public void weeklyDVD() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int DVD = Statistics.getMostPopularDVD(date2, date1);
		if(DVD!=-1) {
			dvdImg.setImage(findResource(DVD).getThumbnail());
		}
		else {
			dvdImg.setImage(null);
		}

	}

	public void monthlyDVD() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int DVD = Statistics.getMostPopularDVD(date2, date1);
		if(DVD!=-1) {
			dvdImg1.setImage(findResource(DVD).getThumbnail());
		}
		else {
			dvdImg1.setImage(null);
		}

	}

	public void allTimeDVD() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int DVD = Statistics.getMostPopularDVD(date2, date1);
		if(DVD!=-1) {
			dvdImg11.setImage(findResource(DVD).getThumbnail());
		}
		else {
			dvdImg11.setImage(null);
		}

	}

	public void weeklyLaptop() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int Laptop = Statistics.getMostPopularLaptop(date2, date1);
		if(Laptop!=-1) {
			laptopImg.setImage(findResource(Laptop).getThumbnail());
		}
		else {
			laptopImg.setImage(null);
		}

	}

	public void monthlyLaptop() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int Laptop = Statistics.getMostPopularLaptop(date2, date1);
		if(Laptop!=-1) {
			laptopImg1.setImage(findResource(Laptop).getThumbnail());
		}
		else {
			laptopImg1.setImage(null);
		}

	}

	public void allTimeLaptop() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int Laptop = Statistics.getMostPopularLaptop(date2, date1);
		if(Laptop!=-1) {
			laptopImg11.setImage(findResource(Laptop).getThumbnail());
		}
		else {
			laptopImg11.setImage(null);
		}

	}

	public void weeklyGame() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int Game = Statistics.getMostPopularGame(date2, date1);
		if(Game!=-1) {
			gameImg.setImage(findResource(Game).getThumbnail());
		}
		else {
			gameImg.setImage(null);
		}

	}

	public void monthlyGame() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int Game = Statistics.getMostPopularGame(date2, date1);
		if(Game!=-1) {
			gameImg1.setImage(findResource(Game).getThumbnail());
		}
		else {
			gameImg1.setImage(null);
		}

	}

	public void allTimeGame() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int Game = Statistics.getMostPopularGame(date2, date1);
		if(Game!=-1) {
			gameImg11.setImage(findResource(Game).getThumbnail());
		}
		else {
			gameImg11.setImage(null);
		}

	}
	
	public void initialiseFineChart() {
		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);
		
		XYChart.Series series = new XYChart.Series();
		
		int fines = Statistics.getMostFine(date1, date2);
		
		series.getData().add(new XYChart.Data("Fines", fines));
		
	}

	private Resource findResource(int rID) {
		for (Resource r : Resource.getResources()) {
			
				if (r.getUniqueID() == rID) {
					return r;
				}
			}return null;

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
