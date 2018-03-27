package me.anisimoff.editor.Command;

public class CannotRedoException extends Exception {
    public CannotRedoException() {
        super("Redo error");
    }
}

