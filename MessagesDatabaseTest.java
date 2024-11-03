import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * MessagesDatabase.java
 * Test cases for the MessagesDatabase class
 *
 * @author Vishal Bhat -- Section L25
 * @version 2 November 2024
 */

public class MessagesDatabaseTest {

    MessagesDatabase md = new MessagesDatabase();

    @Test(timeout = 1000)
    public void readDatabaseTest() {
        boolean readResult = md.readDatabase("messagesTest.txt");
        var results = md.getUserData();

        assertTrue(readResult);

        assertEquals("John45:Hey! How's your day been?", results.get(0));
        assertEquals("Ben500:Been good, hbu?", results.get(1));
        assertEquals("John45:I've been busy lately.", results.get(2));
        assertEquals("Ben500:Same here, I have this project to work on.", results.get(3));
    }

    @Test(timeout = 1000)
    public void writeToDatabaseTest() {
        String username = "John45";
        String message = "Yeah same here.";

        md.readDatabase("messagesTest.txt");
        md.addMessage(username, message);
        boolean writeResult = md.writeToDatabase("messagesTest.txt");
        boolean readResult = md.readDatabase("messagesTest.txt");
        var results = md.getUserData();

        assertTrue(writeResult);
        assertTrue(readResult);

        assertEquals("John45:Hey! How's your day been?", results.get(0));
        assertEquals("Ben500:Been good, hbu?", results.get(1));
        assertEquals("John45:I've been busy lately.", results.get(2));
        assertEquals("Ben500:Same here, I have this project to work on.", results.get(3));
        assertEquals("John45:Yeah same here.", results.get(4));
    }

    @Test(timeout = 1000)
    public void findMessagesTest() {
        boolean readResult = md.readDatabase("messagesTest.txt");
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
        boolean readResult = md.readDatabase("messagesTest.txt");
        boolean deleteResult = md.deleteMessage("John45", "Yeah same here.");
        var results = md.getUserData();
        boolean writeResult = md.writeToDatabase("messagesTest.txt");

        assertTrue(readResult);
        assertTrue(deleteResult);
        assertTrue(writeResult);

        assertEquals("John45:Hey! How's your day been?", results.get(0));
        assertEquals("Ben500:Been good, hbu?", results.get(1));
        assertEquals("John45:I've been busy lately.", results.get(2));
        assertEquals("Ben500:Same here, I have this project to work on.", results.get(3));
    }

    @Test(timeout = 1000)
    public void testMessageAllUsers() {
        boolean readResult = md.readDatabase("messagesTest.txt");
        var allUserMessages = md.messageAllUsers("John45");

        String expected = "Ben500:Been good, hbu?\nBen500:Same here, I have this project to work on.\n";

        StringBuilder output = new StringBuilder();
        for (String message : allUserMessages) {
            output.append(message).append("\n");
        }

        assertTrue(readResult);

        assertEquals(expected, output.toString());
    }

}
