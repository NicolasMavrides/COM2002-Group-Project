import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BookAppointment extends JFrame {	
	private JLabel name;
	private JLabel surname;
	private JLabel date;
	private JTextField namef;
	private JTextField surnamef;
	private JTextField datef;
	private JButton book;
	
	public BookAppointment() {
		super("Book Appointment");
		setLayout(new GridLayout(5,1));
		setSize(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		

		JLabel name = new JLabel("Name: ");
		JLabel surname = new JLabel("Surname: ");
		JLabel date = new JLabel("Date: ");
		
		JTextField namef = new JTextField();
		JTextField surnamef = new JTextField();
		JTextField datef = new JTextField();
		
		JButton book = new JButton("Book");

		
		add(name);
		add(namef);
		add(surname);
		add(surnamef);
		add(date);
		add(datef);
		add(book);
		
		EventHandler eHandler = new EventHandler();
		
		namef.addActionListener(eHandler);
		surnamef.addActionListener(eHandler);
		datef.addActionListener(eHandler);
		book.addActionListener(eHandler);
		
	}
	
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		//action listener method
		public void actionPerformed(ActionEvent event) {
			System.out.println(event.getSource());
			if (event.getSource() == book) {
				System.out.println("Booked");
			} else {
				System.out.println("OOPS");
			}
			
		}
	}
}