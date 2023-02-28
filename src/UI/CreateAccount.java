package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccount {
	private static JFrame createAcc;
	private static JTextField getName,setloginId,getAccountNo,deposit;
	private static JPasswordField getpass,pin;
 public static void newAcc() {
	 createAcc = new JFrame();
	 
	 JLabel lb1 = new JLabel(" Enter Name: ");
	 lb1.setBounds(20,60,100,20);
	 JLabel lb2 = new JLabel(" Set Password: ");
	 lb2.setBounds(20,160,100,20);
	 JLabel lb4 = new JLabel(" Set Login ID: ");
	 lb2.setBounds(20,260,100,20);
	 JLabel lb3 = new JLabel(" Set ATM Pin: ");
	 
	 JLabel lb5 = new JLabel("Your Account No. is:");
	 JLabel lb6 = new JLabel("Deposit Amount: ");
	 
	 getName = new JTextField();
	 getpass = new JPasswordField();
	 pin = new JPasswordField(); 
	 setloginId = new JTextField();
	 getAccountNo = new JTextField();
	 deposit= new JTextField(); 
	 
	 createAcc.add(lb1);
	 createAcc.add(lb2);
	 createAcc.setSize(700,700);
	 createAcc.setResizable(false);
	 createAcc.setLayout(null);
	 createAcc.setVisible(true);
}
}
