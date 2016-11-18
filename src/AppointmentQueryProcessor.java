import java.sql.*;
import java.util.ArrayList;

public class AppointmentQueryProcessor extends QueryProcessor {
	public AppointmentQueryProcessor() { super(); }
	
	public void bookAppointment(Partner p, String date,String startTime, String endTime, int id) throws AppointmentsFrame.AppointmentException {
		String partner = (p==Partner.DENTIST) ? "Dentist": "Hygienist";
		try {
			Statement stmt = con.createStatement(); 

			//check if patient exists
			ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM Patient WHERE Patient_ID = "+ id);
			res.next();
			if (res.getInt(1)==0) {
				throw new AppointmentsFrame.AppointmentException("No such patient.");
			}
			
			//ensure there are no conflicting appointments
			res = stmt.executeQuery("SELECT Start_Time,End_Time FROM Appointment WHERE Date = '"+date.substring(2)+"' AND Patient_ID = " + id);
			while (res.next()) {
				String st = res.getString(1);
				String et = res.getString(2);
				
				if ((startTime.compareTo(st)>=0 && startTime.compareTo(et)<0) || (endTime.compareTo(st)>0 && endTime.compareTo(et)<=0)) {
					throw new AppointmentsFrame.AppointmentException("Conflicting with other personal appointments.");
				}
			}
			
			//ensure partner is available
			res = stmt.executeQuery("SELECT Start_Time,End_Time FROM Appointment WHERE Date = '"+date.substring(2)+"' AND Partner = '" +partner+ "'");
			while (res.next()) {
				String st = res.getString(1);
				String et = res.getString(2);
				
				if ((startTime.compareTo(st)>=0 && startTime.compareTo(et)<0) || (endTime.compareTo(st)>0 && endTime.compareTo(et)<=0)) {
					throw new AppointmentsFrame.AppointmentException(partner+" is not available at that time.");
				}
			}
			
			stmt.executeUpdate("INSERT INTO Appointment (Partner, Date, Start_Time, End_Time, Cost, Patient_ID) "
							+"VALUES ('" + partner + "','" + date.substring(2) + "','" + startTime + "', '" + endTime + "', '0', '" + id + "')");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList<Appointment> getAppointments(Partner p, String date) {
		try {
			Statement stmt = con.createStatement();
			String partner = (p == Partner.DENTIST) ? "Dentist" : "Hygienist";
			String query = "SELECT Start_Time,End_Time,Title,Forename,Surname FROM Appointment ap,Patient pt "
							+ "WHERE ap.Patient_Id=pt.Patient_Id AND Date='" + date.substring(2) 
							+ "' AND partner = '" + partner + "'";
			ResultSet res = stmt.executeQuery(query);
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			while (res.next()) {
				 String startTime = res.getString(1); 
				 String endTime = res.getString(2);
				 String name = res.getString(3) + " " + res.getString(4).charAt(0) + ". " + res.getString(5); 
				 appointments.add(new Appointment(startTime,endTime,name));
			 }
			
			return appointments;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	
	public String findNextAppointment(Partner p, int id) {
		String partner = (p==Partner.DENTIST)? "Dentist" : "Hygienist";
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT Date FROM Appointment WHERE Partner = '"+partner+"' AND Patient_Id = " + id +" ORDER BY Date";
			ResultSet res = stmt.executeQuery(query);
			
			String today = DateProcessor.today().substring(2);
			while (res.next()) {
				String date = res.getString(1);
				if (date.compareTo(today)>=0) {
					return date;
				}
			}
			return null;//res.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int cancelAppointment(Partner p, String date, String startTime) { 
		try {
			Statement stmt = con.createStatement();
			String partner = (p==Partner.DENTIST)? "Dentist" : "Hygienist";
			String query = "DELETE FROM Appointment WHERE Partner = '" + partner + "' AND Date ='" +date.substring(2)
					+ "' AND Start_Time = '" + startTime + "'";
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
