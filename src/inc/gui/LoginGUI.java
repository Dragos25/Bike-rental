package inc.gui;




import inc.conn.DBconn;
import inc.conn.Session;
import inc.def.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI extends JFrame{


    static JFrame LoginWindow = new JFrame("Login Bikerino");
    static GridBagLayout gridBag = new GridBagLayout();
    static GridBagConstraints gbcons = new GridBagConstraints();


    JLabel lblLogin = new JLabel("Bikerino",JLabel.CENTER);
    JLabel lblNume = new JLabel("Username:");
    JLabel lblParola = new JLabel ("Password:");
    JTextField txtUser = new JTextField(30);
    JTextField txtPass = new JPasswordField(30);
    JButton btnLoginC = new JButton("Company Login");
    JButton btnLoginU = new JButton("User Login");
    JButton btnRegisterU=new JButton("User Register");
    JButton btnRegisterC=new JButton("Company Register");
    user userTemp=new user();
    company companyTemp=new company();


    static void CompAdd(Component comp, int x, int y, int w, int h){
        gbcons.gridx = x;
        gbcons.gridy = y;
        gbcons.gridwidth = w;
        gbcons.gridheight = h;
        gridBag.setConstraints(comp, gbcons);
        LoginWindow.add(comp);
    }


    public LoginGUI() {

        ListenForButton lForButton = new ListenForButton();
        gbcons.weightx = 0.5;
        gbcons.weighty = 0.5;
        gbcons.insets = new Insets(5, 5, 5, 5);
        LoginWindow.setLayout(gridBag);

        lblLogin.setFont(new Font("Arial", Font.BOLD, 24));
        CompAdd(lblLogin, 0, 0, 4, 2);

        CompAdd(lblNume, 0, 2, 1, 1);
        CompAdd(lblParola, 0, 3, 1, 1);
        CompAdd(txtUser, 1, 2, 1, 1);
        CompAdd(txtPass, 1, 3, 1, 1);

        gbcons.fill = GridBagConstraints.HORIZONTAL;
       // CompAdd(btnLoginU, 0, 3, 0, 0);
        CompAdd(btnLoginU,0,4,2,1);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        CompAdd(btnRegisterU,0,5,2,1);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        CompAdd(btnLoginC,0,6,2,1);
        gbcons.fill = GridBagConstraints.HORIZONTAL;
        CompAdd(btnRegisterC,0,7,2,1);

        btnRegisterU.setName("ruser");
        btnLoginU.setName("user");



        //gbcons.fill = GridBagConstraints.HORIZONTAL;
        //CompAdd(btnLoginC, 0, 10, 0, 0);
        btnLoginC.setName("company");
        btnLoginC.addActionListener(lForButton);
        btnLoginU.addActionListener(lForButton);
        btnRegisterU.addActionListener(lForButton);
        btnRegisterC.addActionListener(lForButton);


        LoginWindow.setSize(new Dimension(500, 500));
        LoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginWindow.setVisible(true);
    }

    private boolean ServerAuth(String Table) {
        DBconn connection = new DBconn();
        String tempUser = txtUser.getText();
        String tempPass = txtPass.getText();

        try {
            String query = "SELECT username, password, is_banned FROM " + Table + " WHERE username = ? AND password = ?;";

            PreparedStatement preparedStmt = DBconn.getConnection().prepareStatement(query);
            preparedStmt.setString(1, tempUser);
            preparedStmt.setString(2, tempPass);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                boolean banned = rs.getBoolean("is_banned");
                if(banned)
                {
                    JOptionPane.showMessageDialog(LoginWindow, "User blocat!");
                    return false;
                }
                return true;
            }
            else JOptionPane.showMessageDialog(LoginWindow, "Invalid username/password");

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }


    private class ListenForButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!txtUser.getText().isEmpty() && !txtPass.getText().isEmpty()) {
                if (e.getSource() == btnLoginU) {
                    if (ServerAuth(btnLoginU.getName()) == true) {
                        dispose();

                        userTemp.setDB(txtUser.getText(), txtPass.getText());
                        Session.setLoggedIn(userTemp);

                        UserGUI ug = new UserGUI();
                        ug.LoginWindow.setTitle("UserGUI");
                        LoginWindow.setVisible(false);
                    }


                }

                if (e.getSource() == btnLoginC) {
                    if (ServerAuth(btnLoginC.getName()) == true) {
                        dispose();

                        companyTemp.setDB(txtUser.getText(), txtPass.getText());
                        Session.setLoggedIn(companyTemp);

                        CompanyGUI cg = null;
                        try {
                            cg = new CompanyGUI();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        cg.LoginWindow.setTitle("CompanyGUI");
                        LoginWindow.setVisible(false);
                    } else JOptionPane.showMessageDialog(LoginWindow, "Invalid username/password");
                }
            }

            if (e.getSource() == btnRegisterU) {
                dispose();
                RegisterU ru = new RegisterU();
                ru.LoginWindow.setTitle("Register User");
                LoginWindow.setVisible(false);
            }

            if (e.getSource() == btnRegisterC) {
                dispose();
                RegisterC rc = new RegisterC();
                rc.LoginWindow.setTitle("Register Company");
                LoginWindow.setVisible(false);
            }


        }
    }
}