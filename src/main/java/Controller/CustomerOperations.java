package Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Customer;
import model.Meter;
import model.MeterType;
import model.CustomerType;

public class CustomerOperations
{
    private static String CustomerInfo = "CustomerInfo.txt";
    private static String TariffTaxInfo = "TariffTaxInfo.txt";

    public boolean isCustomerIdValid(String customerId)
    {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(CustomerInfo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] result = line.split(",");
                if (result[0].equals(customerId)) {
                    found = true;
                    System.out.println("customer id is valid and it's founded");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return found;
    }

    public boolean isCnicMatchingCustomerId(String customerId, String cnic) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(CustomerInfo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] result = line.split(",");
                if (result[0].equals(customerId) && result[1].equals(cnic)) {
                    found = true;
                    System.out.println("cnic matches the customer id");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return found;
    }
    public boolean login(String username, String password) {
        boolean login = false;
        System.out.println("inside customer login");
        if(isCustomerIdValid(username) && isValidCNIC(Long.parseLong(password)) && isCnicMatchingCustomerId(username,password))
        {
            login = true;
        }
        return login;
    }
    public String[] calculateBill(String customerId, int phase, int regUnits, int peakUnits) {
        String[] billDetails = new String[7];
        try (BufferedReader reader1 = new BufferedReader(new FileReader(CustomerInfo))) {
            String line1;
            boolean customerFound = false;

            while ((line1 = reader1.readLine()) != null) {
                List<String> result = Arrays.asList(line1.split(","));
                if (customerId.equals(result.get(0))) {
                    customerFound = true;

                    try (BufferedReader reader2 = new BufferedReader(new FileReader(TariffTaxInfo))) {
                        int count = 0;
                        double regularUnitCost = 0, fixedCharges = 0, saleTax = 0, peakUnitCost = 0;
                        List<String> result1;

                        String customerType = result.get(5); // DOMESTIC or COMMERCIAL
                        String meterPhase = result.get(7);  // SINGLE_PHASE or THREE_PHASE

                        while ((line1 = reader2.readLine()) != null && count < 4) {
                            result1 = Arrays.asList(line1.split(","));
                            if (meterPhase.equals("SINGLE_PHASE")) {
                                if ("DOMESTIC".equals(customerType) && count == 0) {
                                    regularUnitCost = Double.parseDouble(result1.get(1));
                                    peakUnitCost = 0;
                                    saleTax = Double.parseDouble(result1.get(3));
                                    fixedCharges = Double.parseDouble(result1.get(4));
                                    break;
                                } else if ("COMMERCIAL".equals(customerType) && count == 1) {
                                    regularUnitCost = Double.parseDouble(result1.get(1));
                                    peakUnitCost = 0;
                                    saleTax = Double.parseDouble(result1.get(3));
                                    fixedCharges = Double.parseDouble(result1.get(4));
                                    break;
                                }
                            } else if (meterPhase.equals("THREE_PHASE")) {
                                if ("DOMESTIC".equals(customerType) && count == 2) {
                                    regularUnitCost = Double.parseDouble(result1.get(1));
                                    peakUnitCost = Double.parseDouble(result1.get(2));
                                    saleTax = Double.parseDouble(result1.get(3));
                                    fixedCharges = Double.parseDouble(result1.get(4));
                                    break;
                                } else if ("COMMERCIAL".equals(customerType) && count == 3) {
                                    regularUnitCost = Double.parseDouble(result1.get(1));
                                    peakUnitCost = Double.parseDouble(result1.get(2));
                                    saleTax = Double.parseDouble(result1.get(3));
                                    fixedCharges = Double.parseDouble(result1.get(4));
                                    break;
                                }
                            }
                            count++;
                        }
                        double electricityCost = regUnits * regularUnitCost + peakUnits * peakUnitCost;
                        double totalAmount = electricityCost + (saleTax / 100) * electricityCost + fixedCharges;

                        billDetails[0] = String.format("%f", regularUnitCost);
                        billDetails[1] = String.format("%f", peakUnitCost);
                        billDetails[2] = String.format("%f", saleTax);
                        billDetails[3] = String.format("%f", fixedCharges);
                        billDetails[4] = String.format("%f", electricityCost);
                        billDetails[5] = String.format("%f", totalAmount);
                        billDetails[6] = "Customer ID: " + customerId + "\nName: " + result.get(2) + "\nAddress: " + result.get(3) + "\nPhone: " + result.get(4);
                    }
                    break;
                }
            }
            if (!customerFound) {
                billDetails = null;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return billDetails;
    }

    public boolean isValidCNIC(long cnic)
    {
        String cnicStr = String.valueOf(cnic);
        System.out.println("inside customer operations cnic is: "+cnic);
        return cnicStr.length() == 13;
    }

//    public boolean updateExpiryDate(long cnic, String newExpiryDate)
//    {
//        boolean updated = false;
//        List<String> fileContent = new ArrayList<>();
//        String line;
//
//        try (BufferedReader reader = new BufferedReader(new FileReader("NadraDB.txt"))) {
//            while ((line = reader.readLine()) != null) {
//                List<String> result = Arrays.asList(line.split(","));
//                if (String.valueOf(cnic).equals(result.get(0))) {
//                    result.set(1, newExpiryDate);
//                    updated = true;
//                }
//                fileContent.add(String.join(",", result));
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//        if (updated) {
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("NadraDB.txt"))) {
//                for (String updatedLine : fileContent) {
//                    writer.write(updatedLine);
//                    writer.newLine();
//                }
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//                return false;
//            }
//        }
//        return updated;
//    }

    public boolean updateExpiryDate(String cnic, String newExpiryDate)
    {
        boolean updated = false;
        List<String> fileContent = new ArrayList<>();
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader("NadraDB.txt"))) {
            while ((line = reader.readLine()) != null) {
                System.out.println("here reached");
                List<String> result = Arrays.asList(line.split(","));
                if (cnic.equals(result.getFirst())) {

                    result.set(1, newExpiryDate);
                    updated = true;
                    System.out.println("Updated CNIC: " + cnic + " with new expiry date: " + newExpiryDate);
                } else {
                    System.out.println("No match for CNIC: " + cnic + " in line: " + line);
                }
                fileContent.add(String.join(",", result));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("NadraDB.txt"))) {
                for (String updatedLine : fileContent) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
                return false;
            }
        }
        else {
            System.out.println("CNIC not found. No updates made.");
        }

        return updated;
    }


    public boolean addCustomer(long cnic, String name, String address, long phone, CustomerType customerType, MeterType meterType) {
        boolean customerExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(CustomerInfo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] result = line.split(",");
                if (result[1].equals(String.valueOf(cnic))) {
                    customerExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (!customerExists) {
            Customer newCustomer = new Customer(cnic, name, address, phone, customerType);
            Meter meter = new Meter(newCustomer.getId(), meterType);
            newCustomer.addMeter(meter);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CustomerInfo, true))) {
                writer.write(newCustomer.getId());
                writer.write(",");
                writer.write(String.valueOf(newCustomer.getCnic()));
                writer.write(",");
                writer.write(newCustomer.getName());
                writer.write(",");
                writer.write(newCustomer.getAddress());
                writer.write(",");
                writer.write(String.valueOf(newCustomer.getPhone()));
                writer.write(",");
                writer.write(String.valueOf(newCustomer.getCustomerType()));
                writer.write(",");
                writer.write(meter.getMeterId());
                writer.write(",");
                writer.write(String.valueOf(meter.getMeterType()));
                writer.write(",");
                writer.write(String.valueOf(meter.getConnectionDate()));
                writer.write(",");
                writer.write("0");
                writer.write(",");
                writer.write("0");
                writer.newLine();
                System.out.println("Customer added successfully");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return true;
        } else {
            System.out.println("User already exists");
        }
        return false;
    }
}
