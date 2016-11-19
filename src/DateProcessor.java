import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DateProcessor {
	
	public static String today() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static int currentYear() {
		return Integer.valueOf(today().substring(0, 4));
	}
	
	//convert to yyyy-mm-dd
	public static String formatDate(int year, int month, int day) {
		return String.format("%04d", year) + '-' + String.format("%02d", month) + '-' + String.format("%02d", day);
	} 
	
	public static String[] getWeek(String date){
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			c.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int dayofweek = c.get(Calendar.DAY_OF_WEEK);
		String[] idates = new String[7]; //the dates of the week interested
		
		//find date of sunday
		if (dayofweek!=1) {
			c.add(Calendar.DATE, (8-dayofweek));
		}
		idates[6] = sdf.format(c.getTime());
		
		//find other dates
		for (int i=5;i>=0;i--) {
			c.add(Calendar.DATE, -1);
			idates[i] = sdf.format(c.getTime());
		}
		
		return idates;
	}
	public static String[] getRange(String startDate,String endDate) throws AppointmentsFrame.AppointmentException {
		if (startDate.compareTo(endDate)>0) {
			throw new AppointmentsFrame.AppointmentException("Invalid date range.");
		}
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ArrayList<String> dateList = new ArrayList<String>();
		String currentDate = startDate;
		try {
			c.setTime(sdf.parse(currentDate));
			while (currentDate.compareTo(endDate)<=0) {
				dateList.add(currentDate);
				c.add(Calendar.DATE, 1);
				currentDate = sdf.format(c.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String[] s = new String[dateList.size()];
		dateList.toArray(s);
		return s;
	}
	
	public static void main(String[] args) {
		try {
			String[] range = getRange("2016-11-10","2016-12-09");
			for (String s: range) {
				System.out.println(s);
			}
		} catch (AppointmentsFrame.AppointmentException e) {
			System.out.println("OPs");
		}
	}
	
}
