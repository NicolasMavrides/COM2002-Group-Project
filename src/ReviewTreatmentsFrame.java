import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



@SuppressWarnings("serial")
public class ReviewTreatmentsFrame extends JFrame {
	
	public ReviewTreatmentsFrame(int id) {
		super("Review Patient Treatments");
		setLayout(new BorderLayout());
		setSize(500,200);
		//setResizable(false);
	    setLocationRelativeTo(null);
	    
	    TreatmentQueryProcessor tqp = new TreatmentQueryProcessor();
	    String patientName = tqp.getPatientFullName(id);
	    ArrayList<Treatment> treatments = tqp.getUnpaidTreatments(id);
	    HealthCarePlan hcp = tqp.getHealthCarePlan(id);
	    tqp.close();
	    
	    
	    final int N_TREATMENTS= treatments.size();
	    String[] columnNames = {"Name","Individual Cost","Covered by Healthcare Plan"};
	    Object[][] data = new Object[N_TREATMENTS][3];
	    
	    int remCheckups = 0;
		int remHygiene = 0;
		int remRepairs = 0; 
		
		
	    if (hcp != null) {
	    	remCheckups = hcp.getCoveredCheckups();
	    	remHygiene = hcp.getCoveredHygiene();
	    	remRepairs = hcp.getCoveredRepairs();
	    }
	    
		
	    int costSum =0;
	    for (int i=0;i<N_TREATMENTS;i++) {
	    	Treatment t = treatments.get(i);
	    	int cost = t.getCost();
	    	boolean prepaid = false;
	    	
	    	
	    	switch (t.getType()) {
	    		case CHECKUP:
	    			if (remCheckups>0) {
	    				prepaid = true;
	    				remCheckups --;
	    			}
	    			break;
	    		case HYGIENE: 
	    			if (remHygiene>0) {
	    				prepaid = true;
	    				remHygiene --;
	    			}
	    			break;
	    		case REPAIR:
	    			if (remRepairs>0) {
	    				prepaid = true;
	    				remRepairs --;
	    			}
	    			break;
	    	}
	    	
	    	data[i][0] = t.getName();
	    	data[i][1] = cost;
	    	if (prepaid) {
	    		data[i][2] = "Yes";
	    	}
	    	else {
	    		costSum+=cost;
	    		data[i][2] = "No";
	    	}
	    	
	    }
	    
	    if (hcp!=null) {
	    	hcp.setRemCheckups(remCheckups);
		    hcp.setRemHygiene(remHygiene);
		    hcp.setRemRepairs(remRepairs);
	    }
	    
	    
	    
	    JTable table = new JTable(data, columnNames);
		table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel paymentPanel = new JPanel(new FlowLayout());
        JButton payButton = new JButton("Record Payment");
        paymentPanel.add(new JLabel("Final Cost: " + costSum));
        paymentPanel.add(payButton);
        
        payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TreatmentQueryProcessor tqp = new TreatmentQueryProcessor();
				tqp.recordPayment(id);
				
				JPanel confirmationPane = new JPanel(new GridLayout(0,1));
				confirmationPane.add(new JLabel("Payment Recorded."));
				if (hcp!=null) {
					tqp.updateHealthCarePlan(id, hcp.getUsedCheckups(), hcp.getUsedHygiene(), hcp.getCoveredRepairs());
					
					confirmationPane.add(new JLabel("Remaining checkups: " + hcp.getRemCheckups()));
					confirmationPane.add(new JLabel("Remaining hygiene visits: " + hcp.getRemHygiene()));
					confirmationPane.add(new JLabel("Remaining repairs: " + hcp.getRemRepairs()));
				}
				tqp.close();
				
				
				
				
				JOptionPane.showMessageDialog(null, confirmationPane);
				ReviewTreatmentsFrame.this.dispose();
				
			}
		});
        
        add(new JLabel(patientName),BorderLayout.NORTH);
	    add(scrollPane,BorderLayout.CENTER);
	    add(paymentPanel,BorderLayout.SOUTH);
		setVisible(true);
	}
	
	
	public static class HealthCarePlan {
		private int coveredCheckups;
		private int coveredHygiene;
		private int coveredRepairs;
		
		private int remCheckups;
		private int remHygiene;
		private int remRepairs;
		
		public HealthCarePlan(int c, int h,int r) {
			coveredCheckups = c;
			coveredHygiene = h;
			coveredRepairs = r;
		}
		
		public int getCoveredCheckups() { return coveredCheckups; }
		public int getCoveredHygiene() { return coveredHygiene; }
		public int getCoveredRepairs() { return coveredRepairs; }
		
		public void setRemCheckups(int c) { remCheckups = c; }
		public void setRemHygiene(int h) { remHygiene = h; }
		public void setRemRepairs(int r) { remRepairs = r; }
		
		public int getRemCheckups() { return remCheckups; }
		public int getRemHygiene() { return remHygiene; }
		public int getRemRepairs() { return remRepairs; }
		
		public int getUsedCheckups() {return coveredCheckups-remCheckups;}
		public int getUsedHygiene() {return coveredHygiene-remHygiene;}
		public int getUsedRepairs() {return coveredRepairs-remRepairs;}

		

		public String toString() { return coveredCheckups + " " + coveredHygiene + " " + coveredRepairs; }
		
	}
	
	public static void main(String[] args) {
		new ReviewTreatmentsFrame(2);
	}
	
}