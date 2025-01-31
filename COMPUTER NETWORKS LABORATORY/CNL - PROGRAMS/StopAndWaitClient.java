import java.io.*;
import java.net.*;
import java.util.Random;

public class StopAndWaitClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        Random random = new Random();
        while (true) {
            int frame = in.readInt(); // Receive frame from server
            System.out.println("Received frame: " + frame);

            // Randomly fail acknowledgment
            if (random.nextInt(10) < 3) { // 30% chance to drop acknowledgment
                System.out.println("Simulating acknowledgment loss for frame: " + frame);
                continue;
            }

            out.writeInt(frame); // Send acknowledgment
            out.flush();
        }
    }
}
