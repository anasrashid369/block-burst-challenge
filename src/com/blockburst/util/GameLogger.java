package com.blockburst.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLogger {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String timestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }

    private static boolean prepareFile() {
        File file = new File(Constants.LOG_FILE);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            if (!folder.mkdirs()) {
                System.err.println("Could not create logs folder.");
                return false;
            }
        }

        return true;
    }

    private static void write(String level, String message) {
        try {
            if (!prepareFile()) {
                return;
            }

            File file = new File(Constants.LOG_FILE);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(timestamp() + " [" + level + "] " + message);
                writer.newLine();
            }
        } catch (Exception e) {
            System.err.println("Could not write log: " + e.getMessage());
        }
    }

    public static void info(String message) {
        write("INFO", message);
    }
}
