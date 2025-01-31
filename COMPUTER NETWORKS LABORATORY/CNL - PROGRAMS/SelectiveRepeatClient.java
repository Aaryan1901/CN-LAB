import java.io.*;
import java.net.*;
import java.util.Random;

public class SelectiveRepeatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        Random random = new Random();

        while (true) {
            int frame = in.readInt();
            System.out.println("Received frame: " + frame);

            // Randomly decide not to send acknowledgment to simulate acknowledgment loss
            if (random.nextInt(10) < 3) { // 30% chance to drop acknowledgment
                System.out.println("Simulating acknowledgment loss for frame: " + frame);
                continue; // Skip sending the acknowledgment
            }

            // Send acknowledgment for the received frame
            out.writeInt(frame);
            out.flush();
            System.out.println("Sent acknowledgment for frame: " + frame);
        }
    }
}
