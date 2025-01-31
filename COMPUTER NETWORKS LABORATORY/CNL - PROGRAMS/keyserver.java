import java.io.*;
import java.net.*;

public class keyserver {
    public static void main(String[] args) {
        try {
            // Create a server socket listening on port 3333
            ServerSocket serverSocket = new ServerSocket(3333);
            System.out.println("Server is waiting for a client connection on port 3333...");

            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Create input stream to read data from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Continuously read and display data sent by the client
            String receivedData;
            while ((receivedData = in.readLine()) != null) {
                System.out.println("Received from client: " + receivedData);
            }

            // Close resources
            in.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("Server shut down.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
