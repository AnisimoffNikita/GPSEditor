package me.anisimoff.editor.Command;

import me.anisimoff.editor.GUI.Editor;

public class CommandRemoveRoute extends Command {

    public CommandRemoveRoute(Editor editor) {
        super(editor);
    }

    @Override
    public boolean execute() {
        backup = editor.getStatedRoute();

        editor.removeCurrentRoute();

        return editor.getDatabase().removeRouteByName(backup.getRoute().getName());
    }

    @Override
    public void undo() {
        editor.setStatedRoute(backup);

        editor.getDatabase().saveRoute(backup.getRoute());
    }
}
