package inc.def;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import inc.conn.DBconn;

public class bike {
    private int id_bike;
    private int id_company;
    private String btype;
    private String name;
    private float price;
    private boolean is_rented=false;

    public void set(int id_bike_,int id_company_, String btype_, String name_, float price_, boolean is_rented_)
    {
        id_bike=id_bike_;
        id_company=id_company_;
        btype=btype_;
        name=name_;
        price=price_;
        is_rented=is_rented_;
    }

    public bike(int IDc, String type, String bname, float price)
    {
        this.id_company = IDc;
        this.btype = type;
        this.name = bname;
        this.price = price;
    }

    public bike(){}

    public String getName() {
        return name;
    }

    public String getType()
    {
        return btype;
    }

    public int getId()
    {
        return id_bike;
    }

    public int getId_company() {
        return id_company;
    }

    public void setId_company(int id_company) {
        this.id_company = id_company;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isIs_rented() {
        return is_rented;
    }

    public String Is_rented() {
        if(is_rented == false)
                return "NO";
        return "YES";
    }

    public void setIs_rented(boolean is_rented) {
        this.is_rented = is_rented;
    }

    public int getId_bike() {
        return id_bike;
    }

    public void setId_bike(int id_bike) {
        this.id_bike = id_bike;
    }

    public String toString()
    {
        return name+" "+btype;
    }

    public void insert() {
        System.out.println("Inserting records into the table...");

        try {
            String query = " insert into bike (id_company, btype, name, price)"
                    + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setInt (1, id_company);
            preparedStmt.setString (2, btype);
            preparedStmt.setString (3, name);
            preparedStmt.setFloat (4, price);

            preparedStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    static public void removeBikeWithName(String bname)
    {
        try {
            String query = "DELETE FROM bike WHERE name = ?";
            System.out.println(query);
            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setString (1, bname);

            preparedStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
