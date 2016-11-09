import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RegisterPatient extends JFrame {
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

	//text fields
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
	
	//Constructor for patient registration form
	public RegisterPatient() {
		super("Register Patient");
		setLayout(new GridLayout(5,2));
		setSize(800,150);
		//setResizable(false);
		
		//initialise labels
		JLabel name = new JLabel("Name: ");
		JLabel surname = new JLabel("Surname: ");
		JLabel dob = new JLabel("Date of birth: ");
		JLabel phoneNum = new JLabel("Contact number: ");
		JLabel houseNum = new JLabel("House no.: ");
		JLabel street = new JLabel("Street name: ");
		JLabel district = new JLabel("District: ");
		JLabel city = new JLabel("City: ");
		JLabel postCode = new JLabel("Post code: ");
		
		//initialise text fields
		JTextField namef = new JTextField();
		JTextField surnamef = new JTextField();
		JTextField dobf = new JTextField();
		JTextField phoneNumf = new JTextField();
		JTextField houseNumf = new JTextField("House no.: ");
		JTextField streetf = new JTextField();
		JTextField districtf = new JTextField();
		JTextField cityf = new JTextField();
		JTextField postCodef = new JTextField();

		//initialise submit button
		JButton submit = new JButton("Submit");
		
		//add labels and text fields
		add(name);
		add(namef);
		add(surname);
		add(surnamef);
		add(dob);
		add(dobf);
		add(phoneNum);
		add(phoneNumf);
		add(street);
		add(streetf);
		add(district);
		add(districtf);
		add(city);
		add(cityf);
		add(postCode);
		add(postCodef);
		
		//add submit button
		add(submit);
		
		//event handler object
		EventHandler eHandler = new EventHandler();
		
		//namef.addActionListener(eHandler);
		//surnamef.addActionListener(eHandler);
		//dobf.addActionListener(eHandler);
		//phoneNumf.addActionListener(eHandler);
		//houseNumf.addActionListener(eHandler);
		//streetf.addActionListener(eHandler);
		//districtf.addActionListener(eHandler);
		//cityf.addActionListener(eHandler);
		//postCodef.addActionListener(eHandler);
		submit.addActionListener(eHandler);

	}
	
	//Create class for action listener.
		private class EventHandler implements ActionListener {
			//action listener method
			public void actionPerformed(ActionEvent event) {
				System.out.println(namef);
				if (event.getSource() == submit) {
					System.out.println("Submitted");
					//VIEW CALENDAR INTERFACE CODE
				} else {
					System.out.println("OOPS");
					//BOOK APPOINTMENT INTERFACE CODE
				}
			}
		}
}