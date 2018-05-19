package me.anisimoff.editor.Presenter;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.anisimoff.editor.Command.*;
import me.anisimoff.editor.Constants;
import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Module;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Model.State;
import me.anisimoff.editor.View.View;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.ArrayList;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import io.methvin.watcher.DirectoryWatcher;


public class SimplePresenter implements Presenter {

    private final View view;
    private final Model model;

    private ArrayList<Module> modules;

    public SimplePresenter(View view, Model model) {
        this.view = view;
        this.model = model;
        this.modules = new ArrayList<>();
        setState();
        updateModulesDirectory();

        try {
            DirectoryWatcher.create(Paths.get(Constants.modulePath), event -> {
                modules.clear();
                switch (event.eventType()){
                    case CREATE:
                    case DELETE:
                        updateModulesDirectory();
                        break;
                    default:
                        break;
                }
            }).watchAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateModulesDirectory() {
        File folder = new File(Constants.modulePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < (listOfFiles != null ? listOfFiles.length : 0); i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName();
                try {
                    if (name.substring(name.lastIndexOf(".") + 1).equals("jar")){
                        addModule(listOfFiles[i]);
                    }
                } catch (Exception e) {
                    return ;
                }
            }
        }

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

    @Override
    public void newModule(File jar) {
        Path src = jar.toPath();
        Path dst = Paths.get(Constants.modulePath + jar.getName());
        try {
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setState() {
        if (!model.isNone()) {
            view.setState(model.loadAllRoutes(), model.getRoute());
        } else {
            view.setState(model.loadAllRoutes());
        }
    }

    private void addModule(File jar) {
        try
        {

            JarFile jarFile = new JarFile(jar);
            Enumeration<JarEntry> e = jarFile.entries();

            URL[] urls = { new URL("jar:file:" + jar+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if(je.isDirectory() || !je.getName().endsWith(".class")){
                    continue;
                }

                String className = je.getName().substring(0,je.getName().length()-6);
                className = className.replace('/', '.');
                Class c = cl.loadClass(className);
                if (Module.class.isAssignableFrom(c)) {
                    Constructor cs = c.getConstructor();
                    Module instance = (Module)cs.newInstance();
                    modules.add(instance);
                    view.setModule(modules);
                }
            }


        }
        catch (IOException | InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
