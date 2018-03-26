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

public class CommandOpenPolyline extends Command {
    private final File opened;
    public CommandOpenPolyline(Model model, File opened) {
        super(model);
        this.opened = opened;
    }

    @Override
    public boolean execute(){
        if (opened == null) {
            return false;
        }
        backup = model.getState();

        String polyline;
        try {
            polyline = Utils.readToLine(opened);
        } catch (IOException e) {
            return false;
        }

        ArrayList<Point> path = new ArrayList<>(PolylineEncoder.decode(polyline));

        Route route = new Route(opened.getName(), path, new Date());

        model.setState(State.NotModifiedRoute(route));

        return model.saveRoute();
    }

    @Override
    public void undo() {
        model.removeRoute();
        model.setState(backup);
    }
}
