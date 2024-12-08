import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

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
    private boolean running = true;

    private String userOne;
    private String userTwo;

    private JScrollPane centerPane;
    private JPanel messagePanel;
    private JTextField textField;
    private JButton enterBtn;
    private JTextField searchField;
    private JButton searchBtn;
    private JButton allUsers;
    private JButton friendsOnly;
    private JButton userOptions;

    private ArrayList<JLabel> messages;
    private String[] messagesArr;

    public GUIClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
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
                textField.setText("");
                SwingUtilities.invokeLater(() -> {
                    JScrollBar verticalBar = centerPane.getVerticalScrollBar();
                    verticalBar.setValue(verticalBar.getMaximum());
                });

                updateGUI(userOne, userTwo);
            } else if (e.getSource() == searchBtn) {
                //Send search query in the format ID:2:userOne:NONE:SearchQuery
                String searchQuery = searchField.getText();
                if (!searchQuery.isEmpty()) {
                    out.write("2:" + userOne + ":NONE:" + searchQuery);
                    out.println();
                    out.flush();

                    try {
                        String response = in.readLine();
                        if (!response.isEmpty()) {
                            String[] searchResults = response.split(" {5}");

                            // Format the search results for display in JOptionPane
                            StringBuilder formattedResults = new StringBuilder("Search Results:\n\n");
                            for (String msg : searchResults) {
                                formattedResults.append("- ").append(msg.trim()).append("\n");
                            }

                            JOptionPane.showMessageDialog(null, formattedResults.toString(), TITLE,
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No search results found.", TITLE, JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (e.getSource() == allUsers) {
                System.out.println("4:" + userOne + ":NONE:" + "-");
                out.write("4:" + userOne + ":NONE:" + "-");
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
                        SwingUtilities.invokeLater(() -> updateGUI(userOne, userTwo));
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
                    if (userList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "You have no friends!", TITLE,
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String[] users = userList.split(",");
                    userTwo = (String) JOptionPane.showInputDialog(null,
                            "Select a user to chat with:",
                            TITLE,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            users,
                            users[0]);

                    if (userTwo != null) {
                        SwingUtilities.invokeLater(() -> updateGUI(userOne, userTwo));
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
                    if (chosenUser != null) {
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
            String username = "";
            String password;
            int newUser = JOptionPane.showConfirmDialog(null, "Do you have an account?", TITLE,
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (newUser == JOptionPane.YES_OPTION) {
                username = JOptionPane.showInputDialog(null, "Please enter your username", TITLE,
                        JOptionPane.QUESTION_MESSAGE);
                password = JOptionPane.showInputDialog(null, "Please enter your password", TITLE,
                        JOptionPane.INFORMATION_MESSAGE);
                out.write("12:" + username + ":" + password + ":-");
                out.println();
                out.flush();
            } else if (newUser == JOptionPane.NO_OPTION) {
                username = JOptionPane.showInputDialog(null, "Please enter your username", TITLE,
                        JOptionPane.QUESTION_MESSAGE);
                password = JOptionPane.showInputDialog(null, "Please enter your password", TITLE,
                        JOptionPane.QUESTION_MESSAGE);
                out.write("6:" + username + ":" + password + ":-");
                out.println();
                out.flush();

                try {
                    String creationResponse = in.readLine();
                    if (creationResponse.equals("User created")) {
                        JOptionPane.showMessageDialog(null, "User created!", TITLE,
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, creationResponse, TITLE,
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                out.write("12:" + username + ":" + password + ":-");
                out.println();
                out.flush();
            }

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
                        loadMessages();
                        SwingUtilities.invokeLater(() -> {
                            createGUI(userOne, userTwo);
                        });
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
        if (messagesArr.length != 0) {
            for (int i = 0; i < messagesArr.length; i++) {
                messages.add(new JLabel(messagesArr[i]));
            }
        }

        textField = new JTextField(30);
        textField.addActionListener(actionListener);

        //Set up the button to send messages
        enterBtn = new JButton("Send");
        enterBtn.addActionListener(actionListener);

        searchField = new JTextField(20);
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
        topPanel.add(userOptions);

        content.add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(textField);
        bottomPanel.add(enterBtn);

        content.add(bottomPanel, BorderLayout.SOUTH);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        centerPane = new JScrollPane(messagePanel);
        centerPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        content.add(centerPane, BorderLayout.CENTER);

        for (int i = 0; i < messages.size(); i++) {
            JLabel curr = messages.get(i);
            messagePanel.add(curr);

            addRightClickMenu(curr, messagePanel, messages, userOne, out, in);

        }

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdown();
            }
        });

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

        try {
            String loadedMessages = in.readLine();
            String[] messagesArr = loadedMessages.split(" {5}");

            // Clear the panel and messages list
            messages.clear();
            messagePanel.removeAll();

            // Add updated messages to the GUI
            for (String msg : messagesArr) {
                if (!msg.trim().isEmpty()) { // Skip empty messages
                    JLabel newMessageLabel = new JLabel(msg);
                    messages.add(newMessageLabel);
                    messagePanel.add(newMessageLabel);
                    addRightClickMenu(newMessageLabel, messagePanel, messages, userOne, out, in);
                }
            }

            // Refresh the GUI
            messagePanel.revalidate();
            messagePanel.repaint();

            SwingUtilities.invokeLater(() -> {
                JScrollBar verticalBar = centerPane.getVerticalScrollBar();
                verticalBar.setValue(verticalBar.getMaximum());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadMessages() {
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

        if (!loadedMessages.isEmpty()) {
            messagesArr = loadedMessages.split(" {5}");
        } else {
            messagesArr = new String[0];
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
                    parentPanel.revalidate();
                    parentPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete message.", TITLE,
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                popupMenu.show(label, e.getX(), e.getY());
            }
        });
    }

    private void shutdown() {
        try {
            running = false;
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GUIClient client = new GUIClient();
        client.run();
    }
}