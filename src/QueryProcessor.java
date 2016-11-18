import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
