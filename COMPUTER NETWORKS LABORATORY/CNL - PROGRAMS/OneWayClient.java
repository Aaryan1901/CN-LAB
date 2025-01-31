import java.io.*;
import java.net.*;

public class OneWayClient
{
        public static void main(String[] args)
        {
                try
                {
                        
                        Socket s = new Socket("localhost", 6667);
                        DataInputStream din = new DataInputStream(s.getInputStream());
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        String str = " ";
                        str = br.readLine();
                        dout.writeUTF(str);
                        dout.flush();
                        dout.close();
                        s.close();
                }
                catch(Exception e)
                {
                        System.out.println(e);
                }
        }
}