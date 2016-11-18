import java.awt.*;
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
		
		//fill hour column from 9 to 17 in increments of 20min.
		for (int h=0; h<8; h++) {
			for (int m=0; m<3; m++) {
				data[3*h+m][0] = String.format("%02d", h+9) + ":" + String.format("%02d", m*20); 
			}
		}
		
		
		//----------------------------------------------------------------------
		//find appointments
		
		String[] week = DateProcessor.getWeek(date);
		
		AppointmentQueryProcessor dbc = new AppointmentQueryProcessor();
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
		dbc.close();
		//-----------------------------------------------------
		//draw components
		JTable table = new JTable(data, columnNames);
		table.setEnabled(false);
		table.setDefaultRenderer(Object.class, new AppointmentRender());
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);    
        
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