import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ReviewTreatments extends JFrame {
	private JLabel patientID;
	private JLabel treatment;
	private JLabel cost;
	private JTextField patientIDf;
	private JTextField treatmentf;
	private JTextField costf;
	private JButton submit;
	
	public ReviewTreatments() {
		super("Review Patient Treatments");
		setLayout(new GridLayout(4,5));
		setSize(500,200);
		setResizable(false);
	    setLocationRelativeTo(null);
	    
	    //construct buttons and their labels
	    patientID = new JLabel("Patient ID");
	    patientIDf = new JTextField();
	    treatment = new JLabel("Treatment");
	    treatmentf = new JTextField();
	    cost = new JLabel("Cost");
	    costf = new JTextField();
		submit = new JButton("Submit");
		
		//add buttons
		add(patientID);
		add(patientIDf);
		add(treatment);
		add(treatmentf);
		add(cost);
		add(costf);
		add(submit);
		
		//USED TO SHOW THE OPTIONS BEFORE SUBMITTING IN PLANDETAILS FIELD
		class EventHandler implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				if (event.getSource() == submit) {
					//take the patientID, find cost of treatment
					System.out.println(/*get the treatment, get the cost from the database, check against the healthcare plan, print cost*/);
				} else {
					//nothing
				}
			}
		}
		
		EventHandler eHandler = new EventHandler();
		submit.addActionListener(eHandler);

		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ReviewTreatments();
	}
	
}