package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;

public class CommandRenameRoute extends Command {
    private String name;
    public CommandRenameRoute(Model model, String name) {
        super(model);
        this.name = name;
    }

    @Override
    public boolean execute() {
        if (model.nullState()) {
            return false;
        }
        backup = model.getState();
        model.getRoute().setName(name);
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}