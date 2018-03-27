package me.anisimoff.editor.Command;

import java.util.Stack;

public class CommandInvoker {
    Stack<Command> undoHistory;
    Stack<Command> redoHistory;

    public CommandInvoker() {
        undoHistory = new Stack<>();
        redoHistory = new Stack<>();
    }

    public boolean executeCommand(Command command) {
        boolean executed = command.execute();
        if (executed) {
            undoHistory.push(command);
            redoHistory.clear();
        }
        return executed;
    }

    public boolean undo() throws CannotUndoException {
        if (!canUndo()) {
            clear();
            throw new CannotUndoException();
        }
        Command command = undoHistory.pop();
        command.undo();
        redoHistory.push(command);
        return true;
    }

    public boolean redo() throws CannotRedoException {
        if (!canRedo()) {
            clear();
            throw new CannotRedoException();
        }
        Command command = redoHistory.pop();
        boolean executed = command.execute();
        if (executed) {
            undoHistory.push(command);
        }
        return executed;
    }

    public boolean canUndo() {
        return !undoHistory.empty();
    }

    public boolean canRedo() {
        return !redoHistory.empty();
    }

    public void clear() {
        undoHistory.clear();
        redoHistory.clear();
    }

}
