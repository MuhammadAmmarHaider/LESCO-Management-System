package assignment2;

import client.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MeterReading extends JFrame implements ActionListener
{
    private JTextField tfCustomerId;
    private JTextField tfMeterId;
    private JComboBox<String> cbMonth;
    private JTextField tfRegularUnits;
    private JTextField tfPeakUnits;
    private JTextField tfBillIssueDate;
    private JButton btnAdd;
    private JLabel lblHeader;
    private Client client;
    private void init() 
    {
        this.setTitle("Add Meter Reading");
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        lblHeader = new JLabel("Add Meter Reading", JLabel.CENTER);
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(lblHeader, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(10, 2, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        
        tfCustomerId = new JTextField();
        tfMeterId = new JTextField();
        cbMonth = new JComboBox<>(new String[]
        {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        tfRegularUnits = new JTextField();
        tfPeakUnits = new JTextField("0");
        tfBillIssueDate = new JTextField();
        btnAdd = new JButton("Add Reading");
        btnAdd.setBackground(Color.GREEN);
        
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        JLabel lblCustomerId = new JLabel("Customer ID:");
        lblCustomerId.setFont(labelFont);
        JLabel lblMeterId = new JLabel("Meter ID:");
        lblMeterId.setFont(labelFont);
        JLabel lblBillMonth = new JLabel("Select Bill Month:");
        lblBillMonth.setFont(labelFont);
        JLabel lblRegularUnits = new JLabel("Enter Regular Reading:");
        lblRegularUnits.setFont(labelFont);
        JLabel lblPeakUnits = new JLabel("Enter Peak Reading:");
        lblPeakUnits.setFont(labelFont);
        JLabel lblBillIssueDate = new JLabel("Enter Bill Issue Date (dd-MM-yyyy):");
        lblBillIssueDate.setFont(labelFont);

        centerPanel.add(lblCustomerId);
        centerPanel.add(tfCustomerId);
        centerPanel.add(lblMeterId);
        centerPanel.add(tfMeterId);
        centerPanel.add(lblBillMonth);
        centerPanel.add(cbMonth);
        centerPanel.add(lblRegularUnits);
        centerPanel.add(tfRegularUnits);
        centerPanel.add(lblPeakUnits);
        centerPanel.add(tfPeakUnits);
        centerPanel.add(lblBillIssueDate);
        centerPanel.add(tfBillIssueDate);
        centerPanel.add(new JLabel());
        centerPanel.add(btnAdd);

        this.add(centerPanel, BorderLayout.CENTER);

        btnAdd.addActionListener(this);
        this.setVisible(true);
    }
    public MeterReading(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnAdd) 
        {
            String customerId = tfCustomerId.getText().trim();
            String meterId = tfMeterId.getText().trim();
            String billMonth = (String) cbMonth.getSelectedItem();
            int regularUnits = Integer.parseInt(tfRegularUnits.getText().trim());
            int peakUnits = Integer.parseInt(tfPeakUnits.getText().trim());
            String billIssueDate = tfBillIssueDate.getText().trim();

//            EmployeeOperations employeeOperations = new EmployeeOperations();

            boolean success = client.addMeterReadingRequest(customerId, meterId, billMonth, regularUnits, peakUnits, billIssueDate);

            if (success) 
            {
                JOptionPane.showMessageDialog(this, "Meter reading added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Failed to add meter reading. Please check the details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
