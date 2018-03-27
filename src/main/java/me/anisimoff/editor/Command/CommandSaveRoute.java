package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.Route;

public class CommandSaveRoute extends Command {
    private Route dbBackup;

    public CommandSaveRoute(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        State state = model.getState();

        if(model.isNone()) {
            return false;
        }

        backup = model.getState();
        dbBackup = model.loadRouteByID(model.getRoute().getId());

        boolean result = model.updateRoute();

        if (result) {
            state.setNotModified();
        }

        return result;
    }

    @Override
    public void undo() {
        model.setState(State.ModifiedRoute(dbBackup));
        model.updateRoute();
        model.setState(backup);
    }
}
