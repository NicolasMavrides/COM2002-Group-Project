import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
		String[] week = getWeek(date);
		
		DBConnector dbc = new DBConnector();
		for (int i=0;i<7;i++) {
			
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
		dbc.close();
		//-----------------------------------------------------
		//draw components
		final JTable table = new JTable(data, columnNames);
		table.setEnabled(false);
		table.setDefaultRenderer(Object.class, new AppointmentRender());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);
        
        
        JComboBox<String> hourList = new JComboBox<String>();
		for (int i=9;i<=17;i++)
			hourList.addItem(String.format("%02d", i));
		
		JComboBox<String> minList = new JComboBox<String>();
		for (int i=0;i<=40;i+=20)
			minList.addItem(String.format("%02d", i));
        
        JPanel dateInput = new JPanel(new FlowLayout());
        dateInput.add(new JLabel("Date(YY-MM-DD) and Time(HH:MM):"));
        dateInput.add(new JTextField(2));
        dateInput.add(new JLabel("-"));
        dateInput.add(new JTextField(2));
        dateInput.add(new JLabel("-"));
        dateInput.add(new JTextField(2));
        dateInput.add(hourList);
        dateInput.add(minList);
        
        JPanel bookPane = new JPanel(new GridLayout(2,2));
        JLabel memberlbl = new JLabel("Member ID:");
        memberlbl.setHorizontalAlignment(JLabel.CENTER);
        bookPane.add(memberlbl);
        bookPane.add(new JTextField());
        bookPane.add(dateInput);
        bookPane.add(new JButton("Book appointment"));
        add(bookPane,BorderLayout.SOUTH);
        
	}
	
	private String[] getWeek(String date){
		
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
	
}


