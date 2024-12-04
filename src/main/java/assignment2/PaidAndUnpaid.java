package assignment2;

import client.Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaidAndUnpaid extends JFrame 
{
    private JLabel lblHeader;
    private JLabel lblTotalPaid;
    private JLabel lblPaidAmount;
    private JLabel lblTotalUnpaid;
    private JLabel lblUnpaidAmount;
    private Client client;
    private void init() 
    {
        this.setTitle("Paid & Unpaid Report");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        lblHeader = new JLabel("Paid and Unpaid Bills", JLabel.CENTER);
        lblHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(new GridLayout(2, 2, 10, 10));
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        lblTotalPaid = new JLabel("total paid are ", JLabel.LEFT);
        lblTotalPaid.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        lblPaidAmount = new JLabel("and amounts to ", JLabel.LEFT);
        lblPaidAmount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        lblTotalUnpaid = new JLabel("total unpaid are ", JLabel.LEFT);
        lblTotalUnpaid.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        lblUnpaidAmount = new JLabel("and amounts to ", JLabel.LEFT);
        lblUnpaidAmount.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

        reportPanel.add(lblTotalPaid);
        reportPanel.add(lblPaidAmount);
        reportPanel.add(lblTotalUnpaid);
        reportPanel.add(lblUnpaidAmount);

        this.add(lblHeader, BorderLayout.NORTH);
        this.add(reportPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public PaidAndUnpaid(Client client)
    {
        this.client = client;
        init();
//        EmployeeOperations empOps = new EmployeeOperations();
        Map<String, Double> report = client.paidUnpaidBillsRequest();
  
        lblTotalPaid.setText("total paid are " + report.get("paidCount").intValue());
        lblPaidAmount.setText("and amounts to " + String.format("%.2f", report.get("paidAmount")));
        lblTotalUnpaid.setText("total unpaid are " + report.get("unpaidCount").intValue());
        lblUnpaidAmount.setText("and amounts to " + String.format("%.2f", report.get("unpaidAmount")));
    }
}
