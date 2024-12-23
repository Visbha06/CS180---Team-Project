/**
 * Chat.java
 * Creates an interface for the chats of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 02 November 2024
 */

public class Chat implements ChatInterface {

    private User userOne;
    private User userTwo;
// initializes the chat object
    public Chat(User userOne, User userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
    }
// boolean that returns false if obj has nothing left
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Chat chat = (Chat) obj;
        return (userOne.equals(chat.userOne) && userTwo.equals(chat.userTwo)) ||
                (userOne.equals(chat.userTwo) && userTwo.equals(chat.userOne));
    }
// formats the string to the data, userOne, userTwo
    public String toString() {
            return String.format("[%s;%s]", userOne.getUsername(), userTwo.getUsername());
    }

    public Chat alternateChat() {
        return new Chat(userTwo, userOne);
    }
}

