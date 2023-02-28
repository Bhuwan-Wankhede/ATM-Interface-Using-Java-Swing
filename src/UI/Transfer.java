package UI;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Database.Connector;

public class Transfer 
{
	static JFrame tf = new JFrame("Transfer");
	 private static JTextField tx; // Transfer to Account
	 private static JTextField ps1; //Amount
	 private static JLabel lb4;
	 static JPasswordField  ps2 ; // Pin

	
	public static void transfer() throws ClassNotFoundException, SQLException 
	{
	 JFrame tf = new JFrame("Transfer"); 
	 JLabel lb1 = new JLabel("Enter Account Number ");
	 lb1.setBounds(120,70,200,30);
	 lb1.setFont(new Font("Calibri",Font.PLAIN,19));
	 
	 JLabel lb2 = new JLabel("Enter Amount ");
	 lb2.setBounds(120,250,200,30);
	 lb2.setFont(new Font("Calibri",Font.PLAIN,19));
	 
	 JLabel lb3 = new JLabel("Enter Pin");
	 lb3.setBounds(120,430,200,30);
	 lb3.setFont(new Font("Calibri",Font.PLAIN,19));
	 

//	 String dt = dtf.format(now);
//	 lb4.setText(dt);
//	 lb4.setBounds(120,5,300,50);
	 
	 tx = new JTextField();
	 tx.setBounds(120,120,300,50);
	 
	 ps1 = new JTextField();
	 ps1.setBounds(120,300,300,50);
	 
	 ps2 = new JPasswordField();
	 ps2.setBounds(120,470,300,50);

	 JButton btn1 = new JButton("Proceed");
	 btn1.setBounds(220,580,100,50);
	 btn1.setFocusPainted(false);
	 btn1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				transferBackend();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		 });
	 tf.add(btn1);
	 tf.add(lb1);
	 tf.add(lb2);
	 tf.add(lb3);
	 tf.add(tx);
	 tf.add(ps1);
	 tf.add(ps2);
	 tf.setResizable(false);
	 tf.setSize(600,750);
	 tf.setLayout(null);
	 tf.setVisible(true);
	}
	private static void transferBackend() throws ClassNotFoundException, SQLException, HeadlessException {
		 String ID = Login.tx1.getText();
		 float bal;
//		 String TransferAccN = tx.getText();
		 float Amount = Float.parseFloat(ps1.getText());
		 int Pin = Integer.parseInt(ps2.getText());
		 
		 
// User Account
		 Connection con = Database.Connector.getConnection();
		 Statement st=con.createStatement();
		 String SQL = "select * from atm_interface.details where USERID = '"+ID+"' and pin = '"+Pin+"'";
		 ResultSet sout = st.executeQuery(SQL);
		 
		 if(sout.next()) {
			 bal =  sout.getFloat("Balance");
			 if(bal>Amount)
			 {	 
				 bal = bal - Amount;
				 int check = st.executeUpdate("update atm_interface.details set Balance = '"+bal+"' where USERID ='"+ID+"'");
				 if (check >= 0) 
				 	{
					 transferBackend1();
					 Store_debit();
					 Store_credit();
					 
			      	}
			     else
			         {
			      	   JOptionPane.showMessageDialog(tf,"Transaction Failed", "alert", JOptionPane.ERROR_MESSAGE );
			         }
			  }
			 else {
				 JOptionPane.showMessageDialog(tf,"Not Enough Balance", "alert", JOptionPane.ERROR_MESSAGE );
			 }
			 }
		 con.close(); 
		 }
		 
	private static void transferBackend1() throws ClassNotFoundException, SQLException {
		// Transfer to Account
		 
		 String TransferAccN = tx.getText();
		 float Amount = Float.parseFloat(ps1.getText());
		 float bal1=0;
		 String Id = null;

		 Connection con = Database.Connector.getConnection();
		 Statement st=con.createStatement();
		 String SQL = "select * from atm_interface.details where AccNum = '"+TransferAccN+"'";
		 ResultSet sout = st.executeQuery(SQL);
		 if(sout.next()) {	 
			 
	         bal1 =  sout.getFloat("Balance");
			 Id = sout.getString("USERID");
			 bal1 = bal1+Amount;
			 }
			 int check1 = st.executeUpdate("update atm_interface.details set Balance = '"+bal1+"' where USERID = '"+ Id+"' and AccNum ='"+TransferAccN+"'");
			 if (check1 >=0) 
			     {
				 JOptionPane.showMessageDialog(tf, "Transaction Successful");
		         }
		         else
		         {
		      	  JOptionPane.showMessageDialog(tf,"Transaction Failed", "alert", JOptionPane.ERROR_MESSAGE );
		         }
		 }
		 

	protected static void Store_debit() throws ClassNotFoundException, SQLException{
		 
		 String ID = Login.tx1.getText();
		 String TransferAccN = tx.getText();
		 float Amount = Float.parseFloat(ps1.getText());
		 int AccN = 0;
		 float Balance = 0;
		 String Nt = "Debit";
		 
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 LocalDateTime now = LocalDateTime.now();
		 
		 // Getting Name and AccN of Sender
		    Connection con0= Connector.getConnection();
			Statement st0=con0.createStatement();
			String SQL = "select * from atm_interface.details where USERID = '"+ID+"'";
			 ResultSet sout = st0.executeQuery(SQL);
			 if(sout.next()) {
		      	 AccN = Integer.parseInt(sout.getString("AccNum"));
		      	 Balance = Float.parseFloat(sout.getString("Balance"));
			 }
			 
	     // Adding to Database
		    Connection con= Connector.getConnection();
			Statement st=con.createStatement();
		    int a = st.executeUpdate("INSERT INTO  atm_interface.transfer_history VALUES('"+AccN+"',"+TransferAccN+",'"+Amount+"','"+Nt+"','"+Balance+"','"+dtf.format(now)+"')");
		    con0.close();
		    con.close();
	}
	
	private static void Store_credit() throws ClassNotFoundException, SQLException{
		 String ID = Login.tx1.getText();
		 String TransferAccN = tx.getText();
		 float Amount = Float.parseFloat(ps1.getText());
		 int AccNsender = 0;
		 float Balance = 0;
		 String Nt = "Credit";
//		 String Id = Transfer.Store_debit();
//		 System.out.println(Id);
		 
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		 LocalDateTime now = LocalDateTime.now();
		 
		 // Getting Balance and AccN of Receiver
		    Connection con0= Connector.getConnection();
			Statement st0=con0.createStatement();
			String SQL = "select * from atm_interface.details where AccNum= '"+TransferAccN+"'";
			 ResultSet sout = st0.executeQuery(SQL);
			 if(sout.next()) {
		      	 Balance = Float.parseFloat(sout.getString("Balance"));
			 }
			 
			 // Getting Name and AccN of Sender
			    Connection con1= Connector.getConnection();
				Statement st1=con1.createStatement();
				String SQL1 = "select * from atm_interface.details where USERID = '"+ID+"'";
				 ResultSet sout1 = st1.executeQuery(SQL1);
				 if(sout1.next()) {
			      	 AccNsender = Integer.parseInt(sout1.getString("AccNum"));			     	 
				 }
			 
	     // Adding to Database
		    Connection con= Connector.getConnection();
			Statement st=con.createStatement();
		    int check = st.executeUpdate("INSERT INTO  atm_interface.transfer_history VALUES('"+TransferAccN+"',"+AccNsender+",'"+Amount+"','"+Nt+"','"+Balance+"','"+dtf.format(now)+"')");
		    if(check>=0) {
		    	System.out.println("Added");
		    	tf.dispose();
		    }
		    else {
		    	System.out.println("Failed to add");
		    }
		    con0.close();
		    con1.close();
		    con.close();
	}
}
