import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddUser {
    private static final String FILE_NAME = "users.txt";

    public static boolean saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(user.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
