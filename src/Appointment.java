public class Appointment {        
	private String startTime;
	private String endTime;
	private String patientName;
	private int cost;
	
	public Appointment(String startTime, String endTime, String patientName) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.patientName = patientName;
	}
	
	/*public Appointment(Partner p,String date, String start_Time, int cost) {
		
	}*/
	
	public String getStartTime() { return startTime; }
	public String getEndTime() { return endTime; }
	public String getPatientName() { return patientName; }
	
}