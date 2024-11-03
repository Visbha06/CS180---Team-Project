/**
 * MessageDatabaseInterface.java
 * Creates am interface for the Message Database of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 02 November 2024
 */

import java.util.ArrayList;

public interface MessageDatabaseInterface {

    boolean readDatabase(String filePath);
    boolean writeToDatabase(String filePath);
    boolean findMessages(String username);
    boolean deleteMessage(String username, String message);
    ArrayList<String> messageAllUsers(String username);
    ArrayList<String> messageOnlyFriends(String username);

}
