import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import com.sun.xml.internal.ws.api.Component;

public class RegistrationFrame extends JFrame {
	//labels
	private JLabel title;
	private JLabel name;
	private JLabel surname;
	private JLabel dob;
	private JLabel phoneNum;
	private JLabel houseNum;
	private JLabel street;
	private JLabel district;
	private JLabel city;
	private JLabel postCode;
	private final int CURRENT_YEAR = DateProcessor.currentYear();

	//text fields
	private JTextField titlef;
	private JTextField namef;
	private JTextField surnamef;
	//private JTextField dobf;
	private DatePane dobf;
	private JTextField phoneNumf;
	private JTextField houseNumf;
	private JTextField streetf;
	private JTextField districtf;
	private JTextField cityf;
	private JTextField postCodef;

	//Jbutton
	private JButton submit;
	
	//Constructor for the main page GUI
	public RegistrationFrame() {
		super("Register Patient");
		setLayout(new GridLayout(0,2));
		//setLayout(new SpringLayout());
		setSize(500,400);
	    setLocationRelativeTo(null);
		setResizable(false);
	    
	    //construct text labels/fields/button
	    title = new JLabel("Title: ");
	    name = new JLabel("Name: ");
	    surname = new JLabel("Surname: ");
	    dob = new JLabel("Date of Birth: ");
	    phoneNum = new JLabel("Phone number: ");
	    street = new JLabel("Street: ");
	    houseNum = new JLabel("House no.: ");
	    district = new JLabel("District: ");
	    city = new JLabel("City: ");
	    postCode = new JLabel("Post code: ");
	    
	    titlef = new JTextField();
	    namef = new JTextField();
	    surnamef = new JTextField();
	    dobf = new DatePane(1900,CURRENT_YEAR,(CURRENT_YEAR-18)+"-01-01");
	    phoneNumf = new JTextField();
	    streetf = new JTextField();
	    houseNumf = new JTextField();
	    districtf = new JTextField();
	    cityf = new JTextField();
	    postCodef = new JTextField();

	    submit = new JButton("Submit");
	    
		add(title);
		add(titlef);
		add(name);
		add(namef);
		add(surname);
		add(surnamef);
		add(dob);
		add(dobf);
		add(phoneNum);
		add(phoneNumf);
		add(houseNum);
		add(houseNumf);
		add(street);
		add(streetf);
		add(district);
		add(districtf);
		add(city);
		add(cityf);
		add(postCode);
		add(postCodef);
		add(new JLabel());
		add(submit);
		
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		submit.addActionListener(eHandler);
	    setVisible(true);

	}
	
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		//action listener method
		public void actionPerformed(ActionEvent event) {	
			
			try {
				checkFields();
				RegisterPatientQueryProcessor qp = new RegisterPatientQueryProcessor();
				int id = qp.AddPatient(titlef.getText(), namef.getText(), surnamef.getText(), dobf.getDate(), 
						phoneNumf.getText(), Integer.parseInt(houseNumf.getText()), streetf.getText(), postCodef.getText(), districtf.getText(), cityf.getText());
				qp.close();
				if (id!=-1) {
					int option = JOptionPane.showConfirmDialog(null, "Member registered with id: "+ id + ". Do you want to register another patient?");
					
					switch (option) {
						case 0: resetFields(); break;
						case 1: RegistrationFrame.this.dispose(); break;
					}
				}
				
				//submit.setEnabled(false);
			}
			catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Invalid house number", null, JOptionPane.ERROR_MESSAGE);
			}
			catch (InputException ie) {
				JOptionPane.showMessageDialog(null, ie.getType(), null, JOptionPane.ERROR_MESSAGE);
			}
			

			
		}
	}
	
	public class RegisterPatientQueryProcessor extends QueryProcessor {
		public RegisterPatientQueryProcessor() {
			super();
		}
		public int AddPatient(String title, String name, String surname, String dob, String phoneNum, int houseNum, 
			String streetf, String postCode, String district, String city) {
			Statement stmt;
			try {
				stmt = con.createStatement();
				ResultSet res = stmt.executeQuery("SELECT MAX(Patient_ID) FROM team029.Patient;");
				res.next();
				int id = res.getInt(1)+1;
				
				//insert new address if it doesn't exist
				res = stmt.executeQuery("SELECT COUNT(*) FROM Address WHERE House_No = "+houseNum+" AND Post_Code = '"+ postCode +"'");
				res.next();
				
				if (res.getInt(1)==0) {
					String query = "INSERT INTO Address (House_No, Street_Name, District_Name, City_Name, Post_Code)"	
							+" VALUES ("+houseNum+", '"+streetf+"', '"+district+"', '"+city+"', '"+postCode+"')";
					stmt.executeUpdate(query);
				}
				
				//register patient
				stmt.executeUpdate("INSERT INTO Patient (Patient_ID, Title, Forename, Surname, Birth_Date, Phone_no, House_No, Post_Code) "
						+ "VALUES ("+id+", '"+title+"', '"+name+"', '"+surname+"', '"+dob+"', '"+phoneNum+"', '"+houseNum+"', '"+postCode+"')");
				
				return id;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
			
		}
		
	}
	
	public void resetFields() {
		Container cp = this.getContentPane();
		for (int i=0; i<cp.getComponentCount();i++) {
			java.awt.Component c = cp.getComponent(i);
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			}
		}
		
	}
	
	public void checkFields() throws InputException {
		Container cp = this.getContentPane();
		for (int i=0; i<cp.getComponentCount();i++) {
			java.awt.Component c = cp.getComponent(i);
			if ((c instanceof JTextField) && (((JTextField) c).getText().isEmpty())) {
				throw new InputException("Some fields are empty. Please fill all the fields.");
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class InputException extends Exception {
		String exeptionType;
		InputException(String s) {
			exeptionType = s;
		}
		String getType() {return exeptionType;}
	}
	
	public static void main(String[] args) {
		RegistrationFrame f = new RegistrationFrame();
		//f.hasEmptyFields();
	}
	
}