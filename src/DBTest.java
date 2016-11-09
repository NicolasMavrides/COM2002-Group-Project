import java.sql.*;

public class DBTest {
	public static void main(String[] args) throws SQLException {
		Connection con = null; 
		try { 
		 
		 con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "75780669");
		 Statement stmt = con.createStatement();
		 
		}
		catch (SQLException ex) {
		 ex.printStackTrace();
		}
		finally {
		 if (con != null) 
			 con.close();
		 System.out.println("yay");
		}
		
	}
}
