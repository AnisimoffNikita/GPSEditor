package me.anisimoff.editor.Command;

import me.anisimoff.editor.Constants;
import me.anisimoff.editor.Model.Model;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Model.State;

public class CommandNew extends Command {


    public CommandNew(Model model) {
        super(model);
    }

    @Override
    public boolean execute() {
        backup = model.getState();

        Route route = new Route();

        int index = model.getUntitledNextIndex();
        if (index != 0) {
            route.setName(String.format("%s(%d)", Constants.UNTITLED, index));
        }

        model.setState(State.NotModifiedRoute(route));

        return model.saveRoute();
    }

    @Override
    public void undo() {
        model.removeRoute();
        model.setState(backup);
    }

}
