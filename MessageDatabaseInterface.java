/**
 * MessageDatabaseInterface.java
 * Creates an interface for the Message Database of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 02 November 2024
 */

import java.util.ArrayList;

public interface MessageDatabaseInterface {

    boolean readDatabase();
    boolean writeToDatabase();
    ArrayList<String> findMessages(String username);
    boolean deleteMessage(Chat chat, String username, String message);
    ArrayList<String> messageAllUsers(String username);
    ArrayList<String> messageOnlyFriends(String username);
    ArrayList<String> getUserData();
//    UserDatabase getUserDatabase();

}
