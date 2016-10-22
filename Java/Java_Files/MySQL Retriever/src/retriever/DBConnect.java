package retriever;
import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ArrayList<Transient> transients = new ArrayList<Transient>();
	
	public DBConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/odyssey_test_database?autoReconnect=true&useSSL=false", "root", "nascar09");
			st = con.createStatement();
			
			
		}catch(Exception e){
			System.out.println("Error: " + e);
		}
	}

	public void getData(){
		try{
			String query = "SELECT * FROM transient_information";
			rs = st.executeQuery(query);
			System.out.println("Records from database:");
			while (rs.next()){
				String id = rs.getString("id");
				Double ra = rs.getDouble("ra");
				Double dec = rs.getDouble("declination");
				Double ut = rs.getDouble("ut_date");
				Double mag = rs.getDouble("mag");
				String last = rs.getString("last_time");
				String light = rs.getString("light_curve");
				
				Transient tran = new Transient(id, ra, dec, ut, mag, last, light);
				transients.add(tran);
			}
			
		}catch(Exception e){
			System.out.println("Error: " + e);
		}
	}
}
