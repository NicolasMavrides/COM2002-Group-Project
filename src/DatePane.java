import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DatePane extends JPanel {
		private JComboBox<Integer> dayList;
		private JComboBox<String> monthList;
		private JComboBox<Integer> yearList;
		
		public DatePane(int yStart,int yEnd, String selectedDate) {
			
			super(new FlowLayout());		
			//day
			dayList = new JComboBox<Integer>();
			for (int i=1;i<=31;i++)
				dayList.addItem(i);
			
			//month
			String[] months = {"January","February","March","April","May","June","July",
								"August","September","October","November","December"};
			monthList = new JComboBox<String>();
			for (String month:months)
				monthList.addItem(month);
			
			//year
			yearList = new JComboBox<Integer>();
			for (int i=yStart;i<=yEnd;i++)
				yearList.addItem(i);
			
			if (selectedDate!=null) {
				String[] date = selectedDate.split("-");
				yearList.setSelectedIndex(Integer.valueOf(date[0])-yStart);
				monthList.setSelectedIndex(Integer.valueOf(date[1])-1);
				dayList.setSelectedIndex(Integer.valueOf(date[2])-1);
			}
			
			
			add(dayList);
			add(monthList);
			add(yearList);
		}
		
		
		public int getDay() {return (int)dayList.getSelectedItem();}
		public int getMonth() {return monthList.getSelectedIndex() + 1;}
		public int getYear() {return (int)yearList.getSelectedItem();}
		public String getDate() {return DateProcessor.formatDate(getYear(), getMonth(), getDay());}
	}