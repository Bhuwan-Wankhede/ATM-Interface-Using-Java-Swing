package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import UI.*;

public class Connector {
	
			public static Connection getConnection() throws ClassNotFoundException,SQLException {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("Driver Registered");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm_interface","root","abc123");
//			System.out.println("Connection Successfully");
			return con;
		}
}
