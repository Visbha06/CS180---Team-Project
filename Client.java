import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements ClientInterface {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
// initializes Client Object
    public Client() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Connected to the server.");

            while (true) {
                displayMenu();
                String id = scanner.nextLine();
                String userOne, userTwo, message;

                switch (id) {
                    case "1": // SEND_MESSAGE
                        System.out.print("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        System.out.print("Enter recipient username (UserTwo): ");
                        userTwo = scanner.nextLine();
                        System.out.print("Enter your message: ");
                        message = scanner.nextLine();
                        break;

                    case "2": // FIND_MESSAGE
                        System.out.print("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        userTwo = "NONE";
                        message = "NONE";
                        break;

                    case "3": // DELETE_MESSAGE
                        System.out.print("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        userTwo = "NONE";
                        System.out.print("Enter message to delete: ");
                        message = scanner.nextLine();
                        break;

                    case "4": // ALL_USERS
                        System.out.print("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        userTwo = "NONE";
                        message = "NONE";
                        break;

                    case "5": // FRIENDS_ONLY
                        System.out.print("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        userTwo = "NONE";
                        message = "NONE";
                        break;

                    case "6": // CREATE_USER
                        System.out.println("Create your username (UserOne): ");
                        userOne = scanner.nextLine();
                        userTwo = "NONE";
                        System.out.println("Create your password: ");
                        message = scanner.nextLine();
                        break;



                    case "7": // FIND_USER
                        System.out.println("Enter their username (UserOne): ");
                        userOne = scanner.nextLine();
                        userTwo = "NONE";
                        message = "NONE";
                        break;


                    case "8": // ADD_FRIEND
                        System.out.println("Enter your username (UserOne): " );
                        userOne = scanner.nextLine();
                        System.out.println("Enter username of friend you want to add (UserTwo): " );
                        userTwo = scanner.nextLine();
                        message = "NONE";
                        break;

                    case "9": // REMOVE_FRIEND
                        System.out.println("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        System.out.println("Enter the username of the friend you want to remove (UserTwo): ");
                        userTwo = scanner.nextLine();
                        message = "NONE";
                        break;


                    case "10": // BLOCK
                        System.out.println("Enter your username (UserOne): ");
                        userOne = scanner.nextLine();
                        System.out.println("Enter the username of the user you want to block (UserTwo): ");
                        userTwo = scanner.nextLine();
                        message = "NONE";
                        break;


                    default:
                        System.out.println("Invalid command ID. Try again.");
                        continue;
                }

                // Format and send the message to the server
                String formattedMessage = id + ":" + userOne + ":" + userTwo + ":" + message;
                out.println(formattedMessage);

                // Receive and display the server's response
                String response = in.readLine();
                if (response != null) {
                    System.out.println("Server response: " + response);
                } else {
                    System.out.println("Server disconnected.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) socket.close();
                if (in != null) in.close();
                if (out != null) out.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
// displays all options to the user
    private void displayMenu() {
        System.out.println("\nSelect a command ID:");
        System.out.println("1: Send Message");
        System.out.println("2: Find Messages");
        System.out.println("3: Delete Message");
        System.out.println("4: View All Users");
        System.out.println("5: View Friends Only");
        System.out.println("6: Create User");
        System.out.println("7: Find User");
        System.out.println("8: Add Friend");
        System.out.println("9: Remove Friend");
        System.out.println("10: Block User");
        System.out.print("Enter command ID: ");
    }
//starts the main method for the client class
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}