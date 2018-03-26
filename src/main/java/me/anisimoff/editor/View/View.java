package me.anisimoff.editor.View;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import java.util.List;
import java.util.Vector;

public interface View {
    void setPresenter(Presenter presenter);

    void setRouteList(List<Route> routes);

    void updateRoute(Route route);

    void addNewRoute(Route route);

    void setRoute(Route route);

    void removeRoute();

    void cancelSelection();

    void setUndoEnabled(boolean state);

    void setRedoEnabled(boolean state);

    void setSaveButtonEnabled(boolean state);

    void setRemoveButtonEnabled(boolean state);

    void setSelectionByName(Route route);
}
