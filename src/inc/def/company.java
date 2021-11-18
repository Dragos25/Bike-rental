package inc.def;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import inc.conn.DBconn;

public class company extends Account {
    private int id_company;

    private String address;

    public String getUsername() {
        return username;
    }

    public void set(String username_, String password_, String name_, String email_, String phone_, String address_)
    {
        username=username_;
        password=password_;
        name=name_;
        email=email_;
        phone=phone_;
        address=address_;
    }

    public int getId()
    {
        return id_company;
    }

    public void setDB(String username_, String password_)
    {
        DBconn connection = new DBconn();


        try {
            String query = "SELECT id_company, username, password, name, email, phone,address FROM company WHERE username = ? AND password = ?;";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setString(1, username_);
            preparedStmt.setString(2, password_);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                id_company=rs.getInt("id_company");
                username=rs.getString("username");
                password=rs.getString("password");
                name=rs.getString("name");
                email=rs.getString("email");
                phone=rs.getString("phone");
                address=rs.getString("address");


            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void insert() {
        System.out.println("Inserting records into the table...");

        try {
            String query = " insert into company (username, password, name, email, phone, address)"
                    + " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setString (1, username);
            preparedStmt.setString (2, password);
            preparedStmt.setString (3, name);
            preparedStmt.setString (4, email);
            preparedStmt.setString (5, phone);
            preparedStmt.setString (6, address);

            preparedStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public String toString() {
        return "company{" +
                "id_company=" + id_company +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
