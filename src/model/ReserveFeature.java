package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ReserveFeature {
	
	

	public static int getFreeCopy(int rId,LocalDate date) {
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

public static void setReserve(int copyId) {
	//Copy returned, and someone is waiting for it.
	try {
		 Connection connection = DBHelper.getConnection();

		 
		 PreparedStatement statement = connection.prepareStatement("SELECT * FROM reserve"
	   		+ " WHERE copyId=?");
	       statement.setInt(1, copyId);
	       ResultSet r = statement.executeQuery();
	       statement = connection.prepareStatement("UPDATE copies "
	   	   	   		+ "SET keeper=? AND dueDate=? where copyID=?");
	   	   	       statement.setInt(3, copyId);
	   	   	       int due = getDue(copyId);
	   	   	       if(due==0) {
	   	   	    	   statement.setNull(2, Types.INTEGER);
	   	   	       }else {
	   	   	    	   statement.setInt(2, due);
	   	   	       }
	   	   	       statement.setString(1 ,r.getString("username"));
	   	   	       statement.execute();
	       
	       connection.close();
	       
	       
	}catch(SQLException e) {
		e.printStackTrace();
		
	}
}
	
private static int getDue(int copyId) {
	int due = 0;
	try {
		 Connection connection = DBHelper.getConnection();


		 PreparedStatement statement = connection.prepareStatement("SELECT copies.loanDuration  FROM copies,resource,userRequests WHERE "
		 		+ "userRequests.rID=resource.rID AND copies.rID=resource.rID AND copies.copyID = ?" 
		 		);
	       statement.setInt(1, copyId);

	       ResultSet results = statement.executeQuery();
	       if(results.next()) {
	    	
	    	   due = results.getInt("due");
	       connection.close();
	       }



	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return due;
	}

	
	public static boolean checkReserved(int copyId, int duration, LocalDate date) {
		boolean free = true;
		try {
			 Connection connection = DBHelper.getConnection();


			 PreparedStatement statement = connection.prepareStatement("SELECT * FROM reserve"
  	   		+ " WHERE copyId=?");
  	       statement.setInt(1, copyId);

  	       ResultSet results = statement.executeQuery();
  	       while(results.next()) {
  	    	 String dateDB = results.getString("when");
  	

  	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

  			
  			//convert String to LocalDate
  			LocalDate dbDate = LocalDate.parse(dateDB, formatter);
  	    	 
    		
    		   System.out.println("Check res. "+ChronoUnit.DAYS.between(dbDate, date)+" with duration of "+duration);
    		   if(date.isAfter(dbDate)) {
    			   if(ChronoUnit.DAYS.between(dbDate, date) < duration) {
    				   free = false;
    			   }
    		   }else {
    			   if(ChronoUnit.DAYS.between(date, dbDate) < 2) {
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


	
	
	
	public static boolean reserve(int rId, String username, LocalDate selectedDate) {
		Instant instant = Instant.from(selectedDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateFull = Date.from(instant);
		  SimpleDateFormat normal = new SimpleDateFormat("dd/MM/yyyy");
	       String date = normal.format(dateFull);

		System.out.println("Converted date is "+date);

		int copy = getFreeCopy(rId,selectedDate);
		if(copy==0) {
			return false;
		}
		try {
			 Connection connection = DBHelper.getConnection();


			 PreparedStatement statement = connection.prepareStatement("INSERT INTO reserve("
   	   		+ "copyId,username,'when') VALUES(?,?,?)");
   	       statement.setInt(1, copy);
   	       statement.setString(2, username );
   	       statement.setString(3,date);
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
	   	   	       if(r.getString("dueDate")==null) {
   	       
   	    statement = connection.prepareStatement("UPDATE copies "
   	   	   		+ "SET dueDate=? where copyID=?");
   	   	       statement.setInt(2, copy);
   	   	       statement.setString(1,date);
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
