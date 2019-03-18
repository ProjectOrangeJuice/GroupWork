package application;

import java.util.Calendar;
import java.util.Date;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
	
	private static final int RES_IMG_WIDTH = 200;
    private static final int RES_IMG_HEIGHT = 200;
   
	
	Person person = ScreenManager.getCurrentUser();
	private Date desiredDate1 = null;
	private Date desiredDate2 = null;

	private final String daysOfTheWeek[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
			"Sunday" };

	private final String FORMAT_YMDHMS = new String("yyyy-MM-dd HH:mm:ss");

	public void initialize() {
		this.weeklyBook();
	}
	
	public void weeklyBook() {
		
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
		
		Resource book = Statistics.getMostPopularBook(date1, date2);
		System.out.println(book.getTitle());
	}
		
		/**

		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
		String toogleGroupValue = selectedRadioButton.getText();
		
		
		requestButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switch (toogleGroupValue) {
				case "bookRB":
					try {
						Resource mostPopResource = getMostPopularBook(dayTab);
					
						resourceimage.setImage(mostPopResource.getThumbnail());
						
						descBox.setText("This resource was borrowed the most today");
						
						
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "bookRB1":
					try {
						Resource mostPopResource = getMostPopularBook(weekTab);
						
						resourceimage1.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "bookRB11":
					try {
						Resource mostPopResource = getMostPopularBook(overallTab);
						
						resourceimage11.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "dvdRB":
					try {
						Resource mostPopResource = getMostPopularDVD(dayTab);
						
						resourceimage.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "dvdRB1":
					try {
						Resource mostPopResource = getMostPopularDVD(weekTab);
						
						resourceimage1.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "dvdRB11":
					try {
						Resource mostPopResource = getMostPopularDVD(overallTab);
						
						resourceimage11.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "laptopRB":
					try {
						Resource mostPopResource = getMostPopularLaptop(dayTab);
						
						resourceimage.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case "laptopRB1":
					try {
						Resource mostPopResource = getMostPopularLaptop(weekTab);
						
						resourceimage1.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case "laptopRB11":
					try {
						Resource mostPopResource = getMostPopularLaptop(overallTab);
						
						resourceimage11.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case "gameRB":
					try {
						Resource mostPopResource = getMostPopularGame(dayTab);
						
						resourceimage.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					break;
				case "gameRB1":
					try {
						Resource mostPopResource = getMostPopularGame(weekTab);
						
						resourceimage1.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				case "gameRB11":
					try {
						Resource mostPopResource = getMostPopularGame(overallTab);
						
						resourceimage11.setImage(mostPopResource.getThumbnail());
					} catch (SQLException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					break;
				}

			}
		});

	}

	public Resource getMostPopularBook(Tab tab) throws SQLException, ParseException {
		SimpleDateFormat formatDMY = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		Resource popBook = null;
		int bookID = 0;
		Connection con = DBHelper.getConnection();
		try {
			con = DBHelper.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tab != null) {
			if (tab.equals(dayTab)) {

				desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 00:01");
				desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 23:59");
				String getBooks = "SELECT * FROM  requestsToApprove, resource,book"
						+ "WHERE requestsToApprove.rID=resource.rID AND book.rID=resource.rID"
						+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
						+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";
				
				PreparedStatement pstmt = con.prepareStatement(getBooks);
				ResultSet bookSet = pstmt.executeQuery();

				while (bookSet.next()) {
					bookID = bookSet.getInt("rID");
					popBook = Resource.getResource(bookID);
				}
			} else if (tab.equals(weekTab)) {
				Calendar now = Calendar.getInstance();

				List<Integer> popBooks = new ArrayList<Integer>();

				// getting the dates of the days that are in the current week
				int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
				now.add(Calendar.DAY_OF_MONTH, delta - 7);
				for (int i = 0; i < 7; i++) {
					daysOfTheWeek[i] = formatDMY.format(now.getTime());
					now.add(Calendar.DAY_OF_MONTH, 1);
				}

				// looping though the days
				for (int i = 0; i < 7; i++) {
					String day = daysOfTheWeek[i];

					try {
						desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 00:01");
						desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 23:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}

					String getBooks = "SELECT * FROM  requestsToApprove, resource,book"
							+ "WHERE requestsToApprove.rID=resource.rID AND book.rID=resource.rID"
							+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
							+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

					PreparedStatement pstmt = con.prepareStatement(getBooks);
					ResultSet bookSet = pstmt.executeQuery();

					while (bookSet.next()) {
						bookID = bookSet.getInt("rID");
						popBooks.add(bookID);
					}
				}
				int mostFreq = getMostOccoringElement(popBooks);
				popBook = Resource.getResource(mostFreq);
			} else {

				String getBooks = "SELECT * FROM  requestsToApprove, resource,book"
						+ "WHERE requestsToApprove.rID=resource.rID AND book.rID=resource.rID"
						+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

				PreparedStatement pstmt = con.prepareStatement(getBooks);
				ResultSet bookSet = pstmt.executeQuery();

				while (bookSet.next()) {
					bookID = bookSet.getInt("rID");
					popBook = Resource.getResource(bookID);
				}

			}
		}
		con.close();
		return popBook;
	}

	public Resource getMostPopularDVD(Tab tab) throws SQLException, ParseException {
		SimpleDateFormat formatDMY = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		int dvdID = 0;
		Connection con = DBHelper.getConnection();

		Resource popDVD = null;
		if (tab != null) {
			if (tab.equals(dayTab)) {
				desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 00:01");
				desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 23:59");

				String getDVDs = "SELECT * FROM  requestsToApprove, resource,DVD"
						+ "WHERE requestsToApprove.rID=resource.rID AND DVD.rID=resource.rID"
						+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
						+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

				PreparedStatement pstmt = con.prepareStatement(getDVDs);
				ResultSet dvdSet = pstmt.executeQuery();

				while (dvdSet.next()) {
					dvdID = dvdSet.getInt("rID");
					popDVD = Resource.getResource(dvdID);
				}
			} else if (tab.equals(weekTab)) {
				Calendar now = Calendar.getInstance();

				List<Integer> popDVDs = new ArrayList<Integer>();

				// getting the dates of the days that are in the current week
				int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
				now.add(Calendar.DAY_OF_MONTH, delta - 7);
				for (int i = 0; i < 7; i++) {
					daysOfTheWeek[i] = formatDMY.format(now.getTime());
					now.add(Calendar.DAY_OF_MONTH, 1);
				}

				// looping though the days
				for (int i = 0; i < 7; i++) {
					String day = daysOfTheWeek[i];

					try {
						desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 00:01");
						desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 23:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}

					String getDVDs = "SELECT * FROM  requestsToApprove, resource,DVD"
							+ "WHERE requestsToApprove.rID=resource.rID AND DVD.rID=resource.rID"
							+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
							+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

					PreparedStatement pstmt = con.prepareStatement(getDVDs);
					ResultSet dvdSet = pstmt.executeQuery();

					while (dvdSet.next()) {
						dvdID = dvdSet.getInt("rID");
						popDVDs.add(dvdID);
					}
				}
				int mostFreq = getMostOccoringElement(popDVDs);
				popDVD = Resource.getResource(mostFreq);
			} else {
				String getDVDs = "SELECT * FROM  requestsToApprove, resource,DVD"
						+ "WHERE requestsToApprove.rID=resource.rID AND DVD.rID=resource.rID"
						+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

				PreparedStatement pstmt = con.prepareStatement(getDVDs);
				ResultSet dvdSet = pstmt.executeQuery();

				while (dvdSet.next()) {
					dvdID = dvdSet.getInt("rID");

				}
			}

			popDVD = Resource.getResource(dvdID);
		}
		con.close();
		return popDVD;

	}

	public Resource getMostPopularLaptop(Tab tab) throws SQLException, ParseException {
		SimpleDateFormat formatDMY = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		int laptopID = 0;
		Connection con = DBHelper.getConnection();

		Resource popLaptop = null;

		if (tab != null) {
			if (tab.equals(dayTab)) {
				desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 00:01");
				desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 23:59");

				String getLaptops = "SELECT * FROM  requestsToApprove, resource,Laptop"
						+ "WHERE requestsToApprove.rID=resource.rID AND Laptop.rID=resource.rID"
						+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
						+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

				PreparedStatement pstmt = con.prepareStatement(getLaptops);
				ResultSet laptopSet = pstmt.executeQuery();

				while (laptopSet.next()) {
					laptopID = laptopSet.getInt("rID");
					popLaptop = Resource.getResource(laptopID);
				}
			} else if (tab.equals(weekTab)) {
				Calendar now = Calendar.getInstance();

				List<Integer> popLaptops = new ArrayList<Integer>();

				// getting the dates of the days that are in the current week
				int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
				now.add(Calendar.DAY_OF_MONTH, delta - 7);
				for (int i = 0; i < 7; i++) {
					daysOfTheWeek[i] = formatDMY.format(now.getTime());
					now.add(Calendar.DAY_OF_MONTH, 1);
				}

				// looping though the days
				for (int i = 0; i < 7; i++) {
					String day = daysOfTheWeek[i];

					try {
						desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 00:01");
						desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 23:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}

					String getLaptops = "SELECT * FROM  requestsToApprove, resource,Laptop"
							+ "WHERE requestsToApprove.rID=resource.rID AND Laptop.rID=resource.rID"
							+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
							+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

					PreparedStatement pstmt = con.prepareStatement(getLaptops);
					ResultSet laptopSet = pstmt.executeQuery();

					while (laptopSet.next()) {
						laptopID = laptopSet.getInt("rID");
						popLaptops.add(laptopID);
					}
				}
				int mostFreq = getMostOccoringElement(popLaptops);
				popLaptop = Resource.getResource(mostFreq);
			}
		} else {
			String getDVDs = "SELECT * FROM  requestsToApprove, resource,Laptop"
					+ "WHERE requestsToApprove.rID=resource.rID AND Laptop.rID=resource.rID"
					+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

			PreparedStatement pstmt = con.prepareStatement(getDVDs);
			ResultSet laptopSet = pstmt.executeQuery();

			while (laptopSet.next()) {
				laptopID = laptopSet.getInt("rID");

			}
			popLaptop = Resource.getResource(laptopID);
		}
		con.close();
		return popLaptop;

	}

	public Resource getMostPopularGame(Tab tab) throws SQLException, ParseException {
		SimpleDateFormat formatDMY = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		int GameID = 0;
		Connection con = DBHelper.getConnection();

		Resource popGame = null;

		if (tab != null) {
			if (tab.equals(dayTab)) {
				desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 00:01");
				desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(today + " 23:59");

				String getGames = "SELECT * FROM  requestsToApprove, resource,Game"
						+ "WHERE requestsToApprove.rID=resource.rID AND Game.rID=resource.rID"
						+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
						+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

				PreparedStatement pstmt = con.prepareStatement(getGames);
				ResultSet GameSet = pstmt.executeQuery();

				while (GameSet.next()) {
					GameID = GameSet.getInt("rID");
					popGame = Resource.getResource(GameID);
				}
			} else if (tab.equals(weekTab)) {
				Calendar now = Calendar.getInstance();

				List<Integer> popGames = new ArrayList<Integer>();

				// getting the dates of the days that are in the current week
				int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
				now.add(Calendar.DAY_OF_MONTH, delta - 7);
				for (int i = 0; i < 7; i++) {
					daysOfTheWeek[i] = formatDMY.format(now.getTime());
					now.add(Calendar.DAY_OF_MONTH, 1);
				}

				// looping though the days
				for (int i = 0; i < 7; i++) {
					String day = daysOfTheWeek[i];

					try {
						desiredDate1 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 00:01");
						desiredDate2 = new SimpleDateFormat(FORMAT_YMDHMS).parse(day + " 23:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}

					String getGames = "SELECT * FROM  requestsToApprove, resource,Game"
							+ "WHERE requestsToApprove.rID=resource.rID AND Game.rID=resource.rID"
							+ "AND requestsToApprove.timestamp BETWEEN" + desiredDate1 + desiredDate2
							+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

					PreparedStatement pstmt = con.prepareStatement(getGames);
					ResultSet GameSet = pstmt.executeQuery();

					while (GameSet.next()) {
						GameID = GameSet.getInt("rID");
						popGames.add(GameID);
					}
				}
				int mostFreq = getMostOccoringElement(popGames);
				popGame = Resource.getResource(mostFreq);
			}
		} else {
			String getGames = "SELECT * FROM  requestsToApprove, resource,Game"
					+ "WHERE requestsToApprove.rID=resource.rID AND Game.rID=resource.rID"
					+ "GROUP BY rID ORDER BY COUNT (rID) DESC LIMIT 1";

			PreparedStatement pstmt = con.prepareStatement(getGames);
			ResultSet GameSet = pstmt.executeQuery();

			while (GameSet.next()) {
				GameID = GameSet.getInt("rID");

			}
			popGame = Resource.getResource(GameID);
		}
		con.close();
		return popGame;

	}

	// loop through list and get element that appears most
	public static <T> T getMostOccoringElement(List<T> list) {
		int size = list.size();
		if (size == 0)
			return null;

		int count = 0;
		int maxCount = 0;
		T element = list.get(0);
		T mostOccuringElement = element;

		for (int index = 0; index < size; index++) {
			if (list.get(index).equals(element)) {
				count++;
				if (count > maxCount) {
					maxCount = count;
					mostOccuringElement = element;
				}
			} else {
				count = 1;
			}
			element = list.get(index);
		}
		return mostOccuringElement;
	}
	
	/**
     * Loads resource image from Resource class, so that they can be displayed
     * within the UI.
   
    private void loadResourceImage() {
        // create new resource image to be added.
        resourceimage.setFitWidth(RES_IMG_WIDTH);
        resourceimage.setFitHeight(RES_IMG_HEIGHT);
        resourceimage.setImage(ScreenManager.currentResource.getThumbnail());
    }  */
}
