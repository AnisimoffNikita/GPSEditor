package me.anisimoff.editor;

public class Constants {
    enum Enviroment {DEVELOPMENT, TEST, PRODUCTION};

    private static final Enviroment mode = Enviroment.TEST;
    public static String configPath;
    public static final int CANNOT_CREATE_CONFIG_FOLDER = 1;
    public static final int CANNOT_CREATE_DATABASE = 2;
    public static final String UNTITLED = "untitled";

    static {
        switch (mode) {
            case TEST:
                configPath = String.format("%s/GPSEditor/Test/", System.getProperty("user.home"));
                break;
            case DEVELOPMENT:
                configPath = String.format("%s/GPSEditor/Dev/", System.getProperty("user.home"));
                break;
            case PRODUCTION:
                configPath = String.format("%s/.config/GPSEditor/", System.getProperty("user.home"));
                break;
        }
    }
}
