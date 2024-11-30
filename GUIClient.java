import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * GUIClient.java
 * Creates the GUI for the chat application
 *
 * @author Vishal Bhat, Sathvik Thumma, Rylee Holler, Brayden Hipp
 * @version 21 November 2024
 */

public class GUIClient extends JComponent implements Runnable {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getSource()) {
                case null -> {

                }
                default -> {

                }
            }
        }
    };

    public GUIClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Chat"); // To be changed

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUIClient());
    }

}
