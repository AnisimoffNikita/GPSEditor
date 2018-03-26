package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;

public class CommandRemoveRoute extends Command {

    public CommandRemoveRoute(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        backup = model.getState();

        return model.removeRoute();
    }

    @Override
    public void undo() {
        model.setState(backup);
        model.saveRoute();
    }
}
