package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.MeterType;
import model.Bill;
import model.BillStatus;
import model.CNICInfo;
import model.CustomerType;
import model.Employee;
import model.Meter;

public class EmployeeOperations {

    public static String CustomerInfo = "CustomerInfo.txt";
    public static String EmployeesData = "EmployeesData.txt";
    public static String BillingInfo = "BillingInfo.txt";
    public static String TariffTaxInfo = "TariffTaxInfo.txt";
    public static String NadraDB = "NadraDB.txt";

    public boolean login(String username, String password) {
        boolean login = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(EmployeesData))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> result = Arrays.asList(line.split(","));
                if (username.equals(result.get(0))) {
                    if (password.equals(result.get(1))) {
                        login = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return login;
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        List<String> fileContent = new ArrayList<>();
        boolean passwordUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(EmployeesData))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> result = new ArrayList<>(List.of(line.split(",")));
                if (username.equals(result.get(0))) {
                    if (currentPassword.equals(result.get(1))) {
                        result.set(1, newPassword);
                        passwordUpdated = true;
                        System.out.println("password changed successfully");
                    }
                }
                fileContent.add(String.join(",", result));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (passwordUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EmployeesData))) {
                for (String updatedLine : fileContent) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("user or password input wrong");
        }
        return passwordUpdated;
    }

    public boolean addMeterReading(String customerId, String meterId, String billMonth, int regularUnits, int peakUnits, String billIssueDate) {
        try {
            File input = new File("CustomerInfo.txt");
            String line;
            String meterType = "";

            try (BufferedReader reader1 = new BufferedReader(new FileReader(input))) {
                while ((line = reader1.readLine()) != null) {
                    String[] result = line.split(",");
                    System.out.println("result is: ");
                    for (String str : result) {
                        System.out.println(str);
                    }
                    if (customerId.equals(result[0]) && meterId.equals(result[6])) {
                        meterType = result[7];
                        break;
                    }
                }
            }

            if (!meterType.isEmpty()) {
                File billing = new File("BillingInfo.txt");

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(billing, true))) {
                    writer.write(customerId + "," + meterId + "," + billMonth + "," + regularUnits + ",");
                    if (meterType.equals("THREE_PHASE")) {
                        writer.write(peakUnits + ",");
                    } else {
                        writer.write("0,");
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date = LocalDate.parse(billIssueDate, formatter);

                    writer.write(date.format(formatter) + ",");
                    Bill bill = new Bill(customerId, meterId, meterType.equals("SINGLE_PHASE") ? MeterType.SINGLE_PHASE : MeterType.THREE_PHASE, billMonth, regularUnits, peakUnits, date, date.plusDays(14));
                    bill.calcualteTotalAmount();
                    writer.write(bill.calculateCost() + "," + bill.getSalesTax() + "," + bill.getFixedCharges() + "," + bill.getTotalAmount() + "," + bill.getDueDate() + "," + bill.getStatus() + ",");
                    writer.write((bill.getPaymentDate() == null ? "yyyy-mm-dd" : bill.getPaymentDate().toString()));
                    writer.newLine();

                    updateCustomerInfo(customerId, regularUnits, peakUnits, meterType);
                    System.out.println("bill generated");
                    return true;
                }
            } else {
                System.out.println("customer id or meter id wrong");
                return false;
            }
        } catch (IOException | DateTimeParseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void updateCustomerInfo(String customerId, int regularReading, Integer peakReading) {
        try {
            List<String> fileContent = new ArrayList<>();
            String line;

            try (BufferedReader reader = new BufferedReader(new FileReader(EmployeesData))) {
                while ((line = reader.readLine()) != null) {
                    List<String> result = Arrays.asList(line.split(","));
                    if (customerId.equals(result.get(0))) {
                        result.set(9, String.valueOf(regularReading));
                        if (peakReading != null) {
                            result.set(10, String.valueOf(peakReading));
                        }
                    }
                    fileContent.add(String.join(",", result));
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(EmployeesData))) {
                for (String updatedLine : fileContent) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
            System.out.println("updated");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCustomerInfo(String customerId, int regularUnits, int peakUnits, String meterType) throws IOException {
        File inputFile = new File("CustomerInfo.txt");
        List<String> fileContent = new ArrayList<>();
        String line;
        boolean customerFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            while ((line = reader.readLine()) != null) {
                List<String> result = new ArrayList<>(Arrays.asList(line.split(",")));
                if (customerId.equals(result.get(0))) {
                    customerFound = true;
                    result.set(9, String.valueOf(regularUnits));
                    if (meterType.equals("THREE_PHASE")) {
                        result.set(10, String.valueOf(peakUnits));
                    }
                }
                fileContent.add(String.join(",", result));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        if (!customerFound) {
            System.out.println("Customer with ID " + customerId + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            for (String updatedLine : fileContent) {
                writer.write(updatedLine);
                writer.newLine();
            }
            System.out.println("Customer file updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public boolean payBill(String customerId, String meterId) {
        boolean paid = false;
        String line;
        List<String> fileContent = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BillingInfo))) {
            while ((line = reader.readLine()) != null) {
                List<String> result = Arrays.asList(line.split(","));
                if (customerId.equals(result.get(0)) && meterId.equals(result.get(1))) {
                    if (String.valueOf(BillStatus.UNPAID).equals(result.get(11))) {
                        result.set(11, String.valueOf(BillStatus.PAID));
                        result.set(12, String.valueOf(LocalDate.now()));
                        paid = true;
                    }
                }
                fileContent.add(String.join(",", result));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (paid) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(BillingInfo))) {
                for (String updatedLine : fileContent) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                System.out.println("Bill paid.");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Bill not paid.");
        }

        return paid;
    }

    public boolean updateTaxFile(int phase, int type, double unit, double peak, double saleTax, double fixed) {
        boolean change = false;
        String line;
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TariffTaxInfo))) {
            int count = 0;
            while ((line = reader.readLine()) != null) {
                List<String> result = Arrays.asList(line.split(","));
                if (phase == 1 && type == 1 && count == 0) {
                    result.set(1, String.valueOf(unit));
                    result.set(2, String.valueOf(peak));
                    result.set(3, String.valueOf(saleTax));
                    result.set(4, String.valueOf(fixed));
                    change = true;
                } else if (phase == 1 && type == 2 && count == 1) {
                    result.set(1, String.valueOf(unit));
                    result.set(2, String.valueOf(peak));
                    result.set(3, String.valueOf(saleTax));
                    result.set(4, String.valueOf(fixed));
                    change = true;
                } else if (phase == 2 && type == 1 && count == 2) {
                    result.set(1, String.valueOf(unit));
                    result.set(2, String.valueOf(peak));
                    result.set(3, String.valueOf(saleTax));
                    result.set(4, String.valueOf(fixed));
                    change = true;
                } else if (phase == 2 && type == 2 && count == 3) {
                    result.set(1, String.valueOf(unit));
                    result.set(2, String.valueOf(peak));
                    result.set(3, String.valueOf(saleTax));
                    result.set(4, String.valueOf(fixed));
                    change = true;
                }
                fileContent.add(String.join(",", result));
                count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (change) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(TariffTaxInfo))) {
                for (String updatedLine : fileContent) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                System.out.println("tax file updated");
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("tax file not updated");
        }
        return false;
    }

    public String[][] getBillDetails(String customerId, String meterId) {
        List<String[]> billDetails = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BillingInfo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> result = Arrays.asList(line.split(","));
                if (customerId.equals(result.get(0)) && meterId.equals(result.get(1))) {
                    String[] billData = {
                            result.get(0),
                            result.get(1),
                            result.get(2),
                            result.get(3),
                            result.get(4),
                            result.get(5),
                            result.get(6),
                            result.get(7),
                            result.get(8),
                            result.get(9),
                            result.get(10),
                            result.get(11),
                            result.get(12)
                    };
                    billDetails.add(billData);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return billDetails.toArray(new String[0][0]);
    }

    public Map<String, Double> getPaidAndUnpaidReport() {
        double paidAmount = 0;
        int paidCount = 0;
        double unpaidAmount = 0;
        int unpaidCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(BillingInfo))) { // Proper closure
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> result = Arrays.asList(line.split(","));
                String paymentStatus = result.get(11);
                double billAmount = Double.parseDouble(result.get(9));

                if ("PAID".equalsIgnoreCase(paymentStatus)) {
                    paidCount++;
                    paidAmount += billAmount;
                } else if ("UNPAID".equalsIgnoreCase(paymentStatus)) {
                    unpaidCount++;
                    unpaidAmount += billAmount;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Map<String, Double> reportData = new HashMap<>();
        reportData.put("paidCount", (double) paidCount);
        reportData.put("paidAmount", paidAmount);
        reportData.put("unpaidCount", (double) unpaidCount);
        reportData.put("unpaidAmount", unpaidAmount);
        return reportData;
    }

    public List<CNICInfo> fetchExpiringCNICs()
    {
        List<CNICInfo> expiringCNICs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate next30Days = today.plusDays(30);

        try (BufferedReader br = new BufferedReader(new FileReader(NadraDB)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String cnic = parts[0];
                LocalDate expiryDate = LocalDate.parse(parts[1], formatter);

                if (!expiryDate.isBefore(today) && !expiryDate.isAfter(next30Days)) {
                    expiringCNICs.add(new CNICInfo(cnic, expiryDate));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return expiringCNICs;
    }

    public boolean addMeter(String customerId, int meterType)
    {
        int count = 0;
        long cnic = 0;
        String name = "";
        String address = "";
        long phone = 0;
        CustomerType type = CustomerType.DOMESTIC;

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(CustomerInfo));
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> result = Arrays.asList(line.split(","));
                if (customerId.equals(result.get(0))) {
                    count++;
                    cnic = Long.parseLong(result.get(1));
                    name = result.get(2);
                    address = result.get(3);
                    phone = Long.parseLong(result.get(4));
                    type = "COMMERCIAL".equals(result.get(5)) ? CustomerType.COMMERCIAL : CustomerType.DOMESTIC;
                }
            }

            if (count > 0 && count < 3) {
                try {
                    writer = new BufferedWriter(new FileWriter(CustomerInfo, true));
                    writer.write(customerId + "," + cnic + "," + name + "," + address + "," + phone + "," + type + ",");
                    Meter temp;
                    if (meterType == 1) {
                        temp = new Meter(customerId, MeterType.SINGLE_PHASE);
                    } else {
                        temp = new Meter(customerId, MeterType.THREE_PHASE);
                    }
                    writer.write(temp.getMeterId() + "," + temp.getMeterType() + "," + temp.getConnectionDate() + "," +
                            temp.getRegularUnits() + "," + temp.getPeakHourUnits());
                    writer.newLine();
                    writer.flush();
                    System.out.println("Meter added successfully.");
                    return true;
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            } else {
                if (count > 2) {
                    JOptionPane.showMessageDialog(null, "Customer already has a maximum of 3 meters.");
                } else {
                    System.out.println("Customer ID not found.");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // Ensure that the BufferedReader and BufferedWriter are closed in the finally block
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
        return false;
    }

    public String addEmployee(String username, String password) {
        Employee temp = new Employee();
        temp.setUsername(username);
        temp.setPassword(password);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            if (EmployeesData != null) {
                reader = new BufferedReader(new FileReader(EmployeesData));
                String line;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] result = line.split(",");
                    if (result[0].equals(temp.getUsername())) {
                        found = true;
                        break;
                    }
                }
                // Close the reader as it is no longer needed after reading
                reader.close();

                if (!found) {
                    if (temp.getUsername().isEmpty() && temp.getPassword().isEmpty()) {
                        return "enter username and password";
                    } else {
                        writer = new BufferedWriter(new FileWriter(EmployeesData, true));
                        writer.write(temp.getUsername());
                        writer.write(",");
                        writer.write(temp.getPassword());
                        writer.newLine();
                        writer.close();
                        return "employee added successfully";
                    }
                } else {
                    return "username already exists";
                }
            } else {
                return "employee file not opened";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            // Ensure that the BufferedReader and BufferedWriter are closed in the finally block
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing file: " + e.getMessage());
            }
        }
        return "";
    }
}

