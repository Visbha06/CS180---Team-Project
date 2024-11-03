/**
 * UserDatabaseInterface.java
 * Creates am interface for the User Database of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 02 November 2024
 */

public interface UserDatabaseInterface {

    boolean readDatabase(String filepath);
    boolean writeToDatabase(String filePath);
    boolean checkUsernameAndPassword(String username, String password);
    boolean createNewUser(String username, String password);
    boolean findUser(String username);
    boolean addFriend(String mainUser, String friendRequest);
    boolean removeFriend( String mainUser, String friend);
    boolean block(String mainUser, String blockedUser);

}
