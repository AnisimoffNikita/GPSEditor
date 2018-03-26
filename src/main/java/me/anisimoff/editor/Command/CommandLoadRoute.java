package me.anisimoff.editor.Command;

import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Model.State;

public class CommandLoadRoute extends Command {
    String name;
    public CommandLoadRoute(Model model, String name) {
        super(model);
        this.name = name;
    }

    @Override
    public boolean execute() {
        Route route = model.loadRouteByName(name);

        backup = model.getState();

        if (route == null){
            return false;
        }

        model.setState(State.NotModifiedRoute(route));
        return true;
    }

    @Override
    public void undo() {
        model.setState(backup);
    }
}
