import java.net.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class screenserver {
    public static void main(String[] args) throws Exception {
        ServerSocket server = null;
        Socket socket;

        // Setting up the server to listen on port 4000
        server = new ServerSocket(4000);
        System.out.println("Server waiting for image...");

        // Accepting a client connection
        socket = server.accept();
        System.out.println("Client connected...");

        // Setting up the input stream to receive the image
        InputStream in = socket.getInputStream();
        DataInputStream dis = new DataInputStream(in);

        // Reading the length of the incoming image data
        int len = dis.readInt();
        byte[] data = new byte[len];
        dis.readFully(data);

        // Closing the input streams
        dis.close();
        in.close();

        // Converting the byte array to an image
        InputStream ian = new ByteArrayInputStream(data);
        BufferedImage bImage = ImageIO.read(ian);

        // Saving the received image to disk
        ImageIO.write(bImage, "jpg", new File("screencaptured.jpg"));
        System.out.println("Image saved as 'screencaptured.jpg'");
    }
}
