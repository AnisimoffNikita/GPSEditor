package me.anisimoff.editor.Command;

import me.anisimoff.editor.StatedRoute;
import me.anisimoff.editor.GUI.Editor;

public abstract class Command {
    protected Editor editor;
    protected StatedRoute backup;

    public Command(Editor editor) {
        this.editor = editor;
    }

    public abstract boolean execute();

    public abstract void undo();
}
