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
		new AppointmentsFrame();
		new BookAppointment();
	}
}
