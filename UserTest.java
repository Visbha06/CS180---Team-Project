import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * UserTest.java
 * Test cases for the User class.
 *
 * @author Vishal Bhat -- Section L25
 * @version 3 November 2024
 */

public class UserTest implements UserTestInterface {
//tests the getUserNameAndPassword method
    @Test(timeout = 1000)
    public void getUsernameAndPasswordTest() {
        User newUser = new User("VishalB", "ClANuDSHoLEm");

        String username = "VishalB";
        String password = "ClANuDSHoLEm";

        assertEquals(username, newUser.getUsername());
        assertEquals(password, newUser.getPassword());
    }
    //tests the toString method
    @Test(timeout = 1000)
    public void toStringTest() {
        User newUser = new User("VishalB", "ClANuDSHoLEm");

        String expected = "VishalB,ClANuDSHoLEm";

        assertEquals(expected, newUser.toString());
    }

}
