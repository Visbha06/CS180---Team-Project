/**
 * Action.java
 * An enum for handling user input and processing information with the given value
 *
 * @author Vishal Bhat
 * @version 14 November 2024
 */

public enum Action implements ActionInterface {

    SEND_MESSAGE(1), FIND_MESSAGE(2), DELETE_MESSAGE(3), ALL_USERS(4), FRIENDS_ONLY(5),
    CREATE_USER(6), FIND_USER(7), ADD_FRIEND(8), REMOVE_FRIEND(9), BLOCK(10);

    private final int value;

    Action(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static Action fromInt(int input) {
        for (Action action : Action.values()) {
            if (action.getValue() == input) {
                return action;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + input);
    }

}
