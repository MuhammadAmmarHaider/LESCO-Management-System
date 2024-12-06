package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import client.Client;
import model.CustomerType;
import model.MeterType;



public class RegisterCustomer extends JFrame implements ActionListener
{
    private JLabel lblHeader;
    private JTextField tfCNIC, tfName, tfAddress, tfPhone;
    private JComboBox<String> cbCustomer, cbMeter;
    private JButton btnAdd;
    private JButton btnCancel;
    private JLabel lblMessage;
    private Client client;
    private void init() 
    {
        this.setTitle("Add New Customer");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        lblHeader = new JLabel("Add New Customer", JLabel.CENTER);
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        lblHeader.setBounds(50, 20, 600, 30);
        this.add(lblHeader);
        JLabel lbCnic;
        lbCnic = new JLabel("Enter CNIC:");
        lbCnic.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbCnic.setBounds(50, 80, 200, 25);
        this.add(lbCnic);
        tfCNIC = new JTextField();
        tfCNIC.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tfCNIC.setBounds(250, 80, 400, 25);
        this.add(tfCNIC);

        JLabel lbName;
        lbName = new JLabel("Enter Full Name:");
        lbName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbName.setBounds(50, 120, 200, 25);
        this.add(lbName);
        tfName = new JTextField();
        tfName.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tfName.setBounds(250, 120, 400, 25);
        this.add(tfName);
        JLabel lbAddress;
        lbAddress = new JLabel("Enter Address:");
        lbAddress.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbAddress.setBounds(50, 160, 200, 25);
        this.add(lbAddress);
        tfAddress = new JTextField();
        tfAddress.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tfAddress.setBounds(250, 160, 400, 25);
        this.add(tfAddress);
        JLabel lbPhone;
        lbPhone = new JLabel("Enter Phone Number:");
        lbPhone.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbPhone.setBounds(50, 200, 200, 25);
        this.add(lbPhone);

        tfPhone = new JTextField();
        tfPhone.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tfPhone.setBounds(250, 200, 400, 25);
        this.add(tfPhone);
        JLabel lbCustomer;
        lbCustomer = new JLabel("Customer Type:");
        lbCustomer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbCustomer.setBounds(50, 240, 200, 25);
        this.add(lbCustomer);

        cbCustomer = new JComboBox<>(new String[]{"Domestic", "Commercial"});
        cbCustomer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        cbCustomer.setBounds(250, 240, 400, 25);
        this.add(cbCustomer);
        JLabel lbMeter;
        lbMeter = new JLabel("Meter Type:");
        lbMeter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbMeter.setBounds(50, 280, 200, 25);
        this.add(lbMeter);

        cbMeter = new JComboBox<>(new String[]{"1Phase", "3Phase"});
        cbMeter.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        cbMeter.setBounds(250, 280, 400, 25);
        this.add(cbMeter);
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnCancel.setBounds(250, 330, 190, 30);
        btnCancel.addActionListener(this);
        this.add(btnCancel);
        
        btnAdd = new JButton("Add Customer");
        btnAdd.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnAdd.setBounds(460, 330, 190, 30);
        btnAdd.addActionListener(this);
        btnAdd.setBackground(Color.green);
        this.add(btnAdd);
   
        lblMessage = new JLabel("", JLabel.CENTER);
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 16));
        lblMessage.setBounds(50, 380, 600, 25);
        this.add(lblMessage);
        this.setVisible(true);
    }
    public RegisterCustomer(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnAdd) 
        {
            try 
            {
//                CustomerOperations operations = new CustomerOperations();
                long cnic = Long.parseLong(tfCNIC.getText());
                String name = tfName.getText();
                String address = tfAddress.getText();
                long phone = Long.parseLong(tfPhone.getText());
                CustomerType customerType = cbCustomer.getSelectedItem().equals("Domestic") ? CustomerType.DOMESTIC : CustomerType.COMMERCIAL;
                MeterType meterType = cbMeter.getSelectedItem().equals("1Phase") ? MeterType.SINGLE_PHASE : MeterType.THREE_PHASE;

                boolean success = client.addCustomerRequest(cnic, name, address, phone, customerType, meterType);
               
                if (success) 
                {
                    lblMessage.setText("Customer added successfully");
                } 
                else 
                {
                    lblMessage.setText("customer could not added");
                }
            } 
            catch (NumberFormatException ex) 
            {
                lblMessage.setText("enter valid cnic and phone number");
            }
        }
        else if(e.getSource()==btnCancel)
        {
            this.dispose();
            new CustomerPage(client);
        }
    }
}
