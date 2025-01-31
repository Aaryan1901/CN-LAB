import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Moveclient extends JFrame {
    private JPanel drawPanel;
    private int x, y;
    private boolean isDrawing = false;
    private static PrintWriter out;

    public Moveclient() {
        super("Client - Drawing Panel");

        drawPanel = new JPanel();
        drawPanel.setBackground(Color.LIGHT_GRAY);
        add(drawPanel, BorderLayout.CENTER);

        // Capture mouse dragging for drawing
        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                isDrawing = true;
                sendCoordinates();
            }
        });

        // Stop drawing when mouse is released
        drawPanel.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
                sendCoordinates(); // Send an update to end the stroke
            }
        });

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8500);
            System.out.println("Connected to server.");

            out = new PrintWriter(socket.getOutputStream(), true);

            new Moveclient();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void sendCoordinates() {
        if (out != null) {
            out.println(x + "," + y + "," + isDrawing);
        }
    }
}
