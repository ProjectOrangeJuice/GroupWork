import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	 * Run an sql command and return a resultset
	 *
	 */
	public static ResultSet select(String sql) throws SQLException{
	
			
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
		
			return rs;
			
		
	}
	
	/**
	 * Create a connection to the database
	 * If the database doesn't exist, Create it.
	 *
	 */
	private static Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(LINK);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
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

			Connection conn = connect(); // Opens the database
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
	 * Check to see if the database has tables
	 * Or what version it is
	 *
	 */
	public static void tableCheck() {
		try {
		ResultSet rs = select("SELECT ver FROM system");
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

	/**
	 * tester
	 */
	public static void main(String[] args) {
		tableCheck();
	}
}
