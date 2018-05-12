package me.anisimoff.editor.View;

import me.anisimoff.editor.Module;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public abstract class View {
    protected Presenter presenter;
    protected List<Module> modules;

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public abstract void setState(List<Route> routes);

    public abstract void setState(List<Route> routes, Route route);

    public abstract void warningMessage(String text);

    public abstract void setModule(List<Module> visitors);

}
