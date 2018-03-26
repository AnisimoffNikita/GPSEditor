package me.anisimoff.editor.Command;

import me.anisimoff.editor.GUI.Editor;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.StatedRoute;

public class CommandLoadRoute extends Command {
    String name;
    public CommandLoadRoute(Editor editor, String name) {
        super(editor);
        this.name = name;
    }

    @Override
    public boolean execute() {
        Route route = editor.getDatabase().loadRouteByName(name);

        backup = editor.getStatedRoute();

        if (route == null){
            return false;
        }

        editor.setStatedRoute(StatedRoute.NotModifiedRoute(route));
        return true;
    }

    @Override
    public void undo() {
        editor.setStatedRoute(backup);
    }
}
