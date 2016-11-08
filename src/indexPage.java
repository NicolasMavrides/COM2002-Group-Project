import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IndexPage extends JFrame {
	private JButton viewCalendar;
	private JButton bookAptmt;
	private JButton subHealthPlan;
	private JButton reviewTreatmt;
	private JButton recordPaymt;
	
	public IndexPage() {
		super("Dental System");
		setLayout(new GridLayout(5,1));
		setSize(500,200);
		setResizable(false);
	    setLocationRelativeTo(null);
	    
		viewCalendar = new JButton("View Calendar");
		bookAptmt = new JButton("Book Appointment");
		subHealthPlan = new JButton("Subscribe Patient to Health Plan");
		reviewTreatmt = new JButton("Review Patient Treatments");
		recordPaymt = new JButton("Record Patient Payment");
		
		add(viewCalendar);
		add(bookAptmt);
		add(subHealthPlan);
		add(reviewTreatmt);
		add(recordPaymt);
	}
}