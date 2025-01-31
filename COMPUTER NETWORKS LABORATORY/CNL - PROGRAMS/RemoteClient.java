import java.io.*;
import java.net.*;

class RemoteClient {
    public static void main(String args[]) {
        try {
            int Port;
            BufferedReader Buf = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter the Port Address: ");
            Port = Integer.parseInt(Buf.readLine());

            Socket s = new Socket("localhost", Port);

            if (s.isConnected())
                System.out.println("Server Socket is Connected Successfully.");

            InputStream in = s.getInputStream();
            OutputStream ou = s.getOutputStream();

            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader buf1 = new BufferedReader(new InputStreamReader(in));
            PrintWriter pr = new PrintWriter(ou, true);

            while (true) {
                System.out.print("Enter the Command to be Executed (or type 'exit' to quit): ");
                String command = buf.readLine();

                pr.println(command); // Send command to server
                if (command.equalsIgnoreCase("exit")) break;

                // Read response from server
                String response = buf1.readLine();
                System.out.println("Server Response: " + response); // Display response

                // Allow user to enter next command
                if (response.equals("Command executed successfully with no output.")) {
                    continue; // Skip further waiting if there's no output
                }
            }

            pr.close();
            ou.close();
            in.close();
            s.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
