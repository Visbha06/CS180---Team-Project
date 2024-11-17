import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * ActionTest.java
 * Test cases for the enum Action
 *
 * @author Vishal Bhat
 * @version 14 November 2024
 */
public class ActionTest implements ActionTestInterface {

    @Override
    @Test(timeout = 1000)
    // tests the EnumValues
    public void testEnumValues() {
        assertEquals(1, Action.SEND_MESSAGE.getValue());
        assertEquals(2, Action.FIND_MESSAGE.getValue());
        assertEquals(3, Action.DELETE_MESSAGE.getValue());
        assertEquals(4, Action.ALL_USERS.getValue());
        assertEquals(5, Action.FRIENDS_ONLY.getValue());
        assertEquals(6, Action.CREATE_USER.getValue());
        assertEquals(7, Action.FIND_USER.getValue());
        assertEquals(8, Action.ADD_FRIEND.getValue());
        assertEquals(9, Action.REMOVE_FRIEND.getValue());
        assertEquals(10, Action.BLOCK.getValue());
    }
// Test the FromInt values
    @Override
    @Test(timeout = 1000)
    public void testFromInt() {
        assertEquals(Action.SEND_MESSAGE, Action.fromInt(1));
        assertEquals(Action.FIND_MESSAGE, Action.fromInt(2));
        assertEquals(Action.DELETE_MESSAGE, Action.fromInt(3));
        assertEquals(Action.ALL_USERS, Action.fromInt(4));
        assertEquals(Action.FRIENDS_ONLY, Action.fromInt(5));
        assertEquals(Action.CREATE_USER, Action.fromInt(6));
        assertEquals(Action.FIND_USER, Action.fromInt(7));
        assertEquals(Action.ADD_FRIEND, Action.fromInt(8));
        assertEquals(Action.REMOVE_FRIEND, Action.fromInt(9));
        assertEquals(Action.BLOCK, Action.fromInt(10));
    }
// tests the FromIntThrows Exception
    @Override
    @Test(timeout = 1000)
    public void testFromIntThrowsException() {
        Exception exception = assertThrows(IllegalAccessException.class, () -> {
            Action.fromInt(99);
        });

        assertEquals("No enum constant with value 99", exception.getMessage());
    }

}
