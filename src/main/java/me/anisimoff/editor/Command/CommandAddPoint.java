package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Point;

public class CommandAddPoint extends Command {
    int index;

    public CommandAddPoint(Model model, int index) {
        super(model);
        this.index = index;
    }

    @Override
    public boolean execute() {
        if (model.nullState()) {
            return false;
        }
        backup = model.getState();
        model.getRoute().insertAfter(index, new Point(0.0,0.0));
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
