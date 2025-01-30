import java.io.*;
import java.net.*;

public class Sender {
    public static String encrypt(String message, int key) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a'; // Determine if it's uppercase or lowercase
                char encryptedChar = (char) ((c - base + key) % 26 + base);
                encryptedMessage.append(encryptedChar);
            } else {
                encryptedMessage.append(c); // Keep non-alphabetic characters as they are
            }
        }
        return encryptedMessage.toString();
    }
    

    public static void sendKeyFile(String fileName) throws IOException {
        // Create a socket to send the file
        Socket socket = new Socket("localhost", 12346); // Use a separate port for the key file
        File file = new File(fileName);

        try (FileInputStream fileInputStream = new FileInputStream(file);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes);

            // Send the file size and content
            out.writeInt(fileBytes.length);
            out.write(fileBytes);
            out.flush();
        }

        socket.close();
    }

    public static void main(String[] args) {
        try {
            String message = "INFORMATION TECHNOLOGY";
            int key = 4; // Key size is 4
            String encryptedMessage = encrypt(message, key);

            System.out.println("Encrypted Message: " + encryptedMessage);

            // Write the key to a file
            String keyFileName = "Key";
            try (FileWriter writer = new FileWriter(keyFileName)) {
                writer.write(String.valueOf(key));
            }

            // Establish TCP connection with the receiver for the message
            Socket socket = new Socket("localhost", 12345); // Port for the message
            try (DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                // Send the encrypted message
                out.writeUTF(encryptedMessage);
                out.flush();
            }

            socket.close();

            // Send the key file
            sendKeyFile(keyFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
