import java.io.*;
import java.net.*;

public class Iterative_Server {
    public static void main(String[] args) {
        int port = 1234; // Define the port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            int clientCount = 0;

            while (clientCount < 3) { // Limit to 3 clients
                Socket clientSocket = serverSocket.accept(); // Accept client connection
                clientCount++;
                System.out.println("Client " + clientCount + " connected");

                // Create input and output streams for communication
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientMessage;

                // Read messages from the client until "bye" is received
                while ((clientMessage = input.readLine()) != null) {
                    System.out.println("Received from client " + clientCount + ": " + clientMessage);
                    output.println("Echo: " + clientMessage); // Echo the message back to the client

                    if ("bye".equalsIgnoreCase(clientMessage)) {
                        System.out.println("Client " + clientCount + " disconnected");
                        break;
                    }
                }

                clientSocket.close(); // Close the connection
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
