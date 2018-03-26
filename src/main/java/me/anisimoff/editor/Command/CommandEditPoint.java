package me.anisimoff.editor.Command;

import me.anisimoff.editor.GUI.Editor;

public class CommandEditPoint extends Command {
    public CommandEditPoint(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void undo() {

    }
}
