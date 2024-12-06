
package model;

import java.time.LocalDate;

public class CNICInfo
{
    private String cnic;
    private LocalDate expiryDate;

    public CNICInfo(String cnic, LocalDate expiryDate) 
    {
        this.cnic = cnic;
        this.expiryDate = expiryDate;
    }

    public String getCnic() {
        return cnic;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }
    @Override
    public String toString() {
        return "CNIC: " + cnic + ", Expiry Date: " + expiryDate;
    }
}
