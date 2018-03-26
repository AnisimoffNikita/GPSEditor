package me.anisimoff.editor.Command;

import me.anisimoff.editor.Constants;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.StatedRoute;
import me.anisimoff.editor.Utils;
import me.anisimoff.editor.GUI.Editor;

public class CommandNew extends Command {


    public CommandNew(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        backup = editor.getStatedRoute();

        Route route = new Route();

        int index = editor.getDatabase().getUntitledCount();
        if (index != 0) {
            route.setName(String.format("%s(%d)", Constants.UNTITLED, index));
        }

        StatedRoute statedRoute = StatedRoute.NewRoute(route);
        editor.setStatedRoute(statedRoute);

        return true;
    }

    @Override
    public void undo() {
        editor.removeCurrentRoute();
        editor.setStatedRoute(backup);
    }

}
