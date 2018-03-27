package me.anisimoff.editor.View;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import java.util.List;
import java.util.Vector;

public abstract class View {
    protected Presenter presenter;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public abstract void setState(List<Route> routes);

    public abstract void setState(List<Route> routes, Route route);

    public abstract void warningMessage(String text);
}
