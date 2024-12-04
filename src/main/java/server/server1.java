package server;

import java.io.*;
        import java.net.ServerSocket;
import java.net.Socket;
import Controller.CustomerOperations;
import Controller.EmployeeOperations;

public class server1 {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error starting the server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
            ) {
                String request;
                CustomerOperations customerOperations = new CustomerOperations();
                EmployeeOperations employeeOperations = new EmployeeOperations();

                while ((request = reader.readLine()) != null) {
                    System.out.println("Received request: " + request);
                    String[] requestParts = request.split(",");
                    String response = "false";

                    switch (requestParts[0]) {
                        case "login": {
                            String type = requestParts[1];
                            String username = requestParts[2];
                            String password = requestParts[3];
                            System.out.println("type is :"+type+" username is:"+username+" password is :"+password);
                            if (type.equalsIgnoreCase("customer")) {
                                if (customerOperations.login(username, password)) {
                                    response = "true";
                                    writer.write(String.valueOf(true));
                                    writer.newLine();
                                    writer.flush();
                                }
                            }
                            else if (type.equalsIgnoreCase("employee")) {
                                if (employeeOperations.login(username, password)) {
                                    writer.write(String.valueOf(true));
                                    writer.newLine();
                                    writer.flush();
//                                    response = "true";
                                }
                            }
                            else
                            {
                                writer.write(String.valueOf(false));
                                writer.newLine();
                                writer.flush();
                            }
                            break;
                        }
                        default:
                            response = "Invalid request";
                            writer.write(response);
                            writer.newLine();
                            writer.flush();
                            break;
                    }

                    writer.write(response);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                System.out.println("Error handling client request: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
