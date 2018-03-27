package me.anisimoff.editor.Utils;

import me.anisimoff.editor.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

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

    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
