package me.anisimoff.editor.Presenter;

import me.anisimoff.editor.Command.*;
import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.View.View;

import java.io.File;

public class SimplePresenter implements Presenter {

    private final View view;
    private final Model model;

    public SimplePresenter(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setState(model.loadAllRoutes());

    }

    @Override
    public void newRoute() {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandNew(model))) {
            view.warningMessage("Cannot create new route");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public void openGPX(File opened) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandOpenGPX(model, opened))) {
            view.warningMessage("Cannot open gpx");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public void openPolyline(File opened) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandOpenPolyline(model, opened))) {
            view.warningMessage("Cannot open polyline");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public void removeSelectedRoute() {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandRemoveRoute(model))) {
            view.warningMessage("Cannot remove route");
        }
        view.setState(model.loadAllRoutes());
    }

    @Override
    public void addPointAfterSelected(int index) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandAddPoint(model, index))) {
            view.warningMessage("Cannot add point");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public void removeSelectedPoint(int index) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandRemovePoint(model, index))) {
            view.warningMessage("Cannot remove point");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public void rename(String name) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandRenameRoute(model, name))) {
            view.warningMessage("Cannot rename route");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public void saveRoute() {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandSaveRoute(model))) {
            view.warningMessage("Cannot rename route");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }


    @Override
    public void undo() {
        CommandInvoker invoker = model.getCommandInvoker();
        try {
            invoker.undo();
            if (!model.isNone()) {
                view.setState(model.loadAllRoutes(), model.getRoute());
            } else {
                view.setState(model.loadAllRoutes());
            }
        } catch (CannotUndoException e) {
            view.warningMessage("Cannot undo");
        }
    }

    @Override
    public void redo() {
        CommandInvoker invoker = model.getCommandInvoker();
        try {
            invoker.redo();
            if (!model.isNone()) {
                view.setState(model.loadAllRoutes(), model.getRoute());
            } else {
                view.setState(model.loadAllRoutes());
            }
        } catch (CannotRedoException e) {
            view.warningMessage("Cannot redo");
        }
    }

    @Override
    public boolean needSave() {
        State route = model.getState();
        return route.isModified();
    }


    @Override
    public void edited(int index, Point point) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandEditPoint(model, index, point))) {
            view.warningMessage("Cannot rename route");
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
    }

    @Override
    public boolean select(int id) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandLoadRoute(model, id))) {
            view.warningMessage("Cannot rename route");
            view.setState(model.loadAllRoutes(), model.getRoute());
            return false;
        }
        view.setState(model.loadAllRoutes(), model.getRoute());
        return true;
    }

    @Override
    public boolean canSelect() {
        return needSave();
    }


}
