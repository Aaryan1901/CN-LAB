import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTPClient { // Class name should follow the Java naming conventions
    public static void main(String[] args) {
        String host = "localhost"; // Host where the server is running
        int port = 12345; // The port number the server is listening on

        // Try to establish a connection to the server
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // To send data to the server
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // To receive data from the server
             Scanner scanner = new Scanner(System.in)) { // For reading user input

            // Prompt the user for the file name
            System.out.print("Enter the file name to request: ");
            String fileName = scanner.nextLine(); // Read the file name from the user
            out.println(fileName); // Send the file name to the server

            // Receive and print the server's response
            String responseLine;
            while ((responseLine = in.readLine()) != null) { // Read the server's response line by line
                System.out.println(responseLine); // Print each line received from the server
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions
        }
    }
}
