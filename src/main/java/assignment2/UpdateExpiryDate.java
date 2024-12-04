package assignment2;

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


public class UpdateExpiryDate extends JFrame implements ActionListener 
{
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JTextField tfDate;
    private JButton btnUpdate;
    private JButton btnCancel;
    private Client client;

    private void init() 
    {

        this.setTitle("Update Expiry Date");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(500, 400);

        JLabel lbUpdateExpiry = new JLabel("Update Expiry Date");
        lbUpdateExpiry.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        lbUpdateExpiry.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        lbUpdateExpiry.setHorizontalAlignment(JLabel.CENTER);
        this.add(lbUpdateExpiry, BorderLayout.NORTH);

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(3, 2, 20, 20));
        JLabel lbUsername = new JLabel("Customer Id:");
        JLabel lbPassword = new JLabel("Enter CNIC:");
        JLabel lbDate = new JLabel("Enter Date(yyyy-MM-dd):");

        tfUsername = new JTextField();
        tfPassword = new JPasswordField();
        tfDate = new JTextField();

        inputs.add(lbUsername);
        inputs.add(tfUsername);
        inputs.add(lbPassword);
        inputs.add(tfPassword);
        inputs.add(lbDate);
        inputs.add(tfDate);
        inputs.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        btnUpdate = new JButton("Update");
        btnUpdate.setPreferredSize(new Dimension(100, 30));
        btnUpdate.setBackground(new Color(70, 130, 180));
        btnUpdate.setForeground(Color.white);
        btnUpdate.setFocusable(false);
        btnUpdate.addActionListener(this);
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setFocusable(false);
        btnCancel.addActionListener(this);
        buttons.add(btnCancel);
        buttons.add(btnUpdate);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 50));

        this.add(inputs, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public UpdateExpiryDate(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnUpdate) 
        {
            String customerStr = tfUsername.getText();
            String cnicStr = new String(tfPassword.getPassword());
            String newExpiryDate = tfDate.getText();
            System.out.println("customerStr is: "+customerStr);
            System.out.println("cnicStr is: "+cnicStr);
//            CustomerOperations operations = new CustomerOperations();
            if(client.isCustomerIdValidRequest(customerStr))
            {

//                if(client.isValidCNICRequest(Long.parseLong(cnicStr)))
//                {
                    System.out.println("cnic is valid");
                    boolean success = client.updateExpiryRequest(Long.parseLong(cnicStr), newExpiryDate);
                    if (success) 
                    {
                        JOptionPane.showMessageDialog(this, "expiry date updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(this, "cnic not founded", "Error", JOptionPane.ERROR_MESSAGE);
                    }
//                }
//                else
//                {
//                    JOptionPane.showMessageDialog(this, "enter a 13 digit cnic", "Error", JOptionPane.ERROR_MESSAGE);
//                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "customer id not founded", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else if (e.getSource() == btnCancel) 
        {
            System.exit(0);
            new CustomerPage(client);
        }
    }
}
