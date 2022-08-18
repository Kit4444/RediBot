package at.mlps.rc.mysql.lb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

	public static Connection con;
	
	public static void connect(String host, int port, String db, String user, String pw) throws SQLException, ClassNotFoundException {
		if(!isConnected()) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?autoReconnect=true", user, pw);
			System.out.println("MYSQL | Connected successfully.");
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				System.out.println("MYSQL | Closed connection to database successful!");
			}catch (SQLException ex) {
				System.out.println("MYSQL | Failed to close database-connection!");
				ex.printStackTrace();
			}
		}
	}

	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static Connection getConnection() {
		return con;
	}
}