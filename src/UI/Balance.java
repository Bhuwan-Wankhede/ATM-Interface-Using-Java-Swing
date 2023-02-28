package UI;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Balance{
	static JFrame Bal ;

 public	static void balance()throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		  Bal = new JFrame("Balance");
		 
		 String ID = Login.tx1.getText();
		 int AccN;
		 double Balance;
		 JLabel lb3 = null,lb4 = null;
		 
		 Connection con = Database.Connector.getConnection();
		 Statement st=con.createStatement();
		 String SQL = "select * from atm_interface.details where USERID = '"+ID+"'";
		 ResultSet sout = st.executeQuery(SQL);
		 
		 if(sout.next()) {
			 
      	 AccN = Integer.parseInt(sout.getString("AccNum"));
      	 Balance = Float.parseFloat(sout.getString("Balance"));
      	 
      	 lb3 = new JLabel(String.valueOf(AccN));
  		 lb3.setBounds(170,70,200,30);
  		 lb3.setFont(new Font("Calibri",Font.PLAIN,19));
  		 
  		 lb4 = new JLabel(String.valueOf(Balance));
  		 lb4.setBounds(100,150,200,30);
  		 lb4.setFont(new Font("Calibri",Font.PLAIN,19));
		 }

		 JLabel lb1 = new JLabel("Account Number : ");
		 lb1.setBounds(15,70,200,30);
		 lb1.setFont(new Font("Calibri",Font.PLAIN,19));
		 
		 JLabel lb2 = new JLabel("Balance : ");
		 lb2.setBounds(15,150,200,30);
		 lb2.setFont(new Font("Calibri",Font.PLAIN,19));
		 
		 Bal.add(lb1);
		 Bal.add(lb2);
		 Bal.add(lb3);
		 Bal.add(lb4);
		 Bal.setResizable(false);
		 Bal.setSize(400,300);
		 Bal.setLayout(null);
		 Bal.setVisible(true); 
		 con.close();	
	}
}
