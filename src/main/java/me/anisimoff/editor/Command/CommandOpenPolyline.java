package me.anisimoff.editor.Command;

import me.anisimoff.editor.*;
import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.Utils.PolylineEncoder;
import me.anisimoff.editor.Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandOpenPolyline extends Command {
    File opened;
    public CommandOpenPolyline(Model model, File opened) {
        super(model);
        this.opened = opened;
    }

    @Override
    public boolean execute(){
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

        backup = model.getState();

        State state = State.NewRoute(route);

        model.setState(state);

        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
