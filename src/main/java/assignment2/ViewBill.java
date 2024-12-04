package assignment2;

import client.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ViewBill extends JFrame implements ActionListener
{
    private JTextField tfCustomerId;
    private JTextField tfMeterId;
    private JButton btnViewBill;
    private JTable billTable;
    private JScrollPane tableScrollPane;
    private String[] columnNames= {
            "Customer ID", "Meter ID", "Bill Month", "Regular Units", 
            "Peak Units", "Issue Date", "Electricity Cost", "Sales Tax", 
            "Fixed Charges", "Total Amount", "Due Date", "Payment Status", 
            "Payment Date"
        };
    private Client client;
    private void init() 
    {
        this.setTitle("View Bill");
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel("View Bill", JLabel.CENTER);
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(lblHeader, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 20, 100));
        tfCustomerId = new JTextField();
        tfMeterId = new JTextField();
        btnViewBill = new JButton("View Bill");
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
        JLabel lblCustomerId = new JLabel("Customer Id:");
        lblCustomerId.setFont(labelFont);
        JLabel lblMeterId = new JLabel("Meter Id:");
        lblMeterId.setFont(labelFont);

        lblCustomerId.setBounds(20, 20, 120, 30);
        tfCustomerId.setBounds(150, 20, 200, 30);
        lblMeterId.setBounds(20, 70, 120, 30);
        tfMeterId.setBounds(150, 70, 200, 30);
        btnViewBill.setBounds(400, 70, 200, 30);
        btnViewBill.setFocusable(false);
        btnViewBill.setBackground(Color.green);
        btnViewBill.addActionListener(this);
       
        inputPanel.add(lblCustomerId);
        inputPanel.add(tfCustomerId);
        inputPanel.add(lblMeterId);
        inputPanel.add(tfMeterId);
        inputPanel.add(btnViewBill);
        this.add(inputPanel, BorderLayout.CENTER);
        
        billTable = new JTable(new Object[0][columnNames.length], columnNames);
        billTable.setFillsViewportHeight(true);
        billTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        billTable.setRowHeight(25);

        tableScrollPane = new JScrollPane(billTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.add(tableScrollPane, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    public ViewBill(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnViewBill) 
        {
            String customerId = tfCustomerId.getText().trim();
            String meterId = tfMeterId.getText().trim();
            if (customerId.isEmpty() || meterId.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "enter customer id and meter id", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
//                EmployeeOperations empOps = new EmployeeOperations();
                String[][] billData = client.viewBillsRequest(customerId, meterId);
                if (billData.length == 0) 
                {
                    JOptionPane.showMessageDialog(this, "No billing information found for the given Customer ID and Meter ID.", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                } 
                else 
                {
                    billTable.setModel(new javax.swing.table.DefaultTableModel(billData, columnNames)); 
                }
            }      
        }
    }
}
