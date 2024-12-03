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
    private final String TITLE = "Chat";
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private JTextField textField;
    private JButton enterBtn;
    private JTextField searchField;
    private JButton msgToggle;

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == textField) {

            } else if (e.getSource() == enterBtn) {

            } else if (e.getSource() == searchField) {

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
        int reply = JOptionPane.NO_OPTION;
        do {
            String username = JOptionPane.showInputDialog(null, "Please enter your username", TITLE,
                    JOptionPane.QUESTION_MESSAGE);
            String password = JOptionPane.showInputDialog(null, "Please enter your password", TITLE,
                    JOptionPane.INFORMATION_MESSAGE);

            out.write(username + ":" + password);
            out.println();
            out.flush();

            String response = "";
            try {
                response = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!response.equals("LOGIN ERROR")) {
                createGUI();
            } else {
                JOptionPane.showMessageDialog(null, "Login failed", TITLE, JOptionPane.ERROR_MESSAGE);
                reply = JOptionPane.showConfirmDialog(null, "Do you want to try again?", TITLE,
                        JOptionPane.YES_NO_OPTION);
            }
        } while (reply == JOptionPane.YES_OPTION);
    }

    private void createGUI() {
        JFrame frame = new JFrame(TITLE);

        textField = new JTextField();
        textField.addActionListener(actionListener);
        enterBtn = new JButton();
        enterBtn.addActionListener(actionListener);
        searchField = new JTextField();
        searchField.addActionListener(actionListener);
        msgToggle = new JButton();
        msgToggle.addActionListener(actionListener);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new GUIClient());
    }

}
