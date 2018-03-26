package me.anisimoff.editor.Model;

import me.anisimoff.editor.Command.Command;
import me.anisimoff.editor.Constants;
import me.anisimoff.editor.Utils.PolylineEncoder;
import me.anisimoff.editor.Route;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseModel implements Model {

    private Database database;
    private State state;
    private CommandHistory undoHistory;
    private CommandHistory redoHistory;


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
        return !nullState() && database.saveRoute(getRoute());

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
        String name = getRoute().getName();

        boolean result = database.removeRoute(name);
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
