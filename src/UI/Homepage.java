package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Homepage {
	static JFrame homepg;
	static JLabel lh1,tag;
	static JButton quit,transfer,withdraw,balance,transferhistory;
	
	public static void homepage() throws ClassNotFoundException, SQLException {
		
	homepg = new JFrame("Welcome to Bank");
	hpData();
	homepg.setForeground(Color.BLUE);
	homepg.setSize(1350,800);
	homepg.setResizable(false);
	homepg.setLayout(null);
	homepg.setVisible(true);	
}
	private static void hpData() throws SQLException, ClassNotFoundException {
		String ID = Login.tx1.getText();
		String AccNum;
		String Name;
		JLabel name = null,AccN = null;
		
		lh1 = new JLabel();
		tag = new JLabel("ATM");
		tag.setBounds(40,10,200,100);
		tag.setFont(new Font("SANS_SERIF", Font.BOLD,52));
		tag.setForeground(Color.WHITE);
		
		Connection con = Database.Connector.getConnection();
		Statement st=con.createStatement();
		
		String SQL = "select * from atm_interface.details where USERID = '"+ID+"'";
		ResultSet sout = st.executeQuery(SQL);
		if (sout.next()) 
		{
			AccNum = sout.getString("AccNum");
			Name = sout.getString("Name");
			
			name = new JLabel(Name);
			name.setBounds(40,250,200,100); 
			name.setFont(new Font("Calibri",Font.BOLD,22));
			name.setForeground(Color.WHITE);
			
			AccN = new JLabel(String.valueOf(AccNum));
			AccN.setBounds(40,400,150,100); 
			AccN.setFont(new Font("Calibri",Font.BOLD,22));
			AccN.setForeground(Color.WHITE);
		}
		
		JLabel welcome = new JLabel("Welcome");
		welcome.setBounds(40,200,100,100);
		welcome.setFont(new Font("Calibri",Font.PLAIN,22));
		welcome.setForeground(Color.cyan);
		
		JLabel Acc = new JLabel("Account Number");
		Acc.setBounds(40,350,200,100);
		Acc.setFont(new Font("Calibri",Font.PLAIN,22));
		Acc.setForeground(Color.cyan);
		
		withdraw = new JButton("Withdraw");
		withdraw.setBounds(500,230,250,60);
		withdraw.setBackground(Color.CYAN);
		withdraw.setForeground(Color.WHITE);		
		withdraw.setFont(new Font("Calibri",Font.PLAIN,30));
		withdraw.setFocusPainted(false);
		withdraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Withdraw.Wd();
			}
		});
		
		transfer = new JButton("Transfer");
		transfer.setBounds(780,230,250,60);
		transfer.setForeground(Color.WHITE);		
		transfer.setBackground(Color.CYAN);
		transfer.setFont(new Font("Calibri",Font.PLAIN,30));
		transfer.setFocusPainted(false);
		transfer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Transfer.transfer();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		balance = new JButton("Balance");
		balance.setBounds(500,320,250,60);
		balance.setForeground(Color.WHITE);		
		balance.setBackground(Color.CYAN);
		balance.setFont(new Font("Calibri",Font.PLAIN,30));
		balance.setFocusPainted(false);
		balance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					UI.Balance.balance();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		transferhistory = new JButton("Transaction History");
		transferhistory.setBounds(780,320,250,60);
		transferhistory.setForeground(Color.WHITE);		
		transferhistory.setBackground(Color.CYAN);
		transferhistory.setFont(new Font("Calibri",Font.PLAIN,23));
		transferhistory.setFocusPainted(false);
		transferhistory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UI.Transferhistory.tableUI();
				
			}
			
		});
		
		quit = new JButton("Quit");
		quit.setBounds(500,500,530,60);
		quit.setForeground(Color.WHITE);		
		quit.setBackground(Color.RED);
		quit.setFont(new Font("Calibri",Font.BOLD,32));
		quit.setFocusPainted(false);
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Withdraw.quit();
			}
			
		});
		
		// Creating container for Image
		Container c = homepg.getContentPane(); //Gets the content layer
        lh1.setIcon(new ImageIcon("D:\\Eclipse_WSpace\\ATM interface\\src\\UI\\background1.jpg")); 
        Dimension size = lh1.getPreferredSize(); //Gets the size of the image
        lh1.setBounds(0, 0, size.width, size.height); //Sets the location of the image
        
        
        c.add(withdraw);
      	homepg.add(withdraw);
        
        c.add(transfer);
      	homepg.add(transfer);
        
        c.add(balance);
      	homepg.add(balance);
        
        c.add(transferhistory);
      	homepg.add(transferhistory);
        
        c.add(quit);
      	homepg.add(quit);
      	
        c.add(AccN);
//      homepg.add(AccNum);
      	
        c.add(name);

      	
        c.add(Acc);
		homepg.add(Acc);
        
		c.add(welcome);
		homepg.add(welcome);
        c.add(tag);
		homepg.add(tag);
		
        c.add(lh1); //Adds objects to the container '
		homepg.add(lh1);

	}
}