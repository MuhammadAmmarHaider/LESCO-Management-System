package view;

import client.Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class UpdateTaxFile extends JFrame implements ActionListener 
{
    private JTextField tfRegUnitPrice;
    private JTextField tfPeakUnitPrice;
    private JTextField tfSaleTax;
    private JTextField tfFixedCharges;
    private JRadioButton rbPhase1;
    private JRadioButton rbPhase3;
    private JRadioButton rbDomestic;
    private JRadioButton rbCommercial;
    private JButton btnUpdate;
    private Client client;
    private void init() 
    {
        this.setTitle("Update Tax File");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLayout(null);

        JLabel lbHeader = new JLabel("Update Tax File", JLabel.CENTER);
        lbHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        lbHeader.setBounds(200, 10, 600, 40);
       
        JLabel lbType = new JLabel("Meter Type:");
        JLabel lbRegularUnits = new JLabel("Regular Unit Cost:");
        JLabel lbPeakUnits = new JLabel("Peak Unit Cost:");
        JLabel lbPhase = new JLabel("Phase:");
        JLabel lbSale = new JLabel("Sales Tax:");
        JLabel lbFixed = new JLabel("Fixed Charges:");
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        lbType.setFont(font);
        lbRegularUnits.setFont(font);
        lbPeakUnits.setFont(font);
        lbPhase.setFont(font);
        lbSale.setFont(font);
        lbFixed.setFont(font);
        
        rbPhase1 = new JRadioButton("Phase 1");
        rbPhase3 = new JRadioButton("Phase 3");
        rbDomestic = new JRadioButton("Domestic");
        rbCommercial = new JRadioButton("Commercial");
        rbPhase1.setFont(font);
        rbPhase3.setFont(font);
        rbDomestic.setFont(font);
        rbCommercial.setFont(font);
        
        tfRegUnitPrice = new JTextField();
        tfSaleTax = new JTextField();
        tfFixedCharges = new JTextField();
        tfPeakUnitPrice = new JTextField();
        font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        tfRegUnitPrice.setFont(font);
        tfSaleTax.setFont(font);
        tfFixedCharges.setFont(font);
        tfPeakUnitPrice.setFont(font);

        ButtonGroup group1 = new ButtonGroup();
        group1.add(rbPhase1);
        group1.add(rbPhase3);

        ButtonGroup group2 = new ButtonGroup();
        group2.add(rbDomestic);
        group2.add(rbCommercial);

        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(Color.green);
        btnUpdate.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        btnUpdate.addActionListener(this);
        int labelWidth = 200, labelHeight = 30;
        int fieldWidth = 300, fieldHeight = 40;
        int ySpacing = 20;

        lbPhase.setBounds(50, 80, labelWidth, labelHeight);
        rbPhase1.setBounds(50 + labelWidth + 10, 80, 100, labelHeight);
        rbPhase3.setBounds(50 + labelWidth + 170, 80, 100, labelHeight);

        lbType.setBounds(50, 80 + labelHeight + ySpacing, labelWidth, labelHeight);
        rbDomestic.setBounds(50 + labelWidth + 10, 80 + labelHeight + ySpacing, 150, labelHeight);
        rbCommercial.setBounds(50 + labelWidth + 170, 80 + labelHeight + ySpacing, 150, labelHeight);

        lbRegularUnits.setBounds(50, 80 + 2 * (labelHeight + ySpacing), labelWidth, labelHeight);
        tfRegUnitPrice.setBounds(50 + labelWidth + 10, 80 + 2 * (labelHeight + ySpacing), fieldWidth, fieldHeight);

        lbPeakUnits.setBounds(50, 80 + 3 * (labelHeight + ySpacing), labelWidth, labelHeight);
        tfPeakUnitPrice.setBounds(50 + labelWidth + 10, 80 + 3 * (labelHeight + ySpacing), fieldWidth, fieldHeight);

        lbSale.setBounds(50, 80 + 4 * (labelHeight + ySpacing), labelWidth, labelHeight);
        tfSaleTax.setBounds(50 + labelWidth + 10, 80 + 4 * (labelHeight + ySpacing), fieldWidth, fieldHeight);

        lbFixed.setBounds(50, 80 + 5 * (labelHeight + ySpacing), labelWidth, labelHeight);
        tfFixedCharges.setBounds(50 + labelWidth + 10, 80 + 5 * (labelHeight + ySpacing), fieldWidth, fieldHeight);

        btnUpdate.setBounds(50 + labelWidth + 10, 80 + 6 * (labelHeight + ySpacing), fieldWidth, fieldHeight);

        this.add(lbHeader);
        this.add(lbPhase);
        this.add(rbPhase1);
        this.add(rbPhase3);
        this.add(lbType);
        this.add(rbDomestic);
        this.add(rbCommercial);
        this.add(lbRegularUnits);
        this.add(tfRegUnitPrice);
        this.add(lbPeakUnits);
        this.add(tfPeakUnitPrice);
        this.add(lbSale);
        this.add(tfSaleTax);
        this.add(lbFixed);
        this.add(tfFixedCharges);
        this.add(btnUpdate);
        this.setVisible(true);
    }

    public UpdateTaxFile(Client client)
    {
        this.client = client;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == btnUpdate) 
        {
            int phase = rbPhase1.isSelected() ? 1 : 2;
            int type = rbDomestic.isSelected() ? 1 : 2;
            double unit = Double.parseDouble(tfRegUnitPrice.getText());
            double peak = type == 2 ? Double.parseDouble(tfPeakUnitPrice.getText()) : 0.0;
            double saleTax = Double.parseDouble(tfSaleTax.getText());
            double fixed = Double.parseDouble(tfFixedCharges.getText());
//            EmployeeOperations empOps = new EmployeeOperations();
            boolean success = client.updateTaxFileRequest(phase, type, unit, peak, saleTax, fixed);
            if (success) 
            {   
                JOptionPane.showMessageDialog(null, "tax file updated successfully");
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "tax file not updated");
            }
        }
    }
}
