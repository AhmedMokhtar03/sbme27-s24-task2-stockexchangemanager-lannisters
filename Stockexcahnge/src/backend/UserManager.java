package backend;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserManager {
    public static ArrayList<User> users = new ArrayList<>();
    public static void loadUsersFromCSV(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String username = fields[0];
                String password = fields[1];
                for(User u : users){
                    if(username.equals(u.getUserName())){
                        continue;
                    }
                }
                User user = new User(username, password);
                user.ID= user.hashCode();
                users.add(user);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Other methods...
}