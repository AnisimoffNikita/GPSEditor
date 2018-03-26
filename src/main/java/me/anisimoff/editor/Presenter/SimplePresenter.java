package me.anisimoff.editor.Presenter;

import me.anisimoff.editor.Command.*;
import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.View.View;

import java.io.File;
import java.util.List;

public class SimplePresenter implements Presenter {

    private View view;
    private Model model;

    public SimplePresenter(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public List<Route> getRoutes() {
        return model.loadAllRoutes();
    }

    @Override
    public void newRoute() {
        if (model.executeCommand(new CommandNew(model))) {
            view.addNewRoute(model.getState().getRoute());
            view.setRouteData(model.getState().getRoute());
        }
    }

    @Override
    public void openGPX(File opened) {
        if (model.executeCommand(new CommandOpenGPX(model, opened))) {
            view.addNewRoute(model.getState().getRoute());
            view.setRouteData(model.getState().getRoute());
        }
    }

    @Override
    public void openPolyline(File opened) {
        if (model.executeCommand(new CommandOpenPolyline(model, opened))) {
            view.addNewRoute(model.getState().getRoute());
            view.setRouteData(model.getState().getRoute());
        }
    }

    @Override
    public void removeSelectedRoute() {
        if (model.executeCommand(new CommandRemoveRoute(model))) {
            view.removeRoute();
            view.cancelSelection();
        }
    }

    @Override
    public void undo() {
        if (model.undo()) {
            view.setRedoEnabled(true);
            if (model.undoEmpty()) {
                view.setUndoEnabled(false);
            }
        }else {
            view.setRedoEnabled(false);
            view.setUndoEnabled(false);
            model.clearHistory();
        }
    }

    @Override
    public void redo() {
        if (model.redo()) {
            view.setUndoEnabled(true);
            if (model.redoEmpty()) {
                view.setRedoEnabled(false);
            }
        }else {
            view.setRedoEnabled(false);
            view.setUndoEnabled(false);
            model.clearHistory();
        }
    }

    @Override
    public void addPointAfterSelected(int index) {
        model.executeCommand(new CommandAddPoint(model, index));
    }

    @Override
    public void removeSelectedPoint(int index) {
        model.executeCommand(new CommandRemovePoint(model, index));
    }

    @Override
    public void saveRoute() {
        model.executeCommand(new CommandSave(model));
        view.setRouteData(model.getState().getRoute());
    }

    @Override
    public boolean needSave() {
        State route = model.getState();
        if (route == null) {
            return false;
        }
        return route.isNew() || route.isModified();
    }

    @Override
    public boolean isNew() {
        State route = model.getState();
        return route.isNew();
    }

    @Override
    public void edited(int index, Point point) {
        model.executeCommand(new CommandEditPoint(model, index, point));
    }

    @Override
    public boolean select(String name) {
        Route route = model.loadRouteByName(name);
        if (route == null) {
            return false;
        }
        view.setRouteData(route);
        return true;
    }

    @Override
    public boolean canSelect() {
        return needSave();
    }
}
