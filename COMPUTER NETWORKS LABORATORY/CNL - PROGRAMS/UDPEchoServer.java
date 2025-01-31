import java.net.*;

public class UDPEchoServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(5000);  // Server listening on port 5000
        byte[] receiveBuffer = new byte[1024];

        System.out.println("UDP Echo Server is running...");

        while (true) {
            // Receive the packet
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + receivedMessage);

            // Echo the message back to the client
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getLength(), clientAddress, clientPort);
            socket.send(sendPacket);

            System.out.println("Echoed message to client at " + clientAddress + ":" + clientPort);
        }
    }
}
