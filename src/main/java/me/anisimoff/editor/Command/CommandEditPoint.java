package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.View.Editor;

public class CommandEditPoint extends Command {
    int index;
    Point point;

    public CommandEditPoint(Model model, int index, Point point) {
        super(model);
        this.index = index;
        this.point = point;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void undo() {

    }
}
