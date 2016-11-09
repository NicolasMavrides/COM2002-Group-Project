import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BookAppointment extends JFrame {
	//main page buttons
	private JLabel name;
	private JLabel surname;
	private JLabel date;
	private JTextField namef;
	private JTextField surnamef;
	private JTextField datef;
	private JButton book;
	
	//Constructor for the main page GUI
	public BookAppointment() {
		super("Book Appointment");
		setLayout(new GridLayout(4,2));
		setSize(400,150);
	    setLocationRelativeTo(null);
		setResizable(false);
	    setVisible(true);
	    
	    //construct text labels/fields/button
	    name = new JLabel("Name: ");
	    surname = new JLabel("Surname: ");
	    date = new JLabel("Date: ");
	    namef = new JTextField();
	    surnamef = new JTextField();
	    datef = new JTextField();
		book = new JButton("Book");
		
		//add buttons
		add(name);
		add(namef);
		add(surname);
		add(surnamef);
		add(date);
		add(datef);
		add(book);

		//Declare new object for event handler
		EventHandler eHandler = new EventHandler();
		//add action listeners to buttons
		namef.addActionListener(eHandler);
		surnamef.addActionListener(eHandler);
		datef.addActionListener(eHandler);
		book.addActionListener(eHandler);
	}
	
	//Create class for action listener.
	private class EventHandler implements ActionListener {
		//action listener method
		public void actionPerformed(ActionEvent event) {	
			if (event.getSource() == book) {
			//CODE TO GET INFO FROM TEXT FIELDS AND PUT INTO DB WHEN BOOK BUTTON IS PRESSED.
			}
		}
	}
}