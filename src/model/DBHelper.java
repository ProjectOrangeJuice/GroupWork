package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
/**
* Contains helper methods for the database.
* @author Oliver Harris
*
*/
public class DBHelper {

	private static int VERSION = 8; // Version number for database
	private static String LINK = "jdbc:sqlite:test.db"; // database connection string
	private static String SQL = "src/tables.sql"; // database connection string


	/** 
	* Execute a simple SQL command.
	* @param sql The sql to execute.
	* @return ResultSet The results from the database.
	* @throws SQLException. Unable to execute the query given.
	*/
	private static ResultSet selectKnown(String sql) throws SQLException{

		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		return rs;
	}

	/** 
	* Get the connection for the database.
	* @param keyCheck. True if the database should check foreign keys.
	* @return Connection The connection to database.
	* @throws SQLException. Unable to connect to database.
	*/
	public static Connection getConnection(boolean keyCheck) throws SQLException{
		// SQLite connection string
		Connection conn = null;

		conn = DriverManager.getConnection(LINK); //no foreign key checks
		if(keyCheck) {
			conn.createStatement().execute("PRAGMA foreign_keys = ON");
		}
		return conn;
	}
	
	/**
	 * Get the connection for the database
	 * @return Connection. The connection with foreign key enabled.
	 * @throws SQLException. Unable to connect to database.
	 */
	public static Connection getConnection() throws SQLException{
		return getConnection(true);
	}

	/**
	* Create the tables in the database using the SQL file.
	*/
	private static void createTables() {

		try {
			InputStream in = new FileInputStream(SQL);// Opens up the file

			Scanner s = new Scanner(in);
			s.useDelimiter(";");// Each statement is split with ";"
			Statement stmt = null;

			Connection conn = getConnection(false); // Get the connection without foreign key checks
			stmt = conn.createStatement();
			while (s.hasNext()) {
				String line = s.next();

				if (line.trim().length() > 0) {
					try {
						stmt.execute(line);
					}
					catch(SQLException e){ //Error on the SQL table.
						System.out.println(line);
						e.printStackTrace();//The tables.sql is incorrect
					}
				}
			}
		} catch (SQLException e) {
			// Error in the tables.sql
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			// File not found
			e.printStackTrace();
		} 

	}

	/**
	* Checks if the database is the correct version.
	*/
	public static void tableCheck() {
		try {
			ResultSet rs = selectKnown("SELECT ver FROM system");
			if(rs.next()) {
				int ver = rs.getInt("ver");
				rs.close();
				System.out.println("Table is at "+ver+" program is at "+VERSION);
				if(ver != VERSION) {
					createTables();
					System.out.println("Updating database");
				}
			}else {
				createTables();
				System.out.println("Creating database");
			}
		} catch (SQLException e) {
			System.out.println("Error : "+e.getMessage());
			createTables();
			System.out.println("Creating database");
		}
	}

	
}
