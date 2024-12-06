package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.Client;

public class Login extends JFrame implements ActionListener 
{
    private JLabel lbUsername;
    private JTextField tfUsername;
    private JLabel lbPassword;
    private JPasswordField tfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel lbLogin;
    private String user;
    private Client client;

    private void init() 
    {
   
        this.setTitle("Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(500, 400);
        lbLogin = new JLabel("Login");
        lbLogin.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        lbLogin.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        lbLogin.setHorizontalAlignment(JLabel.CENTER);
        this.add(lbLogin, BorderLayout.NORTH);

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(2, 2, 10, 10));

        lbUsername = new JLabel("Enter username:");
        tfUsername = new JTextField();
        lbPassword = new JLabel("Enter Password:");
        tfPassword = new JPasswordField();
        inputs.add(lbUsername);
        inputs.add(tfUsername);
        inputs.add(lbPassword);
        inputs.add(tfPassword);

        inputs.setBorder(BorderFactory.createEmptyBorder(45, 40, 45, 40));
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.TRAILING));
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(100, 30));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.white);
        btnLogin.setFocusable(false);
        btnLogin.addActionListener(this);

        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setFocusable(false);
        btnCancel.addActionListener(this);
        buttons.add(btnCancel);
        buttons.add(btnLogin);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 45, 40));

        this.add(inputs, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public Login(String user, Client client)
    {
        this.client= client;
        this.user = user;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnLogin) 
        {
            if (user.equalsIgnoreCase("customer")) 
            {
                String customerId = tfUsername.getText();
                String cnicStr = new String(tfPassword.getPassword());
                System.out.println("cnicstr is: "+cnicStr);
//                CustomerOperations operations = new CustomerOperations();

                if (client.sendLoginRequest("customer",customerId, cnicStr))
                {
                    this.dispose();
                    new CustomerActions(this.client);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else if (user.equalsIgnoreCase("employee")) 
            {
                String username = tfUsername.getText();
                String password = new String(tfPassword.getPassword());

//                EmployeeOperations empOps = new EmployeeOperations();
                if (client.sendLoginRequest("employee",username, password))
                {
                    this.dispose();
                    new EmployeeActions(this.client);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "invalid type of user", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else if (e.getSource() == btnCancel) 
        {
            System.exit(0);
        }
    }
}
