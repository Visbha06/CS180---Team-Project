/**
 * Message.java
 * Creates a class for messages of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 02 November 2024
 */

public class Message implements MessageInterface {

    private String user;
    private String message;
//initialized the message object
    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }
// returns the user:message
    public String toString() {
        return user + ":" + message;
    }

    public boolean equals(Object o) {
        if (this == o) return true; // Check if they are the same object
        if (o == null || getClass() != o.getClass()) return false; // Check if o is a Message instance

        Message messageObj = (Message) o; // Cast o to Message

        // Compare both user and message fields
        return user.equals(messageObj.user) && message.equals(messageObj.message);
    }
}
