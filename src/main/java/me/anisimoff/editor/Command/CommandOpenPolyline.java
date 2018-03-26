package me.anisimoff.editor.Command;

import me.anisimoff.editor.*;
import me.anisimoff.editor.GUI.Editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandOpenPolyline extends Command {
    public CommandOpenPolyline(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute(){
        File opened = Utils.openDialog(new FileFilter() {
            public String getDescription() {
                return "Polyline file (*.pl)";
            }

            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".pl");
            }
        }, "Open Polyline");

        if (opened == null) {
            return false;
        }

        String polyline = null;
        try {
            polyline = Utils.readToLine(opened);
        } catch (IOException e) {
            return false;
        }

        List<Point> pathList = PolylineEncoder.decode(polyline);
        ArrayList<Point> path = new ArrayList<>(pathList);

        Route route = new Route(opened.getName(), path, new Date());

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
