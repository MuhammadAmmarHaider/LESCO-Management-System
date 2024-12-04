//package server;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import Controller.CustomerOperations;
//import Controller.EmployeeOperations;
//
//public class server1 {
//    private static final int PORT = 12345;
//
//    public static void main(String[] args) {
//        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            System.out.println("Server started on port " + PORT);
//
//            while (true) {
//                Socket clientSocket = serverSocket.accept();
//                System.out.println("New client connected: " + clientSocket.getInetAddress());
//                new ClientHandler(clientSocket).start();
//            }
//        } catch (IOException e) {
//            System.out.println("Error starting the server: " + e.getMessage());
//        }
//    }
//
//    private static class ClientHandler extends Thread {
//        private final Socket clientSocket;
//
//        public ClientHandler(Socket clientSocket) {
//            this.clientSocket = clientSocket;
//        }
//
//        @Override
//        public void run() {
//            try (
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
//            ) {
//                String request;
//                CustomerOperations customerOperations = new CustomerOperations();
//                EmployeeOperations employeeOperations = new EmployeeOperations();
//
//                while ((request = reader.readLine()) != null) {
//                    System.out.println("Received request: " + request);
//                    String[] requestParts = request.split(",");
//                    String response = "false";
//
//                    switch (requestParts[0]) {
//                        case "login": {
//                            String type = requestParts[1];
//                            String username = requestParts[2];
//                            String password = requestParts[3];
//                            System.out.println("type is :"+type+" username is:"+username+" password is :"+password);
//                            if (type.equalsIgnoreCase("customer")) {
//                                if (customerOperations.login(username, password)) {
//                                    response = "true";
//                                    writer.write(String.valueOf(true));
//                                    writer.newLine();
//                                    writer.flush();
//                                }
//                            }
//                            else if (type.equalsIgnoreCase("employee")) {
//                                if (employeeOperations.login(username, password)) {
//                                    writer.write(String.valueOf(true));
//                                    writer.newLine();
//                                    writer.flush();
////                                    response = "true";
//                                }
//                            }
//                            else
//                            {
//                                writer.write(String.valueOf(false));
//                                writer.newLine();
//                                writer.flush();
//                            }
//                            break;
//                        }
//                        default:
//                            response = "Invalid request";
//                            writer.write(response);
//                            writer.newLine();
//                            writer.flush();
//                            break;
//                    }
//
//                    writer.write(response);
//                    writer.newLine();
//                    writer.flush();
//                }
//            } catch (IOException e) {
//                System.out.println("Error handling client request: " + e.getMessage());
//            } finally {
//                try {
//                    clientSocket.close();
//                } catch (IOException e) {
//                    System.out.println("Error closing client socket: " + e.getMessage());
//                }
//            }
//        }
//    }
//}





package server;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import Controller.CustomerOperations;
import Controller.EmployeeOperations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.CNICInfo;
import model.CustomerType;
import model.MeterType;
import utility.LocalDateAdapter;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args)
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            System.out.println("server.Server started on port " + PORT);

            while (true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread
    {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run()
        {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
            )
            {
                String request;
                EmployeeOperations employeeOperations = new EmployeeOperations();
                CustomerOperations customerOperations = new CustomerOperations();

                while ((request = reader.readLine()) != null)
                {
                    System.out.println("Received request: " + request);
                    String[] requestParts = request.split(",");
                    switch (requestParts[0])
                    {
                        case "login": {
                            String type = requestParts[1];
                            String username = requestParts[2];
                            String password = requestParts[3];
                            boolean result = false;
                            if(type.equalsIgnoreCase("customer"))
                            {
                                System.out.println("inside server customer login part");
                                result = customerOperations.login(username,password);
                            }
                            else
                            {
                                result = employeeOperations.login(username, password);
                            }
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                            break;
                        }
                        case "changePassword": {
                            String username = requestParts[1];
                            String currPassword = requestParts[2];
                            String newPassword = requestParts[3];
                            boolean result = employeeOperations.changePassword(username,currPassword,newPassword);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                            break;
                        }
                        case "addMeterReading":{
                            String customerId = requestParts[1];
                            String meterId = requestParts[2];
                            String billMonth = requestParts[3];
                            int regularUnits = Integer.parseInt(requestParts[4]);
                            int peakUnits = Integer.parseInt(requestParts[5]);
                            String billIssueDate = requestParts[6];
                            boolean result = employeeOperations.addMeterReading(customerId,meterId,billMonth,regularUnits,peakUnits,billIssueDate);
//                            response = result?"Add meter reading successful":"Add meter reading failed";
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                            break;
                        }
                        case "payBill":{
                            String customerId = requestParts[1];
                            String meterId = requestParts[2];
                            boolean billPaid = employeeOperations.payBill(customerId, meterId);
//                            response = billPaid ? "Pay bill successful" : "Pay bill failed";
                            writer.write(String.valueOf(billPaid));
                            writer.newLine();
                            writer.flush();
                            break;
                        }
                        case "updateTaxFile":{
                            int phase = Integer.parseInt(requestParts[1]);
                            int type = Integer.parseInt(requestParts[2]);
                            double unit = Double.parseDouble(requestParts[3]);
                            double peak = Double.parseDouble(requestParts[4]);
                            double saleTax = Double.parseDouble(requestParts[5]);
                            double fixed = Double.parseDouble(requestParts[6]);
                            boolean result = employeeOperations.updateTaxFile(phase,type,unit,peak,saleTax,fixed);
//                            response = result?"Update tax file successful":"Update tax file failed";
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                            break;
                        }

                        case "viewBills":
                        {
                            String customerId = requestParts[1];
                            String meterId = requestParts[2];
                            String[][] result = employeeOperations.getBillDetails(customerId, meterId);

                            StringBuilder serialized = new StringBuilder();
                            for (String[] row : result) {
                                serialized.append(String.join(",", row)).append(";");
                            }

                            writer.write(serialized.toString());
                            writer.newLine();
                            writer.flush();
                            break;
                        }

                        case "paidUnpaidBills": {
                            Map<String, Double> result = employeeOperations.getPaidAndUnpaidReport();

                            Gson gson = new Gson();
                            String jsonResult = gson.toJson(result);

                            writer.write(jsonResult);
                            writer.newLine();
                            writer.flush();
                            break;
                        }

//                        case "showExpiringCnics":
//                        {
//                            List<CNICInfo> result = employeeOperations.fetchExpiringCNICs();
//                            if(result!=null){
//                                System.out.println("there are some expiring cnics");
//                            }
//                            Gson gson = new GsonBuilder()
//                                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
//                                    .create();
//                            String jsonResult = gson.toJson(result);
//                            writer.write(jsonResult);
//                            writer.newLine();
//                            writer.flush();
//                            break;
//                        }
                        case "showExpiringCnics":
                        {
                            try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

                                List<CNICInfo> result = employeeOperations.fetchExpiringCNICs();
                                if(result !=null)
                                {
                                    System.out.println("there are some expiring cnics");
                                }
                                outputStream.writeObject(result);
                                outputStream.flush();
                            } catch (IOException e) {
                                System.out.println("Error in sending object: " + e.getMessage());
                            }
                            break;
                        }


                        case "addMeter":{
                            String customerId = requestParts[1];
                            int meterType = Integer.parseInt(requestParts[2]);
                            boolean result = employeeOperations.addMeter(customerId,meterType);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                        }
                        case "addEmployee":{
                            String username = requestParts[1];
                            String password = requestParts[2];
                            String result = employeeOperations.addEmployee(username,password);
                            writer.write(result);
                            writer.newLine();
                            writer.flush();
                            break;
                        }
                        case "updateExpiry":{
                            long cnic = Long.parseLong(requestParts[1]);
                            String newExpiryDate = requestParts[2];
                            boolean result = customerOperations.updateExpiryDate(cnic,newExpiryDate);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                        }
                        case "customerIdValid":{
                            String customerId = requestParts[1];
                            boolean result = customerOperations.isCustomerIdValid(customerId);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                        }
                        case "calculateBill": {
                            String customerId = requestParts[1];
                            int phase = Integer.parseInt(requestParts[2]);
                            int regUnits = Integer.parseInt(requestParts[3]);
                            int peakUnits = Integer.parseInt(requestParts[4]);

                            String[] result = customerOperations.calculateBill(customerId, phase, regUnits, peakUnits);
                            String newResult = String.join(",", result);
                            writer.write(newResult);
                            writer.newLine();
                            writer.flush();
                            break;
                        }

                        case "isCustomerIdValid":
                        {
                            String customerId = requestParts[1];
                            boolean result = customerOperations.isCustomerIdValid(customerId);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                            break;
                        }
                        case "isCnicMatchingCustomerId":
                        {
                            String customerId = requestParts[1];
                            String cnic = requestParts[2];
                            boolean result = customerOperations.isCnicMatchingCustomerId(customerId,cnic);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                        }
                        case "isValidCNIC":{
                            long cnic = Long.parseLong(requestParts[1]);
                            System.out.println("inside server cnic is: "+cnic);
                            boolean result = customerOperations.isValidCNIC(cnic);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                        }
                        case "addCustomer":{
                            long cnic = Long.parseLong(requestParts[1]);
                            String name = requestParts[2];
                            String address = requestParts[3];
                            long phone= Long.parseLong(requestParts[4]);
                            CustomerType customerType = CustomerType.valueOf(requestParts[5]);
                            MeterType meterType = MeterType.valueOf(requestParts[6]);
                            boolean result = customerOperations.addCustomer(cnic,name,address,phone,customerType,meterType);
                            writer.write(String.valueOf(result));
                            writer.newLine();
                            writer.flush();
                        }

                        default:
                        {
                            String response;
                            response = "Invalid request";
                            writer.write(response);
                            writer.newLine();
                            writer.flush();
                            break;
                        }

                    }

                }
            }
            catch (IOException e)
            {
                System.out.println("Error handling client request: " + e.getMessage());
            }
            finally
            {
                try
                {
                    clientSocket.close();
                }
                catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
