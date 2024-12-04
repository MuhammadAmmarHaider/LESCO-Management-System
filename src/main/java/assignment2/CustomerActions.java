
package assignment2;

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

public class CustomerActions extends JFrame implements ActionListener
{
    private JButton btnCalculateBill;
    private JButton btnUpdateExpiry;
    private JButton btnExit;
    private Timer timer;
    private List<ImageIcon> images;
    private int imageIndex;
    private JLabel lbImage;
    private Client client;
    private void init()
    {
        this.setTitle("Customer Operations");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(1000,650);
        this.setResizable(false);
        timer = new Timer(2000, this);
        btnCalculateBill = new JButton("Calculate Bill");
        btnCalculateBill.setPreferredSize(new Dimension(210,100));
        btnCalculateBill.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnCalculateBill.setFocusable(false);
        btnCalculateBill.setFont(new Font("Consoal",Font.BOLD,25));
        btnCalculateBill.addActionListener(this);
        ImageIcon imgLogin = getCustomImage("customer.png", 50, 50);
        btnCalculateBill.setIcon(imgLogin);
        btnUpdateExpiry = new JButton("Update Expiry");
        btnUpdateExpiry.setPreferredSize(new Dimension(210,100));
        btnUpdateExpiry.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnUpdateExpiry.setFocusable(false);
        btnUpdateExpiry.setFont(new Font("Consolas",Font.BOLD,25));
        btnUpdateExpiry.addActionListener(this);
        ImageIcon imgRegister = getCustomImage("employee.png", 50, 50);
        btnUpdateExpiry.setIcon(imgRegister);
        btnExit = new JButton("exit");
        btnExit.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnExit.setPreferredSize(new Dimension(210,100));
        btnExit.setFocusable(false);
        btnExit.setFont(new Font("Consoal",Font.BOLD,25));
        btnExit.addActionListener(this);
        ImageIcon imgExit = getCustomImage("exit.png", 50, 50);
        btnExit.setIcon(imgExit);
        btnCalculateBill.setBackground(Color.red);
        btnUpdateExpiry.setBackground(Color.green);
        btnExit.setBackground(Color.blue);
        btnCalculateBill.setVerticalTextPosition(JLabel.BOTTOM);
        btnCalculateBill.setHorizontalTextPosition(JLabel.CENTER);

        btnUpdateExpiry.setVerticalTextPosition(JLabel.BOTTOM);
        btnUpdateExpiry.setHorizontalTextPosition(JLabel.CENTER);

        btnExit.setVerticalTextPosition(JLabel.BOTTOM);
        btnExit.setHorizontalTextPosition(JLabel.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        buttonsPanel.setLayout(new GridLayout(3,1,20,20));
        buttonsPanel.add(btnCalculateBill);
        buttonsPanel.add(btnUpdateExpiry);
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
    public CustomerActions(Client client)
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
        if(e.getSource()==btnCalculateBill)
        {
            this.dispose();
            new CalculateBill(client);
            
        }
        if(e.getSource()==btnUpdateExpiry)
        {
            this.dispose();
            new UpdateExpiryDate(client);
        }
        if(e.getSource()==btnExit)
        {
            this.dispose();
            new CustomerPage(client);
        }
    }
}
