import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

@SuppressWarnings("serial")
public class AppointmentsFrame extends JFrame {
	private TabbedAppointmentsPane tap;
	private Container contentPane;
	
	public AppointmentsFrame(String date) {
		setTitle("Calendar");
		setSize(1250,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		

		tap = new TabbedAppointmentsPane(date);
		contentPane.add(createNavPane(),BorderLayout.NORTH);
		contentPane.add(tap, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private JPanel createNavPane() {
		JPanel datePane = new JPanel(new FlowLayout());
		datePane.add(new JLabel("Go to:"));		
		
		//day
		JComboBox<Integer> dayList = new JComboBox<Integer>();
		for (int i=1;i<=31;i++)
			dayList.addItem(i);
		
		//month
		String[] months = {"January","February","March","April","May","June","July",
							"August","September","October","November","December"};
		JComboBox<String> monthList = new JComboBox<String>();
		for (String month:months)
			monthList.addItem(month);
		
		//year
		JComboBox<Integer> yearList = new JComboBox<Integer>();
		for (int i=2016;i>=1996;i--) //TODO add min and max year
			yearList.addItem(i);
		
		datePane.add(dayList);
		datePane.add(monthList);
		datePane.add(yearList);
		
		JButton searchButton = new JButton("Search");
		datePane.add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int day = (int)dayList.getSelectedItem();
				int year = (int)yearList.getSelectedItem();
				int month = monthList.getSelectedIndex() + 1;	
				
				String newDate = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
				
				refreshCalendar(newDate);
				
			}
		});
		
		JButton prevWeek = new JButton("Previous Week");
		JButton nextWeek = new JButton("Next Week");
		
		prevWeek.addActionListener(new weekChangeListener(-1));
		nextWeek.addActionListener(new weekChangeListener(1));
		
		JPanel navPane = new JPanel(new FlowLayout());
		navPane.add(datePane);
		navPane.add(prevWeek);
		navPane.add(nextWeek);
		
		return navPane;
	}
	
	
	private void refreshCalendar(String date) {
		contentPane.remove(tap);
		contentPane.revalidate(); 
		repaint();
		tap = new TabbedAppointmentsPane(date);
		contentPane.add(tap);
		contentPane.revalidate(); 
		repaint();
	}
	
	private class TabbedAppointmentsPane extends JPanel {	
		String date;
		public TabbedAppointmentsPane(String date) {
			super(new BorderLayout());
			this.date = date;
			JTabbedPane tabbedPane = new JTabbedPane();
			
	        tabbedPane.addTab("Dentist", new CalendarPane(Partner.DENTIST,date));
	        tabbedPane.addTab("Hygienist", new CalendarPane(Partner.HYGIENIST,date));
	        
	        add(tabbedPane);
	        
		}
		
		public String getDate() {return date;}
		
	}
	
	private class weekChangeListener implements ActionListener {
		private int direction; // -1 or 1 for previous and next week
		
		public weekChangeListener(int d) {
			direction = d;
		}
		
		public void actionPerformed(ActionEvent e) {
			String date = tap.getDate();
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				c.setTime(sdf.parse(date));
			} catch (ParseException pe) {
				pe.printStackTrace();
			}
			c.add(Calendar.DATE, direction*7);
			
			String newDate = sdf.format(c.getTime());
			refreshCalendar(newDate);
			
		}
		
	}
	
	
	public static void main(String[] args) {
		new AppointmentsFrame("2016-12-14");
		
	}
}
