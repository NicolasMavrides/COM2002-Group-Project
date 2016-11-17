public class Appointment {
	private String startTime;
	private String endTime;
	private String patientName;
	
	public Appointment(String startTime, String endTime, String patientName) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.patientName = patientName;
	}
	
	public String getStartTime() { return startTime; }
	public String getEndTime() { return endTime; }
	public String getPatientName() { return patientName; }
	
}