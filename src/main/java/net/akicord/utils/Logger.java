package net.akicord.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    public static void info(String message) {
        System.out.println(ConsoleColors.AKI_INFO + getTime() + " [INFO] " + ConsoleColors.WHITE + message + ConsoleColors.RESET);
    }
    
    public static void success(String message) {
        System.out.println(ConsoleColors.AKI_SUCCESS + getTime() + " [SUCCESS] " + ConsoleColors.WHITE + message + ConsoleColors.RESET);
    }
    
    public static void warning(String message) {
        System.out.println(ConsoleColors.AKI_WARNING + getTime() + " [WARNING] " + ConsoleColors.WHITE + message + ConsoleColors.RESET);
    }
    
    public static void error(String message) {
        System.out.println(ConsoleColors.AKI_ERROR + getTime() + " [ERROR] " + ConsoleColors.WHITE + message + ConsoleColors.RESET);
    }
    
    public static void debug(String message) {
        System.out.println(ConsoleColors.AKI_SECONDARY + getTime() + " [DEBUG] " + ConsoleColors.WHITE + message + ConsoleColors.RESET);
    }
    
    private static String getTime() {
        return "[" + dateFormat.format(new Date()) + "]";
    }
}
