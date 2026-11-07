package com.blockburst.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLogger {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String timestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
