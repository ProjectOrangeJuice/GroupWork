import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
/**
*
* @author Oliver Harris
*
*/
public class DBHelper {

	private static int VERSION = 1; // Version number for database
	private static String LINK = "jdbc:sqlite:test.db"; // database connection string
	private static String SQL = "src/tables.sql"; // database connection string

	/**
	 * Create the an empty database
	 *
	 */
	private static void createNewDatabase() {

		try (Connection conn = DriverManager.getConnection(LINK)) {
			if (conn != null) {
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Create the tables in the database
	 *
	 */
	private static void createTables() {

		try {
			InputStream in = new FileInputStream(SQL);// Opens up the file

			Scanner s = new Scanner(in);
			s.useDelimiter(";");// Each statement is split with ";"
			Statement st = null;

			Connection conn = DriverManager.getConnection(LINK); // Opens the database
			st = conn.createStatement();
			while (s.hasNext()) {
				String line = s.next();

				if (line.trim().length() > 0) {
					st.execute(line);
				}
			}
		} catch (SQLException e) {
			// Error in the text file
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			// File not found
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * tester
	 */
	public static void main(String[] args) {
		createNewDatabase();
		createTables();
	}
}
