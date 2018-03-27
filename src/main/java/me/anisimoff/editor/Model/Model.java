package me.anisimoff.editor.Model;

import me.anisimoff.editor.Command.CommandInvoker;
import me.anisimoff.editor.Route;

import java.util.List;

public interface Model {
    int getUntitledNextIndex();

    boolean saveRoute();

    boolean updateRoute();

    boolean removeRoute();

    Route loadRouteByID(int id);

    List<Route> loadAllRoutes();

    State getState();

    void setState(State state);

    boolean isNone();

    Route getRoute();

    CommandInvoker getCommandInvoker();
}
