import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class TabbedAppointmentsPane extends JPanel {
	public TabbedAppointmentsPane() {
		super(new BorderLayout());
		
		JPanel navPane = new JPanel(new FlowLayout());
		
		JPanel dateSelPane = createDateSelect("Go to");
		dateSelPane.add(new JButton("Search"));
		navPane.add(dateSelPane);
		navPane.add(new JButton("Previous Week"));
		navPane.add(new JButton("Next Week"));
		add(navPane,BorderLayout.NORTH);
		
		JTabbedPane tabbedPane = new JTabbedPane(); 
        tabbedPane.addTab("Dentist", new Calendar());
        tabbedPane.addTab("Hygienist", new Calendar());
        add(tabbedPane,BorderLayout.CENTER);
        
	}
	
	private JPanel createDateSelect(String labelText) {
		JPanel datePane = new JPanel(new FlowLayout());
		datePane.add(new JLabel(labelText));		
		
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
		
		return datePane;
	}
	
	
}
