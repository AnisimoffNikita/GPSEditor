package me.anisimoff.editor.Command;

import me.anisimoff.editor.GUI.Editor;
import me.anisimoff.editor.Route;

public class CommandSave extends Command {
    public CommandSave(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        Route route = editor.getRoute();

        editor.getStatedRoute().setNotModified();

        return editor.getDatabase().saveRoute(route);
    }

    @Override
    public void undo() {
        editor.getDatabase().removeRouteByName(editor.getRoute().getName());
    }
}
