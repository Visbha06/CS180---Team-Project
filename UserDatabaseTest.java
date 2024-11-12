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

public class UserDatabaseTest implements UserDatabaseTestInterface {

    @Test(timeout = 1000)
    public void readDatabaseTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        boolean result = db.readDatabase();
        ArrayList<String> results = db.getUserData();

        assertTrue(result);

        assertEquals(3, results.size());
        assertEquals("James34,sample_password123,[],[]", results.get(0));
        assertEquals("Ben500,ilikecats15,[],[]", results.get(1));
        assertEquals("VB06,csmajor45,[],[]", results.get(2));
    }

    @Test(timeout = 1000)
    public void writeToDatabaseTest() {
        UserDatabase db = new UserDatabase("testWrite.txt");
        ArrayList<String> sampleData = new ArrayList<>();

        sampleData.add("newUser1,password1,[],[]");
        sampleData.add("newUser2,password2,[],[]");
        sampleData.add("newUser3,password3,[],[]");

        db.setNewUserData(sampleData);
        boolean result = db.writeToDatabase();

        assertTrue(result);

        ArrayList<String> results = db.getNewUserData();

        assertEquals(3, results.size());
        assertEquals("newUser1,password1,[],[]", results.get(0));
        assertEquals("newUser2,password2,[],[]", results.get(1));
        assertEquals("newUser3,password3,[],[]", results.get(2));
    }

    @Test(timeout = 1000)
    public void createNewUserTest() {
        UserDatabase db = new UserDatabase("userTest.txt");
        boolean result;
        try {
            result = db.createNewUser("VishalB", "abcdefg");
        } catch (PasswordException | UserAlreadyExistsException e) {
            result = false;
        }

        assertTrue(result);

        var newUserData = db.getNewUserData();
        String[] split = newUserData.getFirst().split(",");
        User newUser = new User(split[0], split[1]);

        String expected = "VishalB,abcdefg";

        assertEquals(expected, newUser.toString());
    }

    @Test(timeout = 1000)
    public void findUserTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        db.readDatabase();

        assertTrue(db.findUser("James34"));
        assertFalse(db.findUser("nonExistentUser"));
    }

    @Test(timeout = 1000)
    public void checkUsernameAndPasswordTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        db.readDatabase();

        assertTrue(db.checkUsernameAndPassword("James34", "sample_password123"));
        assertFalse(db.checkUsernameAndPassword("James34", "wrong_password"));
        assertFalse(db.checkUsernameAndPassword("UnknownUser", "any_password"));
    }

    @Test(timeout = 1000)
    public void addFriendTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        db.readDatabase();

        boolean result = db.addFriend("James34", "Ben500");
        assertTrue(result);

        ArrayList<String> results = db.getUserData();
        assertTrue(results.get(0).contains("[Ben500]"));

        result = db.addFriend("James34", "Ben500");
        assertFalse(result);

        result = db.addFriend("NonExistentUser", "AnyFriend");
        assertFalse(result);

}

@Test(timeout = 1000)
    public void blockUserTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        db.readDatabase();

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
        UserDatabase db = new UserDatabase("testRead.txt");
        db.readDatabase();

        boolean result = db.removeFriend("James34", "Ben500");
        assertTrue(result);

        ArrayList<String> results = db.getUserData();
        assertFalse(results.get(0).contains("Ben500"));

        result = db.removeFriend("James34", "NonExistentFriend");
        assertFalse(result);

        result = db.removeFriend("NonexistentUser", "AnyFriend");
        assertFalse(result);
    }

    @Test(timeout = 1000)
    public void getUserDataTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        db.readDatabase(); // Populating userData from testRead.txt
        ArrayList<String> result = db.getUserData();

        assertEquals(3, result.size());
        assertEquals("James34,sample_password123,[],[]", result.get(0));
        assertEquals("Ben500,ilikecats15,[],[]", result.get(1));
        assertEquals("VB06,csmajor45,[],[]", result.get(2));
    }

    @Test(timeout = 1000)
    public void getNewUserDataTest() {
        UserDatabase db = new UserDatabase("testCheckUsernameAndPassword.txt");
        db.readDatabase(); // Populating newUserData from testCheckUsernameAndPassword.txt
        ArrayList<String> result = db.getNewUserData();

        assertEquals(3, result.size());
        assertEquals("James34,sample_password123,[],[]", result.get(0));
        assertEquals("Ben500,ilikecats15,[],[]", result.get(1));
        assertEquals("VB06,csmajor45,[],[]", result.get(2));
    }

    @Test(timeout = 1000)
    public void setUserDataTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        ArrayList<String> sampleData = new ArrayList<>();
        sampleData.add("userA,passwordA,[friendA],[blockedA]");
        sampleData.add("userB,passwordB,[friendB],[blockedB]");

        db.setUserData(sampleData);  // Using setter to directly set data
        ArrayList<String> result = db.getUserData();

        assertEquals(2, result.size());
        assertEquals("userA,passwordA,[friendA],[blockedA]", result.get(0));
        assertEquals("userB,passwordB,[friendB],[blockedB]", result.get(1));
    }

    @Test(timeout = 1000)
    public void setNewUserDataTest() {
        UserDatabase db = new UserDatabase("testRead.txt");
        ArrayList<String> sampleData = new ArrayList<>();
        sampleData.add("testUser1,testPass1,[],[]");
        sampleData.add("testUser2,testPass2,[Friend1],[Blocked1]");

        db.setNewUserData(sampleData);
        ArrayList<String> result = db.getNewUserData();

        assertEquals(2, result.size());
        assertEquals("testUser1,testPass1,[],[]", result.get(0));
        assertEquals("testUser2,testPass2,[Friend1],[Blocked1]", result.get(1));
    }
}



