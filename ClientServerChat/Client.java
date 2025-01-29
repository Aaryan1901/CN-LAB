import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost",1234))  {
            
            System.out.println("client connected");

            //input and output stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer= new PrintWriter(socket.getOutputStream(),true);


            //Console
            String message;
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            //read from client
            new Thread(() -> {
                try{
                    String Servermessage ;
                    while ((Servermessage = reader.readLine()) != null)
                    {
                        System.out.println("Server"+Servermessage);
                    }

                }
                catch(IOException e){
                    e.printStackTrace();
                }
                }).start();
                
                
            while (true) {
                message = consoleReader.readLine();
                writer.println(message);
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Client disconnected");
                    break;
                }
            }


              socket.close();
        }
               catch(IOException e){
                e.printStackTrace();

            }
            
                }
            }