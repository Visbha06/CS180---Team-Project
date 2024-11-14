import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Server.java
 * The main server that processes information and returns it to the client.
 *
 * @author Vishal Bhat, Brayden Hipp -- Section L25
 * @version 14 November 2024
 */

public class Server implements ServerInterface {

    private static UserDatabase userDatabase;
    private static MessagesDatabase messagesDatabase;
    private static ArrayList<String> userData;
    private static ArrayList<String> messageData;

    public Server(UserDatabase userDatabase, MessagesDatabase messagesDatabase) {
        Server.userDatabase = userDatabase;
        Server.messagesDatabase = messagesDatabase;
    }

    @Override
    public void start() {
        userData = loadUserData();
        messageData = loadMessages();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> loadMessages() {
        boolean messageDataLoaded = messagesDatabase.readDatabase();
        if (messageDataLoaded) {
            return messagesDatabase.getUserData();
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<String> loadUserData() {
        boolean userDataLoaded = userDatabase.readDatabase();
        if (userDataLoaded) {
            return userDatabase.getUserData();
        } else {
            return null;
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream())
            ) {
                // Send login information in this format: "Username:password"
                String inputLine = in.readLine();
                String[] elements = inputLine.split(":");
                if (Server.userDatabase.checkUsernameAndPassword(elements[0], elements[1])) {
                    var data = Server.userDatabase.getUserData();
                    StringBuilder returnData = new StringBuilder();
                    for (String line : data) {
                        if (!line.contains(elements[0])) {
                            String[] dataElements = line.split(",", 2);
                            returnData.append(dataElements[0]).append(" ");
                        }
                    }
                    out.write(returnData.toString());
                    out.println();
                    out.flush();

                    // All messages sent to the server should be in the following format: "ID:UserOne:UserTwo:Message"
                    inputLine = in.readLine();
                    while (inputLine != null) {
                        int index = Integer.parseInt(inputLine.split(":", 4)[0]);
                        String usernameOne = inputLine.split(":", 4)[1];
                        String usernameTwo = inputLine.split(":", 4)[2];
                        String message = inputLine.split(":", 4)[3];

                        Action action = Action.fromInt(index);

                        switch (action) {
                            case SEND_MESSAGE:
                                Server.messagesDatabase.addMessage(usernameOne, usernameTwo, message);
                                break;
                            case FIND_MESSAGE:
                                ArrayList<String> resultMessages = Server.messagesDatabase.findMessages(usernameOne);
                                String[] resultMessagesArr = resultMessages.toArray(new String[0]);
                                String joinedDataMessages = String.join(",", resultMessagesArr);
                                out.write(joinedDataMessages);
                                out.println();
                                out.flush();
                                break;
                            case DELETE_MESSAGE:
                                boolean resultDelete = Server.messagesDatabase.deleteMessage(usernameOne, message);
                                if (resultDelete) {
                                    out.write("Message deleted");
                                    out.println();
                                    out.flush();
                                }
                                break;
                            case ALL_USERS:
                                ArrayList<String> resultAllUsers = Server.messagesDatabase.messageAllUsers(usernameOne);
                                String[] resultAllUsersArr = resultAllUsers.toArray(new String[0]);
                                String joinedDataAllUsers = String.join(",", resultAllUsersArr);
                                out.write(joinedDataAllUsers);
                                out.println();
                                out.flush();
                                break;
                            case FRIENDS_ONLY:
                                ArrayList<String> resultFriends =
                                        Server.messagesDatabase.messageOnlyFriends(usernameOne);
                                String[] resultFriendsArr = resultFriends.toArray(new String[0]);
                                String joinedDataFriends = String.join(",", resultFriendsArr);
                                out.write(joinedDataFriends);
                                out.println();
                                out.flush();
                                break;
                            case CREATE_USER:
                                boolean resultNewUser;
                                try {
                                    resultNewUser = Server.userDatabase.createNewUser(usernameOne, message);
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

                                break;
                            case ADD_FRIEND:
                                boolean resultAddFriend = Server.userDatabase.addFriend(usernameOne, usernameTwo);
                                if (resultAddFriend) {
                                    out.write(usernameTwo + " added!");
                                    out.println();
                                    out.flush();
                                }
                                Server.userDatabase.readDatabase();
                                break;
                            case REMOVE_FRIEND:
                                boolean resultRemoveFriend = Server.userDatabase.removeFriend(usernameOne, usernameTwo);
                                if (resultRemoveFriend) {
                                    out.write(usernameTwo + " removed!");
                                    out.println();
                                    out.flush();
                                }
                                Server.userDatabase.readDatabase();
                                break;
                            case BLOCK:
                                boolean resultBlock = Server.userDatabase.block(usernameOne, usernameTwo);
                                if (resultBlock) {
                                    out.write(usernameTwo + " blocked!");
                                    out.println();
                                    out.flush();
                                }
                                Server.userDatabase.readDatabase();
                                break;
                        }

                        inputLine = in.readLine();
                    }
                } else {
                    out.write("LOGIN ERROR");
                    out.println();
                    out.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Client disconnected");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(null, null);
        server.start();
    }

    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public MessagesDatabase getMessagesDatabase() {
        return messagesDatabase;
    }


}
