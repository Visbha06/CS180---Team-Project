/**
 * ServerInterface.java
 * Creates am interface for the server of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 08 November 2024
 */

import java.util.ArrayList;

public interface ServerInterface {

    int PORT = 1234;

    public void run();

    public ArrayList<String> loadUserData(String filepath);

    public ArrayList<String> loadMessages(String filepath);

}
