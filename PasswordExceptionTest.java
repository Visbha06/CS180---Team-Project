import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * PasswordExceptionTest.java
 * Test case for the PasswordException exception.
 *
 * @author Vishal Bhat -- Section L25
 * @version 3 November 2024
 */

public class PasswordExceptionTest implements PasswordExceptionTestInterface {

    @Test(timeout = 1000)
    public void throwsPasswordExceptionTest() {
        UserDatabase ud = new UserDatabase();

        Exception e = assertThrows(PasswordException.class, () -> {
            ud.createNewUser("SampleUser", "bob");
        });

        String expectedMessage = "The password should be at least 7 characters long.";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
