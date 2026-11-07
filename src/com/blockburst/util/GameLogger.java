package com.blockburst.util;

import java.io.File;
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
}
