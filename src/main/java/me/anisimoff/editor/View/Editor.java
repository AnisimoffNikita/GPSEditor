package me.anisimoff.editor.View;

import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.List;

public class Editor extends View {
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

    public Editor() {
        setupGUI();
    }

    private void setupGUI() {
        JFrame frame = new JFrame("GPS Editor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setVisible(true);
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
        routesTable.addTableSelectionListener(this::saveIfNeeded);
        routesTable.addTableSelectedListener(presenter);
    }

    private void setRouteDataTable(){
        routeDataTable.setFinishEditingListener(presenter);
        routeDataTable.setRenameListener(presenter);
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
    public void setPresenter(Presenter presenter) {
        super.setPresenter(presenter);

        setButtons();
        setRoutesTable();
        setRouteDataTable();
    }

    @Override
    public void setState(List<Route> routes) {
        routeDataTable.clearData();
        routesTable.setRoutes(routes);
    }

    @Override
    public void setState(List<Route> routes, Route route) {
        routeDataTable.setRoute(route);
        routesTable.setRoutes(routes, route);
    }

    @Override
    public void warningMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}
