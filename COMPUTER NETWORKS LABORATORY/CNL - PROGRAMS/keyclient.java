import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class keyclient extends Frame {
    Label label;
    static Socket s;
    static PrintWriter out;
    TextField txtField;

    public static void main(String[] args) {
        try {
            // Connecting to the server on localhost, port 3333
            s = new Socket("127.0.0.1", 3333);
            System.out.println("Connection Established");

            // Setting up the output stream
            out = new PrintWriter(s.getOutputStream(), true);
            keyclient k = new keyclient();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public keyclient() {
        super("Key Press Event Frame");

        // Setting up GUI components
        Panel panel = new Panel();
        label = new Label();
        txtField = new TextField(20);
        txtField.addKeyListener(new MyKeyListener());

        // Adding components to the Frame
        add(label, BorderLayout.NORTH);
        panel.add(txtField, BorderLayout.CENTER);
        add(panel, BorderLayout.CENTER);

        // Handling window close event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Setting up the frame size and visibility
        setSize(400, 400);
        setVisible(true);
    }

    // Key listener for capturing key presses
    public class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent ke) {
            char i = ke.getKeyChar();
            String str = Character.toString(i);

            // Check if it's a lowercase letter, convert to uppercase, or vice versa
            if (Character.isLowerCase(i)) {
                i = Character.toUpperCase(i);  // Convert to uppercase
            } else if (Character.isUpperCase(i)) {
                i = Character.toLowerCase(i);  // Convert to lowercase
            }

            // Send the converted character and its ASCII value to the server
            out.println(i + " " + (int) i);

            // Display the converted character
            label.setText(i + " (" + (int) i + ")");
        }
    }
}
