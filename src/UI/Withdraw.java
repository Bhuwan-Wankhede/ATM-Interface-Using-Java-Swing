package UI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Database.Connector;

public class Withdraw {
	static JTextField amt;
	 static JPasswordField ps1;
	 static JFrame wd;
	 
	public static void Wd(){
		  wd  = new JFrame("Withdraw");
		 JLabel lb1 = new JLabel("Enter Amount");
		 lb1.setBounds(120,70,200,30);
		 lb1.setFont(new Font("Calibri",Font.PLAIN,19));
		 
		 JLabel lb2 = new JLabel("Enter PIN");
		 lb2.setBounds(120,250,100,30);
		 lb2.setFont(new Font("Calibri",Font.PLAIN,19));
		 
		 amt = new JTextField();
		 amt.setBounds(120,120,300,50);
		 
		  ps1 = new JPasswordField();
		 ps1.setBounds(120,300,300,50);
		 
		 JButton btn1 = new JButton("Proceed");
		 btn1.setBounds(220,420,100,50);
		 btn1.setFocusPainted(false);
		 btn1.addActionListener(new ActionListener() {
		 @Override
				public void actionPerformed(ActionEvent e)
				{ 
						try {
							withDraw();
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
			});
		 wd.add(btn1);
		 wd.add(lb1);
		 wd.add(lb2);
		 wd.add(amt);
		 wd.add(ps1);
		 wd.setResizable(false);
		 wd.setSize(600,600);
		 wd.setLayout(null);
		 wd.setVisible(true);
		 
	}
	 private static void withDraw() throws ClassNotFoundException, SQLException {
		 
		 String ID = Login.tx1.getText();
		 float Amount = Float.parseFloat(amt.getText());
		 int Pin = Integer.parseInt(ps1.getText());
		 float bal = 0;
		 
		 Connection con = Database.Connector.getConnection();
		 Statement st=con.createStatement();
		 String SQL = "select * from atm_interface.details where USERID = '"+ID+"'and pin = '"+Pin+"'";
		 ResultSet sout = st.executeQuery(SQL);
		 if(sout.next()) 
		 {
			 bal = sout.getFloat("Balance");
			 
			 if (bal>Amount) {
			 bal = bal - Amount;
			 
			 int check = st.executeUpdate("update atm_interface.details set Balance = '"+bal+"' where USERID ='"+ID+"'");
			 if (check >= 0) {
	      	   JOptionPane.showMessageDialog(wd, "Collect Your Cash" );
	      	   Store_withdraw();
	         }
	         else {
	      	   JOptionPane.showMessageDialog(wd,"Transaction Failed", "alert", JOptionPane.ERROR_MESSAGE );
	         }
			 }
			 else {
				 JOptionPane.showMessageDialog(wd,"Not Enough Balance", "alert", JOptionPane.ERROR_MESSAGE );
			 }
		 } 
		 con.close();
	 }
	 
private static void Store_withdraw() throws ClassNotFoundException, SQLException{
		 
		 String ID = Login.tx1.getText();
		 String TransferAccN = "NULL";
		 float Amount = Float.parseFloat(amt.getText());
		 int AccN = 0;
		 float Balance = 0;
		 String Nt = "Withdraw";
		 
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
		    int check = st.executeUpdate("INSERT INTO  atm_interface.transfer_history VALUES('"+AccN+"',"+TransferAccN+",'"+Amount+"','"+Nt+"','"+Balance+"','"+dtf.format(now)+"')");
		    if(check>=0) {
		    	System.out.println("Added");
		    	wd.dispose();
		    }
		    else {
		    	System.out.println("Failed to add");
		    }
		    con0.close();
		    con.close();
	}
	 
	protected static void quit()
	{
//		Homepage pg = new Homepage();
//		Transfer trans = new Transfer();
//		Balance b = new Balance();
//		Transferhistory th = new Transferhistory();
//		Withdraw wd = new Withdraw();
		Login.login();
        System.exit(0);
		
	}
}
