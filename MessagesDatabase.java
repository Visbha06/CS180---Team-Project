import java.util.ArrayList;
import java.io.*;

/**
 * MessagesDatabase.java
 * Creates a database that stores and accesses messages from the user
 *
 * @author Brayden Hipp
 * @version November 31st 2024
 */

public class MessagesDatabase extends Thread implements Database {
    //Fields
    private ArrayList<String> userData; //messages from users [username]: [message]
    private final static Object gateKeeper = new Object();

    //constructor
    public MessagesDatabase(ArrayList<String> userData) {
        this.userData = userData;
    }

    //methods
    @Override
    public boolean readDatabase(String filePath) {
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
        }
        return true;
    }

    @Override
    public boolean writeToDatabase(String filePath) {
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

    // TODO: Change implementation -- should return ArrayList<String> foundMessages
    public ArrayList<String> findMessages(String username) {
        synchronized (gateKeeper) {
            //stores all the messages by a certain username arrayList
            ArrayList<String> foundMessages = new ArrayList<>();

            //goes through the userData list and finds messages with the username String
            for (int i = 0; i < userData.size(); i++) {
                if (userData.get(i).contains(username)) {
                    foundMessages.add(userData.get(i));
                    return foundMessages;
                }
            }
            return foundMessages;
        }

    }

    public boolean deleteMessage(String username, String message) {
        synchronized (gateKeeper) {
            int originalSize = userData.size();
            //goes through userData and removes the message from the txt file
            for (int i = 0; i < userData.size(); i++) {
                if (userData.get(i).contains(username) && userData.get(i).contains(message)) {
                    userData.remove(i);
                    i++;
                }

            }
            if (originalSize > userData.size()) {
                return true; //if any messages were deleted
            } else {
                return false; // if no messages were deleted
            }

        }

    }

    //goes through the arrayList userData and returns
    //a new arrayList that has the messages from all users excluding their own
    public ArrayList<String> messagesAllUsers(String username) {
        synchronized (gateKeeper) {
            ArrayList<String> allUserMessages = new ArrayList<>();

            for (int i = 0; i < userData.size(); i++) {
                if (userData.get(i).contains(username)) {
                    userData.remove(i);
                    i++;
                }
            }
            allUserMessages.addAll(userData); //sets allUserMessages to the modified userData
            return allUserMessages;
        }
    }


    public ArrayList<String> messagesOnlyFriends(String userName, String[] friends) {
        synchronized (gateKeeper) {
            ArrayList<String> friendsOnly = new ArrayList<>();

            for (int i = 0; i < userData.size(); i++) {
                for (int j = 0; j < friends.length; j++) {
                    if (userData.get(i).contains(friends[j])) {
                        friendsOnly.add(userData.get(i));
                    }
                }
            }
            return friendsOnly;
        }
    }
}
