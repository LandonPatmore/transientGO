package com.google.android.stardroid.retriever;
import java.sql.*;
import java.util.ArrayList;
/**
 * 
 * @author landon
 * This class connects to the specified MySQL database and pulls all of the specified data into
 * objects and then puts them inside an array to then be further manipulated by other classes.
 *
 */

public class DBConnect {
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private ArrayList<SQLTransient> transients = new ArrayList<SQLTransient>();
	
	public DBConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success");
			
			con = DriverManager.getConnection("jdbc:mysql://pi.cs.oswego.edu:3306/TransientGoTeam10?autoReconnect=true&useSSL=false", "team10", "csc380");
			st = con.createStatement();
            System.out.println("GETTING DATA DEBUG");
			
			
		}catch(Exception e){
			System.out.println("Error: " + e);
		}
	}

	public void getData(){
		try{
			String query = "SELECT * FROM transients";
			rs = st.executeQuery(query);
			System.out.println("Records from database:");
			while (rs.next()){
				String author = rs.getString("author");
				String transientId = rs.getString("transientId");
				String dateAlerted = rs.getString("dateAlerted");
				String datePublished = rs.getString("datePublished");
				Double right_asencsion = rs.getDouble("right_asencsion");
				Double declination = rs.getDouble("declination");
				
				SQLTransient tran = new SQLTransient(author, transientId, dateAlerted, datePublished, right_asencsion, declination);
				transients.add(tran);
			}
			
		}catch(Exception e){
			System.out.println("Error: " + e);
		}
	}

	public ArrayList<SQLTransient> getTransients(){
		return transients;
	}
}
