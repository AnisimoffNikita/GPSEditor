package me.anisimoff.editor;

public class Constants {
    private static final boolean DEV_MODE = false;
    public static String configPath;
    public static final int CANNOT_CREATE_CONFIG_FOLDER = 1;
    public static final int CANNOT_CREATE_DATABASE = 2;
    public static final String UNTITLED = "untitled";

    static {
        if (Constants.DEV_MODE)
            configPath = String.format("%s/GPSEditor/", System.getProperty("user.home"));
        else
            configPath = String.format("%s/.config/GPSEditor/", System.getProperty("user.home"));
    }
}
