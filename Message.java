public class Message {

    private String user;
    private String message;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }

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
