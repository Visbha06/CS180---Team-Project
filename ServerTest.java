import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * ServerTest.java
 * Test cases for methods in the Server class
 *
 * @author Vishal Bhat -- Section L25
 * @version 17 November 2024
 */

public class ServerTest implements ServerTestInterface {

    MessagesDatabase md = new MessagesDatabase("messagesTest.txt", "userTest.txt");
    UserDatabase ud = md.getUserDatabase();
    Server server = new Server(ud, md);
    //tests LoadUserData method
    @Test(timeout = 1000)
    public void testLoadUserData() {
        ArrayList<String> userData = server.loadUserData();

        ArrayList<String> expectedData = new ArrayList<>();
        expectedData.add("John45,ilikecats500,[Ben500],[]");
        expectedData.add("Ben500,abcdefg,[John45],[]");

        String userDataString = userData.toString();
        String expectedDataString = expectedData.toString();

        assertEquals(expectedDataString, userDataString);
    }
    //tests LoadMessages method
    @Test(timeout = 1000)
    public void testLoadMessages() {
        ArrayList<String> messagesData = server.loadMessages();

        ArrayList<String> expectedData = new ArrayList<>();
        expectedData.add("[John45;Ben500]:John45:Hey! How's your day been?");
        expectedData.add("[Ben500;John45]:Ben500:Been good, hbu?");
        expectedData.add("[John45;Ben500]:John45:I've been busy lately.");
        expectedData.add("[Ben500;John45]:Ben500:Same here, I have this project to work on.");

        String messagesDataString = messagesData.toString();
        String expectedDataString = expectedData.toString();

        assertEquals(expectedDataString, messagesDataString);
    }

}
