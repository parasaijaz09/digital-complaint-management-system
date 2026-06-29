import java.sql.*;

public class DatabaseConnection {
    private static Connection connection;
    
    private static final String URL = "jdbc:mysql://localhost:3306/complaint_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connected!");
            } catch (Exception e) {
                System.out.println("❌ Connection error: " + e.getMessage());
            }
        }
        return connection;
    }
    
    // Optional: Close connection method
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
  
    
    public static void main(String[] args) {
    Connection conn = DatabaseConnection.getConnection();
    if (conn != null) {
        System.out.println("✅ Success! Connection is working.");
    } else {
        System.out.println("❌ Failed to connect.");
    }
}
}