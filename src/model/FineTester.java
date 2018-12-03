package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FineTester {

	
	public static void main(String args[]) {
		ArrayList<Fine> fi = new ArrayList();
		DBHelper.forceUpdate();
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM fines WHERE username=1"); //Your sql goes here
			while(rs.next()) {
				int a = rs.getInt("fineID");
				int b = rs.getInt("username");
				int c = rs.getInt("copyID");
				int c2 = rs.getInt("daysOver");
				float d = rs.getFloat("amount");
				String e = rs.getString("dateTime");
				int f  = rs.getInt("paid");
				boolean f1;
				if(f == 1) {
					f1 = true;
				}else {  f1 = false;}
				fi.add(new Fine(d,e,b,c,c2,a,f1));
				
			} 
			
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		
		fi.get(0).setPaid(false);
	
		
		System.out.println("Over");
	}
	
}
