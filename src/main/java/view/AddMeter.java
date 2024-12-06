package view;

import client.Client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddMeter extends JFrame implements ActionListener
{
    private JLabel lblHeader;
    private JTextField tfCustomerId;
    private JComboBox<String> cbMeterType;
    private JButton btnAdd;
    private JLabel lblMessage;
    private Client client;
    private void init() 
    {
        this.setTitle("Add New Meter");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        lblHeader = new JLabel("Add New Meter for Customer", JLabel.CENTER);
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        lblHeader.setBounds(50, 30, 500, 30);
        this.add(lblHeader);
        
        JLabel lblCustomerId;
        lblCustomerId = new JLabel("Enter Customer ID:");
        lblCustomerId.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lblCustomerId.setBounds(50, 100, 200, 25);
        this.add(lblCustomerId);

        tfCustomerId = new JTextField();
        tfCustomerId.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tfCustomerId.setBounds(250, 100, 300, 25);
        this.add(tfCustomerId);
        
        JLabel lblMeterType;
        lblMeterType = new JLabel("Select Meter Type:");
        lblMeterType.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lblMeterType.setBounds(50, 150, 200, 25);
        this.add(lblMeterType);

        cbMeterType = new JComboBox<>(new String[]{"1Phase", "3Phase"});
        cbMeterType.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        cbMeterType.setBounds(250, 150, 300, 25);
        this.add(cbMeterType);
  
        btnAdd = new JButton("Add Meter");
        btnAdd.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        btnAdd.setBounds(200, 220, 200, 30);
        btnAdd.addActionListener(this);
        this.add(btnAdd);

        lblMessage = new JLabel("", JLabel.CENTER);
        lblMessage.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lblMessage.setBounds(50, 280, 500, 25);
        this.add(lblMessage);

        this.setVisible(true);
    }
    public AddMeter(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnAdd) 
        {
            String customerId = tfCustomerId.getText();
            int meterType = cbMeterType.getSelectedIndex() + 1;
            if(client.addMeterRequest(customerId, meterType))
            {
                lblMessage.setText("new meter has been added for customer id " + customerId);
            }
            else
            {
                lblMessage.setText("new meter could not added for customer id " + customerId);
            }
            
        }
    }
}
