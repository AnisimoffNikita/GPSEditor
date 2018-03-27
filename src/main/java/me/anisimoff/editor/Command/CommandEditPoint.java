package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Utils.Utils;

public class CommandEditPoint extends Command {
    private final int index;
    private final Point point;

    public CommandEditPoint(Model model, int index, Point point) {
        super(model);
        this.index = index;
        this.point = point;
    }

    @Override
    public boolean execute() {
        if (model.isNone()) {
            return false;
        }
        backup = (State) Utils.deepClone(model.getState());
        model.getRoute().edit(index, point);
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
