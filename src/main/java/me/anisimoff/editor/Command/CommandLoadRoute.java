package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Model.State;

import java.util.Vector;

public class CommandLoadRoute extends Command {
    private final int id;
    public CommandLoadRoute(Model model, int id) {
        super(model);
        this.id = id;
    }

    @Override
    public boolean execute() {
        Route route = model.loadRouteByID(id);

        backup = model.getState();

        if (route == null){
            return false;
        }

        model.setState(State.NotModifiedRoute(route));

        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
