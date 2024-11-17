import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MessageTest.java
 * Test cases for the Message class.
 *
 * @author Vishal Bhat -- Section L25
 * @version 3 November 2024
 */

public class MessageTest implements MessageTestInterface {
    //tests the toString method
    @Test(timeout = 1000)
    public void toStringTest() {
        Message newMessage = new Message("VishalB", "Hello!");

        String expected = "VishalB:Hello!";

        assertEquals(expected, newMessage.toString());
    }
    //tests the equals method
    @Test(timeout = 1000)
    public void equalsTest() {
        Message message1 = new Message("VishalB", "Hello!");
        Message message2 = new Message("VishalB", "Hello!");
        Message message3 = new Message("VishalB", "Nah.");

        assertTrue(message1.equals(message2));
        assertFalse(message1.equals(message3));
    }

}
