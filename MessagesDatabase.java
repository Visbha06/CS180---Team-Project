import java.util.ArrayList;
import java.io.*;

public class MessagesDatabase extends Thread implements Database {
    //Fields
    private ArrayList<String> userData;
    private static Object gateKeeper = new Object();

    //constructor
    public MessagesDatabase(ArrayList<String> userData) {
        this.userData = userData;
    }

    //methods
    @Override
    public boolean readDatabase(String filePath) {
        try (BufferedReader bfr = new BufferedReader(new FileReader(filePath))) {
            String line = bfr.readLine();
            while (line != null) {
                userData.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean writeToDatabase(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < userData.size(); i++) {
                bw.write(userData.get(i));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean findMessages(String username) {
        //stores all the messages by a certain username arrayList
        ArrayList<String> foundMessages = new ArrayList<>();

        //goes through the userData list and finds messages with the username String
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).contains(username)) {
                foundMessages.add(userData.get(i));
                return true;
            }
        }
        return false;
    }

    public boolean deleteMessage(String username, String message) {
        //goes through userData and removes the message from the txt file
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).contains(username) && userData.get(i).contains(message)) {
                userData.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean messagesAllUsers(String username) {
        return true; //WIP

    }

    public boolean messagesOnlyFriends(String username) {
        return true; //WIP
    }


}
