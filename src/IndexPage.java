<<<<<<< HEAD
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

=======
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
public class IndexPage extends JFrame {
	//main page buttons
	private JButton viewCalendar;
	private JButton regPatient;
	private JButton subHealthPlan;
	private JButton reviewTreatmt;
<<<<<<< HEAD
	private JButton recordPaymt;
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	
	//Constructor for the main page GUI
	public IndexPage() {
		super("Dental System");
<<<<<<< HEAD
		setLayout(new GridLayout(5,1));
		setSize(500,200);
		setResizable(false);
=======
		setLayout(new GridLayout(4,1));
		setSize(500,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	    setLocationRelativeTo(null);
	    
	    //construct buttons and their labels
		viewCalendar = new JButton("View Calendar");
		regPatient = new JButton("Register Patient");
		subHealthPlan = new JButton("Subscribe Patient to Health Plan");
		reviewTreatmt = new JButton("Review Patient Treatments");
<<<<<<< HEAD
		recordPaymt = new JButton("Record Patient Payment");
		//TODO: add register patient button
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
		
		//add buttons
		add(viewCalendar);
		add(regPatient);
		add(subHealthPlan);
		add(reviewTreatmt);
<<<<<<< HEAD
		add(recordPaymt);
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		//add action listeners to buttons
		viewCalendar.addActionListener(eHandler);
		regPatient.addActionListener(eHandler);
		subHealthPlan.addActionListener(eHandler);
<<<<<<< HEAD
		//reviewTreatmt.addActionListener(eHandler);
		recordPaymt.addActionListener(eHandler);
=======
		
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
		
		reviewTreatmt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.valueOf(JOptionPane.showInputDialog("Enter member id:")); 
<<<<<<< HEAD
					QueryProcessor qp = new QueryProcessor();
=======
					TreatmentQueryProcessor qp = new TreatmentQueryProcessor();
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
					
					if (!qp.memberExists(id)) {
						throw new InputException("Member doesn't exist.");
					}
<<<<<<< HEAD
=======
					if (!qp.hasUnpaidAppointments(id)) {
						throw new InputException("No treatments found.");
					}
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
					qp.close();
					new ReviewTreatmentsFrame(id);
					
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Invalid member ID.", null, JOptionPane.ERROR_MESSAGE);
				}
				catch (InputException ie) {
					JOptionPane.showMessageDialog(null, ie.getMsg(), null, JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
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
				new HealthCarePlanFrame();
			} else if (event.getSource() == reviewTreatmt) {
				//REVIEW TREATMENT INTERFACE CODE
			} else {
				//RECORD PAYMENT INTERFACE CODE
			}
		}
	}
	
<<<<<<< HEAD
=======
	
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	private class InputException extends Exception {
		String errorMsg;
		InputException(String s) {
			errorMsg = s;
		}
		String getMsg() {return errorMsg;}
	}
	
	public static void main(String[] args) {
		new IndexPage();
	}
}