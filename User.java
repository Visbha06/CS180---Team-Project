/**
 * User.java
 * Creates a user with a username and password
 *
 * @author Sathvik Thumma -- Section L25
 * @version 2 November 2024
 */

public class User implements UserInterface {
// sets up username and password fields
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
// gets username and password
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return this.username + "," + this.password;
    }

}
