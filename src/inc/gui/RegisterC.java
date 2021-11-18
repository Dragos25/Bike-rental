package inc.gui;


//import com.mysql.cj.log.Log;
import inc.conn.DBconn;
import inc.def.company;
import inc.def.user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterC extends JFrame{

    static JFrame LoginWindow = new JFrame();
    static GridBagLayout gridBag=new GridBagLayout();
    static GridBagConstraints gbcons = new GridBagConstraints();

    JButton registerUser=new JButton("Register");
    JButton backToLogin=new JButton("Back to Login");
    JLabel lname=new JLabel("Name:");
    JLabel lemail=new JLabel("Email:");
    JLabel lphone=new JLabel("Phone:");
    JLabel lusername=new JLabel("Username:");
    JLabel laddress=new JLabel("Adress:");
    JLabel lpassword=new JLabel("Password:");
    JTextField tname=new JTextField(30);
    JTextField tusername=new JTextField(30);
    JTextField temail=new JTextField(30);
    JTextField tphone=new JTextField(30);
    JTextField taddress=new JTextField(30);
    JTextField tpassword=new JPasswordField(30);
    JLabel inregistrare=new JLabel("INREGISTRARE COMPANIE");
    JTextArea test=new JTextArea();

    static void CompAdd(Component comp, int x, int y, int w, int h){
        gbcons.gridx = x;
        gbcons.gridy = y;
        gbcons.gridwidth = w;
        gbcons.gridheight = h;
        gridBag.setConstraints(comp, gbcons);
        LoginWindow.add(comp);
    }




    public RegisterC() {
        ListenForButton listenForButton=new ListenForButton();
        LoginWindow.setSize(new Dimension(500, 500));
        LoginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoginWindow.setVisible(true);
        LoginWindow.setLayout(gridBag);
        gbcons.insets=new Insets(5,5,5,5);
        CompAdd(inregistrare,0,0,3,3);
        CompAdd(lusername,0,3,1,1);
        CompAdd(tusername,1,3,1,1);
        CompAdd(lpassword,0,4,1,1);
        CompAdd(tpassword,1,4,1,1);
        CompAdd(lname,0,5,1,1);
        CompAdd(tname,1,5,1,1);
        CompAdd(lemail,0,6,1,1);
        CompAdd(temail,1,6,1,1);
        CompAdd(lphone,0,7,1,1);
        CompAdd(tphone,1,7,1,1);
        CompAdd(laddress,0,8,1,1);
        CompAdd(taddress,1,8,1,1);
        CompAdd(test,0,10,2,2);


        gbcons.fill = GridBagConstraints.HORIZONTAL;
        CompAdd(registerUser,0,10,2,2);
        CompAdd(backToLogin,0,12,2,2);
        registerUser.setName("Inregistrare");
        backToLogin.setName("BackToLogin");

        registerUser.addActionListener(listenForButton);
        backToLogin.addActionListener(listenForButton);





    }



    private class ListenForButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == registerUser)
                if(!filledData())
                    test.setText("Date necompletate");
                else
                    test.setText("Inregistrat!");

            if(e.getSource() == backToLogin)
            {
                dispose();
                LoginGUI.LoginWindow.setVisible(true);
                LoginWindow.setVisible(false);
            }

        }
    }

    private void insertData()
    {
        DBconn connection = new DBconn();
        company temp=new company();
        temp.set(tusername.getText(), tpassword.getText(), tname.getText(),temail.getText(),tphone.getText(), taddress.getText());
        temp.insert();

    }

    private boolean filledData()
    {
        if(tusername.getText().isEmpty() ||
                tpassword.getText().isEmpty() ||
                tname.getText().isEmpty() ||
                temail.getText().isEmpty() ||
                tphone.getText().isEmpty()||
                taddress.getText().isEmpty())
            return false;
        insertData();
        return true;
    }


}
