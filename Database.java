import java.io.*;
import java.util.ArrayList;
public interface Database {

    boolean readDatabase(String filepath);
    boolean writeToDatabase(String filepath);

}
