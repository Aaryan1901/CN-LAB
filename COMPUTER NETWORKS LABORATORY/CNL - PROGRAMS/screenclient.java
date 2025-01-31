import java.net.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Rectangle;

public class screenclient {
    public static void main(String[] args) throws Exception {
        Socket soc;
        BufferedImage img = null;

        // Connecting to the server at localhost, port 4000
        soc = new Socket("localhost", 4000);
        System.out.println("Client is running...");

        try {
            // Capturing the screen image
            System.out.println("Capturing the screen...");
            Robot robot = new Robot();
            String format = "jpg";
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            // Saving the captured image to a file
            ImageIO.write(screenFullImage, format, new File("screen.jpg"));
            System.out.println("Screen capture completed.");

            // Reading the image file to be sent
            img = ImageIO.read(new File("screen.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();

            // Sending the image to the server
            System.out.println("Sending image to server...");
            OutputStream out = soc.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);

            // Sending the length of the image data
            dos.writeInt(bytes.length);
            // Sending the image data
            dos.write(bytes, 0, bytes.length);

            System.out.println("Image sent to server.");
            dos.close();
            out.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            soc.close();
        }
        
        // Closing the client socket
        soc.close();
    }
}
