package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.View.Editor;

public class CommandRemovePoint extends Command {
    private int index;

    public CommandRemovePoint(Model model, int index) {
        super(model);
        this.index = index;
    }

    @Override
    public boolean execute() {
        if (model.nullState()) {
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
