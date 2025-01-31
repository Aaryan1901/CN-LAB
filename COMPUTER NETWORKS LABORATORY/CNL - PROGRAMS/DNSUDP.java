import java.net.*;
import java.io.*;

public class DNSUDP {
    private static final int DNS_PORT = 53;
    private static final String DNS_SERVER = "8.8.8.8"; 

    public static void main(String[] args) {
        System.out.println("This program performs DNS lookups using the UDP protocol.");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Standard DNS Lookup");
                System.out.println("2. Reverse DNS Lookup");
                System.out.println("3. Exit");
                System.out.print("Enter your choice (1-3): ");
                String choice = in.readLine();

                if (choice.equals("3")) {
                    System.out.println("Exiting...");
                    break;
                }

                String input;
                if (choice.equals("1")) {
                    System.out.print("Enter Host Name: ");
                    input = in.readLine();
                    String result = dnsLookup(input);
                    System.out.println("Result: " + result);
                } else if (choice.equals("2")) {
                    System.out.print("Enter IP Address: ");
                    input = in.readLine();
                    String result = reverseDnsLookup(input);
                    System.out.println("Result: " + result);
                } else {
                    System.out.println("Invalid choice. Please select again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static String dnsLookup(String hostname) {
        System.out.println("Creating UDP socket for DNS lookup...");
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);
            byte[] query = createDNSQuery(hostname);
            System.out.println("Sending DNS query to server: " + DNS_SERVER + ":" + DNS_PORT);
            DatagramPacket packet = new DatagramPacket(query, query.length, InetAddress.getByName(DNS_SERVER), DNS_PORT);
            socket.send(packet);

            System.out.println("Waiting for response...");
            byte[] buffer = new byte[512];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            System.out.println("Response received from server.");
            return extractIP(packet.getData());
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String reverseDnsLookup(String ipAddress) {
        String reversedIP = reverseIP(ipAddress);
        String hostname = reversedIP + ".in-addr.arpa";
        System.out.println("Creating UDP socket for reverse DNS lookup...");
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);
            byte[] query = createDNSQuery(hostname);
            System.out.println("Sending reverse DNS query to server: " + DNS_SERVER + ":" + DNS_PORT);
            DatagramPacket packet = new DatagramPacket(query, query.length, InetAddress.getByName(DNS_SERVER), DNS_PORT);
            socket.send(packet);

            System.out.println("Waiting for response...");
            byte[] buffer = new byte[512];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            System.out.println("Response received from server.");
            return extractHostname(packet.getData());
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    private static String reverseIP(String ip) {
        String[] parts = ip.split("\\.");
        return parts[3] + "." + parts[2] + "." + parts[1] + "." + parts[0];
    }

    private static byte[] createDNSQuery(String hostname) {
        ByteArrayOutputStream query = new ByteArrayOutputStream();
        try {
            query.write(new byte[]{0x12, 0x34}); // Transaction ID
            query.write(new byte[]{0x01, 0x00}); // Flags
            query.write(new byte[]{0x00, 0x01}); // Questions
            query.write(new byte[]{0x00, 0x00}); // Answer RRs
            query.write(new byte[]{0x00, 0x00}); // Authority RRs
            query.write(new byte[]{0x00, 0x00}); // Additional RRs

            for (String part : hostname.split("\\.")) {
                query.write(part.length());
                query.write(part.getBytes());
            }
            query.write(0x00); // Null byte
            query.write(new byte[]{0x00, 0x01}); // Query type A (for standard DNS lookup)
            query.write(new byte[]{0x00, 0x01}); // Query class IN
        } catch (IOException e) {
            e.printStackTrace();
        }
        return query.toByteArray();
    }

    private static String extractIP(byte[] response) {
        if (response.length < 12) return "No valid response";
        
        int answerCount = ((response[6] & 0xFF) << 8) | (response[7] & 0xFF);
        int index = 12;

        // Skip question section
        while (index < response.length && response[index] != 0) {
            index += response[index] + 1;
        }
        index += 5; // Skip null byte, QTYPE, QCLASS

        // Read answer section
        for (int i = 0; i < answerCount; i++) {
            if (index >= response.length) return "No answer found";

            // Handle possible compression pointers
            if ((response[index] & 0xC0) == 0xC0) {
                index += 2; // Skip pointer
            } else {
                index += response[index] + 1; // Skip the name
            }

            index += 4; // Skip QTYPE and QCLASS
            index += 4; // Skip TTL

            int dataLength = ((response[index] & 0xFF) << 8) | (response[index + 1] & 0xFF);
            index += 2;

            // Read the IP address
            StringBuilder ip = new StringBuilder();
            for (int j = 0; j < dataLength; j++) {
                ip.append((response[index + j] & 0xFF));
                if (j < dataLength - 1) ip.append(".");
            }
            return ip.toString();
        }
        return "No answer found";
    }

    private static String extractHostname(byte[] response) {
        if (response.length < 12) return "No valid response";
        
        int answerCount = ((response[6] & 0xFF) << 8) | (response[7] & 0xFF);
        int index = 12;

        // Skip question section
        while (index < response.length && response[index] != 0) {
            index += response[index] + 1;
        }
        index += 5; // Skip null byte, QTYPE, QCLASS

        // Read answer section
        for (int i = 0; i < answerCount; i++) {
            if (index >= response.length) return "No answer found";

            // Handle possible compression pointers
            if ((response[index] & 0xC0) == 0xC0) {
                index += 2; // Skip pointer
            } else {
                index += response[index] + 1; // Skip the name
            }

            index += 4; // Skip QTYPE and QCLASS
            index += 4; // Skip TTL

            // Get the length of the data
            int dataLength = ((response[index] & 0xFF) << 8) | (response[index + 1] & 0xFF);
            index += 2;

            // Read the hostname
            StringBuilder hostname = new StringBuilder();
            for (int j = 0; j < dataLength; j++) {
                if (j > 0) hostname.append(".");
                int len = response[index++];
                byte[] namePart = new byte[len];
                System.arraycopy(response, index, namePart, 0, len);
                hostname.append(new String(namePart));
                index += len;
            }
            return hostname.toString();
        }
        return "No answer found";
    }
}
