package battleship;

import java.io.FileWriter;
import java.io.IOException;

public class Scoreboard {

    public static void save(String result) {
        try {
            FileWriter writer = new FileWriter("scoreboard.txt", true);
            writer.write(result + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}