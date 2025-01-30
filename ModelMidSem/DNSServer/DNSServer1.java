import java.io.IOException;
import java.net.*;

public class DNSServer {
    private static final int SERVER_PORT = 1053;

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT)) {
            System.out.println("DNS Server is running on port " + SERVER_PORT);

            while (true) {
                byte[] buffer = new byte[512];
                DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(requestPacket);

                String domainName = new String(requestPacket.getData(), 0, requestPacket.getLength());
                System.out.println("Received request for: " + domainName);

                String ipAddress;
                try {
                    InetAddress address = InetAddress.getByName(domainName);
                    ipAddress = address.getHostAddress();
                } catch (UnknownHostException e) {
                    ipAddress = "Domain not found";
                }

                byte[] responseBuffer = ipAddress.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length,
                        requestPacket.getAddress(), requestPacket.getPort());
                serverSocket.send(responsePacket);
                System.out.println("Sent response: " + ipAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
