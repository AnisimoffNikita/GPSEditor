package me.anisimoff.editor;

import me.anisimoff.editor.Model.DatabaseModel;
import me.anisimoff.editor.Presenter.SimplePresenter;
import me.anisimoff.editor.Utils.Utils;
import me.anisimoff.editor.View.Editor;
import me.anisimoff.editor.View.View;

import javax.swing.*;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Utils.createConfigDir();
            View view = new Editor();
            view.setPresenter(new SimplePresenter(view, new DatabaseModel()));
        });
    }

}
