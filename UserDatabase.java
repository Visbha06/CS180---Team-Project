import java.io.*;
import java.util.ArrayList;

public class UserDatabase extends Thread implements Database {

    private ArrayList<String> userData;
    private ArrayList<String> newUserData;
    private final static Object gateKeeper = new Object();

    public UserDatabase() {
        this.userData = new ArrayList<>();
        this.newUserData = new ArrayList<>();
    }

    @Override
    public boolean readDatabase(String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line = br.readLine();
            while (line != null) {
                userData.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean writeToDatabase(String filepath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            for (String newUserDatum : newUserData) {
                bw.write(newUserDatum);
                bw.newLine();
            }

            bw.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean checkUsernameAndPassword() {
        return true; // Makeshift
    }

    public boolean createNewUser(String username, String password) throws PasswordException,
            UserAlreadyExistsException {
        if (password.length() < 7)
            throw new PasswordException("The password should be at least 7 characters long.");

        User newUser = new User(username, password);
        if (userData.contains(newUser.toString()))
            throw new UserAlreadyExistsException("This username already exists.");

        newUserData.add(newUser.toString());
        return true;
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
