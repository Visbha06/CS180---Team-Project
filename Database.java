/**
 * DataBase.java
 * Creates am interface for users of the platform
 *
 * @author Rylee Holler -- Section L25
 * @version 30 October 2024
 */

public interface Database {

    boolean readDatabase(String filepath);

    boolean writeToDatabase(String filepath);

}
