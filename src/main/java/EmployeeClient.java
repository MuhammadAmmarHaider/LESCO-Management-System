//import java.io.*;
//import java.net.*;
//
//public class EmployeeClient {
//    private static final String SERVER_ADDRESS = "localhost";  // Change if server is on a different machine
//    private static final int SERVER_PORT = 12345;
//
//    public static void main(String[] args) {
//        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
//
//            System.out.println("Connected to server...");
//            out.println("employee");  // Indicate this is an employee client
//
//            System.out.println("Enter CNIC to update expiry date:");
//            String cnic = userInput.readLine();
//            System.out.println("Enter new expiry date (YYYY-MM-DD):");
//            String newExpiryDate = userInput.readLine();
//
//            out.println("updateCNIC");  // Request to update CNIC expiry
//            out.println(cnic);
//            out.println(newExpiryDate);
//
//            String response = in.readLine();
//            System.out.println("server.Server response: " + response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
