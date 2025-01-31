import java.io.*;
import java.net.*;

public class StopAndWaitServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        socket.setSoTimeout(10000); // Set timeout to 10 seconds

        int frame = 0;
        while (frame < 10) { // Send 10 frames
            System.out.println("Sending frame: " + frame);
            out.writeInt(frame); // Send the frame to the client
            out.flush();

            try {
                int ack = in.readInt(); // Wait for acknowledgment
                if (ack == frame) {
                    System.out.println("Acknowledgment received for frame: " + frame);
                    frame++; // Move to the next frame if acknowledgment received
                } else {
                    System.out.println("Incorrect acknowledgment. Resending frame: " + frame);
                }
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout! No acknowledgment received. Resending frame: " + frame);
            }
        }
        socket.close();
        serverSocket.close();
    }
}
