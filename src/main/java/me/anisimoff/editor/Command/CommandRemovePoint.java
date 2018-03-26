package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.View.Editor;

public class CommandRemovePoint extends Command {
    int index;

    public CommandRemovePoint(Model model, int index) {
        super(model);
        this.index = index;
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void undo() {

    }
}