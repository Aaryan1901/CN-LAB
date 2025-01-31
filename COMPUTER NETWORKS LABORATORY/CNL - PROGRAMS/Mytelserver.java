import java.io.*;
import java.net.*;

public class Mytelserver {
    public static void main(String[] args) {
        String is;
        boolean done = false;

        try {
            System.out.println("Initializing Server..."); // Debug message
            // Creating a ServerSocket to listen on port 3333
            ServerSocket serv = new ServerSocket(3333);
            System.out.println("ServerSocket created, waiting for client connection..."); // Debug message

            Socket sock = serv.accept();
            System.out.println("Server started, client connected"); // Debug message

            // Setting up input streams
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);

            System.out.println("The program started");

            // Loop to read input from the client
            while (!done) {
                is = in.readLine();
                if (is != null) {
                    String[] parts = is.split(" ");
                    String character = parts[0];  // The character sent by the client
                    String asciiValue = parts[1]; // The ASCII value sent by the client

                    // Display the received character and its ASCII value
                    System.out.println("Received: " + character + " (ASCII: " + asciiValue + ")");
                }
            }

            // Closing the server socket
            serv.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
