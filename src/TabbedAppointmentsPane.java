import java.awt.GridLayout;
import javax.swing.*;

public class TabbedAppointmentsPane extends JPanel {
	public TabbedAppointmentsPane() {
		super(new GridLayout(1, 1));
		JTabbedPane tabbedPane = new JTabbedPane();
         
        JComponent dentistPanel = makeTextPanel("Calendar1");
        JComponent cal = new Calendar();
        tabbedPane.addTab("Dentist", cal);
        
        JComponent hygPanel = makeTextPanel("Calendar 2");
        tabbedPane.addTab("Hygieniest", new Calendar());
        
        add(tabbedPane);
        
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        //panel.add(new Calendar());
        return panel;
    }
	
}
