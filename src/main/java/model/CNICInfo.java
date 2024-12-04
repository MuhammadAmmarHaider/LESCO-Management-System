
package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CNICInfo implements Serializable {
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

//    public void setExpiryDate(LocalDate expiryDate)
//    {
//        this.expiryDate = expiryDate;
//    }
//        public static List<CNICInfo> getExpiringCNICs(String filePath)
//        {
//        List<CNICInfo> expiringCNICs = new ArrayList<>();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate today = LocalDate.now();
//        LocalDate next30Days = today.plusDays(30);
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                String cnic = parts[0];
//                LocalDate expiryDate = LocalDate.parse(parts[1], formatter);
//
//                if (!expiryDate.isBefore(today) && !expiryDate.isAfter(next30Days)) {
//                    expiringCNICs.add(new CNICInfo(cnic, expiryDate));
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//
//        return expiringCNICs;
//    }
//    public static boolean isValidCNIC(long cnic)
//    {
//        String cnicStr = String.valueOf(cnic);
//        return cnicStr.length() == 13 && cnic > 0;
//    }
//        public static boolean isValidPhone(long phone)
//    {
//        String phoneStr = String.valueOf(phone);
//        return phoneStr.length() == 11 && phone > 0;
//    }
    @Override
    public String toString() {
        return "CNIC: " + cnic + ", Expiry Date: " + expiryDate;
    }
}
