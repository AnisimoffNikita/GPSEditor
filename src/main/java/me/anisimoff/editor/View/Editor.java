package me.anisimoff.editor.View;

import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.List;

public class Editor implements View {
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

    Presenter presenter;


    public Editor() {
        setupGUI();
    }

    public void setupGUI() {
        JFrame frame = new JFrame("GPS Editor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);

        setButtons();
        setRoutesTable();
        setRouteDataTable();
    }

    private void setButtons() {
        newButton.addActionListener(e -> {
            if (!saveIfNeeded()) {
                return;
            }

            presenter.newRoute();
        });

        openGPXButton.addActionListener(e -> {
            if (!saveIfNeeded()) {
                return;
            }

            File opened = openDialog(new FileFilter() {
                public String getDescription() {
                    return "GPX file (*.gpx)";
                }

                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".gpx");
                }
            }, "Open GPX");

            if (opened != null) {
                presenter.openGPX(opened);
            }
        });

        openPolylineButton.addActionListener(e -> {
            if (!saveIfNeeded()) {
                return;
            }

            File opened = openDialog(null, "Open Polyline");

            if (opened != null) {
                presenter.openPolyline(opened);
            }
        });

        removeSelectedRouteButton.addActionListener(e -> {
            presenter.removeSelectedRoute();
        });

        saveButton.addActionListener(e -> {
            presenter.saveRoute();
        });

        undoButton.addActionListener(e -> {
            presenter.undo();
        });

        redoButton.addActionListener(e -> {
            presenter.redo();
        });

        addPointAfterSelectedButton.addActionListener(e -> {
            int index = routeDataTable.getSelectedIndex();
            presenter.addPointAfterSelected(index);
        });

        removeSelectedPointButton.addActionListener(e -> {
            int index = routeDataTable.getSelectedIndex();
            presenter.removeSelectedPoint(index);
        });
    }

    private void setRoutesTable(){
        routesTable.addTableSelectionListener(presenter);
        routesTable.addTableSelectedListener(presenter);
    }

    private void setRouteDataTable(){
        routeDataTable.addFinishEditingListener(presenter);
    }

    private File openDialog(FileFilter filter, String header) {
        JFileChooser openDialog = new JFileChooser();
        if (filter != null) {
            openDialog.addChoosableFileFilter(filter);
        }
        int ret = openDialog.showDialog(null, header);
        if (ret == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        return openDialog.getSelectedFile();
    }

    private boolean saveIfNeeded() {
        if (presenter.needSave()) {
            int code = checkSaveDialog();
            switch (code) {
                case JOptionPane.CANCEL_OPTION:
                    return false;
                case JOptionPane.NO_OPTION:
                    if (presenter.isNew()) {
                        presenter.removeSelectedRoute();
                    }
                    break;
                case JOptionPane.YES_OPTION:
                    presenter.saveRoute();
                    break;
            }
        }
        return true;
    }

    private int checkSaveDialog() {
        return JOptionPane.showConfirmDialog(null, "Save?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
    }

    @Override
    public void setRouteList(List<Route> routes) {
        routesTable.addList(routes);
    }

    @Override
    public void setRouteData(Route route) {
        routeDataTable.setRoute(route);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setUndoEnabled(boolean state) {
        undoButton.setEnabled(state);
    }

    @Override
    public void setRedoEnabled(boolean state) {
        redoButton.setEnabled(state);
    }

    @Override
    public void cancelSelection() {
        routesTable.cancelSelection();
        routeDataTable.clearData();
    }

    @Override
    public void addNewRoute(Route route) {
        routesTable.addRoute(route);
    }

    @Override
    public void removeRoute() {
        routesTable.removeSelected();
    }

}
