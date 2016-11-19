import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HealthCarePlan extends JFrame {
	private JLabel patientID;
	private JLabel planDesc;
	private JTextField patientIDf;
	private JTextField planDetails;
	private JButton subNHS;
	private JButton subMaintenance;
	private JButton subOralHealth;
	private JButton subDRepair;
	private JButton submit;
	
	public HealthCarePlan() {
		super("Subscribe Patient to Health Plan");
		setLayout(new GridLayout(5,2));
		setSize(400,150);
		setResizable(false);
	    setLocationRelativeTo(null);
	    
	    //construct buttons and their labels
	    patientID = new JLabel("Patient ID: ");
	    planDesc = new JLabel("Plan description: ");
	    patientIDf = new JTextField();
	    planDetails = new JTextField();
		subNHS = new JButton("NHS Free Plan");
		subMaintenance = new JButton("Maintenance Plan");
		subOralHealth = new JButton("Oral Health Plan");
		subDRepair = new JButton("Dental Repair Plan");
		submit = new JButton("Submit");
		
		//add buttons
		add(patientID);
		add(patientIDf);
		add(subNHS);
		add(subMaintenance);
		add(subOralHealth);
		add(subDRepair);
		add(planDesc);
		add(planDetails);
		planDetails.setEditable(false);
		add(submit);
		
		//USED TO SHOW THE OPTIONS BEFORE SUBMITTING IN PLANDETAILS FIELD
		class EventHandler implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == subNHS) {	
					planDetails.setText("2 checks, 2 hygene visits, 6 repairs");
					//Children/U18 - no-charge, 2chk, 2hyg, 6rep
				} else if (event.getSource() == subMaintenance) {
					planDetails.setText("2 checks, 2 hygene visits");
					//£15 2chk, 2hyg 
				} else if (event.getSource() == subOralHealth) {
					planDetails.setText("2 checks, 4 hygene visits");
					//£21 4hyg, 2chk 
				} else if (event.getSource() == subDRepair) {
					planDetails.setText("2 checks, 2 hygene visits, 2 repairs");
					//£36 2chk, 2hyg, 2rep
				} else if (event.getSource() == submit) {
					//take the patientID and option picked
					System.out.println("submitted: " + patientIDf.getText() + ", " + planDetails.getText());
				} else {
					//nothing
				}
			}
		}
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		subNHS.addActionListener(eHandler);
		subMaintenance.addActionListener(eHandler);
		subOralHealth.addActionListener(eHandler);
		subDRepair.addActionListener(eHandler);
		submit.addActionListener(eHandler);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new HealthCarePlan();
	}
	
	public class HealthCareQueryProcessor extends QueryProcessor {
		public HealthCareQueryProcessor() {
			super();
		}
		public void SubscribeHealthPlan(String id, String title, String name, String surname, String dob, String phoneNum, int houseNum, 
				String streetf, String postCode, String city) throws SQLException {
		 Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Patient () VALUES ()");
		}
	}
	
}