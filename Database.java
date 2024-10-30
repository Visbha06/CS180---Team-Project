import java.io.*;
import java.util.ArrayList;
public interface Database {
    //boolean readDatabase(String filePath);
    //boolean writeToDatabase(String filePath);

    default boolean readDatabase(String filePath) {
        //Default implementation of reading file data to ArrayList
        //Each element in ArrayList corresponds to a line of data
        //Could add ArrayList as parameter to make data collection easier
        ArrayList<String> data  = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
           String line = "";
           line = br.readLine();
            while(line != null) {
                data.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    default boolean writeToDatabase(String filePath) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath)))) {

        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
