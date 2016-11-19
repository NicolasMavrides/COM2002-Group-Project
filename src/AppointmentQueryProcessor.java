import java.sql.*;
import java.util.ArrayList;

public class AppointmentQueryProcessor extends QueryProcessor {
	public AppointmentQueryProcessor() { super(); }
	
	public void bookAppointment(Partner p, String date,String startTime, String endTime, int id) throws AppointmentsFrame.AppointmentException {
		String partner = (p==Partner.DENTIST) ? "Dentist": "Hygienist";
		try {
			
			if (!memberExists(id)) {
				throw new AppointmentsFrame.AppointmentException("No such patient.");
			}
			
			Statement stmt = con.createStatement(); 
			
			//ensure there are no conflicting appointments
			ResultSet res = stmt.executeQuery("SELECT Start_Time,End_Time FROM Appointment WHERE Date = '"+date+"' AND Patient_ID = " + id);
			while (res.next()) {
				String st = res.getString(1);
				String et = res.getString(2);
				
				if ((startTime.compareTo(st)>=0 && startTime.compareTo(et)<0) || (endTime.compareTo(st)>0 && endTime.compareTo(et)<=0)) {
					throw new AppointmentsFrame.AppointmentException("Conflicting with other personal appointments.");
				}
			}
			
			//ensure partner is available
			res = stmt.executeQuery("SELECT Start_Time,End_Time FROM Appointment WHERE Date = '"+date+"' AND Partner = '" +partner+ "'");
			while (res.next()) {
				String st = res.getString(1);
				String et = res.getString(2);
				
				if ((startTime.compareTo(st)>=0 && startTime.compareTo(et)<0) || (endTime.compareTo(st)>0 && endTime.compareTo(et)<=0)) {
					throw new AppointmentsFrame.AppointmentException(partner+" is not available at that time.");
				}
			}
			
			stmt.executeUpdate("INSERT INTO Appointment (Partner, Date, Start_Time, End_Time, Cost, Patient_ID) "
							+"VALUES ('" + partner + "','" + date + "','" + startTime + "', '" + endTime + "', '0', '" + id + "')");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList<Appointment> getAppointments(Partner p, String date) {
		try {
			Statement stmt = con.createStatement();
			String partner = (p == Partner.DENTIST) ? "Dentist" : "Hygienist";
			String query = "SELECT Start_Time,End_Time,Title,Forename,Surname,ap.Patient_Id FROM Appointment ap,Patient pt "
							+ "WHERE ap.Patient_Id=pt.Patient_Id AND Date='" + date
							+ "' AND partner = '" + partner + "'";
			ResultSet res = stmt.executeQuery(query);
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			while (res.next()) {
				 String startTime = res.getString(1); 
				 String endTime = res.getString(2);
				 String name = (res.getInt(6)==0)? "Not available.": 
					 res.getString(3) + " " + res.getString(4).charAt(0) + ". " + res.getString(5); 
				 appointments.add(new Appointment(startTime,endTime,name));
				 
			 }
			
			return appointments;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	/*public ArrayList<Appointment> getUnpaidAppointments(int id) {
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM Appointment WHERE Patient_Id = " + id);
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			while (res.next()) {
				int cost = res.getInt(5);
				if (cost!=0) {
					appointments.add(new Appointment(startTime,endTime,name));
				}
				 
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	public String findNextAppointment(Partner p, int id) {
		String partner = (p==Partner.DENTIST)? "Dentist" : "Hygienist";
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT Date FROM Appointment WHERE Partner = '"+partner+"' AND Patient_Id = " + id +" ORDER BY Date";
			ResultSet res = stmt.executeQuery(query);
			
			String today = DateProcessor.today();
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
			String query = "DELETE FROM Appointment WHERE Partner = '" + partner + "' AND Date ='" +date
					+ "' AND Start_Time = '" + startTime + "'";
			return stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public void bookHoliday(Partner p,String startDate,String endDate) throws AppointmentsFrame.AppointmentException {
		
		String[] dateRange = DateProcessor.getRange(startDate, endDate);
		
		try {
			Statement stmt = con.createStatement();
			String partner = (p==Partner.DENTIST)? "Dentist" : "Hygienist";
			for (String date:dateRange) {
				String query = "SELECT COUNT(*) FROM Appointment WHERE Partner = '" + partner + "' AND Date ='" +date+ "';";

				ResultSet res = stmt.executeQuery(query);
				res.next();
				if (res.getInt(1)!=0) {
					throw new AppointmentsFrame.AppointmentException("There are client appointments between these dates.");
				}
			}
			
			for (String date:dateRange) {
				bookAppointment(p, date, "09:00", "17:00", 0);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	/*public void testDate() {
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM Test");
			
			for (int i=0;i<3;i++) {
				res.next();
				System.out.println(res.getString(1));
				System.out.println(res.getString(2));
				System.out.println(res.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void main(String[] args) {
		AppointmentQueryProcessor apq = new AppointmentQueryProcessor();
		//apq.testDate();
	}
}
