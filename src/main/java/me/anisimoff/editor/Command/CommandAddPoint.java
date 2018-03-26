package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;

public class CommandAddPoint extends Command {
    int index;

    public CommandAddPoint(Model model, int index) {
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
