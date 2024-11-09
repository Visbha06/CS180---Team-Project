import java.util.ArrayList;

/**
 * ServerInterface.java
 * Creates an interface for the server of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 08 November 2024
 */

public interface ServerInterface {

    int PORT = 1234;

    void run();

    ArrayList<String> loadUserData(String filepath);

    ArrayList<String> loadMessages(String filepath);

}
