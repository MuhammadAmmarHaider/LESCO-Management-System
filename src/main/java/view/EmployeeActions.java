package view;
import client.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EmployeeActions extends JFrame implements ActionListener
{
    private JButton btnChangePassword;
    private JButton btnMeterReading;
    private JButton btnPayBill;
    private JButton btnUpdateTax;
    private JButton btnViewBill;
    private JButton btnPaidUnpaid;
    private JButton btnExpiringCnic;
    private JButton btnNewMeter;
    private JButton btnExit;
    private Client client;
    private void init()
    {
        this.setTitle("Employee Operations");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1000,650);
        this.setResizable(false);
        btnChangePassword = new JButton("Change Password");
        btnChangePassword.setPreferredSize(new Dimension(210,100));
        btnChangePassword.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnChangePassword.setFocusable(false);
        btnChangePassword.setFont(new Font("Consoal",Font.BOLD,25));
        btnChangePassword.addActionListener(this);
        
        btnViewBill = new JButton("View Bills");
        btnViewBill.setPreferredSize(new Dimension(210,100));
        btnViewBill.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnViewBill.setFocusable(false);
        btnViewBill.setFont(new Font("Consolas",Font.BOLD,25));
        btnViewBill.addActionListener(this);
        
        btnMeterReading = new JButton("Add Meter Reading");
        btnMeterReading.setPreferredSize(new Dimension(210,100));
        btnMeterReading.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnMeterReading.setFocusable(false);
        btnMeterReading.setFont(new Font("Consolas",Font.BOLD,25));
        btnMeterReading.addActionListener(this);
        
        btnPayBill = new JButton("Pay Bill");
        btnPayBill.setPreferredSize(new Dimension(210,100));
        btnPayBill.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnPayBill.setFocusable(false);
        btnPayBill.setFont(new Font("Consolas",Font.BOLD,25));
        btnPayBill.addActionListener(this);
        
        btnUpdateTax = new JButton("Update Tax File");
        btnUpdateTax.setPreferredSize(new Dimension(210,100));
        btnUpdateTax.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnUpdateTax.setFocusable(false);
        btnUpdateTax.setFont(new Font("Consolas",Font.BOLD,25));
        btnUpdateTax.addActionListener(this);
        
        
        btnPaidUnpaid = new JButton("Paid & Unpaid Bills");
        btnPaidUnpaid.setPreferredSize(new Dimension(210,100));
        btnPaidUnpaid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnPaidUnpaid.setFocusable(false);
        btnPaidUnpaid.setFont(new Font("Consolas",Font.BOLD,25));
        btnPaidUnpaid.addActionListener(this);
        
        btnExpiringCnic = new JButton("Show Expiring Cnics");
        btnExpiringCnic.setPreferredSize(new Dimension(210,100));
        btnExpiringCnic.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnExpiringCnic.setFocusable(false);
        btnExpiringCnic.setFont(new Font("Consolas",Font.BOLD,25));
        btnExpiringCnic.addActionListener(this);
        
        btnNewMeter = new JButton("Add Meter");
        btnNewMeter.setPreferredSize(new Dimension(210,100));
        btnNewMeter.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnNewMeter.setFocusable(false);
        btnNewMeter.setFont(new Font("Consolas",Font.BOLD,25));
        btnNewMeter.addActionListener(this);

        btnExit = new JButton("exit");
        btnExit.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnExit.setPreferredSize(new Dimension(210,100));
        btnExit.setFocusable(false);
        btnExit.setFont(new Font("Consoal",Font.BOLD,25));
        btnExit.addActionListener(this);

        
        btnChangePassword.setBackground(Color.red);
        btnMeterReading.setBackground(Color.CYAN);
        btnPayBill.setBackground(Color.gray);
        btnUpdateTax.setBackground(Color.GREEN);
        btnViewBill.setBackground(Color.PINK);
        btnPaidUnpaid.setBackground(Color.orange); 
        btnExpiringCnic.setBackground(Color.blue);
        btnNewMeter.setBackground(Color.magenta);
        btnExit.setBackground(Color.yellow);
        
        
      

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        buttonsPanel.setLayout(new GridLayout(3,1,20,20));
        buttonsPanel.add(btnChangePassword);
        buttonsPanel.add(btnMeterReading);
        buttonsPanel.add(btnPayBill);
        buttonsPanel.add(btnUpdateTax);
        buttonsPanel.add(btnViewBill);
        buttonsPanel.add(btnPaidUnpaid);
        buttonsPanel.add(btnExpiringCnic);
        buttonsPanel.add(btnNewMeter);
        buttonsPanel.add(btnExit);

        this.add(buttonsPanel,BorderLayout.CENTER);
        this.setVisible(true);        
    }
    public EmployeeActions(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==btnChangePassword)
        {
            this.dispose();
            new ChangePassword(client);
        }
        else if(e.getSource()==btnMeterReading)
        {
            this.dispose();
            new MeterReading(client);
        }
        else if(e.getSource()==btnPayBill)
        {
            this.dispose();
            new PayBill(client);
            
        }
        else if(e.getSource()==btnUpdateTax)
        {
            this.dispose();
            new UpdateTaxFile(client);
        }
        else if(e.getSource()==btnViewBill)
        {
            this.dispose();
            new ViewBill(client);
        }
        else if(e.getSource()==btnPaidUnpaid)
        {
            this.dispose();
            new PaidAndUnpaid(client);
        }
        else if(e.getSource()==btnExpiringCnic)
        {
            this.dispose();
            new ExpiringCnics(client);
            
        }
        else if(e.getSource()==btnNewMeter)
        {
            this.dispose();
            new AddMeter(this.client);
        }
        else if(e.getSource() == btnExit)
        {
            System.exit(0);
            new HomPage(client);
        } 
    }
}
