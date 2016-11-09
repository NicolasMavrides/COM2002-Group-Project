import java.awt.Container;

import javax.swing.*;

public class AppointmentsFrame extends JFrame {
	public AppointmentsFrame() {
		setTitle("Calendar");
		setSize(500,300);
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
