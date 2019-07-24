package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * New reserve feature. Handles reservations.
 * 
 * @author Oliver Harris.
 */
public class ReserveFeature {

	/** The Constant RESERVE_FINE. */
	private static final double RESERVE_FINE = 5;

	/**
	 * Gets the reserves.
	 *
	 * @return the reserves.
	 */
	public static ArrayList<Reserve> getReserves() {
		ArrayList<Reserve> r = new ArrayList<Reserve>();
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM reserve,copies,resource WHERE "
					+ "copies.rID= resource.rID AND reserve.copyId=copies.copyID");

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				r.add(new Reserve(results.getString("username"), 
						results.getString("title"), results.getString("due"),
						results.getInt("copyId"), results.getInt("rID"),
						results.getInt("id")));

			}

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * Checks to see if the user has already reserved a resource.
	 * 
	 * @param rId      The resource id.
	 * @param username The username.
	 * @return Date they have reserved, or empty if they haven't
	 */
	public static String canReserve(int rId, String username) {
		String date = "";
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM reserve,copies " 
			+ "WHERE reserve.copyId=copies.copyID AND  copies.rID=?"
			+ " AND username=?");
			statement.setInt(1, rId);
			statement.setString(2,username);

			ResultSet results = statement.executeQuery();
			if (results.next()) {

				date = results.getString("due");

			}

			connection.close();
			return date;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Gets the free copy.
	 *
	 * @param rId  the resource id.
	 * @param date the date it is required.
	 * @return the free copy.
	 */
	public static int getFreeCopy(int rId, LocalDate date) {
		int free = 0;
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM copies" 
			+ " WHERE rID=? AND holdBack='no'");
			statement.setInt(1, rId);

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				if (results.getString("borrowDate") == null) {

					free = results.getInt("copyID");
				} else {
					String dateDB = results.getString("dueDate");
					if (dateDB != null) {
						
						
						DateTimeFormatter formatter = 
								DateTimeFormatter.ofPattern("d/MM/yyyy",
										Locale.ENGLISH);
						LocalDate dbDate = LocalDate.parse(dateDB, formatter);
						if (date.isAfter(dbDate)) {

							free = results.getInt("copyID");
						}
					}

					if (free == 0) {

						int duration = results.getInt("loanDuration");
						LocalDate today = LocalDate.now();
						if (ChronoUnit.DAYS.between(today, date) > duration) {

							free = results.getInt("copyID");
						}

					}
				}
				if (free != 0) {
					if (!checkReserved(free, results.getInt("loanDuration"),
							date)) {
						free = 0;
					} else {
						connection.close();
						return free;
					}
				}

			}

			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return free;
	}

	/**
	 * Reserve a copy.
	 *
	 * @param copyId the copy to reserve.
	 */
	public static void setReserve(int copyId) {

		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM reserve WHERE copyId=?");
			statement.setInt(1, copyId);
			ResultSet r = statement.executeQuery();
			statement = connection.prepareStatement("UPDATE copies " 
			+ "SET keeper=? AND dueDate=? where copyID=?");
			statement.setInt(3, copyId);
			int due = getDue(copyId);
			if (due == 0) {
				statement.setNull(2, Types.INTEGER);
			} else {
				statement.setInt(2, due);
			}
			statement.setString(1, r.getString("username"));
			statement.execute();

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Gets the amount of days somebody can loan a copy.
	 *
	 * @param copyId the copy id.
	 * @return the number of days the loan is.
	 */
	private static int getDue(int copyId) {
		int due = 0;
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("SELECT copies.loanDuration  "
							+ "FROM copies,resource,userRequests WHERE "
							+ "userRequests.rID=resource.rID AND "
							+ "copies.rID=resource.rID AND copies.copyID = ?");
			statement.setInt(1, copyId);

			ResultSet results = statement.executeQuery();
			if (results.next()) {

				due = results.getInt("due");
				connection.close();
			}
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return due;
	}

	/**
	 * Check for late collections and fine them.
	 */
	public static void checkForLate() {
		try {
			Connection connection = DBHelper.getConnection();

			Instant instant = Instant.from(LocalDate.now().atStartOfDay(
					ZoneId.systemDefault()));
			Date dateFull = Date.from(instant);
			SimpleDateFormat normal = new SimpleDateFormat("dd/MM/yyyy");
			String date = normal.format(dateFull);

			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM reserve,copies" 
			+ " WHERE copies.copyId=reserve.copyId AND 'due' < ?");
			statement.setString(1, date);

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String username = results.getString("reserve.username");
				statement = connection.prepareStatement(
						"INSERT INTO fines "
						+ "(userName,rID,daysOver,amount,dateTime,"
								+ "paid) VALUES (?,?,?,?,?,0)");

				statement.setString(1, username);
				statement.setInt(2, results.getInt("copies.rID"));
				statement.setInt(3, 1);
				statement.setDouble(4, RESERVE_FINE);

				SimpleDateFormat normalDateFormat = 
						new SimpleDateFormat("dd/MM/yyyy");
				statement.setString(5, date);

				statement.executeUpdate();

				statement = connection.prepareStatement("DELETE FROM "
						+ "reserve WHERE id=?");

				statement.setInt(1, results.getInt("reserve.id"));

				statement.executeUpdate();

			}

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Check reserved.
	 *
	 * @param copyId   the copy id
	 * @param duration the duration
	 * @param date     the date
	 * @return true, if successful
	 */
	public static boolean checkReserved(int copyId, int duration, LocalDate date) {
		boolean free = true;
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection.prepareStatement("SELECT * "
					+ "FROM reserve WHERE copyId=?");
			statement.setInt(1, copyId);

			ResultSet results = statement.executeQuery();
			while (results.next()) {
				String dateDB = results.getString("due");

				DateTimeFormatter formatter = 
						DateTimeFormatter.ofPattern("d/MM/yyyy");

				// convert String to LocalDate
				LocalDate dbDate = LocalDate.parse(dateDB, formatter);

				if (date.isAfter(dbDate)) {
					if (ChronoUnit.DAYS.between(dbDate, date) < duration) {
						free = false;
					}
				} else {
					if (ChronoUnit.DAYS.between(date, dbDate) < 2) {
						free = false;
					}
				}

			}

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return free;
	}

	/**
	 * Reserve a resource.
	 *
	 * @param rId          the resource id.
	 * @param username     the username.
	 * @param selectedDate the selected date.
	 * @return true, if successful.
	 */
	public static boolean reserve(int rId, String username, 
			LocalDate selectedDate) {
		Instant instant = Instant.from(selectedDate.atStartOfDay(
				ZoneId.systemDefault()));
		Date dateFull = Date.from(instant);
		SimpleDateFormat normal = new SimpleDateFormat("dd/MM/yyyy");
		String date = normal.format(dateFull);

		int copy = getFreeCopy(rId, selectedDate);
		if (copy == 0) {
			return false;
		}
		try {
			Connection connection = DBHelper.getConnection();

			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO reserve(" 
			+ "copyId,username,due) VALUES(?,?,?)");
			statement.setInt(1, copy);
			statement.setString(2, username);
			statement.setString(3, date);
			statement.execute();
			LocalDate date2 = selectedDate.minusDays(2);
			instant = Instant.from(date2.atStartOfDay(ZoneId.systemDefault()));
			dateFull = Date.from(instant);
			normal = new SimpleDateFormat("dd/MM/yyyy");
			date = normal.format(dateFull);

			statement = connection.prepareStatement("SELECT * " 
			+ "FROM copies WHERE copyID=?");
			statement.setInt(1, copy);

			ResultSet r = statement.executeQuery();
			if (r.getString("dueDate") == null) {

				statement = connection.prepareStatement("UPDATE copies " 
				+ "SET dueDate=? where copyID=?");
				statement.setInt(2, copy);
				statement.setString(1, date);
				statement.execute();
			}

			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
}
