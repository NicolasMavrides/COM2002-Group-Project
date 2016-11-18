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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		

		tap = new TabbedAppointmentsPane(date);
		contentPane.add(createNavPane(),BorderLayout.NORTH);
		contentPane.add(tap, BorderLayout.CENTER);

		setVisible(true);
	}
	
	private JPanel createNavPane() {
		JPanel dateSearchPane = new JPanel(new FlowLayout());
		dateSearchPane.add(new JLabel("Go to:"));		
		dateSearchPane.add(new DatePane(1990,2016));
		
		JButton searchButton = new JButton("Search");
		dateSearchPane.add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatePane dp = (DatePane)dateSearchPane.getComponents()[1]; 
				String newDate = DateProcessor.formatDate(dp.getYear(),dp.getMonth(),dp.getDay());
				refreshCalendar(newDate);
			}
		});
		
		JButton prevWeek = new JButton("Previous Week");
		JButton nextWeek = new JButton("Next Week");
		
		prevWeek.addActionListener(new weekChangeListener(-1));
		nextWeek.addActionListener(new weekChangeListener(1));
		
		
		
		JButton findAppointmentButton = new JButton("Find Appointment");
		JPanel searchAppointmentPane = new JPanel(new FlowLayout());
		searchAppointmentPane.add(new JLabel("Enter member id to find upcoming appointment:"));
		searchAppointmentPane.add(new JTextField(2));
		searchAppointmentPane.add(findAppointmentButton);
		
		
		findAppointmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppointmentQueryProcessor qp = new AppointmentQueryProcessor();
				JTabbedPane jtb = (JTabbedPane)tap.getComponent(0);
				
				JTextField idInput = (JTextField)searchAppointmentPane.getComponent(1);
				
				int id = Integer.valueOf(idInput.getText());
				
				Partner partner = (jtb.getSelectedIndex()==0)? Partner.DENTIST : Partner.HYGIENIST;
				String appointmentDate = qp.findNextAppointment(partner, id);
				if (appointmentDate==null) {
					System.out.println("No appointments found");
				}
				else {
					refreshCalendar(20+appointmentDate);
				}
				qp.close();
				//System.out.println(qp.findAppointment(partner, id));
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		
		JPanel cancelAppointmentPane = new JPanel(new FlowLayout());
		cancelAppointmentPane.add(new JLabel("Cancel appointment on: "));
		cancelAppointmentPane.add(new DatePane(1990,2016));
		cancelAppointmentPane.add(CalendarPane.createTimeList(9,17,1));
		cancelAppointmentPane.add(CalendarPane.createTimeList(0,40,20));
		cancelAppointmentPane.add(cancelButton);
		
		cancelButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				
				JTabbedPane jtb = (JTabbedPane)tap.getComponent(0);
				Partner partner = (jtb.getSelectedIndex()==0)? Partner.DENTIST : Partner.HYGIENIST;
				
				String date = ((DatePane)cancelAppointmentPane.getComponent(1)).getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.setLenient(false);
				
				try {
					sdf.parse(date);
				}
				catch (ParseException pe) {
					System.out.println("Date doesn't exist");
				}
				
				String startTime = ((JComboBox<String>)cancelAppointmentPane.getComponent(2)).getSelectedItem().toString();
				startTime += ":"+((JComboBox<String>)cancelAppointmentPane.getComponent(3)).getSelectedItem().toString();
				
				System.out.println(date+startTime);
				AppointmentQueryProcessor qp = new AppointmentQueryProcessor();
				qp.cancelAppointment(partner, date, startTime);
				qp.close();
				refreshCalendar(date);
			}
		});
		
		JPanel navPane = new JPanel(new GridLayout(0,1));
		JPanel weekNavPane = new JPanel(new FlowLayout());
		
		weekNavPane.add(dateSearchPane);
		weekNavPane.add(prevWeek);
		weekNavPane.add(nextWeek);
		navPane.add(weekNavPane);
		
		navPane.add(searchAppointmentPane);
		navPane.add(cancelAppointmentPane);
		
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
	
	private class DatePane extends JPanel {
		private JComboBox<Integer> dayList;
		private JComboBox<String> monthList;
		private JComboBox<Integer> yearList;
		private DatePane(int yStart,int yEnd) {
			
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
			for (int i=yEnd;i>=yStart;i--) //TODO add min and max year
				yearList.addItem(i);
			
			add(dayList);
			add(monthList);
			add(yearList);
		}
		
		private int getDay() {return (int)dayList.getSelectedItem();}
		private int getMonth() {return monthList.getSelectedIndex() + 1;}
		private int getYear() {return (int)yearList.getSelectedItem();}
		private String getDate() {return (String.valueOf(getYear()) + '-' + String.valueOf(getMonth()) + '-' + String.valueOf(getDay()));}
	}
	
	
	public static void main(String[] args) {
		new AppointmentsFrame(DateProcessor.today());
		
	}
}