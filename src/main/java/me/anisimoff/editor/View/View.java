package me.anisimoff.editor.View;

import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import java.util.List;

public interface View {
    void setRouteList(List<Route> routes);

    void setRouteData(Route route);

    void addNewRoute(Route route);

    void cancelSelection();

    void setPresenter(Presenter presenter);

    void setUndoEnabled(boolean state);

    void setRedoEnabled(boolean state);

    void removeRoute();
}
