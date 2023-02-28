package UI;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Transferhistory {
  static JFrame frame1;
  private static JTable table;
  
    
	public static void tableUI() {
		String[] columnNames = {"Sender AccountNumber","Receiver AccountNumber","Amount","Transaction","Balance","Date"};
	
	    JFrame frame1 = new JFrame("Transaction History");
        frame1.setLayout(new BorderLayout());
	    DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        
        JScrollPane scroll = new JScrollPane(table); 
        scroll.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
         String ID = Login.tx1.getText();
        
		 String saccNum="";
		 String raccNum="";
		 float amt,bal;
		 String Trans="";
		 String Date = "";
		 String AccNum = null;
		 
		try {
			Connection con0 = Database.Connector.getConnection();
			Statement st=con0.createStatement();
			
			String SQL = "select * from atm_interface.details where USERID = '"+ID+"'";
			ResultSet sout = st.executeQuery(SQL);
			if (sout.next()) 
			{
				AccNum = sout.getString("AccNum");
			}
			
		 Connection con = Database.Connector.getConnection();
    	 PreparedStatement ps = con.prepareStatement("select * from atm_interface.transfer_history where AccountNumber = '"+AccNum+"'");
    	 ResultSet rs = ps.executeQuery();
    	 int i = 0;
    	 while (rs.next()) {
    		 saccNum = rs.getString(1);
    		 raccNum = rs.getString(2);
    		 amt = rs.getFloat(3);
    		 Trans= rs.getString(4);
    		 bal = rs.getFloat(5);
   		 Date = rs.getString(6);
//    		 System.out.println(""+saccNum+"\t"+raccNum+"\t"+amt+"\t"+Trans+"\t"+bal+"\t"+Date+"");
             model.addRow(new Object[]{saccNum,raccNum,amt,Trans,bal,Date});

            i++;
    	 }
         con0.close();
         con.close();
		}
		catch(Exception e1) {
			 JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
        
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(1000,900);     
	}
}
