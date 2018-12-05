package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**This class represents a fine. Holding all the information about the fine
 * @author Oliver Harris
 *
 */
public class Fine {

	private float amount;
	private String stamp;
	private int resource;
	private int daysOver;
	private int fineId;
	private String username;
	private boolean paid;


	/**
	 * @param amount
	 * @param stamp timestamp
	 * @param user who owns this fine
	 * @param resource the resource that caused this fine
	 * @param daysOver days over due
	 * @param fineId
	 * @param paid
	 */
	public Fine(float amount, String stamp,String user, int resource, int daysOver, int fineId, boolean paid ) {
		this.amount = amount;
		this.username = user;
		this.stamp = stamp;
		this.resource = resource;
		this.daysOver = daysOver;
		this.fineId = fineId;
		this.paid = paid;
	}


	public boolean isPaid() {
		return paid;
	}


	public void setPaid(boolean paid) {
		this.paid = paid;
		try {
			Connection conn = DBHelper.getConnection();
			int paidInt = paid ? 1 : 0;
			PreparedStatement pstmt = conn.prepareStatement("UPDATE fines set paid = ? WHERE fineId = ?");
			pstmt.setInt(1, paidInt); 
			pstmt.setInt(2, fineId); 
			int updates = pstmt.executeUpdate();
			if(updates != 1) {
				System.out.println("Updating of paid failed. Either too many or none updated (It updated "+updates+")");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public float getAmount() {
		return amount;
	}


	public String getStamp() {
		return stamp;
	}


	public int getResource() {
		return resource;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getResourceName() {
		return Resource.getResource(resource).getTitle();

	}


	public int getDaysOver() {
		return daysOver;
	}


	public int getFineId() {
		return fineId;
	}

	public static ArrayList<Fine> getFines(String username) {
		ArrayList<Fine> fi = new ArrayList<Fine>();
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM fines WHERE username=?");
			pstmt.setString(1,username);
			ResultSet rs = pstmt.executeQuery(); //Your sql goes here //Your sql goes here
			while(rs.next()) {
				int a = rs.getInt("fineID");
				String b = rs.getString("username");
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
			return fi;
			
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static ArrayList<Fine> getFines() {
		ArrayList<Fine> fi = new ArrayList<Fine>();
		try {
			Connection conn = DBHelper.getConnection(); //get the connection
			Statement stmt = conn.createStatement(); //prep a statement
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM fines");
			ResultSet rs = pstmt.executeQuery(); //Your sql goes here //Your sql goes here
			while(rs.next()) {
				int a = rs.getInt("fineID");
				String b = rs.getString("username");
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
			return fi;
			
			
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return null;
	}


	public boolean contains(String search) {
		if(this.getStamp().contains(search)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean containsUser(String search) {
		if(this.getUsername().contains(search)) {
			return true;
		}else {
			return false;
		}
	}

}
