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


  private static int VERSION = 1; //Version number for database
  private static String LINK = "jdbc:sqlite:test.db"; //database connection string
  private static String SQL = "src/tables.sql"; //database connection string

  /**
  * Create the an empty database
  *
  * @param fileName the database file name
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

  private static void createTables(){

    try {
      InputStream in = new FileInputStream("src/tables.sql");

      Scanner s = new Scanner(in);
      s.useDelimiter(";");
      Statement st = null;


      Connection conn = DriverManager.getConnection(LINK);
      st = conn.createStatement();
      while (s.hasNext())
      {
        String line = s.next();
        if (line.startsWith("/*!") && line.endsWith("*/"))
        {
          int i = line.indexOf(' ');
          line = line.substring(i + 1, line.length() - " */".length());
        }

        if (line.trim().length() > 0)
        {
          st.execute(line);
        }
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();

    } catch (FileNotFoundException e) {// TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally
    {

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
