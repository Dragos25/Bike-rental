package inc.def;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import inc.conn.DBconn;

public class rental {
    private int id_rental;
    private int id_user;
    private int id_bike;
    private String rent_date;
    private String rent_hour;
    private String return_hour;

    public int getId_rental() {
        return id_rental;
    }

    public void setId_rental(int id_rental) {
        this.id_rental = id_rental;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_bike() {
        return id_bike;
    }

    public void setId_bike(int id_bike) {
        this.id_bike = id_bike;
    }

    public String getRent_date() {
        return rent_date;
    }

    public void setRent_date(String rent_date) {
        this.rent_date = rent_date;
    }

    public String getRent_hour() {
        return rent_hour;
    }

    public void setRent_hour(String rent_hour) {
        this.rent_hour = rent_hour;
    }

    public String getReturn_hour() {
        return return_hour;
    }

    public void setReturn_hour(String return_hour) {
        this.return_hour = return_hour;
    }

    public void insert() {
        System.out.println("Inserting records into the table...");

        try {
            String query = " insert into rental (id_rental,id_user, id_bike, rent_date, rent_hour, return_hour)"
                    + " values (?,?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setInt(1,id_rental);
            preparedStmt.setInt (2, id_user);
            preparedStmt.setInt (3, id_bike);
            preparedStmt.setString (4, rent_date);
            preparedStmt.setString (5, rent_hour);
            preparedStmt.setString (6, return_hour);


            preparedStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
