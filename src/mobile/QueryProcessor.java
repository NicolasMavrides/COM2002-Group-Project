package mobile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryProcessor {
	protected Connection con;
	public QueryProcessor() {
		con = null; 
		try {
			con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "75780669"); 
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean memberExists(int id) {

		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM Patient WHERE Patient_ID = "+ id);
			res.next();
			if (res.getInt(1)!=0) {
				return true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return false;
		
	}
	
	public String getPatientFullName(int id) {
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT Title,Forename,Surname FROM Patient WHERE Patient_ID = "+ id);
			res.next();
			return res.getString(1) + " " +res.getString(2)+ " " + res.getString(3);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
