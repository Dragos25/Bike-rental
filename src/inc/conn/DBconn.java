package inc.conn;
import java.sql.*;

public class DBconn{

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public DBconn(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biciclete?autoReconnect=true&useSSL=false", "root", "ProiectBiciclete22");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void select() {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from user");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("email"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}