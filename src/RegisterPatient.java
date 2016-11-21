import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class RegisterPatient extends JFrame {
	//labels
	private JLabel id;
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

	//text fields
	private JTextField idf;
	private JTextField titlef;
	private JTextField namef;
	private JTextField surnamef;
	private JTextField dobf;
	private JTextField phoneNumf;
	private JTextField houseNumf;
	private JTextField streetf;
	private JTextField districtf;
	private JTextField cityf;
	private JTextField postCodef;

	//Jbutton
	private JButton submit;
	
	//Constructor for the main page GUI
	public RegisterPatient() {
		super("Register Patient");
		setLayout(new GridLayout(12,2));
		setSize(500,250);
	    setLocationRelativeTo(null);
		setResizable(false);
	    
	    //construct text labels/fields/button
		id = new JLabel("ID: ");
	    title = new JLabel("Title: ");
	    name = new JLabel("Name: ");
	    surname = new JLabel("Surname: ");
	    dob = new JLabel("Date of Birth: ");
	    phoneNum = new JLabel("Phone number: ");
	    houseNum = new JLabel("House number: ");
	    street = new JLabel("Street: ");
	    district = new JLabel("District: ");
	    city = new JLabel("City: ");
	    postCode = new JLabel("Post code: ");
	    
	    idf = new JTextField();
	    titlef = new JTextField();
	    namef = new JTextField();
	    surnamef = new JTextField();
	    dobf = new JTextField();
	    phoneNumf = new JTextField();
	    houseNumf = new JTextField();
	    streetf = new JTextField();
	    districtf = new JTextField();
	    cityf = new JTextField();
	    postCodef = new JTextField();

	    submit = new JButton("Submit");
	    
	    add(id);
	    add(idf);
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
		add(submit);
		
		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		idf.addActionListener(eHandler);
		titlef.addActionListener(eHandler);
		namef.addActionListener(eHandler);
		surnamef.addActionListener(eHandler);
		dobf.addActionListener(eHandler);
		phoneNumf.addActionListener(eHandler);
		houseNumf.addActionListener(eHandler);
		streetf.addActionListener(eHandler);
		districtf.addActionListener(eHandler);
		cityf.addActionListener(eHandler);
		postCodef.addActionListener(eHandler);
		submit.addActionListener(eHandler);
	    setVisible(true);

	}
	
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		//action listener method
		public void actionPerformed(ActionEvent event) {	
			if (event.getSource() == submit) {
				RegisterPatientQueryProcessor execute = new RegisterPatientQueryProcessor();
				try {
					execute.AddPatient(idf.getText(), titlef.getText(), namef.getText(), surnamef.getText(), dobf.getText(), 
							phoneNumf.getText(), Integer.parseInt(houseNumf.getText()), streetf.getText(), postCodef.getText(), districtf.getText(), cityf.getText());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public class RegisterPatientQueryProcessor extends QueryProcessor {
		public RegisterPatientQueryProcessor() {
			super();
		}
		public void AddPatient(String id, String title, String name, String surname, String dob, String phoneNum, int houseNum, 
				String streetf, String postCode, String district, String city) throws SQLException {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO Patient (Patient_ID, Title, Forename, Surname, Birth_Date, Phone_no, House_No, Post_Code, City) "
					+ "VALUES ("+id+", '"+title+"', '"+name+"', '"+surname+"', '"+dob+"', '"+phoneNum+"', '"+houseNum+"', '"+postCode+"', '"+city+"')");
		}
	}
}