import java.io.*;
import java.net.*;

public class Receiver {
    public static String decrypt(String encryptedMessage, int key) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a'; // Determine if it's uppercase or lowercase
                char decryptedChar = (char) ((c - base - key + 26) % 26 + base);
                decryptedMessage.append(decryptedChar);
            } else {
                decryptedMessage.append(c); // Keep non-alphabetic characters as they are
            }
        }
        return decryptedMessage.toString();
    }
    

    public static int receiveKeyFile(String fileName) throws IOException {
        // Create a server socket to receive the file
        ServerSocket serverSocket = new ServerSocket(12346); // Use a separate port for the key file
        Socket socket = serverSocket.accept();

        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            // Receive the file size
            int fileSize = in.readInt();
            byte[] fileBytes = new byte[fileSize];

            // Read the file content
            in.readFully(fileBytes);

            // Write the file to disk
            fileOutputStream.write(fileBytes);
            System.out.println("Key file received successfully!");
        }

        socket.close();
        serverSocket.close();

        // Read and return the key from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            return Integer.parseInt(reader.readLine());
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Receiver is waiting for the connection...");

            Socket socket = serverSocket.accept();

            // Receive the encrypted message
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String encryptedMessage = in.readUTF();
            System.out.println("Received Encrypted Message: " + encryptedMessage);

            socket.close();
            serverSocket.close();

            // Receive the key file
            String keyFileName = "Key";
            int key = receiveKeyFile(keyFileName);

            // Decrypt the message
            System.out.println("Decrypted Message: " + decrypt(encryptedMessage, key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
