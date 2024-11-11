import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server implements ServerInterface {

    private UserDatabase userDatabase;
    private MessagesDatabase messagesDatabase;
    private ArrayList<String> userData;
    private ArrayList<String> messageData;
    private String userFilepath;
    private String messagesFilepath;

    public Server(UserDatabase userDatabase, MessagesDatabase messagesDatabase, String userFilepath,
                  String messagesFilepath) {
        this.userDatabase = userDatabase;
        this.messagesDatabase = messagesDatabase;
        this.userFilepath = userFilepath;
        this.messagesFilepath = messagesFilepath;
    }

    @Override
    public void start() {
        userData = loadUserData(userFilepath);
        messageData = loadMessages(messagesFilepath);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<String> loadMessages(String filepath) {
        try{
            BufferedReader bfr = new BufferedReader(new FileReader(filepath));
            String line = bfr.readLine();
            while(line != null){
                messageData.add(line);
                line = bfr.readLine();
            }

        }catch(IOException e){
            return null;
        }
        return messagesDatabase.getUserData();

    }

    // TODO
    @Override
    public ArrayList<String> loadUserData(String filepath) {
        try{
            BufferedReader bfr = new BufferedReader(new FileReader(filepath));
            String line = bfr.readLine();
            while(line != null){
                userData.add(line);
                line = bfr.readLine();
            }

        }catch(IOException e){
            return null;
        }
        return userDatabase.getUserData();

    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {

        }
    }

    public static void main(String[] args) {
        Server server = new Server(null, null, "userDatabase.txt",
                "messagesDatabase.txt");
        server.start();
    }
}
