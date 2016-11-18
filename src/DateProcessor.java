import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateProcessor {
	
	public static String today() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
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
	
	public static void main(String[] args) {
		System.out.println("10:10".compareTo("09:20"));
	}
	
}
