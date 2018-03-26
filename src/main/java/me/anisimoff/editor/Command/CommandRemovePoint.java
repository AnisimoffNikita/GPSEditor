package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;

public class CommandRemovePoint extends Command {
    private final int index;

    public CommandRemovePoint(Model model, int index) {
        super(model);
        this.index = index;
    }

    @Override
    public boolean execute() {
        if (model.isNone()) {
            return false;
        }
        backup = model.getState();
        model.getRoute().remove(index);
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
