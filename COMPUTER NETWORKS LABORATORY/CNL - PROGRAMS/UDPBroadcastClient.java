import java.net.*;
import java.io.*;

public class UDPBroadcastClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(5000);  // Bind to the broadcast port
        socket.setBroadcast(true);  // Enable broadcast receiving

        byte[] receiveBuffer = new byte[1024];
        System.out.println("Listening for broadcast messages...");

        while (true) {
            // Prepare to receive packet
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);  // Receive broadcast packet

            // Extract and display the message
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Broadcast message received: " + receivedMessage);
        }
    }
}
