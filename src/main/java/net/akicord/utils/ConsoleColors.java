package net.akicord.utils;

public class ConsoleColors {
    public static final String RESET = "\033[0m";
    
    // Cores regulares
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    
    // Cores em negrito
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD = "\033[1;36m";
    public static final String WHITE_BOLD = "\033[1;37m";
    
    // Cores AkiCord
    public static final String AKI_PRIMARY = "\033[38;2;147;112;219m"; // Roxo
    public static final String AKI_SECONDARY = "\033[38;2;100;149;237m"; // Azul
    public static final String AKI_SUCCESS = "\033[38;2;50;205;50m"; // Verde
    public static final String AKI_ERROR = "\033[38;2;220;20;60m"; // Vermelho
    public static final String AKI_WARNING = "\033[38;2;255;165;0m"; // Laranja
    public static final String AKI_INFO = "\033[38;2;135;206;235m"; // Azul claro
}
