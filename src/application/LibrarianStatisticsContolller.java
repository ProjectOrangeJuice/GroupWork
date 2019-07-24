package application;

import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Person;
import model.Resource;
import model.Statistics;
import model.User;

/**
 * 
 * @author James Finlayson 905234
 *
 */
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
	private Text avgFine;

	@FXML
	private LineChart<Number, Number> fineChart;

	private static final String END_HOUR = "23:59:59";

	private int monthStart = 0;
	private int monthEnd = 30;

	Person person = ScreenManager.getCurrentUser();

	/**
	 * Initialises the controller
	 */
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

	/**
	 * gets the most popular book for this week sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void weeklyBook() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int Book = Statistics.getMostPopularBook(date2, date1);
		if (Book != -1) {
			bookImg.setImage(findResource(Book).getThumbnail());
			descBox.appendText("The most popular book is: " + findResource(Book).getTitle() + "\n");
		} else {
			bookImg.setImage(null);
		}

	}

	/**
	 * gets the most popular book for this month sets the tumbnail as displayed
	 * image sets the title as variable in description box
	 */
	public void monthlyBook() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int Book = Statistics.getMostPopularBook(date2, date1);
		if (Book != -1) {
			bookImg1.setImage(findResource(Book).getThumbnail());
			descBox1.appendText("The most popular book is: " + findResource(Book).getTitle() + "\n");
		} else {
			bookImg1.setImage(null);
		}

	}

	/**
	 * gets the most popular book for all time sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void allTimeBook() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int Book = Statistics.getMostPopularBook(date2, date1);
		if (Book != -1) {
			bookImg11.setImage(findResource(Book).getThumbnail());
			descBox11.appendText("The most popular book is: " + findResource(Book).getTitle() + "\n");
		} else {
			bookImg11.setImage(null);
		}

	}

	/**
	 * gets the most popular DVD for this week sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void weeklyDVD() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int DVD = Statistics.getMostPopularDVD(date2, date1);
		if (DVD != -1) {
			dvdImg.setImage(findResource(DVD).getThumbnail());
			descBox.appendText("The most popular DVD is: " + findResource(DVD).getTitle() + "\n");
		} else {
			dvdImg.setImage(null);
		}

	}

	/**
	 * gets the most popular DVD for this month sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void monthlyDVD() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int DVD = Statistics.getMostPopularDVD(date2, date1);
		if (DVD != -1) {
			dvdImg1.setImage(findResource(DVD).getThumbnail());
			descBox1.appendText("The most popular DVD is: " + findResource(DVD).getTitle() + "\n");
		} else {
			dvdImg1.setImage(null);
		}

	}

	/**
	 * gets the most popular DVD for all time sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void allTimeDVD() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int DVD = Statistics.getMostPopularDVD(date2, date1);
		if (DVD != -1) {
			dvdImg11.setImage(findResource(DVD).getThumbnail());
			descBox11.appendText("The most popular DVD is: " + findResource(DVD).getTitle() + "\n");
		} else {
			dvdImg11.setImage(null);
		}

	}

	/**
	 * gets the most popular Laptop for this week sets the tumbnail as displayed
	 * image sets the title as variable in description box
	 */
	public void weeklyLaptop() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int Laptop = Statistics.getMostPopularLaptop(date2, date1);
		if (Laptop != -1) {
			laptopImg.setImage(findResource(Laptop).getThumbnail());
			descBox.appendText("The most popular Laptop is: " + findResource(Laptop).getTitle() + "\n");
		} else {
			laptopImg.setImage(null);
		}

	}

	/**
	 * gets the most popular Laptop for this month sets the tumbnail as displayed
	 * image sets the title as variable in description box
	 */
	public void monthlyLaptop() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int Laptop = Statistics.getMostPopularLaptop(date2, date1);
		if (Laptop != -1) {
			laptopImg1.setImage(findResource(Laptop).getThumbnail());
			descBox1.appendText("The most popular Laptop is: " + findResource(Laptop).getTitle() + "\n");
		} else {
			laptopImg1.setImage(null);
		}

	}

	/**
	 * gets the most popular Laptop for all time sets the tumbnail as displayed
	 * image sets the title as variable in description box
	 */
	public void allTimeLaptop() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int Laptop = Statistics.getMostPopularLaptop(date2, date1);
		if (Laptop != -1) {
			laptopImg11.setImage(findResource(Laptop).getThumbnail());
			descBox11.appendText("The most popular Laptop is: " + findResource(Laptop).getTitle() + "\n");
		} else {
			laptopImg11.setImage(null);
		}

	}

	/**
	 * gets the most popular Game for this week sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void weeklyGame() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(7, 0);

		int Game = Statistics.getMostPopularGame(date2, date1);
		if (Game != -1) {
			gameImg.setImage(findResource(Game).getThumbnail());
			descBox.appendText("The most popular Game is: " + findResource(Game).getTitle() + "\n");
		} else {
			gameImg.setImage(null);
		}

	}

	/**
	 * gets the most popular Game for this month sets the tumbnail as displayed
	 * image sets the title as variable in description box
	 */
	public void monthlyGame() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 1);

		int Game = Statistics.getMostPopularGame(date2, date1);
		if (Game != -1) {
			gameImg1.setImage(findResource(Game).getThumbnail());
			descBox1.appendText("The most popular Game is: " + findResource(Game).getTitle() + "\n");
		} else {
			gameImg1.setImage(null);
		}

	}

	/**
	 * gets the most popular Game for all time sets the tumbnail as displayed image
	 * sets the title as variable in description box
	 */
	public void allTimeGame() {

		String date1 = dateFormat(0, 0);
		String date2 = dateFormat(0, 48);

		int Game = Statistics.getMostPopularGame(date2, date1);
		if (Game != -1) {
			gameImg11.setImage(findResource(Game).getThumbnail());
			descBox11.appendText("The most popular Game is: " + findResource(Game).getTitle() + "\n");
		} else {
			gameImg11.setImage(null);
		}

	}

	/**
	 * change chart to display 30days before current
	 * @param event mouse click
	 */
	@FXML
	public void prev(MouseEvent event) {
		monthStart += 30;
		monthEnd += 30;
		initialiseFineChart();
	}

	/**
	 * change chart to display 30days after current
	 * @param event mouse click
	 */
	@FXML
	public void next(MouseEvent event) {
		if (monthStart > 0) {
			monthStart -= 30;
			monthEnd -= 30;
			initialiseFineChart();
		}
	}

	/**
	 * initialises chart to display number of fines for given time period
	 */
	public void initialiseFineChart() {
		String date1 = dateFormat(monthStart, 0);
		String date2 = dateFormat(monthEnd, 0);
		// Change average text
		double total = Statistics.getAvgFine(date2, date1);
		avgFine.setText("Total fine for "+monthStart+" to " + monthEnd + " days ago is " + total);

		XYChart.Series series = new XYChart.Series();

		for (int i = monthStart; i < monthEnd; i++) {
			date1 = dateFormat(i - 1, 0);
			date2 = dateFormat(i, 0);
			int fines = Statistics.getMostFine(date2, date1);
			System.out.println("For " + i + " - " + fines);
			series.getData().add(new XYChart.Data("" + i, fines));
		}
		fineChart.getData().removeAll();
		fineChart.getData().clear();
		fineChart.getData().add(series);

	}

	/**
	 * finds specific resource in set of all resources using rID
	 * 
	 * @param rID
	 * @return resource
	 */
	private Resource findResource(int rID) {
		for (Resource r : Resource.getResources()) {

			if (r.getUniqueID() == rID) {
				return r;
			}
		}
		return null;

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