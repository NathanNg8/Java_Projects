import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String USER = "root";  // your MySQL username
    private static final String PASSWORD = "12345678"; // your MySQL password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // optional, but safe
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
            return null;
        }
    }
}