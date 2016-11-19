import javax.swing.*;

import java.awt.BorderLayout;
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
	    	System.out.println(hcp.toString());
	    	remCheckups = hcp.getRemCheckups();
	    	remHygiene = hcp.getRemHygiene();
	    	remRepairs = hcp.getRemRepairs();
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
	    
	    JTable table = new JTable(data, columnNames);
		table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(new JLabel(patientName),BorderLayout.NORTH);
	    add(scrollPane,BorderLayout.CENTER);
	    add(new JLabel("Final Cost: " + costSum),BorderLayout.SOUTH);
		setVisible(true);
	}
	
	public static class HealthCarePlan {
		private int remCheckups;
		private int remHygiene;
		private int remRepairs; 
		
		public HealthCarePlan(int remCheckups, int remHygiene,int remRepairs) {
			this.remCheckups = remCheckups;
			this.remHygiene = remHygiene;
			this.remRepairs = remRepairs;
		}

		public int getRemCheckups() { return remCheckups; }
		public int getRemHygiene() { return remHygiene; }
		public int getRemRepairs() { return remRepairs; }
		public String toString() { return remCheckups + " " + remHygiene + " "+ remRepairs; }
		
	}
	
	public static void main(String[] args) {
		new ReviewTreatmentsFrame(1);
	}
	
}