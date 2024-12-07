import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * GUIClient.java
 * Creates the GUI for the chat application
 *
 * @author Vishal Bhat, Sathvik Thumma, Rylee Holler, Brayden Hipp
 * @version 6 November 2024
 */

public class GUIClient extends JComponent implements Runnable, GUIClientInterface {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;
    private static final String TITLE = "Chat";
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private String userOne;
    private String userTwo;

    private JScrollPane centerPane;
    private JTextField textField;
    private JButton enterBtn;
    private JTextField searchField;
    private JButton searchBtn;
    private JButton allUsers;
    private JButton friendsOnly;
    private JButton userOptions;

    private ArrayList<JLabel> messages;

    public GUIClient() {
        try {
            while (true) {
                socket = new Socket(SERVER_ADDRESS, PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
            }
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

    // Added an ActionListener to handle button clicks and communication
    ActionListener actionListener = new ActionListener() {
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
                SwingUtilities.invokeLater(() -> {
                    JScrollBar verticalBar = centerPane.getVerticalScrollBar();
                    verticalBar.setValue(verticalBar.getMaximum());
                });
            } else if (e.getSource() == searchBtn) {
                //Send search query in the format ID:2:userOne:NONE:SearchQuery
                String searchQuery = searchField.getText();
                if (!searchQuery.isEmpty()) {
                    out.write("2:" + userOne + ":NONE:" + searchQuery);
                    out.println();
                    out.flush();
                }
            } else if (e.getSource() == allUsers) {
                out.write("4:" + userOne + ":NONE" + "-");
                out.println();
                out.flush();

                try {
                    String userList = in.readLine();
                    String[] users = userList.split(",");
                    userTwo = (String) JOptionPane.showInputDialog(null,
                            "Select a user to chat with:",
                            TITLE,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            users,
                            users[0]);

                    if (userTwo != null) {
                        updateGUI(userOne, userTwo);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getSource() == friendsOnly) {
                out.write("5:" + userOne + ":NONE:-");
                out.println();
                out.flush();
                try {
                    String userList = in.readLine();
                    String[] users = userList.split(",");
                    if (users.length == 0) {
                        JOptionPane.showMessageDialog(null, "You have no friends!", TITLE,
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    userTwo = (String) JOptionPane.showInputDialog(null,
                            "Select a user to chat with:",
                            TITLE,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            users,
                            users[0]);

                    if (userTwo != null) {
                        updateGUI(userOne, userTwo);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getSource() == userOptions) {
                out.write("4:" + userOne + ":NONE:-");
                out.println();
                out.flush();
                try {
                    String userList = in.readLine();
                    String[] users = userList.split(",");
                    String[] options = {"Friend", "Unfriend", "Block", "Cancel"};
                    String chosenUser = (String) JOptionPane.showInputDialog(null,
                            "Select a user to chat with:",
                            TITLE,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            users,
                            users[0]);
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            "Choose your operation: " + chosenUser,
                            TITLE,
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );

                    switch (choice) {
                        case 0 -> {
                            out.write("8:" + userOne + ":" + chosenUser + ":-");
                            out.println();
                            out.flush();
                            try {
                                String response = in.readLine();
                                if (!response.equals("ERROR")) {
                                    JOptionPane.showMessageDialog(null, chosenUser + " added!",
                                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Friend already added!",
                                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        case 1 -> {
                            out.write("9:" + userOne + ":" + chosenUser + ":-");
                            out.println();
                            out.flush();
                            try {
                                String response = in.readLine();
                                if (!response.equals("ERROR")) {
                                    JOptionPane.showMessageDialog(null, chosenUser + " removed!",
                                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, chosenUser +
                                            " is not in your friends list!", TITLE, JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        case 2 -> {
                            out.write("10:" + userOne + ":" + chosenUser + ":-");
                            out.println();
                            out.flush();
                            try {
                                String response = in.readLine();
                                if (!response.equals("ERROR")) {
                                    JOptionPane.showMessageDialog(null, chosenUser + " blocked!",
                                            TITLE, JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, chosenUser +
                                            " is already blocked!", TITLE, JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    @Override
    public void run() {
        int reply;
        do {
            reply = JOptionPane.NO_OPTION;
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
                out.write("4:" + username + ":NONE:-");
                out.println();
                out.flush();
                this.userOne = username;

                try {
                    // Receive the list of users from the server and display in a JOptionPane
                    String userList = in.readLine();
                    String[] users = userList.split(",");
                    this.userTwo = (String) JOptionPane.showInputDialog(null,
                            "Select a user to chat with:",
                            TITLE,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            users,
                            users[0]);

                    if (userTwo != null) {
                        createGUI(userOne, userTwo);
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
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        messages = new ArrayList<>();
        String loadedMessages = "";
        out.write("11:" + userOne + ":" + userTwo + ":-");
        out.println();
        out.flush();

        try {
            loadedMessages = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] messagesArr = loadedMessages.split(" {5}");

        for (int i = 0; i < messagesArr.length; i++) {
            messages.set(i, new JLabel(messagesArr[i]));
        }

        textField = new JTextField();
        textField.addActionListener(actionListener);

        //Set up the button to send messages
        enterBtn = new JButton("Send");
        enterBtn.addActionListener(actionListener);

        searchField = new JTextField();
        searchField.addActionListener(actionListener);

        // Set up the button for search functionality
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(actionListener);

        allUsers = new JButton("All Users");
        allUsers.addActionListener(actionListener);

        friendsOnly = new JButton("Friends");
        friendsOnly.addActionListener(actionListener);

        userOptions = new JButton("User Options");
        userOptions.addActionListener(actionListener);

        JPanel topPanel = new JPanel();
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(allUsers);
        topPanel.add(friendsOnly);

        content.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(textField);
        bottomPanel.add(enterBtn);

        content.add(bottomPanel, BorderLayout.SOUTH);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        centerPane = new JScrollPane(messagePanel);
        centerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        content.add(centerPane, BorderLayout.CENTER);

        for (int i = 0; i < messages.size(); i++) {
            JLabel curr = messages.get(i);
            messagePanel.add(curr);

            addRightClickMenu(curr, messagePanel, messages, userOne, out, in);

        }

        //GUI frame remains minimal since chat selection is handled through JOptionPane
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void updateGUI(String userOne, String userTwo) {
        out.write("11:" + userOne + ":" + userTwo + ":-");
        out.println();
        out.flush();
        String loadedMessages = "";

        try {
            loadedMessages = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] messagesArr = loadedMessages.split(" {5}");

        for (int i = 0; i < messagesArr.length; i++) {
            JLabel curr = messages.get(i);
            curr.setText(messagesArr[i]);
            messages.set(i, curr);
            messages.get(i).repaint();
        }
    }

    private static void addRightClickMenu(JLabel label, JPanel parentPanel, ArrayList<JLabel> messages, String user,
                                          PrintWriter out, BufferedReader in) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        popupMenu.add(deleteItem);

        deleteItem.addActionListener(e -> {

            out.write("3:" + user + ":NONE:" + label.getText());
            out.println();
            out.flush();
            try {
                String response = in.readLine();
                if (!response.equals("ERROR")) {
                    JOptionPane.showMessageDialog(null, "Message deleted!", TITLE,
                            JOptionPane.INFORMATION_MESSAGE);
                    parentPanel.remove(label);
                    messages.remove(label);
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong", TITLE,
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            parentPanel.revalidate();
            parentPanel.repaint();
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                popupMenu.show(label, e.getX(), e.getY());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIClient::new);
    }
}