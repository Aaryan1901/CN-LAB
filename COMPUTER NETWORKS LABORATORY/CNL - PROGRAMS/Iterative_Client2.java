import java.io.*;
import java.net.*;

public class Iterative_Client2 {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server hostname
        int port = 1234; // Server port

        try (Socket socket = new Socket(hostname, port)) {
            // Create input and output streams for communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String userMessage;
            System.out.println("Connected to the server. Type your message:");

            while (true) {
                System.out.print("Enter message: ");
                userMessage = consoleInput.readLine();
                output.println(userMessage); // Send the message to the server

                // If "bye" is sent, disconnect the client
                if ("bye".equalsIgnoreCase(userMessage)) {
                    break;
                }

                // Read and display the echoed message from the server
                String serverMessage = input.readLine();
                System.out.println(serverMessage);
            }

            socket.close(); // Close the connection
        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}
