package me.anisimoff.editor.Command;

import me.anisimoff.editor.*;
import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.Utils.GPXParseException;
import me.anisimoff.editor.Utils.GPXReader;

import java.io.File;

public class CommandOpenGPX extends Command {
    private final File opened;

    public CommandOpenGPX(Model model, File opened) {
        super(model);
        this.opened = opened;
    }

    @Override
    public boolean execute() {
        if (opened == null) {
            return false;
        }

        backup = model.getState();

        Route route;
        try {
            route = GPXReader.parse(opened);
        } catch (GPXParseException e) {
            return false;
        }

        model.setState(State.NotModifiedRoute(route));

        return model.saveRoute();
    }

    @Override
    public void undo() {
        model.removeRoute();
        model.setState(backup);
    }
}
