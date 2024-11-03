import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * ChatTest.java
 * Test cases for the Chat class.
 *
 * @author Vishal Bhat -- Section L25
 * @version 3 November 2024
 */

public class ChatTest {

    @Test(timeout = 1000)
    public void equalsTest() {
        User userOne = new User("VishalB", "abcdefg");
        User userTwo = new User("SathvikT", "hijklmno");
        User userThree = new User("RyleeH", "pqrstuv");

        Chat chatOne = new Chat(userOne, userTwo);
        Chat chatTwo = new Chat(userTwo, userThree);
        Chat chatThree = new Chat(userOne, userTwo);

        assertTrue(chatOne.equals(chatThree));
        assertFalse(chatOne.equals(chatTwo));
    }

}
