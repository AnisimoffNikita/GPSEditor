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

        boolean result = model.updateRoute();

        if (result) {
            state.setNotModified();
        }

        return result;
    }

    @Override
    public void undo() {

        model.updateRoute();
    }
}
