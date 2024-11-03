import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

/**
 * UserDatabaseTest.java
 * Test cases for the UserDatabase class
 *
 * @author Vishal Bhat, Sathvik Thumma -- Section L25
 * @version 2 November 2024
 */

public class UserDatabaseTest {

    @Test(timeout = 1000)
    public void readDatabaseTest() {
        UserDatabase db = new UserDatabase();
        boolean result = db.readDatabase("testRead.txt");
        ArrayList<String> results = db.getUserData();

        assertTrue(result);

        assertEquals(3, results.size());
        assertEquals("James34,sample_password123,[],[]", results.get(0));
        assertEquals("Ben500,ilikecats15,[],[]", results.get(1));
        assertEquals("VB06,csmajor45,[],[]", results.get(2));
    }

    @Test(timeout = 1000)
    public void writeToDatabaseTest() {
        UserDatabase db = new UserDatabase();
        ArrayList<String> sampleData = new ArrayList<>();

        sampleData.add("newUser1,password1,[],[]");
        sampleData.add("newUser2,password2,[],[]");
        sampleData.add("newUser3,password3,[],[]");

        db.setNewUserData(sampleData);
        boolean result = db.writeToDatabase("testWrite.txt");

        assertTrue(result);

        ArrayList<String> results = db.getNewUserData();

        assertEquals(3, results.size());
        assertEquals("newUser1,password1,[],[]", results.get(0));
        assertEquals("newUser2,password2,[],[]", results.get(1));
        assertEquals("newUser3,password3,[],[]", results.get(2));
    }

    @Test(timeout = 1000)
    public void findUserTest() {
        UserDatabase db = new UserDatabase();
        db.readDatabase("testRead.txt");

        assertTrue(db.findUser("James34"));
        assertFalse(db.findUser("nonExistentUser"));
    }

    @Test(timeout = 1000)
    public void checkUsernameAndPasswordTest() {
        UserDatabase db = new UserDatabase();
        db.readDatabase("testRead.txt");

        assertTrue(db.checkUsernameAndPassword("James34", "sample_password123"));
        assertFalse(db.checkUsernameAndPassword("James34", "wrong_password"));
        assertFalse(db.checkUsernameAndPassword("UnknownUser", "any_password"));
    }

    @Test(timeout = 1000)
    public void blockUserTest() {
        UserDatabase db = new UserDatabase();
        db.readDatabase("testRead.txt");

        boolean result = db.block("James34", "BlockedUser");
        assertTrue(result);

        ArrayList<String> results = db.getUserData();
        assertTrue(results.get(0).contains("BlockedUser"));

        result = db.block("James34", "BlockedUser"); // Already blocked
        assertFalse(result);

        result = db.block("NonexistentUser", "AnyUser");
        assertFalse(result);
    }

    @Test(timeout = 1000)
    public void removeFriendTest() {
        UserDatabase db = new UserDatabase();
        db.readDatabase("testRead.txt");

        boolean result = db.removeFriend("James34", "Ben500");
        assertTrue(result);

        ArrayList<String> results = db.getUserData();
        assertFalse(results.get(0).contains("Ben500"));

        result = db.removeFriend("James34", "NonExistentFriend");
        assertFalse(result);

        result = db.removeFriend("NonexistentUser", "AnyFriend");
        assertFalse(result);
    }
}



