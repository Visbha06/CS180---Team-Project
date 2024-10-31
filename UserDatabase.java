import java.util.ArrayList;

public class UserDatabase extends Thread implements Database {

    private ArrayList<String> userData;
    private final static Object gateKeeper = new Object();

    public UserDatabase() {
        this.userData = new ArrayList<>();
    }

    @Override
    public boolean readDatabase(String filepath) {
        return true; // Makeshift
    }

    @Override
    public boolean writeToDatabase(String filepath) {
        return true; // Makeshift
    }

    public boolean checkUsernameAndPassword() {
        return true; // Makeshift
    }

    public boolean createNewUser(String username, String password) throws PasswordException,
            UserAlreadyExistsException {
        return true; // Makeshift
    }

    public boolean findUser(String username) {
        return true; // Makeshift
    }

    public boolean addFriend(String username) {
        return true; // Makeshift
    }

    public boolean removeFriend(String username) {
        return true; // Makeshift
    }

    public boolean block(String username) {
        return true; // Makeshift
    }

}
