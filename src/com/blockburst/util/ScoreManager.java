package com.blockburst.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
            GameLogger.error("Best score file does not contain a number", e);
            return 0;
        } catch (IOException e) {
            GameLogger.error("Could not read best score", e);
            return 0;
        }
    }

    public void saveBest(int score) {
        int best = loadBest();

        if (score <= best) {
            return;
        }

        File file = new File(Constants.BEST_SCORE_FILE);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            if (!folder.mkdirs()) {
                GameLogger.error("Could not create data folder", new IOException("Folder creation failed"));
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.valueOf(score));
            writer.newLine();
        } catch (IOException e) {
            GameLogger.error("Could not save best score", e);
        }
    }
}
