import java.io.*;
import java.net.*;

class RemoteServer {
    public static void main(String args[]) {
        try {
            int Port;
            BufferedReader Buf = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter the Port Address: ");
            Port = Integer.parseInt(Buf.readLine());

            ServerSocket ss = new ServerSocket(Port);
            System.out.println("Server is Ready To Receive Commands.");
            System.out.println("Waiting for Client...");

            Socket s = ss.accept();

            if (s.isConnected())
                System.out.println("Client Socket is Connected Successfully.");

            InputStream in = s.getInputStream();
            OutputStream ou = s.getOutputStream();
            BufferedReader buf = new BufferedReader(new InputStreamReader(in));
            PrintWriter pr = new PrintWriter(ou, true);

            while (true) {
                String cmd = buf.readLine(); // Receive command from client
                if (cmd.equalsIgnoreCase("exit")) {
                    pr.println("Server Shutting Down...");
                    break;
                }

                try {
                    ProcessBuilder processBuilder;

                    if (cmd.equalsIgnoreCase("explorer")) {
                        // Open File Explorer
                        processBuilder = new ProcessBuilder("explorer.exe");
                    } else if (cmd.equalsIgnoreCase("settings")) {
                        // Open Settings application in Windows
                        processBuilder = new ProcessBuilder("explorer", "ms-settings:");
                    } else if (cmd.equalsIgnoreCase("cmd")) {
                        // Open Command Prompt
                        processBuilder = new ProcessBuilder("cmd.exe", "/c", "start cmd.exe");
                    } else {
                        // For other commands, execute them through cmd.exe
                        processBuilder = new ProcessBuilder("cmd.exe", "/c", cmd);
                    }

                    processBuilder.redirectErrorStream(true); // Merge error and output streams
                    Process process = processBuilder.start();

                    // Capture command output
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    StringBuilder output = new StringBuilder();
                    String line;

                    // Read standard output
                    while ((line = stdInput.readLine()) != null) {
                        output.append(line).append("\n");
                    }

                    if (output.length() > 0) {
                        pr.println(output.toString()); // Send output back to client
                        System.out.println("Command Output:\n" + output); // Print on server console
                    } else {
                        pr.println("Command executed successfully with no output.");
                        System.out.println("Command executed successfully with no output.");
                    }
                } catch (IOException e) {
                    pr.println("Error Executing Command: " + e.getMessage());
                    System.out.println("Error Executing Command: " + e.getMessage());
                }
            }

            pr.close();
            ou.close();
            in.close();
            s.close();
            ss.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
