import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserDatabaseTest {

    @Test(timeout = 1000)
    public void readDatabaseTest() {
        UserDatabase db = new UserDatabase();
        boolean result = db.readDatabase("testRead.txt");
        ArrayList<String> results = db.getUserData();

        assertTrue(result);

        assertEquals(3, results.size());
        assertEquals("Test Line 1", results.get(0));
        assertEquals("Test Line 2", results.get(1));
        assertEquals("Test Line 3", results.get(2));
    }

    @Test(timeout = 1000)
    public void writeToDatabaseTest() {
        UserDatabase db = new UserDatabase();
        ArrayList<String> sampleData = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            sampleData.add("Test Line " + (i + 1));
        }

        db.setNewUserData(sampleData);
        boolean result = db.writeToDatabase("testWrite.txt");

        assertTrue(result);

        ArrayList<String> results = db.getNewUserData();

        assertEquals(3, results.size());
        assertEquals("Test Line 1", results.get(0));
        assertEquals("Test Line 2", results.get(1));
        assertEquals("Test Line 3", results.get(2));
    }

}
