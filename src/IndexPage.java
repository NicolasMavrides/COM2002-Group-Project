import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class IndexPage extends JFrame {
	//main page buttons
	private JButton viewCalendar;
	private JButton regPatient;
	private JButton subHealthPlan;
	private JButton reviewTreatmt;
	private JButton recordPaymt;
	
	//Constructor for the main page GUI
	public IndexPage() {
		super("Dental System");
		setLayout(new GridLayout(5,1));
		setSize(500,200);
		setResizable(false);
	    setLocationRelativeTo(null);
	    
	    //construct buttons and their labels
		viewCalendar = new JButton("View Calendar");
		regPatient = new JButton("Register Patient");
		subHealthPlan = new JButton("Subscribe Patient to Health Plan");
		reviewTreatmt = new JButton("Review Patient Treatments");
		recordPaymt = new JButton("Record Patient Payment");
		//TODO: add register patient button
		
		//add buttons
		add(viewCalendar);
		add(regPatient);
		add(subHealthPlan);
		add(reviewTreatmt);
		add(recordPaymt);
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		//add action listeners to buttons
		viewCalendar.addActionListener(eHandler);
		regPatient.addActionListener(eHandler);
		subHealthPlan.addActionListener(eHandler);
		reviewTreatmt.addActionListener(eHandler);
		recordPaymt.addActionListener(eHandler);
		setVisible(true);
	}
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		//action listener method
		public void actionPerformed(ActionEvent event) {	
			if (event.getSource() == viewCalendar) {
				new AppointmentsFrame();
			} else if (event.getSource() == regPatient) {
				new RegisterPatient();
			} else if (event.getSource() == subHealthPlan) {
				new HealthCarePlan();
			} else if (event.getSource() == reviewTreatmt) {
				//REVIEW TREATMENT INTERFACE CODE
			} else {
				//RECORD PAYMENT INTERFACE CODE
			}
		}
	}
	public static void main(String[] args) {
		new IndexPage();
	}
}