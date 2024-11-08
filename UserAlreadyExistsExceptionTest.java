import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * UserAlreadyExistsExceptionTest.java
 * Test case for the UserAlreadyExistsException exception.
 *
 * @author Vishal Bhat -- Section L25
 * @version 3 November 2024
 */

public class UserAlreadyExistsExceptionTest {

    @Test
    public void throwsUserAlreadyExistsException() {
        UserDatabase ud = new UserDatabase();

        boolean readResult = ud.readDatabase("testRead.txt");

        Exception e = assertThrows(UserAlreadyExistsException.class, () -> {
            ud.createNewUser("VB06", "Ilikecars49");
        });

        String expectedMessage = "This username already exists.";
        String actualMessage = e.getMessage();

        assertTrue(readResult);
        assertTrue(actualMessage.contains(expectedMessage));
    }

}