import java.util.Scanner;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSResolver {
    public static void main(String[] args) {
        // Obtain domain name from user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter domain name: ");
        String domain = scanner.nextLine();
        scanner.close();
       
        // DNS resolution code will go here
        try {
            // Perform DNS lookup
            InetAddress address = InetAddress.getByName(domain);
            System.out.println("IP Address for " + domain + " is: " + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Unable to resolve host: " + e.getMessage());
        }
    }
}
