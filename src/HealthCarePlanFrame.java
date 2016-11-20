import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JComboBox planList;
	private String[] healthCarePlans;
	
	public HealthCarePlanFrame() {
		super("Subscribe Patient to Health Plan");
		setLayout(new GridLayout(5,2));
		setSize(400,150);
		setResizable(false);
	    setLocationRelativeTo(null);
	    String[] healthCarePlans = { "Maintenance Plan", "Oral Health Plan", "Dental Repair Plan", "NHS Free Plan" };
	    
	    //construct buttons and their labels
	    patientID = new JLabel("Patient ID: ");
	    planDesc = new JLabel("Plan description: ");
	    patientIDf = new JTextField();
	    planDetails = new JTextField();
	    planList = new JComboBox(healthCarePlans);
	    planList.setSelectedIndex(1);
		submit = new JButton("Submit");
		
		//add buttons
		add(patientID);
		add(patientIDf);
		add(planDesc);
		add(planDetails);
		planDetails.setEditable(false);
		add(planList);
		add(submit);
		
		//USED TO SHOW THE OPTIONS BEFORE SUBMITTING IN PLANDETAILS FIELD
		class EventHandler implements ActionListener {
			public void actionPerformed(ActionEvent event) {
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
				}
			}
		}
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		submit.addActionListener(eHandler);
		planList.addActionListener(eHandler);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new HealthCarePlanFrame();
	}
	
	public class HealthCareQueryProcessor extends QueryProcessor {
		public HealthCareQueryProcessor() {
			super();
		}
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
}