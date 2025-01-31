import java.io.*;
import java.rmi.*;

public class rmiclient {
    public static void main(String args[]) throws Exception {
        try {
            // Lookup the remote object
            serverint server = (serverint) Naming.lookup("rmi://localhost:5000/abc");

            // Read input from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the number:");
            int number = Integer.parseInt(reader.readLine());

            // Call the remote method
            System.out.println("The factorial of " + number + " is " + server.fact(number));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
