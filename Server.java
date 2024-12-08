import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Server.java
 * The main server that processes information and returns it to the client.
 *
 * @author Vishal Bhat, Brayden Hipp -- Section L25
 * @version 17 November 2024
 */

public class Server extends Thread implements ServerInterface {

    private static UserDatabase userDatabase;
    private static MessagesDatabase messagesDatabase;
    private static ArrayList<String> userData;
    private static ArrayList<String> messageData;

    // constructs Server Object
    public Server(UserDatabase userDatabase, MessagesDatabase messagesDatabase) {
        Server.userDatabase = userDatabase;
        Server.messagesDatabase = messagesDatabase;
    }

    // Starts the server and listens for client connections
    @Override
    public void start() {
        //userData = loadUserData();
        //messageData = loadMessages();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port: " + PORT);
            // Accept and handle client connections
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads message data from the database
    @Override
    public ArrayList<String> loadMessages() {
        boolean messageDataLoaded = messagesDatabase.readDatabase();
        if (messageDataLoaded) {
            return messagesDatabase.getUserData();
        } else {
            return null;
        }
    }

    // Loads user data from the database
    @Override
    public ArrayList<String> loadUserData() {
        boolean userDataLoaded = userDatabase.readDatabase();
        if (userDataLoaded) {
            return userDatabase.getUserData();
        } else {
            return null;
        }
    }

    // Handles individual client connections
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                startBuffers();
                while (clientSocket.isConnected()) {
                    String inputLine = in.readLine();
                    while (inputLine != null) {
                        int index = Integer.parseInt(inputLine.split(":", 4)[0]);
                        String usernameOne = inputLine.split(":", 4)[1];
                        String usernameTwo = inputLine.split(":", 4)[2];
                        String message = inputLine.split(":", 4)[3];

                        Action action = Action.fromInt(index);

                        switch (action) {
                            case SEND_MESSAGE:
                                // Handle sending a message
                                Server.messagesDatabase.addMessage(usernameOne, usernameTwo, message);
                                //Server.messagesDatabase.readDatabase();
                                break;
                            case FIND_MESSAGE:
                                // Find Messages for a user
                                ArrayList<String> resultMessages = Server.messagesDatabase.findMessages(usernameOne, message);
                                String[] resultMessagesArr = resultMessages.toArray(new String[0]);
                                String joinedDataMessages;
                                if (resultMessagesArr.length > 0) {
                                    joinedDataMessages = String.join("     ", resultMessagesArr);
                                } else {
                                    joinedDataMessages = "";
                                }
                                out.write(joinedDataMessages);
                                out.println();
                                out.flush();
                                break;
                            case DELETE_MESSAGE:
                                //Deletes a specific message
                                String passwordOne = Server.userDatabase.findUserAndPassword(usernameOne)
                                        .split(" ")[1];
                                String passwordTwo = Server.userDatabase.findUserAndPassword(usernameTwo)
                                        .split(" ")[1];

                                User userOne = new User(usernameOne, passwordOne);
                                User userTwo = new User(usernameTwo, passwordTwo);

                                Chat chat = new Chat(userOne, userTwo);
                                boolean resultDelete =
                                        Server.messagesDatabase.deleteMessage(chat, usernameOne, message);
                                if (resultDelete) {
                                    out.write("Message deleted");
                                } else {
                                    out.write("ERROR");
                                }
                                out.println();
                                out.flush();
                                break;
                            case ALL_USERS:
                                //sends message to all users
                                //Server.userDatabase.readDatabase();
                                ArrayList<String> resultAllUsers =
                                        Server.messagesDatabase.messageAllUsers(usernameOne);
                                String[] resultAllUsersArr = resultAllUsers.toArray(new String[0]);
                                String joinedDataAllUsers = String.join(",", resultAllUsersArr);
                                out.write(joinedDataAllUsers);
                                out.println();
                                out.flush();
                                break;
                            case FRIENDS_ONLY:
                                //sends messages to friends only
                                ArrayList<String> resultFriends =
                                        Server.messagesDatabase.messageOnlyFriends(usernameOne);
                                String[] resultFriendsArr = resultFriends.toArray(new String[0]);
                                String joinedDataFriends;
                                if (resultFriendsArr.length > 0) {
                                    joinedDataFriends = String.join(",", resultFriendsArr);
                                } else {
                                    joinedDataFriends = "";
                                }
                                out.write(joinedDataFriends);
                                out.println();
                                out.flush();
                                break;
                            case CREATE_USER:
                                // creates a new user
                                boolean resultNewUser;
                                try {
                                    resultNewUser = Server.userDatabase.createNewUser(usernameOne, usernameTwo);
                                    if (resultNewUser) {
                                        out.write("User created");
                                        out.println();
                                        out.flush();
                                    }
                                } catch (PasswordException | UserAlreadyExistsException e) {
                                    out.write(e.getMessage());
                                    out.println();
                                    out.flush();
                                }
                                break;
                            case FIND_USER:
                                // Finds a specific user
                                String desiredUser = Server.userDatabase.findUser(usernameOne);
                                out.write(desiredUser);
                                out.println();
                                out.flush();
                                break;
                            case ADD_FRIEND:
                                // adds a new friend
                                boolean resultAddFriend = Server.userDatabase.addFriend(usernameOne, usernameTwo);
                                if (resultAddFriend) {
                                    out.write(usernameTwo + " added!");
                                } else {
                                    out.write("ERROR");
                                }
                                out.println();
                                out.flush();
                                break;
                            case REMOVE_FRIEND:
                                // removes an existing friend
                                boolean resultRemoveFriend =
                                        Server.userDatabase.removeFriend(usernameOne, usernameTwo);
                                if (resultRemoveFriend) {
                                    out.write(usernameTwo + " removed!");
                                } else {
                                    out.write("ERROR");
                                }
                                out.println();
                                out.flush();
                                break;
                            case BLOCK:
                                //blocks a user
                                boolean resultBlock = Server.userDatabase.block(usernameOne, usernameTwo);
                                if (resultBlock) {
                                    out.write(usernameTwo + " blocked!");
                                } else {
                                    out.write("ERROR");
                                }
                                out.println();
                                out.flush();
                                break;
                            case LOAD:
                                System.out.println(Server.userDatabase.findUserAndPassword(usernameOne));
                                String loadPasswordOne = Server.userDatabase.findUserAndPassword(usernameOne)
                                        .split(" ")[1];
                                String loadPasswordTwo = Server.userDatabase.findUserAndPassword(usernameTwo)
                                        .split(" ")[1];
                                Chat loadChat = new Chat(new User(usernameOne, loadPasswordOne),
                                        new User(usernameTwo, loadPasswordTwo));
                                ArrayList<String> loadedMessages = Server.messagesDatabase.loadMessages(loadChat);
                                String loadedMessagesString = String.join("     ", loadedMessages);
                                out.write(loadedMessagesString);
                                out.println();
                                out.flush();
                                break;
                            case LOGIN:
                                String user = usernameOne;
                                String password = usernameTwo;
                                if (Server.userDatabase.checkUsernameAndPassword(user, password)) {
                                    // Send list of users excluding the current user
                                    var data = Server.userDatabase.getUserData();
                                    StringBuilder returnData = new StringBuilder();
                                    for (String line : data) {
                                        if (!line.contains(user)) {
                                            String[] dataElements = line.split(",", 2);
                                            returnData.append(dataElements[0]).append(" ");
                                        }
                                    }
                                    out.write(returnData.toString());
                                } else {
                                    out.write("LOGIN ERROR");
                                }
                                out.println();
                                out.flush();
                                break;
                        }

                        inputLine = in.readLine();
                    }
                }

            } catch (IOException e) {
                closeBuffers();
            } finally {
                try {
                    closeBuffers();
                    clientSocket.close();
                    System.out.println("Client disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void startBuffers() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void closeBuffers() {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Main method to initialize and start the server
    public static void main(String[] args) {
        MessagesDatabase md = new MessagesDatabase("messagesDatabase.txt", "userDatabase.txt");
        UserDatabase ud = md.getUserDatabase();

        Server server = new Server(ud, md);
        server.start();
    }

}
