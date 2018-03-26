package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;

public class CommandSaveRoute extends Command {
    public CommandSaveRoute(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        State state = model.getState();

        if(model.isNone()) {
            return false;
        }

        backup = state;

        boolean result = model.updateRoute();

        if (result) {
            state.setNotModified();
        }

        return result;
    }

    @Override
    public void undo() {
        model.setState(backup);
        model.updateRoute();
    }
}
