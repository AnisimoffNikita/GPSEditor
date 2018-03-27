package me.anisimoff.editor.Model;

import me.anisimoff.editor.Command.Command;
import me.anisimoff.editor.Command.CommandInvoker;
import me.anisimoff.editor.Route;

import java.util.List;

public class DatabaseModel implements Model {

    private final Database database;
    private State state;
    private final CommandInvoker commandInvoker;


    public DatabaseModel() {
        database = new Database();
        commandInvoker = new CommandInvoker();
        state = new State();
    }

    @Override
    public int getUntitledNextIndex() {
        return database.getUntitledNextIndex();
    }

    @Override
    public boolean saveRoute() {
        if (isNone()) {
            return false;
        }

        int id = database.insertRoute(getRoute());
        if (id == -1) {
            return false;
        }
        state.getRoute().setId(id);

        return true;
    }

    @Override
    public boolean updateRoute() {
        if (isNone()) {
            return false;
        }

        return database.updateRoute(getRoute());
    }

    @Override
    public Route loadRouteByID(int id) {
        return database.loadRouteByID(id);
    }

    @Override
    public boolean removeRoute() {
        if (isNone()) {
            return false;
        }
        boolean result = database.removeRoute(getRoute().getId());
        if (result) {
            state = State.NoneRoute();
        }

        return result;
    }

    @Override
    public List<Route> loadAllRoutes() {
       return database.loadAllRoutes();
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public Route getRoute() {
        if (isNone()) {
            return null;
        }
        return state.getRoute();
    }

    @Override
    public boolean isNone() {
        return state.isNone();
    }

    @Override
    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }


}
