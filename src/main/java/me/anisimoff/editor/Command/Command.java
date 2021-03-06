package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Model.State;

public abstract class Command {
    final Model model;
    State backup;

    Command(Model model) {
        this.model = model;
    }

    public abstract boolean execute();

    public abstract void undo();
}
