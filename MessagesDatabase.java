import java.util.ArrayList;
import java.io.*;

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

    //constructor
    public MessagesDatabase() {
        if (userData == null) {
            this.userData = new ArrayList<>();
            this.userDatabase = new UserDatabase();
        }
//        this.newUserData = new ArrayList<>();
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
//            newUserData = new ArrayList<>();
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
                }
            }
            return foundMessages;
        }

    }

    public boolean deleteMessage(String username, String message) {
        int originalSize = userData.size();
        synchronized (gateKeeper) {
            //goes through userData and removes the message from the txt file
            for (int i = 0; i < userData.size(); i++) {
                if (userData.get(i).contains(username) && userData.get(i).contains(message)) {
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
            ArrayList<String> allUserMessages = new ArrayList<>();

            for (int i = 0; i < userData.size(); ) {
                if (userData.get(i).contains(username)) {
                    userData.remove(i);
                } else {
                    i++;
                }
            }

            allUserMessages.addAll(userData); //sets allUserMessages to the modified userData
            return allUserMessages;
        }
    }

    // TODO: Uses userData from MessagesDatabase instead of userDatabase, needs correction. Create a userDatabase object
    // TODO: and instantiate it in the constructor. Use the getUserData method from that class.
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
                splits[0] = newUserName;
                //checks to see if the userNames match
                if (newUserName.equals(username)) {
                    String friendsData = splits[2].replace("[", "").replace("]", "");
                    friends = friendsData.split(",");
                }

            }


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

    public void addMessage(String username, String message) {
        userData.add(username + ":" + message);
    }

    public ArrayList<String> getUserData() {
        return userData;
    }
}
