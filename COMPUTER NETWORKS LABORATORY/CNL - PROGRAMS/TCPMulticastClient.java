import java.io.*;
import java.net.*;

public class TCPMulticastClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000); // Connect to the server on port 5000
        System.out.println("Connected to the multicast TCP server.");

        // To receive multicast messages from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // To send messages from this client to the server
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Thread to read messages from server (multicast)
        new Thread(() -> {
            try {
                String messageFromServer;
                while ((messageFromServer = in.readLine()) != null) {
                    System.out.println("Message from server: " + messageFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Main thread to send messages from client to the server
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String messageToSend;
        while ((messageToSend = consoleInput.readLine()) != null) {
            out.println("Client message: " + messageToSend);
        }

        // Closing resources when done
        socket.close();
    }
}
