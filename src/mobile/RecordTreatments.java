package mobile;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RecordTreatments extends JFrame {
	//main page buttons
	private JButton viewCalendar;
	private JButton recordTreatment;
	
	public RecordTreatments() {
		super("Record Treatments");
		setLayout(new GridLayout(4,1));
		setSize(500,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	}
}