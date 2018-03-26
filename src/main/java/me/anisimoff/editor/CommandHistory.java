package me.anisimoff.editor;

import me.anisimoff.editor.Command.Command;

import java.util.Stack;

public class CommandHistory {
    private Stack<Command> history = new Stack<Command>();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return history.pop();
    }

    public Boolean isEmpty() { return history.isEmpty(); }

    public void clear() { history.clear(); }
}