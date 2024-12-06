package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import client.Client;
import model.CNICInfo;

public class ExpiringCnics extends JFrame 
{
    private JLabel lblHeader;
    private JTable tbExpiring;
    private Client client;
    private void init(List<CNICInfo> expiringCNICs) 
    {
        this.setTitle("CNICs Expiring Within 30 Days");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        lblHeader = new JLabel("CNICs Expiring Within 30 Days", JLabel.CENTER);
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        this.add(lblHeader, BorderLayout.NORTH);
        if (expiringCNICs == null) {
            expiringCNICs = new ArrayList<>();
        }
        String[] columnNames = {"CNIC", "Expiry Date"};
        String[][] rowData = new String[expiringCNICs.size()][2];

        for (int i = 0; i < expiringCNICs.size(); i++) 
        {
            CNICInfo info = expiringCNICs.get(i);
            rowData[i][0] = info.getCnic();
            rowData[i][1] = info.getExpiryDate().toString();
        }

        tbExpiring = new JTable(rowData, columnNames);
        tbExpiring.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        tbExpiring.setRowHeight(25);
        tbExpiring.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(tbExpiring);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public ExpiringCnics(Client client)
    {
        this.client = client;
//        EmployeeOperations employeeOperations = new EmployeeOperations();
        List<CNICInfo> expiringCNICs = client.showExpiringCnicsRequest();
        init(expiringCNICs);
    }
}
