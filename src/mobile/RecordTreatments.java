package mobile;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class RecordTreatments extends JFrame {
	//main page buttons
	//private JLabel id 
	//private JLabel 
	//private JButton viewCalendar;
	private JButton recordTreatment;
	private JComboBox treatments;
	// Dentist, 2016-12-14, 13:00
	//Treatments
	//Operations
	
	public RecordTreatments() {
		super("Record Treatments");
		setLayout(new GridLayout(4,1));
		setSize(500,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    setVisible(true);
	    String[] healthCarePlans = { "Anagram Filling", "Check-up", "Gold crown", "Resin fill" };
	    treatments = new JComboBox(healthCarePlans);
	    recordTreatment = new JButton("Record Treatment");
	    
	    add(treatments);
	    add(recordTreatment);
	    EventHandler eHandler = new EventHandler();
	    treatments.addActionListener(eHandler);
	    recordTreatment.addActionListener(eHandler);

	}
	
	    
		private class EventHandler implements ActionListener {
			//action listener method
			public void actionPerformed(ActionEvent event) {	
			 if (event.getSource() == recordTreatment) {
				 RecordTreatmentQueryProcessor execute = new RecordTreatmentQueryProcessor();
				 System.out.println(treatments.getSelectedItem().toString());
				 try {
						execute.RecordTreatment(treatments.getSelectedItem().toString());
				} catch (SQLException e) {
				e.printStackTrace();
				}
		 	} 
		}
	}
		
		public class RecordTreatmentQueryProcessor extends QueryProcessor {
			public RecordTreatmentQueryProcessor() {
				super();
			}
			
			public void RecordTreatment(String name) throws SQLException {
				Statement stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO Patient (Partner, Date, Start_time, Treatment_name) VALUES ('Dentist', '2016-12-14', '13:00', '"+name+"')");
			}
		}
	}