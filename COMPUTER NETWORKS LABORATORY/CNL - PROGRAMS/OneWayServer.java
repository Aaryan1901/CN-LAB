import java.io.*;
import java.net.*;

public class OneWayServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket ss = new ServerSocket(6667);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = dis.readUTF();
            String code = "";
            
            for(int i = 0; i < str.length(); i++)
            {
                switch(str.charAt(i))
                {
                    case 'a': code += "1 "; break;
                    case 'b': code += "2 "; break;
                    case 'c': code += "3 "; break;
                    case 'd': code += "4 "; break;
                    case 'e': code += "5 "; break;
                    case 'f': code += "6 "; break;
                    case 'g': code += "7 "; break;
                    case 'h': code += "8 "; break;
                    case 'i': code += "9 "; break;
                    case 'j': code += "10 "; break;
                    case 'k': code += "11 "; break;
                    case 'l': code += "12 "; break;
                    case 'm': code += "13 "; break;
                    case 'n': code += "14 "; break;
                    case 'o': code += "15 "; break;
                    case 'p': code += "16 "; break;
                    case 'q': code += "17 "; break;
                    case 'r': code += "18 "; break;
                    case 's': code += "19 "; break;
                    case 't': code += "20 "; break;
                    case 'u': code += "21 "; break;
                    case 'v': code += "22 "; break;
                    case 'w': code += "23 "; break;
                    case 'x': code += "24 "; break;
                    case 'y': code += "25 "; break;
                    case 'z': code += "26 "; break;
                    default: code += str.charAt(i) + " "; break;   
                }
            }
            
            System.out.println("Decoded Message : " + code);
            ss.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
