package assignment2;

import client.Client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterEmployee extends JFrame implements ActionListener 
{
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JLabel lblMessage;
    private Client client;

    private void init() 
    {
        this.setTitle("Register Employee");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        JLabel lblTitle = new JLabel("Register Employee", JLabel.CENTER);
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        lblTitle.setBounds(0, 20, 600, 40);
        this.add(lblTitle);
        JLabel lblUsername = new JLabel("Enter Username:");
        lblUsername.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        lblUsername.setBounds(100, 90, 200, 30);
        this.add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        tfUsername.setBounds(320, 90, 200, 30);
        this.add(tfUsername);
        JLabel lblPassword = new JLabel("Enter Password:");
        lblPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        lblPassword.setBounds(100, 155, 200, 30);
        this.add(lblPassword);

        tfPassword = new JPasswordField();
        tfPassword.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        tfPassword.setBounds(320, 155, 200, 30);
        this.add(tfPassword);
       
        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        btnRegister.setBounds(320, 230, 150, 40);
        btnRegister.addActionListener(this);
        this.add(btnRegister);

        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        btnCancel.setBounds(150, 230, 150, 40);
        this.add(btnCancel);
        
        lblMessage = new JLabel("", JLabel.CENTER);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMessage.setBounds(50, 300, 500, 30);
        this.add(lblMessage);
        this.setVisible(true);
    }

    public RegisterEmployee(Client client)
    {
        this.client = client;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnRegister) 
        {
            String username = tfUsername.getText().trim();
            String password = new String(tfPassword.getPassword()).trim();
//            EmployeeOperations employeeOperations = new EmployeeOperations();
            String message = client.addEmployeeRequest(username, password);
            lblMessage.setText(message);
        }
        else if(e.getSource()==btnCancel)
        {
            System.exit(0);
            new HomPage(client);
        }
    }
}
