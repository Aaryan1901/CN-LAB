import java.io.*;
import java.net.*;

public class FTPServer { // Class name should follow the Java naming conventions (capitalize the first letter)
    public static void main(String[] args) {
        int port = 12345; // Port where the server will listen for client connections

        // Try to create a ServerSocket to listen on the given port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            // Infinite loop to continuously accept client connections
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Accept the incoming client connection
                    System.out.println("Connection from " + clientSocket.getInetAddress());
                    new ClientHandler(clientSocket).start(); // Start a new thread for each client
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle errors related to server socket creation
        }
    }
}

// Thread class to handle client requests
class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        // Initialize the BufferedReader and PrintWriter for communication with the client
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Receive the file name from the client
            String fileName = in.readLine();
            File file = new File(fileName);

            // Check if the file exists and is a regular file
            if (file.exists() && file.isFile()) {
                long fileSize = file.length(); // Get the size of the file
                String fileFormat = getFileExtension(fileName); // Get the file extension

                // Send the file information to the client
                out.println("File Name: " + fileName);
                out.println("File Size: " + fileSize + " bytes");
                out.println("File Format: " + fileFormat);
                out.println("File Content:");

                // Send the file content line by line to the client
                try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        out.println(line); // Send each line of the file to the client
                    }
                }
            } else {
                out.println("File not found."); // If the file doesn't exist, notify the client
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle errors related to I/O
        } finally {
            // Close the client socket at the end of communication
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to get the file extension
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return lastIndexOfDot == -1 ? "" : fileName.substring(lastIndexOfDot + 1);
    }
}
