package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class RandomId
{
      public static String generateUniqueCustomerId(File customers,int index) 
      {
    
        Random rand = new Random();
        String newId = null;
        boolean isUnique;
      
            do {
            newId = String.format("%04d", rand.nextInt(10000));
            isUnique = checkUniqueId(newId, customers,index);
        } while (!isUnique);
      
        return newId;
    }

    private static boolean checkUniqueId(String id, File customers,int index) {
        if (!customers.exists()) {
            return true; 
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(customers));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerData = line.split(",");
                if (customerData.length > 0 && customerData[index].equals(id)) {
                    return false; 
                }
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        return true;
    }
}


public class Customer {
    private String id;
    private long cnic;
    private String name;
    private String address;
    private long phone;
    private CustomerType customerType;
    private ArrayList<Meter> meters;

    public Customer(long cnic, String name, String address, long phone, CustomerType customerType) {
        try {
            this.id = RandomId.generateUniqueCustomerId(new File("CustomerInfo.txt"), 0);

            if (isValidCNIC(cnic)) {
                this.cnic = cnic;
            } else {
                throw new IOException("cnic should be 13 digits");
          
            }

            if (isValidPhone(phone)) {
                this.phone = phone;
            } else {
                throw new IOException("phone should be 11 digits");
          
            }

            this.name = name;
            this.address = address;
            this.customerType = customerType;
            this.meters = new ArrayList<>();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(0); 
        }
    }

    public Customer(long cnic, String name, String address, long phone, CustomerType customerType, Meter meter) {
        this(cnic, name, address, phone, customerType);

        if (meter != null) {
            this.meters.add(meter);
        }
    }

    public Customer(long cnic, String name, String address, long phone, CustomerType customerType, ArrayList<Meter> meters) {
        this(cnic, name, address, phone, customerType);

        if (meters != null) {
            int count = 0;
            for (int i = 0; i < meters.size() && count < 3; i++) {
                this.meters.add(meters.get(i));
                count++;
            }
        }
    }

 
    private boolean isValidCNIC(long cnic) {
        String cnicStr = String.valueOf(cnic);
        return cnicStr.length() == 13 && cnic > 0;
    }

    private boolean isValidPhone(long phone) {
        String phoneStr = String.valueOf(phone);
        return phoneStr.length() == 11 && phone > 0;
    }

    public void setCnic(long cnic) {
        if (isValidCNIC(cnic)) {
            this.cnic = cnic;
        } else {
            System.out.println("invalid cnic: must be 13 digits loong");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(long phone) {
        if (isValidPhone(phone)) {
            this.phone = phone;
        } else {
            System.out.println("invalid phone: must be 11 digits long");
        }
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public void addMeter(Meter meter) {
        if (this.meters.size() < 3) {
            this.meters.add(meter);
        } else {
            System.out.println("customer already has 3 meters.");
        }
    }

    public String getId() {
        return id;
    }

    public long getCnic() {
        return cnic;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getPhone() {
        return phone;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public ArrayList<Meter> getMeters() {
        return meters;
    }
}
