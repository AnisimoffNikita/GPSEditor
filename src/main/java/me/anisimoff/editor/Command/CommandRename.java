package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;

public class CommandRename extends Command {
    public CommandRename(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void undo() {

    }
}
