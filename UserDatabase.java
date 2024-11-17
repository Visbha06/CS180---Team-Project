import java.io.*;
import java.util.ArrayList;

/**
 * UserDatabase.java
 * Creates a database for users of the platform
 *
 * @author Vishal Bhat, Sathvik Thumma -- Section L25
 * @version 31 October 2024
 */

public class UserDatabase extends Thread implements UserDatabaseInterface {
    //    private String username;
//    private String password;
    private ArrayList<String> userData;
    private ArrayList<String> newUserData;
    private final static Object gateKeeper = new Object();
    private String filePath;
// consturcts the UserDatabse Object
    public UserDatabase(String filePath) {
//        this.username = username;
//        this.password = password;
        this.userData = new ArrayList<>();
        this.newUserData = new ArrayList<>();
        this.filePath = filePath;

    }
// reads data from the database and returns false when no more data can be read
    @Override
    public boolean readDatabase() {
        synchronized (gateKeeper) {
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line = br.readLine();
                while (line != null) {
                    userData.add(line);
                    line = br.readLine();
                }
            } catch (IOException e) {
                return false;
            }
        }

        return true;
    }
    // writes data to the database and returns false when no more data can be written
    @Override
    public boolean writeToDatabase() {
        synchronized (gateKeeper) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
                for (String newUserDatum : newUserData) {
                    bw.write(newUserDatum);
                    bw.newLine();
                }

                bw.flush();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
// returns true if username and password are right
    public boolean checkUsernameAndPassword(String username, String password) {
        synchronized (gateKeeper) {
            for (String user : userData) {
                String[] piece = user.split(",");
                if (piece[0].equals(username) && piece[1].equals(password)) {
                    return true;
                }

            }
            return false;
        }
    }
//checks if password is long enough and makes sure no duplicate usernames are made, also updates the database
    public boolean createNewUser(String username, String password) throws PasswordException,
            UserAlreadyExistsException {
        synchronized (gateKeeper) {
            if (password.length() < 7)
                throw new PasswordException("The password should be at least 7 characters long.");

            User newUser = new User(username, password);
            boolean alreadyExists = false;
            for (String data : userData) {
                String[] segments = data.split(",");
                if (segments[0].equals(username)) {
                    alreadyExists = true;
                    break;
                }
            }
            if (alreadyExists)
                throw new UserAlreadyExistsException("This username already exists.");

            newUserData.add(newUser.toString());

            this.writeToDatabase();
            this.readDatabase();
        }
        return true;
    }
// finds a specific username
    public String findUser(String username) {
        synchronized (gateKeeper) {
            for (String user : userData) {
                String[] piece = user.split(",");
                if (piece[0].equals(username)) {
                    return piece[0];
                }
            }
            return "NOT_FOUND";
        }
    }
//finds a user and returns their username and password
    public String findUserAndPassword(String username) {
        synchronized (gateKeeper) {
            for (String user : userData) {
                String[] piece = user.split(",");
                if (piece[0].equals(username)) {
                    return piece[0] + " " + piece[1];
                }
            }
            return "NOT_FOUND";
        }
    }
// adds a friend
    public boolean addFriend(String mainUser, String friendRequest) {
        synchronized (gateKeeper) {
            for (int i = 0; i < userData.size(); i++) {
                String[] piece = userData.get(i).split(",");
                if (piece[0].equals(mainUser)) {
                    String friendsData = piece[2].replace("[", "").replace("]", "");
                    String[] friends;
                    if (friendsData.equals("")) {
                        friends = new String[0];
                    } else {
                        friends = friendsData.split(";");
                    }
                    boolean isFriendPresent = false;

                    for (String friend : friends) {
                        if (friend.equals(friendRequest)) {
                            isFriendPresent = true;
                            break;
                        }
                    }

                    if (!isFriendPresent) {
                        String updatedFriends = friendsData.isEmpty() ? friendRequest : friendsData + ";" + friendRequest;
                        piece[2] = "[" + updatedFriends + "]";
                        userData.set(i, String.join(",", piece));
                        return true;
                    }
                }
            }
            return false;
        }
    }

// removes a friend
    public boolean removeFriend(String mainUser, String friend) {
        synchronized (gateKeeper) {
            for (int i = 0; i < userData.size(); i++) {
                String[] piece = userData.get(i).split(",");
                if (piece[0].equals(mainUser)) {
                    String friendsData = piece[2].replace("[", "").replace("]", "");
                    String[] friends;
                    if (friendsData.isEmpty()) {
                        friends = new String[0];
                    } else {
                        friends = friendsData.split(";");
                    }
                    StringBuilder updatedFriends = new StringBuilder();

                    for (String friendItem : friends) {
                        if (!friendItem.equals(friend)) {
                            if (updatedFriends.length() > 0) {
                                updatedFriends.append(";");
                            }
                            updatedFriends.append(friendItem);
                        }
                    }

                    piece[2] = "[" + updatedFriends + "]";
                    userData.set(i, String.join(",", piece));
                    return true;
                }
            }
            return false;
        }
    }
// blocks a user
    public boolean block(String mainUser, String blockedUser) {
        synchronized (gateKeeper) {
            for (int i = 0; i < userData.size(); i++) {
                String[] piece = userData.get(i).split(",");
                if (piece[0].equals(mainUser)) {
                    String blockedData = piece[3].replace("[", "").replace("]", "");
                    String[] blockedUsers;
                    if (blockedData.isEmpty()) {
                        blockedUsers = new String[0];
                    } else {
                        blockedUsers = blockedData.split(";");
                    }
                    boolean isBlockedPresent = false;

                    for (String blocked : blockedUsers) {
                        if (blocked.equals(blockedUser)) {
                            isBlockedPresent = true;
                            break;
                        }
                    }

                    if (!isBlockedPresent) {
                        String updatedBlocked = blockedData;
                        if (!blockedData.isEmpty()) {
                            updatedBlocked += ";";
                        }
                        updatedBlocked += blockedUser;
                        piece[3] = "[" + updatedBlocked + "]";
                        userData.set(i, String.join(",", piece));
                        return true;
                    }
                }
            }
            return false;
        }
    }
// returns userData
    public ArrayList<String> getUserData() {
        return userData;
    }
    // returns NewUserData
    public ArrayList<String> getNewUserData() {
        return newUserData;
    }
// sets UserData
    public void setUserData(ArrayList<String> userData) {
        this.userData = userData;
    }
//sets NewUserData
    public void setNewUserData(ArrayList<String> newUserData) {
        this.newUserData = newUserData;
    }
//returns string filePath
    public String getFilePath() {
        return filePath;
    }

}

