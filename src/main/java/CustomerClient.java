//import java.io.*;
//import java.net.*;
//
//public class CustomerClient {
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
//            out.println("customer");  // Indicate this is a customer client
//
//            System.out.println("Enter Customer ID:");
//            String customerId = userInput.readLine();
//            System.out.println("Enter regular units used:");
//            int regUnits = Integer.parseInt(userInput.readLine());
//            System.out.println("Enter peak units used:");
//            int peakUnits = Integer.parseInt(userInput.readLine());
//
//            out.println("calculateBill");  // Request to calculate bill
//            out.println(customerId);
//            out.println(regUnits);
//            out.println(peakUnits);
//
//            String response;
//            while ((response = in.readLine()) != null) {
//                System.out.println(response);  // Display the bill details or failure message
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
