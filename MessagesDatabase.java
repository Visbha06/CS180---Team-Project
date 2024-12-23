import java.util.ArrayList;
import java.io.*;
import java.util.Collections;

/**
 * MessagesDatabase.java
 * Creates a database that stores and accesses messages from the user
 *
 * @author Brayden Hipp
 * @version November 31st 2024
 */

public class MessagesDatabase extends Thread implements MessageDatabaseInterface {
    //Fields
    private ArrayList<String> userData; //messages from users [username]:[message]
    //    private ArrayList<String> newUserData;
    private final static Object gateKeeper = new Object();
    private UserDatabase userDatabase;
    private String filePath;

    //constructor
    public MessagesDatabase(String filepath, String userDatabaseFilePath) {
        if (userData == null) {
            this.userData = new ArrayList<>();
            this.userDatabase = new UserDatabase(userDatabaseFilePath);
            this.filePath = filepath;

            userDatabase.readDatabase();
        }
//        this.newUserData = new ArrayList<>();
    }

    //Reads messages from the database file into the `userData` list.
    @Override
    public boolean readDatabase() {
        synchronized (gateKeeper) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(filePath))) {
                String line = bfr.readLine();
                while (line != null) {
                    userData.add(line);
                    line = bfr.readLine();
                }
            } catch (IOException e) {
                return false;
            }
//            newUserData = new ArrayList<>();
        }
        return true;
    }

    //Writes messages from the `userData` list into the database file.
    @Override
    public boolean writeToDatabase() {
        synchronized (gateKeeper) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (int i = 0; i < userData.size(); i++) {
                    bw.write(userData.get(i));
                    bw.newLine();
                }
                bw.flush();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    //finds all messages from a user
    public ArrayList<String> findMessages(String username, String message) {
        synchronized (gateKeeper) {
            //stores all the messages by a certain username arrayList
            ArrayList<String> foundMessages = new ArrayList<>();

            //goes through the userData list and finds messages with the username String
            for (int i = 0; i < userData.size(); i++) {
                if (userData.get(i).contains(username) && userData.get(i).contains(message)) {
                    String foundMessage = userData.get(i).split(":", 2)[1];
                    foundMessages.add(foundMessage);
                }
            }
            return foundMessages;
        }

    }

    // Load messages into a chat
    public ArrayList<String> loadMessages(Chat chat) {
        synchronized (gateKeeper) {
            ArrayList<String> messages = new ArrayList<>();

            Chat alternateChat = chat.alternateChat();

            for (int i = 0; i < userData.size(); i++) {
                String currMessage = userData.get(i);
                if (currMessage.contains(chat.toString()) || currMessage.contains(alternateChat.toString())) {
                    messages.add(userData.get(i).substring(currMessage.indexOf("]") + 2));
                }
            }

            return messages;
        }
    }

    // delete a specific message from a user
    public boolean deleteMessage(Chat chat, String username, String message) {
        int originalSize = userData.size();
        synchronized (gateKeeper) {
            //goes through userData and removes the message from the txt file
            for (int i = 0; i < userData.size(); i++) {
                String chatString = userData.get(i).split(":")[0];
                int semicolonIndex = chatString.indexOf(";");
                String usernameOne = chatString.substring(1, semicolonIndex);
                String usernameTwo = chatString.substring(semicolonIndex + 1, chatString.length() - 1);

                String dataUserOne = userDatabase.findUserAndPassword(usernameOne);
                String dataUserTwo = userDatabase.findUserAndPassword(usernameTwo);

                String passwordOne = dataUserOne.split(" ")[1];
                String passwordTwo = dataUserTwo.split(" ")[1];

                User userOne = new User(usernameOne, passwordOne);
                User userTwo = new User(usernameTwo, passwordTwo);

                Chat compareChat = new Chat(userOne, userTwo);

                String user = userData.get(i).split(":")[1];
                String msg = userData.get(i).split(":")[2];

                if (chat.equals(compareChat) && user.equals(username) && msg.equals(message)) {
                    userData.remove(i);
                    break;
                }
            }
        }

        return originalSize > userData.size(); //if any messages were deleted
    }

    //goes through the arrayList userData and returns
    //a new arrayList that has the messages from all users excluding their own
    public ArrayList<String> messageAllUsers(String username) {
        synchronized (gateKeeper) {
            ArrayList<String> userDataCopy = userDatabase.getUserData();
            ArrayList<String> allUsers = new ArrayList<>();
            ArrayList<String> blockedUsers = new ArrayList<>();
            for (int i = 0; i < userDataCopy.size(); i++) {
                if (userDataCopy.get(i).split(",")[0].equals(username)) {
                    String[] pieces = userDataCopy.get(i).split(",");
                    String blockedUsersString = pieces[3];
                    blockedUsersString = blockedUsersString.replace("[", "")
                            .replace("]", "");
                    String[] blockedUsersArr;
                    if (!blockedUsersString.isEmpty()) {
                        blockedUsersArr = blockedUsersString.split(";");
                    } else {
                        blockedUsersArr = new String[0];
                    }
                    Collections.addAll(blockedUsers, blockedUsersArr);
                    break;
                }
            }

            for (int i = 0; i < userDataCopy.size(); ) {
                String user = userDataCopy.get(i).split(",")[0];
                userDataCopy.set(i, user);
                if (user.contains(username) || blockedUsers.contains(user)) {
                    userDataCopy.remove(i);
                } else {
                    i++;
                }
            }

            allUsers.addAll(userDataCopy); //sets allUsers to the modified userData
            return allUsers;
        }
    }

    //find messages from friends only
    public ArrayList<String> messageOnlyFriends(String username) {
        synchronized (gateKeeper) {
            ArrayList<String> friendsOnly = new ArrayList<>();
            ArrayList<String> newUserData = userDatabase.getUserData();

            String newUserName = "";
            //contains the friends from userDataBase that match with the userName
            String[] friends = new String[0];

            for (int i = 0; i < newUserData.size(); i++) {
                String[] splits = newUserData.get(i).split(",");
                //gets name from userData
                newUserName = splits[0];
                String friendsSplitData = splits[2];
                //checks to see if the userNames match
                if (newUserName.equals(username)) {
                    String friendsData = friendsSplitData.replace("[", "").replace("]", "");
                    if (!friendsData.contains(","))
                        friends = new String[]{friendsData};
                    else
                        friends = friendsData.split(",");
                    break;
                }

            }

            StringBuilder foundFriends = new StringBuilder();
            boolean found;
            for (int i = 0; i < newUserData.size(); i++) {
                found = newUserData.get(i).split(",")[0].equals(username);
                for (int j = 0; j < friends.length; j++) {
                    if (newUserData.get(i).contains(friends[j]) && !foundFriends.toString().contains(friends[j])) {
                        String friendName = friends[j];
                        foundFriends.append(friendName).append(",");
                        friendsOnly.add(friendName);
                        break;
                    }
                }
                if (found)
                    break;
            }

            return friendsOnly;
        }
    }

    // adds a message and adds it into the database
    public void addMessage(String usernameOne, String usernameTwo, String message) {
        synchronized (gateKeeper) {
            String line = String.format("[%s;%s]:%s:%s", usernameOne, usernameTwo, usernameOne, message);
            userData.add(line);
            this.writeToDatabase();
        }
    }

    //returns userData
    public ArrayList<String> getUserData() {
        return userData;
    }

    //returns userDatabase
    public UserDatabase getUserDatabase() {
        return userDatabase;
    }

}
