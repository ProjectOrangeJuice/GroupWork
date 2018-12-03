package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class transactionsTester {

	static Transactions t;
	public static void main(String args[]) {
		ArrayList<Payment> p = new ArrayList();
		DBHelper.forceUpdate();
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			ResultSet rs = stmt.executeQuery("SELECT * FROM transactions WHERE username=1"); //Your sql goes here
			while(rs.next()) {
				int a = rs.getInt("transactionId");
				String b = rs.getString("username");
				float c = rs.getFloat("paid");
				String d = rs.getString("dateTime");
				System.out.println(a + b + c + d);

				p.add(new Payment(a,b,c,d));

			}

			t = new Transactions(1,p);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		t.addPayment(new Payment(3,"1",400,"bla"));

		System.out.println("Over");



		try {
			Connection conn = DBHelper.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("insert into dvd(director,runtime,language,rId) values ('bob',200,'english',77);"); // "?" is a placeholder
			pstmt.executeUpdate();//This can return a value to tell you if it was successful.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
