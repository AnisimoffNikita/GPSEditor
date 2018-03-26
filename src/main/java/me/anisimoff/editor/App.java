package me.anisimoff.editor;

import me.anisimoff.editor.Model.Database;
import me.anisimoff.editor.GUI.Editor;

public class App {

    public static void main(String[] args) {
        Utils.createConfigDir();
        Database db = new Database();

        Editor editor = new Editor(db);
        editor.setupGUI();
    }

}
