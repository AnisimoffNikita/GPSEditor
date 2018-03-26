package me.anisimoff.editor.Command;

import me.anisimoff.editor.*;
import me.anisimoff.editor.GUI.Editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class CommandOpenGPX extends Command {
    public CommandOpenGPX(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        File opened = Utils.openDialog(new FileFilter() {
            public String getDescription() {
                return "GPX file (*.gpx)";
            }

            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".gpx");
            }
        }, "Open GPX");

        if (opened == null) {
            return false;
        }

        Route route = null;
        try {
            route = GPXReader.parse(opened);
        } catch (GPXParseException e) {
            //TODO: Warning
            return false;
        }

        backup = editor.getStatedRoute();

        StatedRoute statedRoute = StatedRoute.NewRoute(route);
        editor.setStatedRoute(statedRoute);

        return true;
    }

    @Override
    public void undo() {
        editor.removeCurrentRoute();
        editor.setStatedRoute(backup);
    }
}
