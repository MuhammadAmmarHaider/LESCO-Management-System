
package view;

import client.Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HomPage extends JFrame implements ActionListener
{
    private JButton btnCustomer;
    private JButton btnEmployee;
    private JButton btnExit;
    private Timer timer;
    private List<ImageIcon> images;
    private int imageIndex;
    private JLabel lbImage;
    private Client client;
    private void init()
    {
        this.setTitle("Home Page");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1000,650);
        this.setResizable(false);
        timer = new Timer(2000, this);
        btnCustomer = new JButton("Customer");
        btnCustomer.setPreferredSize(new Dimension(200,100));
        btnCustomer.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnCustomer.setFocusable(false);
        btnCustomer.setFont(new Font("Consoal",Font.BOLD,25));
        btnCustomer.addActionListener(this);
        ImageIcon imgCustomer = getCustomImage("customer.png", 55, 55);
        btnCustomer.setIcon(imgCustomer);
        btnEmployee = new JButton("Employee");
        btnEmployee.setPreferredSize(new Dimension(200,100));
        btnEmployee.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnEmployee.setFocusable(false);
        btnEmployee.setFont(new Font("Consoal",Font.BOLD,25));
        btnEmployee.addActionListener(this);
        ImageIcon imgEmployee = getCustomImage("employee.png", 55, 55);
        btnEmployee.setIcon(imgEmployee);
        btnExit = new JButton("exit");
        btnExit.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnExit.setPreferredSize(new Dimension(200,100));
        btnExit.setFocusable(false);
        btnExit.setFont(new Font("Consoal",Font.BOLD,25));
        btnExit.addActionListener(this);
        ImageIcon imgExit = getCustomImage("exit.png", 55, 55);
        btnExit.setIcon(imgExit);
        btnCustomer.setBackground(Color.red);
        btnEmployee.setBackground(Color.green);
        btnExit.setBackground(Color.blue);
        btnCustomer.setVerticalTextPosition(JLabel.BOTTOM);
        btnCustomer.setHorizontalTextPosition(JLabel.CENTER);

        btnEmployee.setVerticalTextPosition(JLabel.BOTTOM);
        btnEmployee.setHorizontalTextPosition(JLabel.CENTER);

        btnExit.setVerticalTextPosition(JLabel.BOTTOM);
        btnExit.setHorizontalTextPosition(JLabel.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        buttonsPanel.setLayout(new GridLayout(3,1,20,20));
        buttonsPanel.add(btnCustomer);
        buttonsPanel.add(btnEmployee);
        buttonsPanel.add(btnExit);
        lbImage = new JLabel();
        lbImage.setHorizontalAlignment(JLabel.CENTER);
        lbImage.setVerticalAlignment(JLabel.CENTER);
        images = new ArrayList<>();
        ImageIcon image1 = getCustomImage("lesco1.jpg", 730, 600);
        ImageIcon image2= getCustomImage("lesco2.jpg", 730, 600);
        ImageIcon image3 = getCustomImage("lesco3.jpg", 730, 600);
        ImageIcon image4 = getCustomImage("lesco4.jpg", 730, 600);
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        
        lbImage.setIcon(image1);
        timer.start();
        this.add(buttonsPanel,BorderLayout.WEST);
        add(lbImage, BorderLayout.CENTER);
        this.setVisible(true);        
    }
    private ImageIcon getCustomImage(String name,int width,int height)
    {
        ImageIcon image = new ImageIcon(name);
        Image customImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(customImage);
    }
    public HomPage(Client client)
    {
        this.client = client;
        init();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==timer)
        {
            imageIndex = (imageIndex+1)%images.size();
            lbImage.setIcon(images.get(imageIndex));
        }
        if(e.getSource()==btnCustomer)
        {
            this.dispose();
            new CustomerPage(client);
        }
        if(e.getSource()==btnEmployee)
        {
            this.dispose();
            new EmployeePage(client);
        }
        if(e.getSource()==btnExit)
        {
            System.exit(0);
        }
    }
}
