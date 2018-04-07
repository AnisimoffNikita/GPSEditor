package me.anisimoff.editor;

import me.anisimoff.editor.Model.DatabaseModel;
import me.anisimoff.editor.Presenter.SimplePresenter;
import me.anisimoff.editor.Utils.Utils;
import me.anisimoff.editor.View.Editor;
import me.anisimoff.editor.View.View;

import javax.swing.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }

            Utils.createConfigDir();
            View view = new Editor();
            view.setPresenter(new SimplePresenter(view, new DatabaseModel()));

        });
    }

}
