package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.View.Editor;

public class CommandEditPoint extends Command {
    private int index;
    private Point point;

    public CommandEditPoint(Model model, int index, Point point) {
        super(model);
        this.index = index;
        this.point = point;
    }

    @Override
    public boolean execute() {
        if (model.nullState()) {
            return false;
        }
        backup = model.getState();
        model.getRoute().edit(index, point);
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
