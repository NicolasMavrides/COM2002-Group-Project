import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressWarnings("serial")
public class AppointmentsFrame extends JFrame {
	private TabbedAppointmentsPane tap;
	private Container contentPane;
	private final String TODAY = DateProcessor.today();
	private final int CURRENT_YEAR = DateProcessor.currentYear();
	
	public AppointmentsFrame() {
		setTitle("Calendar");
		setSize(1250,500);
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());		

		tap = new TabbedAppointmentsPane(TODAY);
		contentPane.add(createNavPane(),BorderLayout.NORTH);
		contentPane.add(tap, BorderLayout.CENTER);
		contentPane.add(createBookPane(),BorderLayout.SOUTH);

		setVisible(true);
	}
	
	private JPanel createNavPane() {
		
		//Finding a specific date
		
		//Search by date
		JPanel dateSearchPane = new JPanel(new FlowLayout());
		dateSearchPane.add(new JLabel("Go to:"));
		dateSearchPane.add(new DatePane(CURRENT_YEAR-5,CURRENT_YEAR+5,TODAY));
		
		JButton searchButton = new JButton("Search");
		dateSearchPane.add(searchButton);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DatePane dp = (DatePane)dateSearchPane.getComponents()[1]; 
				String newDate = DateProcessor.formatDate(dp.getYear(),dp.getMonth(),dp.getDay());
				
				try {
					validateDate(newDate);
					refreshCalendar(newDate);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Date doesn't exist.", null, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		//Next and previous week
		JButton prevWeek = new JButton("Previous Week");
		JButton nextWeek = new JButton("Next Week");
		
		prevWeek.addActionListener(new weekChangeListener(-1));
		nextWeek.addActionListener(new weekChangeListener(1));
		
		//---------------------------
		//Finding an upcoming appointment
		
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
					JOptionPane.showMessageDialog(null, "No appointments found.", null, JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					refreshCalendar(20+appointmentDate);
				}
				qp.close();
			}
		});
		
		
		//--------------------------------------
		//Cancelling an appointment
		/*JButton cancelButton = new JButton("Cancel");
		
		JPanel cancelAppointmentPane = new JPanel(new FlowLayout());
		cancelAppointmentPane.add(new JLabel("Cancel appointment on: "));
		cancelAppointmentPane.add(new DatePane(CURRENT_YEAR,CURRENT_YEAR+5,TODAY));
		cancelAppointmentPane.add(new TimePane());
		cancelAppointmentPane.add(cancelButton);
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String date = ((DatePane)cancelAppointmentPane.getComponent(1)).getDate();
				
				try {
					validateDate(date);
					TimePane tp = (TimePane)cancelAppointmentPane.getComponent(2);
					String startTime = tp.getTime();
					
					AppointmentQueryProcessor qp = new AppointmentQueryProcessor();
					int cancelResult = qp.cancelAppointment(tap.getPartner(), date, startTime);
					qp.close();
					
					if (cancelResult ==0) {
						JOptionPane.showMessageDialog(null, "There are no appointments at that time.", null, JOptionPane.ERROR_MESSAGE);
					}
					else {
						refreshCalendar(date);
					}
					
				}
				catch (ParseException pe) {
					JOptionPane.showMessageDialog(null, "Date doesn't exist.", null, JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});*/
		
		//Add all 3 functionalities
		JPanel navPane = new JPanel(new GridLayout(0,1));
		JPanel weekNavPane = new JPanel(new FlowLayout());
		
		weekNavPane.add(dateSearchPane);
		weekNavPane.add(prevWeek);
		weekNavPane.add(nextWeek);
		
		navPane.add(weekNavPane);
		navPane.add(searchAppointmentPane);
		//navPane.add(cancelAppointmentPane);
		
		return navPane;
	}
	
	private JPanel createBookPane() {
		
		JPanel dateInput = new JPanel(new FlowLayout());
		dateInput.add(new JLabel("Member ID:"));
		dateInput.add(new JTextField(2));
        dateInput.add(new JLabel("Date and time period:"));  
        dateInput.add(new DatePane(CURRENT_YEAR,CURRENT_YEAR+5,TODAY));
        dateInput.add(new TimePane());
        dateInput.add(new JLabel("to"));
        dateInput.add(new TimePane());
        
        
        JButton bookButton = new JButton("Book appointment");
        
        //JPanel submitPane = new JPanel(new FlowLayout());
        //JTextField idTextField = new JTextField(2);
        
        
        //submitPane.add(bookButton);
        
		
		JPanel bookPane = new JPanel(new GridLayout(2,2));
		bookPane.add(dateInput);
		bookPane.add(bookButton);
        /*JLabel memberlbl = new JLabel("Member ID:");
        memberlbl.setHorizontalAlignment(JLabel.CENTER);
        bookPane.add(memberlbl);
        bookPane.add(new JTextField());
        bookPane.add(dateInput);
        bookPane.add(bookButton);*/
        
        bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		
        		
				JTextField memberIdInput = (JTextField)bookPane.getComponent(1);
				
				
				
				try {
					int id = Integer.valueOf(memberIdInput.getText());
					
					DatePane dateSelPane = (DatePane)(dateInput.getComponent(1));
					TimePane stInput = (TimePane)(dateInput.getComponent(2));
					TimePane etInput = (TimePane)(dateInput.getComponent(4));
					String startTime = stInput.getTime();
					String endTime = etInput.getTime();
					String bookDate = dateSelPane.getDate();
					
					validateDate(bookDate); //validate date
					//will throw ParseException if a non-existing date is selected e.x. 2016-02-30
					
					//validate appointment (start>end and not finishing after 17:00)
					if ((startTime.compareTo(endTime)>=0) || (endTime.compareTo("17:00")>0)) {
						throw new AppointmentException("Invalid time.");
					}
					
					//Insert appointment into database and show it on the calendar
					AppointmentQueryProcessor dbc = new AppointmentQueryProcessor();
					dbc.bookAppointment(tap.getPartner(), bookDate, startTime, endTime, id);
					dbc.close();
					refreshCalendar(bookDate);
					
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Invalid date or member ID.", null, JOptionPane.ERROR_MESSAGE);
				}
				catch (ParseException pe) {
					JOptionPane.showMessageDialog(null, "Date doesn't exist.", null, JOptionPane.ERROR_MESSAGE);
				}
				catch (AppointmentException ex) {
					JOptionPane.showMessageDialog(null, ex.getType(), null, JOptionPane.ERROR_MESSAGE);
				}
			
				
				
			}
        });
        
        JButton cancelButton = new JButton("Cancel");
		
		JPanel cancelAppointmentPane = new JPanel(new FlowLayout());
		cancelAppointmentPane.add(new JLabel("Cancel appointment on: "));
		cancelAppointmentPane.add(new DatePane(CURRENT_YEAR,CURRENT_YEAR+5,TODAY));
		cancelAppointmentPane.add(new TimePane());
		cancelAppointmentPane.add(cancelButton);
		
		cancelAppointmentPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String date = ((DatePane)cancelAppointmentPane.getComponent(1)).getDate();
				
				try {
					validateDate(date);
					TimePane tp = (TimePane)cancelAppointmentPane.getComponent(2);
					String startTime = tp.getTime();
					
					AppointmentQueryProcessor qp = new AppointmentQueryProcessor();
					int cancelResult = qp.cancelAppointment(tap.getPartner(), date, startTime);
					qp.close();
					
					if (cancelResult ==0) {
						JOptionPane.showMessageDialog(null, "There are no appointments at that time.", null, JOptionPane.ERROR_MESSAGE);
					}
					else {
						refreshCalendar(date);
					}
					
				}
				catch (ParseException pe) {
					JOptionPane.showMessageDialog(null, "Date doesn't exist.", null, JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});

		JPanel holidayPane = new JPanel(new FlowLayout());
		holidayPane.add(new JLabel("From: "));
		holidayPane.add(new DatePane(CURRENT_YEAR, CURRENT_YEAR+5, TODAY));
		holidayPane.add(new JLabel("To: "));
		holidayPane.add(new DatePane(CURRENT_YEAR, CURRENT_YEAR+5, TODAY));
		holidayPane.add(new JButton("Book Holiday"));
		holidayPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		
        bookPane.add(cancelAppointmentPane);
        bookPane.add(holidayPane);
        
        return bookPane;
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
	
	private static void validateDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		sdf.parse(date);
	}
	
	//----------------------------------
	//CLASSES
	
	//PANELS
	private class TabbedAppointmentsPane extends JPanel {	
		String date;
		private TabbedAppointmentsPane(String date) {
			super(new BorderLayout());
			this.date = date;
			JTabbedPane tabbedPane = new JTabbedPane();
			
	        tabbedPane.addTab("Dentist", new CalendarPane(Partner.DENTIST,date));
	        tabbedPane.addTab("Hygienist", new CalendarPane(Partner.HYGIENIST,date));
	        
	        add(tabbedPane);
	        
		}
		
		//return partner based on the selected tab
		private Partner getPartner() {
			JTabbedPane jtb = (JTabbedPane)this.getComponent(0);
			return (jtb.getSelectedIndex()==0)? Partner.DENTIST : Partner.HYGIENIST;
		}
		
		public String getDate() {return date;}
		
	}
	
	private class DatePane extends JPanel {
		private JComboBox<Integer> dayList;
		private JComboBox<String> monthList;
		private JComboBox<Integer> yearList;
		
		private DatePane(int yStart,int yEnd, String selectedDate) {
			
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

			String[] date = selectedDate.split("-");
			yearList.setSelectedIndex(Integer.valueOf(date[0])-yStart);
			monthList.setSelectedIndex(Integer.valueOf(date[1])-1);
			dayList.setSelectedIndex(Integer.valueOf(date[2])-1);
			
			add(dayList);
			add(monthList);
			add(yearList);
		}
		
		
		private int getDay() {return (int)dayList.getSelectedItem();}
		private int getMonth() {return monthList.getSelectedIndex() + 1;}
		private int getYear() {return (int)yearList.getSelectedItem();}
		private String getDate() {return DateProcessor.formatDate(getYear(), getMonth(), getDay());}
	}
	
	private class TimePane extends JPanel {
		private JComboBox<String> hourList;
		private JComboBox<String> minList;

		private TimePane() {
			super(new FlowLayout());

			hourList = new JComboBox<String>();
			for (int i=9;i<=17;i++)
				hourList.addItem(String.format("%02d", i));
			
			minList = new JComboBox<String>();
			for (int i=0;i<=40;i+=20)
				minList.addItem(String.format("%02d", i));
			
			add(hourList);
			add(new JLabel(":"));
			add(minList);
		}
		
		private String getHour() { return hourList.getSelectedItem().toString(); }
		private String getMin() { return minList.getSelectedItem().toString(); }
		private String getTime() { return getHour() + ":" + getMin(); }
		
	}
	
	//EVENT LISTENERS
	private class weekChangeListener implements ActionListener {
		private int direction; // -1 or 1 for previous and next week
		
		public weekChangeListener(int direction) {
			this.direction = direction;
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
	
	//EXCEPTIONS
	static class AppointmentException extends Exception {
		String exeptionType;
		AppointmentException(String s) {
			exeptionType = s;
		}
		String getType() {return exeptionType;}
	}
	
	
	public static void main(String[] args) {
		new AppointmentsFrame();
		
	}
}