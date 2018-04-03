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
        setState();
    }

    @Override
    public void openGPX(File opened) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandOpenGPX(model, opened))) {
            view.warningMessage("Cannot open gpx");
        }
        setState();
    }

    @Override
    public void openPolyline(File opened) {
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandOpenPolyline(model, opened))) {
            view.warningMessage("Cannot open polyline");
        }
        setState();
    }

    @Override
    public void removeSelectedRoute() {
        if(model.isNone()) {
            view.warningMessage("No route to edit");
            return;
        }
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandRemoveRoute(model))) {
            view.warningMessage("Cannot remove route");
        }
        setState();
    }

    @Override
    public void addPointAfterSelected(int index) {
        if(model.isNone()) {
            view.warningMessage("No route to edit");
            return;
        }
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandAddPoint(model, index))) {
            view.warningMessage("Cannot add point");
        }
        setState();
    }

    @Override
    public void removeSelectedPoint(int index) {
        if(model.isNone()) {
            view.warningMessage("No route to edit");
            return;
        }
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandRemovePoint(model, index))) {
            view.warningMessage("Cannot remove point");
        }
        setState();
    }

    @Override
    public void rename(String name) {
        if(model.isNone()) {
            view.warningMessage("No route to rename");
            return;
        }
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandRenameRoute(model, name))) {
            view.warningMessage("Cannot rename route");
        }
        setState();
    }

    @Override
    public void saveRoute() {
        if(model.isNone()) {
            view.warningMessage("No route to save");
            return;
        }
        CommandInvoker invoker = model.getCommandInvoker();
        if (!invoker.executeCommand(new CommandSaveRoute(model))) {
            view.warningMessage("Cannot save route");
        }
        setState();
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

    private void setState() {
        if (!model.isNone()) {
            view.setState(model.loadAllRoutes(), model.getRoute());
        } else {
            view.setState(model.loadAllRoutes());
        }
    }

}
