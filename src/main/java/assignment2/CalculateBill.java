package assignment2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client.Client;

import javax.swing.JOptionPane;

public class CalculateBill extends JFrame implements ActionListener
{
    private JTextField tfCustomerId;
    private JTextField tfRegularUnits;
    private JTextField tfPeakUnits;
    private JRadioButton rbPhase1;
    private JRadioButton rbPhase3;
    private JButton btnCalculate;
    private JLabel lbRegUnitCost ;
    private JLabel lbPeakUnitCost ;
    private JLabel lbSaleTax;
    private JLabel lbFixCharges;
    private JLabel lbElectricityCost;
    private JLabel lbTotalCost;
    private Client client;
    private void init() 
    {
        this.setTitle("Calculate Bill");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLayout(null);

        JLabel lbCustomerId = new JLabel("Customer Id:");
        JLabel lbRegularUnits = new JLabel("Regular Units:");
        JLabel lbPeakUnits = new JLabel("Peak Units:");
        JLabel lbPhase = new JLabel("Phase:");
        JLabel lbRegUnit = new JLabel("Regular Unit Cost:");
        JLabel lbPeakUnit = new JLabel("Peak Unit Cost:");
        JLabel lbSale = new JLabel("Sales Tax:");
        JLabel lbFixed = new JLabel("Fixed Charges:");
        JLabel lbElectricity = new JLabel("Cost of Electricity:");
        JLabel lbTotal = new JLabel("Total Amount:");

        lbRegUnitCost = new JLabel("");
        lbPeakUnitCost = new JLabel("");
        lbSaleTax = new JLabel("");
        lbFixCharges = new JLabel("");
        lbElectricityCost = new JLabel("");
        lbTotalCost = new JLabel("");

        tfCustomerId = new JTextField();
        tfRegularUnits = new JTextField();
        tfPeakUnits = new JTextField();

        rbPhase1 = new JRadioButton("Phase 1");
        rbPhase3 = new JRadioButton("Phase 3");
        ButtonGroup group = new ButtonGroup();
        group.add(rbPhase1);
        group.add(rbPhase3);

        btnCalculate = new JButton("Calculate");
        btnCalculate.setBackground(Color.blue);
        btnCalculate.setForeground(Color.white);
        btnCalculate.addActionListener(this);
        btnCalculate.setFocusable(false);
        
        int labelWidth = 150, labelHeight = 40;
        int fieldWidth = 200, fieldHeight = 40;

        lbCustomerId.setBounds(50, 50, labelWidth, labelHeight);
        tfCustomerId.setBounds(50 + labelWidth + 10, 50, fieldWidth, fieldHeight);
        
        lbRegularUnits.setBounds(50, 150, labelWidth, labelHeight);
        tfRegularUnits.setBounds(50 + labelWidth + 10, 150, fieldWidth, fieldHeight);

        lbPeakUnits.setBounds(50, 200, labelWidth, labelHeight);
        tfPeakUnits.setBounds(50 + labelWidth + 10, 200, fieldWidth, fieldHeight);

        lbPhase.setBounds(50, 100, labelWidth, labelHeight);
        rbPhase1.setBounds(50 + labelWidth + 10, 100, 100, labelHeight);
        rbPhase3.setBounds(50 + labelWidth + 120, 100, 100, labelHeight);

        btnCalculate.setBounds(50 + labelWidth + 10, 250, fieldWidth, fieldHeight);

        lbRegUnit.setBounds(500, 50, labelWidth, labelHeight);
        lbRegUnitCost.setBounds(500 + labelWidth + 10, 50, fieldWidth, labelHeight);

        lbPeakUnit.setBounds(500, 100, labelWidth, labelHeight);
        lbPeakUnitCost.setBounds(500 + labelWidth + 10, 100, fieldWidth, labelHeight);

        lbSale.setBounds(500, 150, labelWidth, labelHeight);
        lbSaleTax.setBounds(500 + labelWidth + 10, 150, fieldWidth, labelHeight);

        lbFixed.setBounds(500, 200, labelWidth, labelHeight);
        lbFixCharges.setBounds(500 + labelWidth + 10, 200, fieldWidth, labelHeight);

        lbElectricity.setBounds(500, 250, labelWidth, labelHeight);
        lbElectricityCost.setBounds(500 + labelWidth + 10, 250, fieldWidth, labelHeight);

        lbTotal.setBounds(500, 300, labelWidth, labelHeight);
        lbTotalCost.setBounds(500 + labelWidth + 10, 300, fieldWidth, labelHeight);

        this.add(lbCustomerId);
        this.add(tfCustomerId);
        this.add(lbRegularUnits);
        this.add(tfRegularUnits);
        this.add(lbPeakUnits);
        this.add(tfPeakUnits);
        this.add(lbPhase);
        this.add(rbPhase1);
        this.add(rbPhase3);
        this.add(btnCalculate);
        this.add(lbRegUnit);
        this.add(lbRegUnitCost);
        this.add(lbPeakUnit);
        this.add(lbPeakUnitCost);
        this.add(lbSale);
        this.add(lbSaleTax);
        this.add(lbFixed);
        this.add(lbFixCharges);
        this.add(lbElectricity);
        this.add(lbElectricityCost);
        this.add(lbTotal);
        this.add(lbTotalCost);

        this.setVisible(true);
    }

    public CalculateBill(Client client)
    {
        this.client = client;
        init();
    }
@Override
public void actionPerformed(ActionEvent e) 
{
    if (e.getSource() == btnCalculate) 
    {
        String customerId = tfCustomerId.getText();
        int regUnits = Integer.parseInt(tfRegularUnits.getText());
        int peakUnits = rbPhase3.isSelected() ? Integer.parseInt(tfPeakUnits.getText()) : 0;
        int phase = rbPhase1.isSelected() ? 1 : 3;
//        CustomerOperations customerOperations = new CustomerOperations();
        String[] billDetails = client.calculateBillRequest(customerId, phase, regUnits, peakUnits);

        if (billDetails != null) 
        {
            lbRegUnitCost.setText(billDetails[0]);
            lbPeakUnitCost.setText(billDetails[1]);
            lbSaleTax.setText(billDetails[2]);
            lbFixCharges.setText(billDetails[3]);
            lbElectricityCost.setText(billDetails[4]);
            lbTotalCost.setText(billDetails[5]);
        } 
        else 
        {
            JOptionPane.showMessageDialog(null, "customer not found");
        }
    }
}


}
