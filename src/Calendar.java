import java.awt.*;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class Calendar extends JPanel {
	public Calendar(){//ResultSet appointments) {
		setLayout(new BorderLayout());
		String[] columnNames = {"Hour","Monday", "Tuesday", "Wendesday",
                "Thursday", "Friday", "Saturday", "Sunday"};
		
		Object[][] data = new Object[24][8];
		
		for (int h=0; h<8; h++) {
			for (int m=0; m<3; m++) {
				data[3*h+m][0] = String.format("%02d", h+9) + ":" + String.format("%02d", m*20); 
			}
		}
		
		data[6][1] = "Alexandros";
		data[7][1] = "";
		//data[26][1] 
		
		
		
		final JTable table = new JTable(data, columnNames);
		
		table.setEnabled(false);
		
		table.setDefaultRenderer(Object.class, new AppointmentRender());
		
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);
        
        
        
        JPanel dateInput = new JPanel(new FlowLayout());
        dateInput.add(new JLabel("Insert date(Day-Month-Year): "));
        dateInput.add(new JTextField(2));
        dateInput.add(new JTextField(2));
        dateInput.add(new JTextField(2));
        
        JPanel bookPane = new JPanel(new GridLayout(2,2));
        JLabel memberlbl = new JLabel("Member ID:");
        memberlbl.setHorizontalAlignment(JLabel.CENTER);
        bookPane.add(memberlbl);
        bookPane.add(new JTextField());
        bookPane.add(dateInput);
        bookPane.add(new JButton("Book appointment"));
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
	
	
}


