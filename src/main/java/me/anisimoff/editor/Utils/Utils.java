package me.anisimoff.editor.Utils;

import me.anisimoff.editor.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {



    public static File openDialog(FileFilter filter, String header) {
        JFileChooser openDialog = new JFileChooser();
        openDialog.addChoosableFileFilter(filter);
        int ret = openDialog.showDialog(null, header);
        if (ret == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return openDialog.getSelectedFile();
    }

    private static int index = 0;
    public static int untitledNumber() {
        return index++;
    }

    public static String readToLine(File file) throws IOException {
        String content = "";

        content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        return content;
    }

    public static void createConfigDir() {
        File theDir = new File(Constants.configPath);

        if (!theDir.exists()) {
            boolean result = false;

            try{
                result = theDir.mkdir();
            }
            catch(SecurityException se){
                System.exit(Constants.CANNOT_CREATE_CONFIG_FOLDER);
            }

            if (!result) {
                System.exit(Constants.CANNOT_CREATE_CONFIG_FOLDER);
            }
        }
    }

}
