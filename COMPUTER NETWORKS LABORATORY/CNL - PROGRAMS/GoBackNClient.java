import java.io.*;
import java.net.*;
import java.util.Random;

public class GoBackNClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        Random random = new Random();
        int expectedFrame = 0;

        while (true) {
            int frame = in.readInt();
            System.out.println("Received frame: " + frame);

            if (frame == expectedFrame) {
                if (random.nextInt(10) < 3) { // Simulate 30% acknowledgment loss
                    System.out.println("Simulating acknowledgment loss for frame: " + frame);
                    continue;
                }
                out.writeInt(expectedFrame);
                out.flush();
                expectedFrame++;
            } else {
                out.writeInt(expectedFrame - 1); // Send last acknowledged frame
                out.flush();
            }
        }
    }
}
