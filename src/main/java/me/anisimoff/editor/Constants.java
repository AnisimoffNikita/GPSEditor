package me.anisimoff.editor;

public class Constants {
    public static final boolean DEV_MODE = true;
    public static String configPath;
    public static int CANNOT_CREATE_CONFIG_FOLDER = 1;
    public static int CANNOT_CREATE_DATABASE = 2;
    public static String DB_NAME = "routes.db";
    public static String UNTITLED = "untitled";

    static {
        if (Constants.DEV_MODE)
            configPath = String.format("%s/GPSEditor/", System.getProperty("user.home"));
        else
            configPath = String.format("%s/.config/GPSEditor/", System.getProperty("user.home"));
    }
}
