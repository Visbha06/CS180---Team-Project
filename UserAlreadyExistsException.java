/**
 * UserAlreadyExistsException.java
 * This exception is thrown when the username already exists
 *
 * @author Vishal Bhat
 * @version 30 October 2024
 */

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
