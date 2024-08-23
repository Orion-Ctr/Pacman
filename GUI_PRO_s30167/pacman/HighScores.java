package pacman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScores {

    public static List<String> loadHighScores(String filename) {
        List<String> highScores = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return highScores; // Return empty list if file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    public static void saveHighScores(List<String> highScores, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String score : highScores) {
                writer.write(score);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

