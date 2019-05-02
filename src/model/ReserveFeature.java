package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class ReserveFeature {



	private static int getFreeCopy(int rId,LocalDate date) {
		int free = 0;
		try {
			 Connection connection = DBHelper.getConnection();


			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM copies"
  	   		+ " WHERE rID=?");
  	       statement.setInt(1, rId);

  	       ResultSet results = statement.executeQuery();
  	       while(results.next()) {
  	    	   if(results.getString("borrowDate") == null) {
  	    		   System.out.println("Contains a free copy");

  	    		   free = results.getInt("copyID");
  	    	   }else {
  	    		   String dateDB = results.getString("dueDate");
  	    		   if(dateDB !=null) {
  	    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH);
  	    		   LocalDate dbDate = LocalDate.parse(dateDB, formatter);
  	    		   if(date.isAfter(dbDate)) {
  	    			   System.out.println("Due date is after!");
  	    			   free = results.getInt("copyID");
  	    		   }
  	    		   }

  	    		   if(free == 0) {



  	    			   int duration = results.getInt("loanDuration");
  	    			 LocalDate today = LocalDate.now();
  	    			 if(ChronoUnit.DAYS.between(today, date) > duration) {
  	    				 System.out.println("Copy can be free");
  	    				 free = results.getInt("copyID");
  	    			 }

  	    		   }
  	    	   }
  	    	   if(free != 0) {
  	    		   if(!checkReserved(free,results.getInt("loanDuration"),date)) {
  	    			   free = 0;
  	    		   }else {
  	    			   connection.close();
  	    			   return free;
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


	public static boolean checkReserved(int copyId, int duration, LocalDate date) {
		boolean free = false;
		try {
			 Connection connection = DBHelper.getConnection();


			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM reserve"
  	   		+ " WHERE copyId=?");
  	       statement.setInt(1, copyId);

  	       ResultSet results = statement.executeQuery();
  	       while(results.next()) {
  	    	 String dateDB = results.getString("when");

    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy", Locale.ENGLISH);
    		   LocalDate dbDate = LocalDate.parse(dateDB, formatter);

    		   if(date.isAfter(dbDate)) {
    			   if(ChronoUnit.DAYS.between(dbDate, date) > duration) {
    				   free = true;
    			   }
    		   }else {
    			   if(ChronoUnit.DAYS.between(date, dbDate) > duration) {
    				   free = true;
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


	public static void reserve(int rId, String username, LocalDate selectedDate) {
		Instant instant = Instant.from(selectedDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateFull = Date.from(instant);
		  SimpleDateFormat normal = new SimpleDateFormat("dd/MM/yyyy");
	       String date = normal.format(dateFull);

		System.out.println("Converted date is "+date);

		System.out.println(getFreeCopy(rId,selectedDate));
		try {
			 Connection connection = DBHelper.getConnection();


			 PreparedStatement statement = connection.prepareStatement("INSERT INTO reserver("
   	   		+ "copyId,username,when) VALUES(?,?,?)");
   	       statement.setInt(1, 1);
   	       statement.setString(2, username );
   	       statement.setString(3,date);
   	       statement.execute();
   	       connection.close();



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
