package inc.gui;


//import com.mysql.cj.log.Log;
import com.mysql.cj.log.Log;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.Result;
import inc.conn.DBconn;
import inc.conn.Session;
import inc.def.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CompanyGUI extends JFrame{

    static JFrame LoginWindow = new JFrame();
    static GridBagLayout gridBag = new GridBagLayout();
    private static JPanel MainMenu = new JPanel(gridBag);
    private static JPanel BikeMenu = new JPanel(gridBag);
    private static JPanel ReviewMenu = new JPanel(gridBag);
    private static JPanel BlockMenu = new JPanel(gridBag);
    static GridBagConstraints gbcons = new GridBagConstraints();


    //Main menu
    private JLabel WelcomeLabel=new JLabel("Bine ati venit, " + Session.getLoggedIn().getName() + "!");
    private JLabel MenuLabel=new JLabel("Menu");
    private JButton BikeMenuBtn=new JButton("Bikes");
    private JButton ReviewMenuBtn=new JButton("Review");
    private JButton BlockMenuBtn =new JButton("Block Users");
    private JButton backToMain = new JButton("Main Menu");
    private JButton backToMain2 = new JButton("Main Menu");
    private JButton backToMain3 = new JButton("Main Menu");
    private JButton banUser = new JButton("Blocheaza User");


    //Bike menu
    private JTable bikeTable = new JTable();
    private JButton addBikeBtn = new JButton("Add");
    private JButton removeBikeBtn = new JButton("Remove");
    private JTextField BtypeTXT=new JTextField(30);
    private JTextField BnameTXT=new JTextField(30);
    private JTextField BpriceTXT=new JTextField(30);
    private JLabel lblBtype = new JLabel("Type:");
    private JLabel lblBname = new JLabel ("Name (Delete here):");
    private JLabel lblBprice = new JLabel ("Price /h");
    private JComboBox users = new JComboBox();
    private ArrayList<user> allUsers=new ArrayList<>();
    private JLabel nrRented=new JLabel("Test");

    //Review menu
    private JLabel startDate=new JLabel("Data inceput");
    private JLabel endDate=new JLabel("Data sfarsit");
    private JTextField startDateText=new JTextField(30);
    private JTextField endDateText=new JTextField(30);
    private JButton showNrBikes=new JButton("Numar bikes");
    private JLabel nrRentedDates=new JLabel("");

    private static void CompAdd(JPanel tempPanel, Component comp, int x, int y, int w, int h){
        gbcons.gridx = x;
        gbcons.gridy = y;
        gbcons.gridwidth = w;
        gbcons.gridheight = h;
        gridBag.setConstraints(comp, gbcons);
        tempPanel.add(comp);
    }

    private ArrayList<bike>  bikeList() {
        ArrayList<bike> bikeList = new ArrayList<>();

        try {
            String query = "SELECT * FROM bike WHERE id_company = " + Session.getLoggedIn().getId() + ";";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();


            while(rs.next()){
                bike tempBike = new bike();
                tempBike.setId_bike(rs.getInt("id_bike"));
                tempBike.setId_company(rs.getInt("id_company"));
                tempBike.setBtype(rs.getString("btype"));
                tempBike.setName(rs.getString("name"));
                tempBike.setPrice(rs.getFloat("price"));
                tempBike.setIs_rented(rs.getBoolean("is_rented"));
                bikeList.add(tempBike);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bikeList;

    }

    private void ShowBikes(){
        ArrayList<bike> bikes = bikeList();



        bikeTable = new JTable(0,6);
        DefaultTableModel model = (DefaultTableModel)bikeTable.getModel();
        System.out.println(bikeTable.getRowCount());

        Object[] row = new Object[6];

        row[0]="Id_bike";
        row[1]="Id_company";
        row[2]="Bike Type";
        row[3]="Name";
        row[4]="Price";
        row[5]="Rented";
        model.addRow(row);

        for(int i=0;i<bikes.size();i++){
            row[0]=bikes.get(i).getId_bike();
            row[1]=bikes.get(i).getId_company();
            row[2]=bikes.get(i).getBtype();
            row[3]=bikes.get(i).getName();
            row[4]=bikes.get(i).getPrice();
            row[5]=bikes.get(i).Is_rented();
            System.out.println(row[0]);
            if(row[0]!=null)
                 model.addRow(row);
            System.out.println(model.getValueAt(i,0));

        }

        for(int i=0;i<model.getRowCount();i++)
            if(model.getValueAt(i,0)==null) {model.removeRow(i); i=0;}

    }

    private void addBike(){
        bike temp = new bike(Session.getLoggedIn().getId(),BtypeTXT.getText(),BnameTXT.getText(),Float.parseFloat(BpriceTXT.getText()));
        temp.insert();
    }

    private void AllMenuGenerate() throws SQLException {
        //Main Menu
        LoginWindow.add(MainMenu);
        CompAdd(MainMenu,WelcomeLabel,0,0,1,1);
        CompAdd(MainMenu,MenuLabel,0,3,1,1);
        CompAdd(MainMenu, BikeMenuBtn,0,4,1,2);
        CompAdd(MainMenu, ReviewMenuBtn,0,6,1,2);
        CompAdd(MainMenu, BlockMenuBtn,0,8,1,2);
        MainMenu.setVisible(true);

        //Bike Menu

        ShowBikes();
        CompAdd(BikeMenu,addBikeBtn,2,0,3,1);
        CompAdd(BikeMenu,removeBikeBtn,4,0,1,1);

        CompAdd(BikeMenu,lblBtype,2,1,3,1);
        CompAdd(BikeMenu,BtypeTXT,4,1,1,1);

        CompAdd(BikeMenu,lblBname,2,2,3,1);
        CompAdd(BikeMenu,BnameTXT,4,2,1,1);

        CompAdd(BikeMenu,lblBprice,2,3,3,1);
        CompAdd(BikeMenu,BpriceTXT,4,3,1,1);


        CompAdd(BikeMenu,backToMain,3,4,1,1);
        CompAdd(BikeMenu,bikeTable,3,5,1,1);



        //Review Menu
        reviewMenu();
        CompAdd(ReviewMenu,nrRented,1,1,1,1);
        CompAdd(ReviewMenu,backToMain2,1,6,1,1);
        CompAdd(ReviewMenu, startDate,0,2,1,1);
        CompAdd(ReviewMenu,startDateText,1,2,1,1);
        CompAdd(ReviewMenu,endDate,0,3,1,1);
        CompAdd(ReviewMenu,endDateText,1,3,1,1);
        CompAdd(ReviewMenu,showNrBikes,1,4,1,1);
        CompAdd(ReviewMenu,nrRentedDates,1,5,1,1);

        //Block Menu
        CompAdd(BlockMenu,users,0,0,2,2);
        CompAdd(BlockMenu,banUser,0,2,1,1);
        CompAdd(BlockMenu,backToMain3,0,3,1,1);
    }

    public void reviewMenu() throws SQLException {
        int nrinchiriat;
        String query="select count(id_bike) as nr from bike where is_rented=true;";
        PreparedStatement p= DBconn.getConnection().prepareStatement(query);
        ResultSet rs=p.executeQuery();
        rs.next();
        nrinchiriat=rs.getInt("nr");
        nrRented.setText("In acest moment sunt inchiriate "+nrinchiriat+" biciclete.");

    }

    public void nrBikes() throws SQLException {
        if(startDateText.getText().isEmpty() || endDateText.getText().isEmpty()) return;


        String query="Select count(id_rental) as nr from rental where rent_date between  ? and ?;";
        PreparedStatement p=DBconn.getConnection().prepareStatement(query);
        p.setString(1,startDateText.getText());
        p.setString(2,endDateText.getText());
        ResultSet rs=p.executeQuery();
        rs.next();
        int nrinchiriat=rs.getInt("nr");
        nrRentedDates.setText("Intre cele doua date au fost inchiriate "+nrinchiriat+" biciclete");
    }

    public void addUsers()
    {

            DBconn connection = new DBconn();
            users.removeAllItems();
            allUsers.clear();



            try {
                String query = "SELECT username,name FROM user;";

                PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);


                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    String tempUser=rs.getString(("username"));
                    String tempName=rs.getString("name");

                    user u=new user();
                    u.setName(tempUser);
                    u.setUsername(tempName);
                    allUsers.add(u);


                }


            } catch (SQLException e) {
                e.printStackTrace();

            }

            for(user u:allUsers)
            {
                users.addItem(u.getName()+" "+u.getUsername());
            }


        }




    public CompanyGUI() throws SQLException {
        gbcons.insets=new Insets(5,5,5,5);
        ListenForButton listenForButton = new ListenForButton();
        AllMenuGenerate();

        gbcons.insets=new Insets(5,5,5,5);
        LoginWindow.setResizable(false);
        LoginWindow.setSize(new Dimension(1000, 600));
        LoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginWindow.setVisible(true);

        BikeMenuBtn.addActionListener(listenForButton);
        ReviewMenuBtn.addActionListener(listenForButton);
        BlockMenuBtn.addActionListener(listenForButton);
        backToMain.addActionListener(listenForButton);
        backToMain2.addActionListener(listenForButton);
        backToMain3.addActionListener(listenForButton);
        addBikeBtn.addActionListener(listenForButton);
        removeBikeBtn.addActionListener(listenForButton);
        banUser.addActionListener(listenForButton);
        showNrBikes.addActionListener(listenForButton);
        addUsers();
    }

    public void banUser(String name) throws SQLException {
        String query="update user set is_banned=true where username=?;";

        PreparedStatement p=DBconn.getConnection().prepareStatement(query);
        p.setString(1,name);
        p.execute();

    }

    private class ListenForButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(BikeMenuBtn)){
                LoginWindow.add(BikeMenu);
                BikeMenu.setVisible(true);
                MainMenu.setVisible(false);
            }

            if (e.getSource().equals(ReviewMenuBtn)){
                LoginWindow.add(ReviewMenu);
                ReviewMenu.setVisible(true);
                MainMenu.setVisible(false);
            }

            if (e.getSource().equals(BlockMenuBtn)){
                LoginWindow.add(BlockMenu);
                BlockMenu.setVisible(true);
                MainMenu.setVisible(false);
            }

            if (e.getSource().equals(backToMain) || e.getSource().equals(backToMain2) || e.getSource().equals(backToMain3)) {
                LoginWindow.remove(BikeMenu);
                LoginWindow.remove(ReviewMenu);
                LoginWindow.remove(BlockMenu);
                BikeMenu.setVisible(false);
                ReviewMenu.setVisible(false);
                BlockMenu.setVisible(false);
                //LoginWindow.add(MainMenu);
                MainMenu.setVisible(true);
            }

            if (e.getSource().equals(addBikeBtn)){
                bikeTable.removeAll();

                addBike();

                bikeList();

            }

            if (e.getSource().equals(removeBikeBtn)){
                bike.removeBikeWithName(BnameTXT.getText());
            }

            if(e.getSource().equals(banUser))
            {
                try {
                    banUser(allUsers.get(users.getSelectedIndex()).getName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(e.getSource().equals(showNrBikes))
            {
                try {
                    nrBikes();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
    }
}
