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
                //After successful login, request the list of users from the server
                out.write("LIST_USERS");
                out.println();
                out.flush();

                try {
                    // Receive the list of users from the server and display in a JOptionPane
                    String userList = in.readLine();
                    String[] users = userList.split(",");
                    String userTwo = (String) JOptionPane.showInputDialog(null,
                            "Select a user to chat with:",
                            TITLE,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            users,
                            users[0]);

                    if (userTwo != null) {
                        createGUI(username, userTwo);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                JOptionPane.showMessageDialog(null, "Login failed", TITLE, JOptionPane.ERROR_MESSAGE);
                reply = JOptionPane.showConfirmDialog(null, "Do you want to try again?", TITLE,
                        JOptionPane.YES_NO_OPTION);
            }
        } while (reply == JOptionPane.YES_OPTION);
    }

    private void createGUI(String userOne, String userTwo) {
        JFrame frame = new JFrame(TITLE);


        textField = new JTextField();
        textField.addActionListener(actionListener);

        //Set up the button to send messages
        enterBtn = new JButton("Send");
        enterBtn.addActionListener(actionListener);


        searchField = new JTextField();
        searchField.addActionListener(actionListener);

        // Set up the button for search functionality
        msgToggle = new JButton("Search");
        msgToggle.addActionListener(actionListener);

        // Added an ActionListener to handle button clicks and communication
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == enterBtn) {
                    //Send message in the format ID:1:userOne:userTwo:Message
                    String message = textField.getText();
                    if (!message.isEmpty()) {
                        out.write("1:" + userOne + ":" + userTwo + ":" + message);
                        out.println();
                        out.flush();
                    }
                } else if (e.getSource() == msgToggle) {
                    //Send search query in the format ID:2:userOne:NONE:SearchQuery
                    String searchQuery = searchField.getText();
                    if (!searchQuery.isEmpty()) {
                        out.write("2:" + userOne + ":NONE:" + searchQuery);
                        out.println();
                        out.flush();
                    }
                }
            }
        };

        //GUI frame remains minimal since chat selection is handled through JOptionPane
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}