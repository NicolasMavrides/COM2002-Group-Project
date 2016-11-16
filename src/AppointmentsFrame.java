import java.awt.Container;

import javax.swing.*;

@SuppressWarnings("serial")
public class AppointmentsFrame extends JFrame {
	public AppointmentsFrame() {
		setTitle("Calendar");
		setSize(750,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		contentPane.add(new TabbedAppointmentsPane());
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
<<<<<<< HEAD
		new AppointmentsFrame();
		//new BookAppointment();
=======
		//new AppointmentsFrame(); --calendar 
		//new BookAppointment(); -- book appointments gui
		new RegisterPatient();
>>>>>>> 1fa4742cb39cb52fadea710022475bb85d0476b4
	}
}
