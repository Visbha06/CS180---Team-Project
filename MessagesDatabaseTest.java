import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * MessagesDatabase.java
 * Test cases for the MessagesDatabase class
 *
 * @author Vishal Bhat -- Section L25
 * @version 17 November 2024
 */

public class MessagesDatabaseTest implements MessagesDatabaseTestInterface {

    MessagesDatabase md = new MessagesDatabase("messagesTest.txt", "userTest.txt");

    @Test(timeout = 1000)
    public void readDatabaseTest() {
        boolean readResult = md.readDatabase();
        var results = md.getUserData();

        assertTrue(readResult);

        assertEquals("[John45;Ben500]:John45:Hey! How's your day been?", results.get(0));
        assertEquals("[Ben500;John45]:Ben500:Been good, hbu?", results.get(1));
        assertEquals("[John45;Ben500]:John45:I've been busy lately.", results.get(2));
        assertEquals("[Ben500;John45]:Ben500:Same here, I have this project to work on.", results.get(3));
    }

    @Test(timeout = 1000)
    public void writeToDatabaseTest() {
        String usernameOne = "John45";
        String usernameTwo = "Ben500";
        String message = "Yeah same here.";

        md.readDatabase();
        md.addMessage(usernameOne, usernameTwo, message);
        boolean writeResult = md.writeToDatabase();
        boolean readResult = md.readDatabase();
        var results = md.getUserData();

        assertTrue(writeResult);
        assertTrue(readResult);

        assertEquals("[John45;Ben500]:John45:Hey! How's your day been?", results.get(0));
        assertEquals("[Ben500;John45]:Ben500:Been good, hbu?", results.get(1));
        assertEquals("[John45;Ben500]:John45:I've been busy lately.", results.get(2));
        assertEquals("[Ben500;John45]:Ben500:Same here, I have this project to work on.", results.get(3));
        assertEquals("[John45;Ben500]:John45:Yeah same here.", results.get(4));
    }

    @Test(timeout = 1000)
    public void findMessagesTest() {
        boolean readResult = md.readDatabase();
        var foundMessages = md.findMessages("John45");

        StringBuilder output = new StringBuilder();
        for (var foundMessage : foundMessages) {
            output.append(foundMessage).append("\n");
        }

        String expected = "John45:Hey! How's your day been?\nJohn45:I've been busy lately.\nJohn45:Yeah same here.\n";

        assertTrue(readResult);
        assertEquals(expected, output.toString());
    }

    @Test(timeout = 1000)
    public void deleteMessagesTest() {
        boolean readResult = md.readDatabase();

        User userOne = new User("John45", "ilikecats500");
        User userTwo = new User("Ben500", "abcdefg");

        Chat chat = new Chat(userOne, userTwo);

        boolean deleteResult = md.deleteMessage(chat, "John45", "Yeah same here.");
        var results = md.getUserData();
        boolean writeResult = md.writeToDatabase();

        assertTrue(readResult);
        assertTrue(deleteResult);
        assertTrue(writeResult);

        assertEquals("[John45;Ben500]:John45:Hey! How's your day been?", results.get(0));
        assertEquals("[Ben500;John45]:Ben500:Been good, hbu?", results.get(1));
        assertEquals("[John45;Ben500]:John45:I've been busy lately.", results.get(2));
        assertEquals("[Ben500;John45]:Ben500:Same here, I have this project to work on.", results.get(3));
    }

    @Test(timeout = 1000)
    public void messageAllUsersTest() {
        boolean readResult = md.readDatabase();
        var allUserMessages = md.messageAllUsers("John45");

        String expected = "Ben500:Been good, hbu?\nBen500:Same here, I have this project to work on.\n";

        StringBuilder output = new StringBuilder();
        for (String message : allUserMessages) {
            output.append(message).append("\n");
        }

        assertTrue(readResult);

        assertEquals(expected, output.toString());
    }

    @Test (timeout = 1000)
    public void messageOnlyFriendsTest() {
        boolean result = md.readDatabase();
        var results = md.messageOnlyFriends("John45");

        String expected = "Ben500\n";

        StringBuilder actual = new StringBuilder();
        for (String name : results) {
            actual.append(name).append("\n");
        }

        assertTrue(result);
        assertEquals(expected, actual.toString());
    }

    @Test(timeout = 1000)
    public void addMessageTest() {
        boolean result = md.readDatabase();
        md.addMessage("Ben500", "John45", "Test message.");

        var data = md.getUserData();

        assertTrue(result);
        assertEquals("John45:Hey! How's your day been?", data.get(0));
        assertEquals("Ben500:Been good, hbu?", data.get(1));
        assertEquals("John45:I've been busy lately.", data.get(2));
        assertEquals("Ben500:Same here, I have this project to work on.", data.get(3));
        assertEquals("Ben500:Test message.", data.get(4));
    }

    @Test(timeout = 1000)
    public void getUserDataTest() {
        boolean result = md.readDatabase();
        var userData = md.getUserData();

        String expected = "John45:Hey! How's your day been?\nBen500:Been good, hbu?\nJohn45:I've been busy lately.\n" +
                "Ben500:Same here, I have this project to work on.\n";

        StringBuilder actual = new StringBuilder();
        for (String user : userData) {
            actual.append(user).append("\n");
        }

        assertTrue(result);
        assertEquals(expected, actual.toString());
    }

}
