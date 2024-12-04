package model;

import java.io.File;
import java.time.LocalDate;


public class Meter
{
    private String meterId;
    private String customerId;
    private MeterType meterType;
    private LocalDate connectionDate;
    private int regularUnits;
    private int peakHourUnits;
   
    public Meter(String customerId,MeterType meterType)
    {
        this.meterId = RandomId.generateUniqueCustomerId(new File("CustomerInfo.txt"),6);
        this.customerId = customerId;
        this.meterType = meterType;
        this.connectionDate = LocalDate.now();
        this.regularUnits = 0;
        this.peakHourUnits = 0;
    }
    public Meter(String customerId, MeterType meterType,int regularUnits,int peakHourUnits)
    {
        this.customerId = customerId;
         this.meterId = RandomId.generateUniqueCustomerId(new File("CustomerInfo.txt"),6);
        this.meterType = meterType;
        this.connectionDate = LocalDate.now();
        this.regularUnits = regularUnits;
        if(meterType == MeterType.THREE_PHASE)
        this.peakHourUnits = peakHourUnits;
        else
            this.peakHourUnits =0;
    }
   
    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public void setConnectionDate(LocalDate connectionDate) {
        this.connectionDate = connectionDate;
    }

    public void setRegularUnits(int regularUnits) {
        this.regularUnits = regularUnits;
    }

    public void setPeakHourUnits(int peakHourUnits) {
        if(this.meterType == MeterType.THREE_PHASE)
        {
            this.peakHourUnits = peakHourUnits;
            
        }
        else
        {
            System.out.println("Peak hour units can be set for only three phase meters not for single pahse");
        }
    }
    
    public MeterType getMeterType() {
        return meterType;
    }
    public String getMeterId()
    {
        return meterId;
    }
    public LocalDate getConnectionDate() {
        return connectionDate;
    }

    public int getRegularUnits() {
        return regularUnits;
    }

    public int getPeakHourUnits() {
        return peakHourUnits;
    }
    void add1PhaseMeterReading(int regularUnits)
    {
        this.regularUnits = regularUnits;
    }
    void add3PhaseMeterReading(int regularUnits,int peakUnits)
    {
        this.regularUnits = regularUnits;
        this.peakHourUnits = peakUnits;
    }
}