package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Utils.Utils;

public class CommandAddPoint extends Command {
    private final int index;

    public CommandAddPoint(Model model, int index) {
        super(model);
        this.index = index;
    }

    @Override
    public boolean execute() {
        if (model.isNone()) {
            return false;
        }
        backup = (State)Utils.deepClone(model.getState());
        model.getRoute().insertAfter(index, new Point(0.0,0.0));
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
