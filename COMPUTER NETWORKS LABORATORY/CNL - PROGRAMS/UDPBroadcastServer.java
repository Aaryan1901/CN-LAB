import java.net.*;
import java.io.*;

public class UDPBroadcastServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();  // DatagramSocket for broadcasting
        socket.setBroadcast(true);  // Enable broadcasting

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        String messageToSend;
        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");  // Broadcast address for all devices in the network
        int port = 5000;  // The port on which to broadcast

        System.out.println("UDP Broadcast Server started. Type messages to broadcast:");

        while (true) {
            System.out.print("Enter message to broadcast: ");
            messageToSend = consoleInput.readLine();
            byte[] buffer = messageToSend.getBytes();

            // Create the DatagramPacket for broadcasting
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, port);

            // Broadcast the packet
            socket.send(packet);
            System.out.println("Broadcasted message: " + messageToSend);
        }
    }
}
