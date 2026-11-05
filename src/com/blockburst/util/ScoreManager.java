package com.blockburst.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScoreManager {

    public int loadBest() {
        File file = new File(Constants.BEST_SCORE_FILE);

        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            if (line == null || line.trim().isEmpty()) {
                return 0;
            }

            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.err.println("Best score file does not contain a number.");
            return 0;
        } catch (IOException e) {
            System.err.println("Could not read best score: " + e.getMessage());
            return 0;
        }
    }

    public void saveBest(int score) {
    }
}
