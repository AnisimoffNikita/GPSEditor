package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.Route;

public class CommandSave extends Command {
    public CommandSave(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        State state = model.getState();

        backup = state;

        state.setNotModified();

        return model.saveRoute();
    }

    @Override
    public void undo() {
        model.removeSelectedRoute();
        model.setState(backup);
        model.saveRoute();
    }
}
