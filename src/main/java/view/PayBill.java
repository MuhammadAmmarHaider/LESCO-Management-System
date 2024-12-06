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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PayBill extends JFrame implements ActionListener 
{
    private JLabel lbUsername;
    private JTextField tfUsername;
    private JLabel lbPassword;
    private JPasswordField tfPassword;
    private JButton btnPay;
    private JButton btnCancel;
    private JLabel lbLogin;
    private Client client;
    private void init() 
    {
   
        this.setTitle("Pay Bill");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(500, 400);
        lbLogin = new JLabel("Pay Bill");
        lbLogin.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
        lbLogin.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        lbLogin.setHorizontalAlignment(JLabel.CENTER);
        this.add(lbLogin, BorderLayout.NORTH);

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(2, 2, 10, 10));

        lbUsername = new JLabel("Enter Customer Id:");
        tfUsername = new JTextField();
        lbPassword = new JLabel("Enter Meter Id:");
        tfPassword = new JPasswordField();
        inputs.add(lbUsername);
        inputs.add(tfUsername);
        inputs.add(lbPassword);
        inputs.add(tfPassword);

        inputs.setBorder(BorderFactory.createEmptyBorder(45, 40, 45, 40));
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.TRAILING));
        btnPay = new JButton("Pay");
        btnPay.setPreferredSize(new Dimension(100, 30));
        btnPay.setBackground(new Color(70, 130, 180));
        btnPay.setForeground(Color.white);
        btnPay.setFocusable(false);
        btnPay.addActionListener(this);

        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(100, 30));
        btnCancel.setFocusable(false);
        btnCancel.addActionListener(this);
        buttons.add(btnCancel);
        buttons.add(btnPay);
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 45, 40));

        this.add(inputs, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public PayBill(Client client)
    {
        this.client = client;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnPay) 
        {
            String customerId = tfUsername.getText().trim();
            String meterId = new String(tfPassword.getPassword()).trim();
//            EmployeeOperations empOps = new EmployeeOperations();
            boolean isPaid = client.payBillRequest(customerId, meterId);

            if (isPaid) 
            {
            lbLogin.setText("bill paid successfully");
            lbLogin.setForeground(Color.GREEN);
            } 
            else 
            {
                lbLogin.setText("bill could not paid");
                lbLogin.setForeground(Color.RED);
            }
        } 
        else if (e.getSource() == btnCancel) 
        {
            System.exit(0);
        }
    }
}
