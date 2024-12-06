package view;

import client.Client;

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

public class ChangePassword extends JFrame implements ActionListener 
{
    private JTextField tfUsername;
    private JPasswordField tfCurrentPassword;
    private JPasswordField tfNewPassword;
    private JButton btnChange;
    private JButton btnCancel;
    private Client client;
    private void init() 
    {
        this.setTitle("Change Password");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(500, 400);

        JLabel lbChangePassword = new JLabel("Change Password");
        lbChangePassword.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        lbChangePassword.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        lbChangePassword.setHorizontalAlignment(JLabel.CENTER);
        this.add(lbChangePassword, BorderLayout.NORTH);

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(3, 2, 20, 20));

        JLabel lbUsername = new JLabel("Username:");
        JLabel lbCurrentPassword = new JLabel("Current Password:");
        JLabel lbNewPassword = new JLabel("New Password:");

        tfUsername = new JTextField();
        tfCurrentPassword = new JPasswordField();
        tfNewPassword = new JPasswordField();

        inputs.add(lbUsername);
        inputs.add(tfUsername);
        inputs.add(lbCurrentPassword);
        inputs.add(tfCurrentPassword);
        inputs.add(lbNewPassword);
        inputs.add(tfNewPassword);

        inputs.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        btnChange = new JButton("Update");
        btnChange.setPreferredSize(new Dimension(100, 30));
        btnChange.setBackground(new Color(70, 130, 180));
        btnChange.setForeground(Color.white);
        btnChange.setFocusable(false);
        btnChange.addActionListener(this);

        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setFocusable(false);
        btnCancel.addActionListener(this);

        buttons.add(btnCancel);
        buttons.add(btnChange);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 50));

        this.add(inputs, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public ChangePassword(Client client)
    {
        this.client = client;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnChange) 
        {
            String username = tfUsername.getText();
            String currentPassword = new String(tfCurrentPassword.getPassword());
            String newPassword = new String(tfNewPassword.getPassword());
//            EmployeeOperations employeeOperations = new EmployeeOperations();
            boolean success = client.changePasswordRequest(username, currentPassword, newPassword);
            if (success) 
            {
                JOptionPane.showMessageDialog(this, "password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else 
            {
                JOptionPane.showMessageDialog(this, "password could not updated", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == btnCancel) 
        {
            System.exit(0);
        }
    }
}
