import java.net.*;
import java.io.*;

public class UDPEchoClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();  // Creating socket for sending and receiving
        InetAddress serverAddress = InetAddress.getByName("localhost");  // Server's address (localhost in this case)
        int serverPort = 5000;  // Server's port

        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        byte[] sendBuffer = new byte[1024];
        byte[] receiveBuffer = new byte[1024];

        System.out.println("UDP Client started. Type messages to send to the server:");

        while (true) {
            // Read message from console input
            System.out.print("Enter message: ");
            String messageToSend = consoleInput.readLine();
            sendBuffer = messageToSend.getBytes();

            // Send the message to the server
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Receive the echoed message from the server
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String echoedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Echoed from server: " + echoedMessage);
        }
    }
}
