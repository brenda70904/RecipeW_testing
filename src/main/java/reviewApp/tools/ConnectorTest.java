package reviewApp.tools;

import java.sql.Connection;
import java.sql.SQLException;

import reviewApp.dal.ConnectionManager;
public class ConnectorTest {
	public void connectionTest() {
		
		ConnectionManager connectionManager = new ConnectionManager();
		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			if (connection != null) {
                System.out.println("Successfully connected to the database!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
		}catch(SQLException e){
			System.out.println("Connection failed.");
            e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connectionManager.closeConnection(connection);
				}catch(SQLException e) {
					System.out.println("Failed to close connection.");
                    e.printStackTrace();
				}
			}
		}
	}
}
