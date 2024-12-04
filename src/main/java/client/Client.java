package client;

import assignment2.HomPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CNICInfo;
import model.CustomerType;
import model.MeterType;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Client {
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

    private String sendRequest(String request) {
        try {
            output.println(request); // Send request to server
            return input.readLine(); // Wait for server response
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

//    public List<CNICInfo> showExpiringCnicsRequest()
//    {
//        String response = sendRequest("showExpiringCnics");
//        Type listType = new TypeToken<List<CNICInfo>>() {}.getType();
//
//        return new Gson().fromJson(response, listType);
//    }
public List<CNICInfo> showExpiringCnicsRequest() {
    try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
        List<CNICInfo> expiringCNICs = (List<CNICInfo>) inputStream.readObject();
        if(expiringCNICs!=null)
        {
            System.out.println("in client also there are some expiring cnics");
        }
        return expiringCNICs;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        return null;
    }
}


    public boolean addMeterRequest(String customerId, int meterType) {
        String response = sendRequest("addMeter," + customerId + "," + meterType);
        return Boolean.parseBoolean(response);
    }

    public String addEmployeeRequest(String username, String password) {
        return sendRequest("addEmployee," + username + "," + password);
    }

    public boolean updateExpiryRequest(long cnic, String newExpiryDate) {
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

/*
package client;

import assignment2.HomPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CNICInfo;
import model.CustomerType;
import model.MeterType;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader input;
    private BufferedWriter output;

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            new HomPage(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendLoginRequest(String type, String username, String password) {
        try {
            String request = "login," + type + "," + username + "," + password;
            output.write(request);
            output.newLine();
            output.flush();

            String serverResponse = input.readLine();
            return Boolean.parseBoolean(serverResponse);
        } catch (IOException e) {
            System.out.println("Error during login request: " + e.getMessage());
            return false;
        }
    }
    public boolean changePasswordRequest(String username,String currPassword,String newPassword )
    {
        try
        {
            output.write("changePassword,"+username+","+currPassword+","+newPassword);
            boolean response = Boolean.parseBoolean(input.readLine());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "Password Changed successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "Password change fails");
//            }
            return  response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }
    public boolean addMeterReadingRequest(String customerId,String meterId,String billMonth,int regularUnits,int peakUnits,String billIssueDate)
    {
        try
        {
            output.write("addMeterReading,"+customerId+","+meterId+","+billMonth+","+regularUnits+","+peakUnits+","+billIssueDate);
            boolean response = Boolean.parseBoolean(input.readLine());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "Meter added successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "New meter could not can be added");
//            }
            return  response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }
    public  boolean payBillRequest(String customerId,String meterId)
    {
        try
        {
            output.write("payBill,"+customerId+","+meterId);
            boolean response = Boolean.parseBoolean(input.readLine());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "bill paid successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "bill not paid");
//            }
            return  response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }
    public boolean updateTaxFileRequest(int phase,int type,double unit,double peak,double saleTax,double fixed)
    {
        try
        {
            output.write("updateTaxFile,"+phase+","+type+","+unit+","+peak+","+saleTax+","+fixed);
            boolean response = Boolean.parseBoolean(input.readLine());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "tax file updated successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "tax file not updated");
//            }
            return response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String[][] viewBillsRequest(String customerId,String meterId)
    {
        try {
            output.write("viewBills," + customerId + "," + meterId);
            String serialized = input.readLine();

            String[] rows = serialized.split(";");
            String[][] result = new String[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                // Split each row by ","
                result[i] = rows[i].split(",");
            }
            if(result!=null)
            {
                return result;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "there is no bill for given id");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Map<String, Double> paidUnpaidBillsRequest()
    {
        try
        {
            output.write("paidUnpaidBills");
            String jsonResponse = input.readLine();

            Gson gson = new Gson();
            Map<String, Double> response = gson.fromJson(jsonResponse, Map.class);
//            response.forEach((key, value) -> System.out.println(key + ": " + value));
            return response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<CNICInfo> showExpiringCnicsRequest()
    {
        try {
            output.write("showExpiringCnics");
            String jsonResponse = input.readLine(); // Read JSON response

            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CNICInfo>>() {}.getType();
                List<CNICInfo> result = gson.fromJson(jsonResponse, listType); // Deserialize JSON to list

                return result;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "There are no expiring CNICs");
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }
        return null;
    }
    public boolean addMeterRequest(String customerId,int meterType)
    {
        try
        {
            output.write("addMeter,"+customerId+","+meterType);
            boolean result = Boolean.parseBoolean(input.readLine());
            if(!result)
            {
                JOptionPane.showMessageDialog(null, "New meter could not can be added");
            }
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public String addEmployeeRequest(String username,String password)
    {
        try
        {
            output.write("addEmployee,"+username+","+password);
            String result = input.readLine();
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public boolean updateExpiryRequest(long cnic,String newExpiryDate)
    {
        try
        {
            output.write("updateExpiry,"+cnic+","+newExpiryDate);
            boolean result = Boolean.parseBoolean(input.readLine());
            if(!result)
            {
                JOptionPane.showMessageDialog(null, "cnic expiry date not updated");
            }
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean isCustomerIdValidRequest(String customerId)
    {
        try
        {
            output.write("customerIdValid,"+customerId);
            boolean result = Boolean.parseBoolean(input.readLine());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String[] calculateBillRequest(String customerId,int phase,int regUnits,int peakUnits)
    {
        try {
            output.write("calculateBill," + customerId + "," + phase + "," + regUnits + "," + peakUnits);

            String serializedResponse = input.readLine();
            String[] result = serializedResponse.split(",");

            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }
    public boolean isCnicMatchingCustomerIdRequest(String customerId,String cnic)
    {
        try
        {
            output.write("isCnicMatchingCustomerId,"+customerId+","+cnic);
            boolean result = Boolean.parseBoolean(input.readLine());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean isValidCNICRequest(long cnic)
    {
        try
        {
            output.write("isValidCNIC,"+cnic);
            boolean result = Boolean.parseBoolean(input.readLine());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addCustomerRequest(long cnic, String name, String address, long phone, CustomerType customerType, MeterType meterType)
    {
        try
        {
            output.write("addCustomer,"+cnic+","+name+","+address+","+phone+","+customerType+","+meterType);
            boolean result = Boolean.parseBoolean(input.readLine());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
*/


/*package client;

import assignment2.HomPage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CNICInfo;
import model.CustomerType;
import model.MeterType;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.List;
import java.util.Map;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public static void main(String[] args) {
        new Client().start();
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            new HomPage(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void sendLoginRequestAsync(String type, String username, String password) {
//        new Thread(() -> {
//            boolean result = sendLoginRequest(type, username, password);
//            SwingUtilities.invokeLater(() -> {
//                if (result) {
//                    JOptionPane.showMessageDialog(null, "Login Successful!");
//                    if (type.equalsIgnoreCase("customer")) {
//                        new CustomerActions(this);
//                    } else if (type.equalsIgnoreCase("employee")) {
//                        new EmployeeActions(this);
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Login Failed");
//                }
//            });
//        }).start();
//    }

    public boolean sendLoginRequest(String type,String username, String password) {
        try
        {
            boolean serverResponse = false;
            if(type.equalsIgnoreCase("customer"))
            {
                output.writeUTF("login,"+ type+","+ username + "," + password);

                serverResponse = Boolean.parseBoolean(input.readUTF());
//                handleLoginResponse(serverResponse,"customer");
                return serverResponse;
            }
            else if(type.equalsIgnoreCase("employee"))
            {
                output.writeUTF("login,"+type+","+username+","+password);
                serverResponse = Boolean.parseBoolean(input.readUTF());
                return serverResponse;
//                handleLoginResponse(serverResponse,"employee");
            }
            return serverResponse;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

//    public void handleLoginResponse(boolean response,String type)
//    {
//        if (response && type.equalsIgnoreCase("customer"))
//        {
//            JOptionPane.showMessageDialog(null, "Login Successful!");
//            new CustomerActions(this);
//        }
//        else if(response && type.equalsIgnoreCase("employee"))
//        {
//            JOptionPane.showMessageDialog(null, "Login Successful!");
//            new EmployeeActions(this);
//        }
//        else
//        {
//            JOptionPane.showMessageDialog(null, "Login Failed");
//        }
//    }
    public boolean changePasswordRequest(String username,String currPassword,String newPassword )
    {
        try
        {
            output.writeUTF("changePassword,"+username+","+currPassword+","+newPassword);
            boolean response = Boolean.parseBoolean(input.readUTF());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "Password Changed successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "Password change fails");
//            }
            return  response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }
    public boolean addMeterReadingRequest(String customerId,String meterId,String billMonth,int regularUnits,int peakUnits,String billIssueDate)
    {
        try
        {
            output.writeUTF("addMeterReading,"+customerId+","+meterId+","+billMonth+","+regularUnits+","+peakUnits+","+billIssueDate);
            boolean response = Boolean.parseBoolean(input.readUTF());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "Meter added successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "New meter could not can be added");
//            }
            return  response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }
    public  boolean payBillRequest(String customerId,String meterId)
    {
        try
        {
            output.writeUTF("payBill,"+customerId+","+meterId);
            boolean response = Boolean.parseBoolean(input.readUTF());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "bill paid successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "bill not paid");
//            }
            return  response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }
    public boolean updateTaxFileRequest(int phase,int type,double unit,double peak,double saleTax,double fixed)
    {
        try
        {
            output.writeUTF("updateTaxFile,"+phase+","+type+","+unit+","+peak+","+saleTax+","+fixed);
            boolean response = Boolean.parseBoolean(input.readUTF());
//            if(response)
//            {
//                JOptionPane.showMessageDialog(null, "tax file updated successfully");
//            }
//            else
//            {
//                JOptionPane.showMessageDialog(null, "tax file not updated");
//            }
            return response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String[][] viewBillsRequest(String customerId,String meterId)
    {
        try {
            output.writeUTF("viewBills," + customerId + "," + meterId);
            String serialized = input.readUTF();

            String[] rows = serialized.split(";");
            String[][] result = new String[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                // Split each row by ","
                result[i] = rows[i].split(",");
            }
            if(result!=null)
            {
                return result;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "there is no bill for given id");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Map<String, Double> paidUnpaidBillsRequest()
    {
        try
        {
            output.writeUTF("paidUnpaidBills");
            String jsonResponse = input.readUTF();

            Gson gson = new Gson();
            Map<String, Double> response = gson.fromJson(jsonResponse, Map.class);
//            response.forEach((key, value) -> System.out.println(key + ": " + value));
            return response;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<CNICInfo> showExpiringCnicsRequest()
    {
        try {
            output.writeUTF("showExpiringCnics");
            String jsonResponse = input.readUTF(); // Read JSON response

            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CNICInfo>>() {}.getType();
                List<CNICInfo> result = gson.fromJson(jsonResponse, listType); // Deserialize JSON to list

                return result;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "There are no expiring CNICs");
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public boolean addMeterRequest(String customerId,int meterType)
    {
        try
        {
            output.writeUTF("addMeter,"+customerId+","+meterType);
            boolean result = Boolean.parseBoolean(input.readUTF());
            if(!result)
            {
                JOptionPane.showMessageDialog(null, "New meter could not can be added");
            }
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public String addEmployeeRequest(String username,String password)
    {
        try
        {
            output.writeUTF("addEmployee,"+username+","+password);
            String result = input.readUTF();
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public boolean updateExpiryRequest(long cnic,String newExpiryDate)
    {
        try
        {
            output.writeUTF("updateExpiry,"+cnic+","+newExpiryDate);
            boolean result = Boolean.parseBoolean(input.readUTF());
            if(!result)
            {
                JOptionPane.showMessageDialog(null, "cnic expiry date not updated");
            }
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean isCustomerIdValidRequest(String customerId)
    {
        try
        {
            output.writeUTF("customerIdValid,"+customerId);
            boolean result = Boolean.parseBoolean(input.readUTF());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String[] calculateBillRequest(String customerId,int phase,int regUnits,int peakUnits)
    {
        try {
            output.writeUTF("calculateBill," + customerId + "," + phase + "," + regUnits + "," + peakUnits);

            String serializedResponse = input.readUTF();
            String[] result = serializedResponse.split(",");

            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }
    public boolean isCnicMatchingCustomerIdRequest(String customerId,String cnic)
    {
        try
        {
            output.writeUTF("isCnicMatchingCustomerId,"+customerId+","+cnic);
            boolean result = Boolean.parseBoolean(input.readUTF());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean isValidCNICRequest(long cnic)
    {
        try
        {
            output.writeUTF("isValidCNIC,"+cnic);
            boolean result = Boolean.parseBoolean(input.readUTF());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addCustomerRequest(long cnic,String name,String address,long phone,CustomerType customerType,MeterType meterType)
    {
        try
        {
            output.writeUTF("addCustomer,"+cnic+","+name+","+address+","+phone+","+customerType+","+meterType);
            boolean result = Boolean.parseBoolean(input.readUTF());
            return result;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
 */
