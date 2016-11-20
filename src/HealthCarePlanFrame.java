import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
import java.sql.ResultSet;
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
<<<<<<< HEAD
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
=======
import javax.swing.JFrame;
import javax.swing.JLabel;
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
import javax.swing.JTextField;

public class HealthCarePlanFrame extends JFrame {
	private JLabel patientID;
	private JLabel planDesc;
	private JTextField patientIDf;
	private JTextField planDetails;
	private JButton subNHS;
	private JButton subMaintenance;
	private JButton subOralHealth;
	private JButton subDRepair;
	private JButton submit;
<<<<<<< HEAD
	private JComboBox planList;
	private String[] healthCarePlans;
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	
	public HealthCarePlanFrame() {
		super("Subscribe Patient to Health Plan");
		setLayout(new GridLayout(5,2));
		setSize(400,150);
		setResizable(false);
	    setLocationRelativeTo(null);
<<<<<<< HEAD
	    String[] healthCarePlans = { "Maintenance Plan", "Oral Health Plan", "Dental Repair Plan", "NHS Free Plan" };
=======
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
	    
	    //construct buttons and their labels
	    patientID = new JLabel("Patient ID: ");
	    planDesc = new JLabel("Plan description: ");
	    patientIDf = new JTextField();
	    planDetails = new JTextField();
<<<<<<< HEAD
	    planList = new JComboBox(healthCarePlans);
	    planList.setSelectedIndex(1);
=======
		subNHS = new JButton("NHS Free Plan");
		subMaintenance = new JButton("Maintenance Plan");
		subOralHealth = new JButton("Oral Health Plan");
		subDRepair = new JButton("Dental Repair Plan");
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
		submit = new JButton("Submit");
		
		//add buttons
		add(patientID);
		add(patientIDf);
<<<<<<< HEAD
		add(planDesc);
		add(planDetails);
		planDetails.setEditable(false);
		add(planList);
=======
		add(subNHS);
		add(subMaintenance);
		add(subOralHealth);
		add(subDRepair);
		add(planDesc);
		add(planDetails);
		planDetails.setEditable(false);
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
		add(submit);
		
		//USED TO SHOW THE OPTIONS BEFORE SUBMITTING IN PLANDETAILS FIELD
		class EventHandler implements ActionListener {
			public void actionPerformed(ActionEvent event) {
<<<<<<< HEAD
				if (event.getSource() == planList && planList.getSelectedItem() == "Maintenance Plan") {
					planDetails.setText("2 checks, 2 hygene visits");

				} else if (event.getSource() == planList && planList.getSelectedItem() == "Oral Health Plan") {
					planDetails.setText("2 checks, 4 hygene visits");

				} else if (event.getSource() == planList && planList.getSelectedItem() == "Dental Repair Plan") {
					planDetails.setText("2 checks, 4 hygene visits, 2 repairs");

				} else if (event.getSource() == planList && planList.getSelectedItem() == "NHS Free Plan") {
					planDetails.setText("2 checks, 2 hygene visits, 6 repairs");

				} else if (event.getSource() == submit) {
					HealthCareQueryProcessor execute = new HealthCareQueryProcessor();
					execute.memberExists(Integer.parseInt(patientIDf.getText()));
					try {
						if (execute.planExists(Integer.parseInt(patientIDf.getText())))
							JOptionPane.showMessageDialog(null, "This member is already subscribed to a Health Care Plan", null, JOptionPane.ERROR_MESSAGE);
						else { 
							execute.SubscribeHealthPlan(Integer.parseInt(patientIDf.getText()), planList.getSelectedItem().toString());
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
=======
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
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
				}
			}
		}
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
<<<<<<< HEAD
		submit.addActionListener(eHandler);
		planList.addActionListener(eHandler);
=======
		subNHS.addActionListener(eHandler);
		subMaintenance.addActionListener(eHandler);
		subOralHealth.addActionListener(eHandler);
		subDRepair.addActionListener(eHandler);
		submit.addActionListener(eHandler);
		
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new HealthCarePlanFrame();
	}
	
	public class HealthCareQueryProcessor extends QueryProcessor {
		public HealthCareQueryProcessor() {
			super();
		}
<<<<<<< HEAD
		public void SubscribeHealthPlan(int id, String name) throws SQLException {
		 Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Healthcare_Plan (Patient_ID, Prepaid_Checkups, Prepaid_Hygiene, Prepaid_Repairs, Name) "
					+ "VALUES ('"+id+"','0','0','0','"+name+"')");
		}
		public boolean planExists(int id) throws SQLException {
			Statement stmt = con.createStatement();
		    ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM Healthcare_Plan WHERE Patient_ID = "+id);
		    res.next();
		    return res.getInt(1)==1;
		}
	}
=======
		public void SubscribeHealthPlan(String id, String title, String name, String surname, String dob, String phoneNum, int houseNum, 
				String streetf, String postCode, String city) throws SQLException {
		 Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Patient () VALUES ()");
		}
	}
	
>>>>>>> 2a780cfdcf7aa0109d1cc485f29e3d581eaefd8f
}