package me.anisimoff.editor.Model;

import me.anisimoff.editor.Route;

import java.util.ArrayList;
import java.util.List;

public interface Model {
    int getUntitledCount();

    boolean saveRoute(Route route);
    Route loadRouteByName(String name);
    boolean removeRouteByName(String name);
    List<Route> loadAllRoutes();
}
