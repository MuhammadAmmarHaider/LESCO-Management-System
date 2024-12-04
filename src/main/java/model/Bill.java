package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Bill
{
    private String customerId;
    private String meterId;
    private MeterType meterType;
    private Month month;
    private int regularReading;
    private int peakReading;
    private LocalDate readingDate;
    private double regularUnitCost;
    private double peakUnitCost;
    private double saleTax;
    private double fixedcharges;
    private double totalAmount;
    private LocalDate dueDate;
    private BillStatus status;
    private int prevRegular;
    private int prevPeak;
    private LocalDate paymentDate;
    private CustomerType customerType;

    public Bill(String customerId,String meterId,MeterType type, Month month, int regularReading, int peakReading, LocalDate readingDate, LocalDate dueDate) 
    {
        this.customerId = customerId;
        this.meterId = meterId;
        this.meterType = type;
        this.month = month;
        this.regularReading = regularReading;
        this.peakReading = peakReading;
        this.readingDate = readingDate;
        this.dueDate = dueDate;
        this.status = BillStatus.UNPAID;
        this.paymentDate = null;
        
              File tarrif = new File("TariffTaxInfo.txt");
      try
      {
          if(tarrif != null )
          {
                BufferedReader reader = new BufferedReader(new FileReader(tarrif));
                String line;
                int count =0;
                while((line = reader.readLine())!=null && count<4)
                {
                    String [] result = line.split(",");
                    if(getCustomerType(customerId) == CustomerType.DOMESTIC)
                    {
                            if(meterType == MeterType.SINGLE_PHASE)
                            {
                                if(count==0)
                                {
                                    regularUnitCost = Double.parseDouble(result[1]);
                                    this.fixedcharges = Double.parseDouble(result[4]);
                                    this.saleTax = Double.parseDouble(result[3]);
                                    peakUnitCost = 0;
                                }
                                
                            }
                            else if(meterType == MeterType.THREE_PHASE)
                            {
                                if(count==2)
                                {
                                    regularUnitCost = Double.parseDouble(result[1]);
                                    this.fixedcharges = Double.parseDouble(result[4]);
                                    this.saleTax = Double.parseDouble(result[3]);
                                    peakUnitCost = Double.parseDouble(result[2]);
                                }
                            }
                    }
                    else
                    {
                         
                            if(meterType == MeterType.SINGLE_PHASE)
                            {
                                if(count==1)
                                {
                                    regularUnitCost = Double.parseDouble(result[1]);
                                    this.fixedcharges = Double.parseDouble(result[4]);
                                    this.saleTax = Double.parseDouble(result[3]);
                                    peakUnitCost = 0;
                                }
                            }
                            else if(meterType == MeterType.THREE_PHASE)
                            {
                                if(count==3)
                                {
                                    regularUnitCost = Double.parseDouble(result[1]);
                                    this.fixedcharges = Double.parseDouble(result[4]);
                                    this.saleTax = Double.parseDouble(result[3]);
                                    peakUnitCost = Double.parseDouble(result[2]);
                                }
                            }
                        }
                   count++;
                }
              
          }
      }
      catch(IOException e)
      {
          System.out.println(e.getMessage());
      }
  
    }
    public Bill(String customerId, String meterId, MeterType type, String month, int regularReading, int peakReading, LocalDate readingDate, LocalDate dueDate) 
    {
        this(customerId, meterId, type, Month.valueOf(month.toUpperCase()), regularReading, peakReading, readingDate, dueDate);
    }

    private CustomerType getCustomerType(String customerId)
    {   
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader("CustomerInfo.txt"));
            if(reader!=null)
            {
                String line;
                while((line = reader.readLine())!=null)
                {
                    List<String>result = Arrays.asList(line.split(",")); 
                    if(customerId.equals(result.get(0)))
                    {
                        if(String.valueOf(CustomerType.DOMESTIC).equals(result.get(5)))
                        {
                            return CustomerType.DOMESTIC;
                        }
                        else
                        {
                            return CustomerType.COMMERCIAL;
                        }
                    }
                }
            }
            else
            {
                System.out.println("could not can open the customer info file ");
            }
            
        } catch (Exception e) 
        {
            
        }
        return null;
    }
    public double calculateCost()
    {
        
        return (this.regularReading - this.prevRegular)*regularUnitCost+ (this.peakReading-this.prevPeak) * peakUnitCost;
    }
    public void calcualteTotalAmount()
    {
        this.totalAmount = calculateCost()+this.fixedcharges+((this.saleTax/100)*calculateCost());
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setRegularReading(int regularReading) {
        this.regularReading = regularReading;
    }

    public void setPeakReading(int peakReading) {
        this.peakReading = peakReading;
    }

    public void setReadingDate(LocalDate readingDate) {
        this.readingDate = readingDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    public void setMeterId(String meterId)
    {
        this.meterId = meterId;
    }
    public String getMeterId()
    {
        return this.meterId;
    }
    public String getCustomerId() {
        return customerId;
    }

    public Month getMonth() {
        return month;
    }
    public double getSalesTax()
    {
        return this.saleTax;
    }
    public double getFixedCharges()
    {
        return this.fixedcharges;
    }
    public int getRegularReading() {
        return regularReading;
    }

    public int getPeakReading() {
        return peakReading;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BillStatus getStatus() {
        return status;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    } 
    public void printBillDetails()
    {
        System.out.println("Customer Id: "+this.customerId);
        System.out.println("Meter Id: "+this.meterId);
        System.out.println("Bill Month: "+this.month);
        System.out.println("Meter Type: "+this.meterType);
        System.out.println("Units consumed in regular hours: "+this.regularReading);
        System.out.println("Units consumed in peak hours: "+this.peakReading);
        System.out.println("Reading Date: "+this.readingDate);
        System.out.println("% sale tax: "+this.saleTax);
        System.out.println("Fixed Charges: "+this.fixedcharges);
        System.out.println("Total Amount: "+this.totalAmount);
        System.out.println("Paid Status: "+this.status);
        if(this.status == BillStatus.PAID)
        {
            System.out.println("Payment Date: "+this.paymentDate);
        }
    }
}