package me.anisimoff.editor.Model;

import me.anisimoff.editor.Command.Command;
import me.anisimoff.editor.Route;

import java.util.List;

public interface Model {
    int getUntitledNextIndex();

    boolean saveRoute();

    boolean removeSelectedRoute();

    Route loadRouteByName(String name);

    List<Route> loadAllRoutes();

    State getState();

    void setState(State route);

    boolean undo();

    boolean redo();

    boolean executeCommand(Command command);

    boolean undoEmpty();

    void clearHistory();

    boolean redoEmpty();
}
