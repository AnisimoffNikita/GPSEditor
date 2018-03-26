package me.anisimoff.editor.Model;

import me.anisimoff.editor.Command.Command;
import me.anisimoff.editor.Route;

import java.util.List;

public interface Model {
    int getUntitledNextIndex();

    boolean saveRoute();

    boolean updateRoute();

    boolean removeRoute();

    Route loadRouteByName(String name);

    List<Route> loadAllRoutes();

    State getState();

    void setState(State state);

    boolean isNone();

    Route getRoute();

    boolean executeCommand(Command command);

    boolean undo();

    boolean redo();

    boolean undoEmpty();

    boolean redoEmpty();

    void clearHistory();
}
