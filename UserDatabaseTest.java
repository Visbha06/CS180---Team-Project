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

}
