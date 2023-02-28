package UI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
	static JPasswordField tx2 ;
	public static JTextField tx1;
	static JFrame login,Homepg;

	public static void login() {
		JLabel l1,l2,l3,l4,img,img2,hyperlink; 
		JButton b1;
		JCheckBox cb1;
		login = new JFrame("ATM Interface");
	
		l1 = new JLabel("Welcome To XYZ Bank");
		l2 = new JLabel("User ID:");
		l3 = new JLabel("User Password:");
		l4 = new JLabel("New Customer?");
		hyperlink = new JLabel(" Create Account");
		
	    tx1 = new JTextField(); 
		tx2  = new JPasswordField(); 
		b1 = new JButton(" Login ");
		
		l1.setBounds(140,30,300,80);
		l1.setFont(new Font("Calibri",Font.BOLD,30));
		l1.setForeground(Color.WHITE);
		
		l4.setBounds(0,0,100,100);
		l2.setBounds(60,170,230,100);
		l2.setFont(new Font("Calibri", Font.BOLD, 18));
		
		l3.setBounds(60,250,230,100);
		l3.setFont(new Font("Calibri", Font.BOLD, 18));
		
		l4.setBounds(200,450,100,50);
		l4.setForeground(Color.blue);
		l4.setFont(new Font("Calibri",Font.PLAIN, 12));
	
		b1.setBackground(Color.blue);
		b1.setForeground(Color.WHITE);
		b1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Login.connect();
				}
				catch(Exception e1) {
					
				}
			}

		});
		hyperlink.setForeground(Color.blue);
		hyperlink.setFont(new Font("Calibri", Font.ITALIC, 12));
		hyperlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		hyperlink.setBounds(285,450,100,50); 
		hyperlink.addMouseListener((MouseListener) new MouseAdapter() {
			 
		    public void mouseClicked(MouseEvent e) {
		        // the user clicks on the label
		    	login.dispose();
		    	Account.createaccount() ;
		    }
		});
		
		img = new JLabel();
		
		img.setIcon(new ImageIcon("D:\\Eclipse_WSpace\\ATM interface\\src\\icon8.png"));
        Dimension size = img.getPreferredSize();
        img.setBounds(0,0,1600,130);
        
		
		tx1.setBounds(210,200,220,40);
		tx2.setBounds(210,280,220,40);
		
		b1.setBounds(200,400,100,50);
		
		cb1 = new JCheckBox("Show Password");
		cb1.setBounds(210,320,130,50);
		cb1.setOpaque(true);
		cb1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(cb1.isSelected()) {
					tx2.setEchoChar((char)0);
				}else {
					tx2.setEchoChar('*');
				}
			}
		});
		login.add(l1);
		login.add(l2);
		login.add(l3);
		login.add(l4);
		login.add(hyperlink);
		login.add(tx1);
		login.add(tx2);
		login.add(b1);
		login.add(cb1);
		login.add(img);
		login.setSize(600,600);
		login.setResizable(false);
		login.setLayout(null);
		login.setVisible(true);	
		
		login.setDefaultCloseOperation(login.EXIT_ON_CLOSE);
	}
	
	private static void connect() throws ClassNotFoundException, SQLException {
		String ID = tx1.getText();
		String pass = tx2.getText();
		if(ID.length()==0 || pass.length()==0) {
			JOptionPane.showMessageDialog(login,"Please Enter UserId & Password","Alert",JOptionPane.WARNING_MESSAGE);     
		}
		Connection con = Database.Connector.getConnection();
		Statement st=con.createStatement();
		String SQL = "select * from atm_interface.details where USERID = '"+ID+"' and Password = '"+pass+"'"; 
		ResultSet sout = st.executeQuery(SQL);
		if (sout.next()) 
		{
			login.dispose();
			Homepage.homepage();
		}
		else {
		JOptionPane.showMessageDialog(login,"Incorrect UserId or Password","Alert",JOptionPane.WARNING_MESSAGE);     
		}
		con.close();
	}
}
