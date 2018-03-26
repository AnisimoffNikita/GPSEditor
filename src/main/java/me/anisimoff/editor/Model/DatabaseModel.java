package me.anisimoff.editor.Model;

import me.anisimoff.editor.Command.Command;
import me.anisimoff.editor.Route;

import java.util.List;

public class DatabaseModel implements Model {

    private final Database database;
    private State state;
    private final CommandHistory undoHistory;
    private final CommandHistory redoHistory;


    public DatabaseModel() {
        database = new Database();
        undoHistory = new CommandHistory();
        redoHistory = new CommandHistory();
        state = null;
    }

    @Override
    public int getUntitledNextIndex() {
        return database.getUntitledNextIndex();
    }

    @Override
    public boolean saveRoute() {
        if (nullState()) {
            return false;
        }

        if (state.isNew()) {
            return database.insertRoute(getRoute());
        } else {
            return database.updateRoute(getRoute());
        }
    }

    @Override
    public Route loadRouteByName(String name) {
        return database.loadRouteByName(name);
    }

    @Override
    public boolean removeRoute() {
        if (nullState()) {
            return false;
        }
        boolean result = database.removeRoute(getRoute().getId());
        if (result) {
            state = null;
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
        if (nullState()) {
            return null;
        }
        return state.getRoute();
    }

    @Override
    public boolean undo() {
        Command command = undoHistory.pop();
        command.undo();
        redoHistory.push(command);
        return true;
    }

    @Override
    public boolean redo() {
        Command command = redoHistory.pop();
        boolean executed = command.execute();
        if (executed) {
            undoHistory.push(command);
        }
        return executed;
    }

    @Override
    public boolean executeCommand(Command command) {
        boolean executed = command.execute();
        if (executed) {
            undoHistory.push(command);
            redoHistory.clear();
        }
        return executed;
    }

    @Override
    public boolean undoEmpty() {
        return undoHistory.isEmpty();
    }

    @Override
    public void clearHistory() {
        undoHistory.clear();
        redoHistory.clear();
    }

    @Override
    public boolean redoEmpty() {
        return redoHistory.isEmpty();
    }

    @Override
    public boolean nullState() {
        return state == null;
    }
}
