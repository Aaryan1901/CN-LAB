import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Moveserver extends JFrame {
    private JPanel drawPanel;
    private List<Point> points = new ArrayList<>();
    private boolean isDrawing = false;

    public Moveserver() {
        super("Server - Drawing Panel");

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);

                // Draw all the points received from the client
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    if (p1 != null && p2 != null) {
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        };

        drawPanel.setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(drawPanel, BorderLayout.CENTER);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8500);
            System.out.println("Server is listening on port 8500...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Moveserver server = new Moveserver();

            String input;
            while ((input = in.readLine()) != null) {
                String[] data = input.split(",");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                boolean isDrawing = Boolean.parseBoolean(data[2]);

                server.updateDrawing(x, y, isDrawing);
            }

            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateDrawing(int x, int y, boolean isDrawing) {
        if (isDrawing) {
            points.add(new Point(x, y));
        } else {
            points.add(null); // Add null to indicate the end of a stroke
        }
        drawPanel.repaint();
    }
}
