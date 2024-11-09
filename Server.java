import java.util.ArrayList;

public class Server implements ServerInterface {

    private UserDatabase userDatabase;
    private MessagesDatabase messagesDatabase;
    private ArrayList<String> userData;
    private ArrayList<String> messageData;

    public Server(UserDatabase userDatabase, MessagesDatabase messagesDatabase) {
        this.userDatabase = userDatabase;
        this.messagesDatabase = messagesDatabase;
    }

    @Override
    public void run() {

    }

    // TODO
    @Override
    public ArrayList<String> loadMessages(String filepath) {
        return null;
    }

    // TODO
    @Override
    public ArrayList<String> loadUserData(String filepath) {
        return null;
    }
}
