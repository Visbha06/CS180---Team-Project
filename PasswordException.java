/**
 * PasswordException.java
 * This exception is thrown when a password is faulty
 *
 * @author Vishal Bhat, Section L25
 * @version 30 October 2024
 */

public class PasswordException extends Exception implements PasswordExceptionInterface {
    public PasswordException(String message) {
        super(message);
    }
}
