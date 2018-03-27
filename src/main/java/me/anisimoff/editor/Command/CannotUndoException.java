package me.anisimoff.editor.Command;

public class CannotUndoException extends Exception {
    public CannotUndoException() {
        super("Undo error");
    }
}
