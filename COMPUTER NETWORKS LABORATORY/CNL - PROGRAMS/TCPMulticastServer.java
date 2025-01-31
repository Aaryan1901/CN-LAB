import java.io.*;
import java.net.*;
import java.util.*;

public class TCPMulticastServer {
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Multicast TCP Server started on port 5000...");

        // A new thread to continuously accept new clients
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                    clients.add(clientSocket);

                    // Handle client in a separate thread
                    new ClientHandler(clientSocket).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Multicast messages from the server to all clients
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while (true) {
            System.out.print("Enter message to multicast: ");
            message = consoleInput.readLine();

            multicastMessage(message);
        }
    }

    // Method to send a message to all connected clients
    private static void multicastMessage(String message) {
        for (Socket client : clients) {
            try {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println("Multicast Message: " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class to manage each client in a separate thread
class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String receivedMessage;
            while ((receivedMessage = in.readLine()) != null) {
                System.out.println("Received from client: " + receivedMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
