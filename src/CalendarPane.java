import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class CalendarPane extends JPanel {
	
	public CalendarPane(Partner partner, String date) {
		setLayout(new BorderLayout());
		String[] columnNames = {"Hour","Monday", "Tuesday", "Wendesday",
                "Thursday", "Friday", "Saturday", "Sunday"};
		
		Object[][] data = new Object[24][8];
		
		for (int h=0; h<8; h++) {
			for (int m=0; m<3; m++) {
				data[3*h+m][0] = String.format("%02d", h+9) + ":" + String.format("%02d", m*20); 
			}
		}
		
		
		//----------------------------------------------------------------------
		//find appointments
		
		String[] week = DateProcessor.getWeek(date);
		
		QueryProcessor dbc = new QueryProcessor();
		for (int i=0;i<7;i++) {
			//show date on column names
			String[] splitDate = week[i].split("-");
			columnNames[i+1] += " " + splitDate[2] + "/" + splitDate[1];
			
			
			ArrayList<Appointment> appointments = new ArrayList<Appointment>();
			if (partner == Partner.DENTIST) {
				appointments = dbc.getAppointments(Partner.DENTIST, week[i]);
			}
			else {
				appointments = dbc.getAppointments(Partner.HYGIENIST, week[i]);
			}
			
			//Modify the table data for the appointments found
			for (Appointment ap: appointments) {
				String st = ap.getStartTime();
				String et = ap.getEndTime();
				String name = ap.getPatientName();
				
				int hourSt = Integer.valueOf(st.substring(0, 2));
				int minSt = Integer.valueOf(st.substring(3));
				int hourEt = Integer.valueOf(et.substring(0, 2));
				int minEt = Integer.valueOf(et.substring(3));
				
				int startSlot = (hourSt-9)*3+minSt/20;
				int endSlot = (hourEt-9)*3+minEt/20;
				
				data[startSlot][i+1] = name;
				for (int j=startSlot+1;j<endSlot;j++) {
					data[j][i+1] = "";
				}
			}
		}
		//dbc.close();
		//-----------------------------------------------------
		//draw components
		final JTable table = new JTable(data, columnNames);
		table.setEnabled(false);
		table.setDefaultRenderer(Object.class, new AppointmentRender());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);

        
        JPanel dateInput = new JPanel(new FlowLayout());
        dateInput.add(new JLabel("Date(YYYY-MM-DD) and times:"));
        dateInput.add(new JTextField(4));
        dateInput.add(new JLabel("-"));
        dateInput.add(new JTextField(2));
        dateInput.add(new JLabel("-"));
        dateInput.add(new JTextField(2));
        //dateInput.add(hourList);
        //dateInput.add(minList);
        dateInput.add(createTimeList(9,17,1));
        dateInput.add(createTimeList(0,40,20));
        dateInput.add(new JLabel("to"));
        dateInput.add(createTimeList(9,17,1));
        dateInput.add(createTimeList(0,40,20));
        
        JButton bookButton = new JButton("Book appointment");
        JPanel bookPane = new JPanel(new GridLayout(2,2));
        JLabel memberlbl = new JLabel("Member ID:");
        memberlbl.setHorizontalAlignment(JLabel.CENTER);
        bookPane.add(memberlbl);
        bookPane.add(new JTextField());
        bookPane.add(dateInput);
        bookPane.add(bookButton);
        
        bookButton.addActionListener(new ActionListener() {
        	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				
				JTextField yearInput = (JTextField)dateInput.getComponent(1);
				JTextField monthInput = (JTextField)dateInput.getComponent(3);
				JTextField dayInput = (JTextField)dateInput.getComponent(5);
				JComboBox<String> startHInput = (JComboBox<String>)dateInput.getComponent(6);
				JComboBox<String> startMInput = (JComboBox<String>)dateInput.getComponent(7);
				JComboBox<String> endHInput = (JComboBox<String>)dateInput.getComponent(9);
				JComboBox<String> endMInput = (JComboBox<String>)dateInput.getComponent(10);
				JTextField memberIdInput = (JTextField)bookPane.getComponent(1);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sdf.setLenient(false);
				
				try {
					int year = Integer.valueOf(yearInput.getText());
					int month = Integer.valueOf(monthInput.getText());
					int day = Integer.valueOf(dayInput.getText());
					int id = Integer.valueOf(memberIdInput.getText());
					
					String startTime = startHInput.getSelectedItem().toString() + ':' +startMInput.getSelectedItem().toString();
					String endTime = endHInput.getSelectedItem().toString() + ':' + endMInput.getSelectedItem().toString();
					String bookDate = DateProcessor.formatDate(year, month, day);
					
					
					sdf.parse(bookDate); //validate date
					//validate appointment (start>end and not finishing after 17:00)
					if ((startTime.compareTo(endTime)>=0) || (endTime.compareTo("17:00")>0)) {
						throw new AppointmentException("Invalid time.");
					}
					
					
					System.out.println("Booking..");
					dbc.bookAppointment(partner, bookDate, startTime, endTime, id);
					System.out.println("Succesful booking!");
					
				}
				catch (NumberFormatException nfe) {
					System.out.println("Invalid date or member Id");
				}
				catch (ParseException pe) {
					System.out.println("Date doesn't exist");
				}
				catch (AppointmentException te) {
					System.out.println(te.getType());
				}
				
				
				
			}
        });
        
        add(bookPane,BorderLayout.SOUTH);
        
	}
	
	//class for colouring appointments
	class AppointmentRender extends DefaultTableCellRenderer  {
		@Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
			  JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

			  if (value!=null && col!=0) {
				  l.setBackground(Color.LIGHT_GRAY);
			  }
			  else
				  l.setBackground(Color.WHITE);
			  return l;
        }
    }
	
	private static JComboBox<String> createTimeList(int start,int end,int inc) {
		JComboBox<String> cb = new JComboBox<String>();
		for (int i=start;i<=end;i+=inc)
			cb.addItem(String.format("%02d", i));
		return cb;
	}
	
	static class AppointmentException extends Exception {
		String exeptionType;
		AppointmentException(String s) {
			exeptionType = s;
		}
		String getType() {return exeptionType;}
	}
	
}


