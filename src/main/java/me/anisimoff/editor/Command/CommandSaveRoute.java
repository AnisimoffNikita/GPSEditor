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

        backup = state;

        state.setNotModified();

        return model.saveRoute();
    }

    @Override
    public void undo() {
        model.setState(backup);
        model.saveRoute();
    }
}
