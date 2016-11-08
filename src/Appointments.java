import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Appointments extends JFrame {
	private JLabel item1;
	
	public Appointments() {
		super("Dental System");
		setLayout(new FlowLayout());
		
		item1 = new JLabel("This is a label");
	}
}