package me.anisimoff.editor.GUI;

import me.anisimoff.editor.*;
import me.anisimoff.editor.Command.*;
import me.anisimoff.editor.Model.Model;

import javax.swing.*;

public class Editor{
    private JPanel mainPanel;
    private JButton openGPXButton;
    private JButton openPolylineButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton newButton;
    private JButton saveButton;
    private JButton addPointAfterSelectedButton;
    private JButton removeSelectedPointButton;
    private JButton removeSelectedRouteButton;
    private RoutesTable routesTable;
    private RouteDataTable routeDataTable;

    private CommandHistory undoHistory;
    private CommandHistory redoHistory;
    private StatedRoute statedRoute;
    private Model database;

    public Editor(Model model) {
        statedRoute = null;
        undoHistory = new CommandHistory();
        redoHistory = new CommandHistory();
        database = model;
    }

    public Route getRoute() {
        return statedRoute.getRoute();
    }

    public StatedRoute getStatedRoute() {
        return statedRoute;
    }

    public void setStatedRoute(StatedRoute route) {
        statedRoute = route;
        if (route == null) {
            routesTable.cancelSelection();
            routeDataTable.clearData();
        }else {
            routesTable.setRoute(route.getRoute());
            routeDataTable.setRoute(route.getRoute());
        }
    }

    public void removeCurrentRoute() {
        statedRoute = null;
        routesTable.removeSelected();
        routeDataTable.clearData();
    }




    public void setupGUI() {
        JFrame frame = new JFrame("GPS Editor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);

        setToolbar();
        setRoutesTable();
        setRouteDataTable();
    }

    private void setToolbar() {
        newButton.addActionListener(e -> {
            if (needSave()) {
                executeCommand(new CommandNew(this));
            }
        });

        openGPXButton.addActionListener(e -> {
            if (needSave()) {
                executeCommand(new CommandOpenGPX(this));
            }
        });

        openPolylineButton.addActionListener(e -> {
            if (needSave()) {
                executeCommand(new CommandOpenPolyline(this));
            }
        });

        openPolylineButton.addActionListener(e -> {
            executeCommand(new CommandSave(this));
        });

        removeSelectedRouteButton.addActionListener(e -> {
            executeCommand(new CommandRemoveRoute(this));
        });

        undoButton.addActionListener(e -> {
            undo();
        });

        redoButton.addActionListener(e -> {
            redo();
        });
    }

    private void setRoutesTable(){
        routesTable.addTableSelectionListener(this::needSave);

        routesTable.addTableSelectedListener(name -> executeCommand(new CommandLoadRoute(this, name)));

        routesTable.addList(database.loadAllRoutes());
    }

    private void setRouteDataTable(){
        routeDataTable.setControls(addPointAfterSelectedButton, removeSelectedPointButton);
    }

    public Model getDatabase() {
        return database;
    }

    public void addCommand(Command command) {
        executeCommand(command);
    }

    private boolean executeCommand(Command command) {
        boolean executed = command.execute();
        if (executed) {
            undoButton.setEnabled(true);
            undoHistory.push(command);
            redoButton.setEnabled(false);
            redoHistory.clear();
        }
        return executed;
    }

    private void undo() {
        Command command = undoHistory.pop();
        command.undo();

        redoButton.setEnabled(true);
        redoHistory.push(command);

        if (undoHistory.isEmpty()) {
            undoButton.setEnabled(false);
        }
    }

    private void redo() {
        Command command = redoHistory.pop();

        if (command.execute()) {
            undoButton.setEnabled(true);
            undoHistory.push(command);

            if (redoHistory.isEmpty()) {
                redoButton.setEnabled(false);
            }
        } else {
            redoButton.setEnabled(false);
            redoHistory.clear();
        }
    }

    private boolean needSave() {
        if (statedRoute == null ||
                statedRoute.isNotModified()){
            return true;
        }

        int code = JOptionPane.showConfirmDialog(null, "Save?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (code) {
            case JOptionPane.CANCEL_OPTION:
                return false;
            case JOptionPane.NO_OPTION:
                if (statedRoute.isNew()) {
                    executeCommand(new CommandRemoveRoute(this));
                }
                break;
            case JOptionPane.YES_OPTION:
                executeCommand(new CommandSave(this));
                break;
        }
        return true;
    }



}
