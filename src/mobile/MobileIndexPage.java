package mobile;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


@SuppressWarnings("serial")

public class MobileIndexPage extends JFrame {
	//main page buttons
	private JButton viewCalendar;
	private JButton recordTreatment;
	
	//Constructor for the main page GUI
	public MobileIndexPage() {
		super("Mobile Dental System");
		setLayout(new GridLayout(4,2));
		setSize(500,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    
	    //construct buttons and their labels
		viewCalendar = new JButton("View Calendar");
		recordTreatment = new JButton("Record Treatments");
		
		//add buttons
		add(viewCalendar);
		add(recordTreatment);
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		viewCalendar.addActionListener(eHandler);
		recordTreatment.addActionListener(eHandler);
		
		setVisible(true);
	}
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {	
			if (event.getSource() == viewCalendar) {
				//new ();
				//View all their appointments for a given day, seeing clearly which patient is up for the next
				//appointment on a given day. 
			} else if (event.getSource() == recordTreatment) {
				new RecordTreatments();
				//Record one or more treatments given to the patient in the last consultation, indicating the
				//kind of treatment and its cost.
				//Table: Operation
			}
		}
	}
	
	public static void main(String[] args) {
		new MobileIndexPage();
	}
}