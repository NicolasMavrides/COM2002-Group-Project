import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")

public class IndexPage extends JFrame {
	//main page buttons
	private JButton viewCalendar;
	private JButton regPatient;
	private JButton subHealthPlan;
	private JButton reviewTreatmt;

	
	//Constructor for the main page GUI
	public IndexPage() {
		super("Dental System");
		setLayout(new GridLayout(4,1));
		setSize(500,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	    
	    //construct buttons and their labels
		viewCalendar = new JButton("View Calendar");
		regPatient = new JButton("Register Patient");
		subHealthPlan = new JButton("Subscribe Patient to Health Plan");
		reviewTreatmt = new JButton("Review Patient Treatments");
		
		//add buttons
		add(viewCalendar);
		add(regPatient);
		add(subHealthPlan);
		add(reviewTreatmt);
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		//add action listeners to buttons
		viewCalendar.addActionListener(eHandler);
		regPatient.addActionListener(eHandler);
		subHealthPlan.addActionListener(eHandler);

		
		
		reviewTreatmt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.valueOf(JOptionPane.showInputDialog("Enter member id:")); 

					TreatmentQueryProcessor qp = new TreatmentQueryProcessor();

					
					if (!qp.memberExists(id)) {
						throw new InputException("Member doesn't exist.");
					}

					if (!qp.hasUnpaidAppointments(id)) {
						throw new InputException("No treatments found.");
					}
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