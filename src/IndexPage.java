import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class IndexPage extends JFrame {
	//main page buttons
	private JButton viewCalendar;
	private JButton bookAptmt;
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
		bookAptmt = new JButton("Book Appointment");
		subHealthPlan = new JButton("Subscribe Patient to Health Plan");
		reviewTreatmt = new JButton("Review Patient Treatments");
		recordPaymt = new JButton("Record Patient Payment");
		
		//add buttons
		add(viewCalendar);
		add(bookAptmt);
		add(subHealthPlan);
		add(reviewTreatmt);
		add(recordPaymt);
		-
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		//add action listeners to buttons
		viewCalendar.addActionListener(eHandler);
		bookAptmt.addActionListener(eHandler);
		subHealthPlan.addActionListener(eHandler);
		reviewTreatmt.addActionListener(eHandler);
		recordPaymt.addActionListener(eHandler);
		
	}
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		//action listener method
		public void actionPerformed(ActionEvent event) {	
			if (event.getSource() == viewCalendar) {
				//VIEW CALENDAR INTERFACE CODE
			} else if (event.getSource() == bookAptmt) {
				//BOOK APPOINTMENT INTERFACE CODE
			} else if (event.getSource() == subHealthPlan) {
				//SUBSCRIBE HEALTH PLAN INTERFACE CODE
			} else if (event.getSource() == reviewTreatmt) {
				//REVIEW TREATMENT INTERFACE CODE
			} else {
				//RECORD PAYMENT INTERFACE CODE
			}
		}
	}
}