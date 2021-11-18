package inc.def;

import java.sql.*;
import java.util.Scanner;
import inc.conn.DBconn;


public class user extends Account {
    private int id_user;
    private boolean banned;

    public int getId()
    {
        return id_user;
    }

    public void userInit() {
        System.out.println(this.getClass().getName());
        Scanner input = new Scanner(System.in);
        System.out.println("Username:");
        username = input.nextLine();
        System.out.println("Password:");
        password = input.nextLine();
        System.out.println("Name:");
        name = input.nextLine();
        System.out.println("E-mail:");
        email = input.nextLine();
        System.out.println("Phone:");
        phone = input.nextLine();
    }

    public void set(String username_, String password_, String name_, String email_, String phone_)
    {
        username=username_;
        password=password_;
        name=name_;
        email=email_;
        phone=phone_;
    }

    public void setDB(String username_, String password_)
    {
        DBconn connection = new DBconn();


        try {
            String query = "SELECT id_user, username, password, name, email, phone FROM user WHERE username = ? AND password = ?;";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setString(1, username_);
            preparedStmt.setString(2, password_);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                id_user=rs.getInt("id_user");
                username=rs.getString("username");
                password=rs.getString("password");
                name=rs.getString("name");
                email=rs.getString("email");
                phone=rs.getString("phone");


            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void insert() {
        System.out.println("Inserting records into the table...");

        try {
            String query = " insert into user (username, password, name, email, phone)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setString (1, username);
            preparedStmt.setString (2, password);
            preparedStmt.setString (3, name);
            preparedStmt.setString (4, email);
            preparedStmt.setString (5, phone);

            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }



    public boolean isBanned() {
        return banned;
    }

    @Override
    public String toString() {
        return "inc.def.user{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }




}