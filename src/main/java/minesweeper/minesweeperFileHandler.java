package minesweeper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class minesweeperFileHandler {
    private final String filepath;

    public minesweeperFileHandler(String difficulty) {
        if (difficulty.equals("Easy")) {
            this.filepath = "easyLeaderboard.txt";
        }

        else if (difficulty.equals("Medium")) {
            this.filepath = "mediumLeaderboard.txt";
        }

        else if(difficulty.equals("Hard")) {
            this.filepath = "hardLeaderboard.txt";
        }
        else {
            this.filepath = difficulty;
        }
    }

    public void writeToFile(List<ArrayList<String>> leaderboard) {
        try (FileWriter fileWriter = new FileWriter(this.filepath)) {
            for (ArrayList<String> arrayList : leaderboard) {
                fileWriter.write(String.join(": ", arrayList));
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            return;
        }
    }

    public List<ArrayList<String>> readFromFile() {
        List<ArrayList<String>> leaderboard = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(this.filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                ArrayList<String> words = new ArrayList<>(Arrays.asList(line.split(": ")));
                leaderboard.add(words);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaderboard;
    }
}
