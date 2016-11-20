import java.sql.*;
import java.util.ArrayList;

public class TreatmentQueryProcessor extends QueryProcessor{
	public TreatmentQueryProcessor() {super();}
	
<<<<<<< HEAD
	public ArrayList<Treatment> getUnpaidTreatments(int id) {
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT op.name,tm.cost FROM Appointment ap, Operation op, Treatment tm WHERE " 
=======
	public boolean hasUnpaidAppointments(int id) {
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM Appointment WHERE (Cost>0) AND Patient_ID = " + id);
			res.next();
			return (res.getInt(1)>0) ? true : false;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Treatment> getUnpaidTreatments(int id) {
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT op.name,tm.cost,tm.cosmetic FROM Appointment ap, Operation op, Treatment tm WHERE " 
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
						   +"(ap.Partner = op.Partner AND ap.Date = op.Date AND ap.Start_Time = op.Start_Time) " 
						   +"AND op.name = tm.name AND ap.Patient_ID = " + id + " AND (ap.cost > 0)";
			
			ResultSet res = stmt.executeQuery(query);
			
			ArrayList<Treatment> treatments = new ArrayList<Treatment>();
			while (res.next()) {
<<<<<<< HEAD
				treatments.add(new Treatment(res.getString(1),res.getInt(2)));
=======
				treatments.add(new Treatment(res.getString(1),res.getInt(2),res.getBoolean(3)));
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
			}
			return treatments;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ReviewTreatmentsFrame.HealthCarePlan getHealthCarePlan(int id) {
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT (Covered_checkups-Prepaid_Checkups) AS remC, (Covered_Hygiene-Prepaid_Hygiene) AS remH,"
			+"(Covered_Repairs-Prepaid_Repairs) AS remR  FROM Healthcare_Plan hcp, HC_Plan_Type hct WHERE hcp.Patient_ID = "+id
			+" AND hcp.Name = hct.Name";
			
			ResultSet res = stmt.executeQuery(query);
			
<<<<<<< HEAD
			//System.out.println(res.getInt(1)+ res.getInt(2)+ res.getInt(3));
			
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
			return (res.next()) ?  new ReviewTreatmentsFrame.HealthCarePlan(res.getInt(1), res.getInt(2), res.getInt(3)) : null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateHealthCarePlan(int id, int prepaidC,int prepaidH, int prepaidR) {
		try {
			Statement stmt = con.createStatement();
			String query = "UPDATE Healthcare_Plan SET Prepaid_Checkups=Prepaid_Checkups+" + prepaidC + ", Prepaid_Hygiene = Prepaid_Hygiene+"
					+ prepaidH +",Prepaid_Repairs = Prepaid_Repairs+"+prepaidR+" WHERE Patient_ID= " + id;
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void recordPayment(int id) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE Appointment SET Cost = 0 WHERE Patient_ID  =" +id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		TreatmentQueryProcessor qp = new TreatmentQueryProcessor();
<<<<<<< HEAD
		//qp.getHealthCarePlan(1);
		qp.updateHealthCarePlan(1, 1, 1, 1);
=======
		qp.getUnpaidTreatments(2);
		//qp.getHealthCarePlan(1);
		//qp.updateHealthCarePlan(1, 1, 1, 1);
		
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	}
}
