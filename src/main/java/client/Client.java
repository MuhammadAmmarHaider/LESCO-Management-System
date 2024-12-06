package client;

import view.HomPage;
import com.google.gson.Gson;
import model.CNICInfo;
import model.CustomerType;
import model.MeterType;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client
{
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            new HomPage(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String sendRequest(String request)
    {
        System.out.println("Sending request: " + request);
        try {
            output.println(request);
            return input.readLine();
        }
        catch (IOException e) {
            System.out.println("Error in communication: " + e.getMessage());
            return null;
        }
    }

    public boolean sendLoginRequest(String type, String username, String password) {
        String response = sendRequest("login," + type + "," + username + "," + password);
        return Boolean.parseBoolean(response);
    }

    public boolean changePasswordRequest(String username, String currPassword, String newPassword) {
        String response = sendRequest("changePassword," + username + "," + currPassword + "," + newPassword);
        return Boolean.parseBoolean(response);
    }

    public boolean addMeterReadingRequest(String customerId, String meterId, String billMonth, int regularUnits, int peakUnits, String billIssueDate) {
        String response = sendRequest("addMeterReading," + customerId + "," + meterId + "," + billMonth + "," + regularUnits + "," + peakUnits + "," + billIssueDate);
        return Boolean.parseBoolean(response);
    }

    public boolean payBillRequest(String customerId, String meterId) {
        String response = sendRequest("payBill," + customerId + "," + meterId);
        return Boolean.parseBoolean(response);
    }

    public boolean updateTaxFileRequest(int phase, int type, double unit, double peak, double salesTax, double fixed) {
        String response = sendRequest("updateTaxFile," + phase + "," + type + "," + unit + "," + peak + "," + salesTax + "," + fixed);
        return Boolean.parseBoolean(response);
    }

    public String[][] viewBillsRequest(String customerId, String meterId) {
        String response = sendRequest("viewBills," + customerId + "," + meterId);
        if (response == null || response.isEmpty()) return null;

        String[] rows = response.split(";");
        String[][] result = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split(",");
        }
        return result;
    }

    public Map<String, Double> paidUnpaidBillsRequest() {
        String response = sendRequest("paidUnpaidBills");
        return new Gson().fromJson(response, Map.class);
    }
    public List<CNICInfo> showExpiringCnicsRequest() {
        String response = sendRequest("showExpiringCnics");
        List<CNICInfo> cnicList = new ArrayList<>();

        if (response != null && !response.isEmpty()) {
            String[] records = response.split(";");
            for (String record : records) {
                if (!record.isEmpty()) {
                    String[] attributes = record.split(",");
                    String cnic = attributes[0];
                    LocalDate expiryDate = LocalDate.parse(attributes[1]);
                    cnicList.add(new CNICInfo(cnic, expiryDate));
                }
            }
        }

        return cnicList;
    }

    public boolean addMeterRequest(String customerId, int meterType) {
        String response = sendRequest("addMeter," + customerId + "," + meterType);
        return Boolean.parseBoolean(response);
    }

    public String addEmployeeRequest(String username, String password) {
        return sendRequest("addEmployee," + username + "," + password);
    }

    public boolean updateExpiryRequest(String cnic, String newExpiryDate) {
        String response = sendRequest("updateExpiry," + cnic + "," + newExpiryDate);
        return Boolean.parseBoolean(response);
    }

    public boolean isCustomerIdValidRequest(String customerId) {
        String response = sendRequest("customerIdValid," + customerId);
        return Boolean.parseBoolean(response);
    }

    public String[] calculateBillRequest(String customerId, int phase, int regUnits, int peakUnits) {
        String response = sendRequest("calculateBill," + customerId + "," + phase + "," + regUnits + "," + peakUnits);
        return response.split(",");
    }

    public boolean isCnicMatchingCustomerIdRequest(String customerId, String cnic) {
        String response = sendRequest("isCnicMatchingCustomerId," + customerId + "," + cnic);
        return Boolean.parseBoolean(response);
    }

    public boolean isValidCNICRequest(long cnic)
    {
        System.out.println("inside client cnic is: "+cnic);
        String response = sendRequest("isValidCNIC," + cnic);
        return Boolean.parseBoolean(response);
    }

    public boolean addCustomerRequest(long cnic, String name, String address, long phone, CustomerType customerType, MeterType meterType) {
        String response = sendRequest("addCustomer," + cnic + "," + name + "," + address + "," + phone + "," + customerType + "," + meterType);
        return Boolean.parseBoolean(response);
    }
}
