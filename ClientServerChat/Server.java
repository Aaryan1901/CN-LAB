import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket serversocket = new ServerSocket(1234))  {
            System.out.println("server is waiting");
            Socket socket = serversocket.accept();
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
                    String clientmessage ;
                    while ((clientmessage = reader.readLine()) != null)
                    {
                        System.out.println("Client"+clientmessage);
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
              
            

        
    
    

