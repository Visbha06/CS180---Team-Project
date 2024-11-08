import java.util.ArrayList;

public interface ServerInterface {

    int PORT = 1234;

    public void run();

    public ArrayList<String> loadUserData(String filepath);

    public ArrayList<String> loadMessages(String filepath);

}
