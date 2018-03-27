package me.anisimoff.editor.View;

import com.sun.istack.internal.NotNull;
import me.anisimoff.editor.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RoutesTable extends JPanel {
    private JTable table;
    private JPanel panel;

    private int index;
    private final ArrayList<TableSelectionListener> tableSelectionListeners;
    private final ArrayList<TableSelectedListener> tableSelectedListeners;

    private ArrayList<Route> routes;


    private class DataModel extends DefaultTableModel {
        final String[] columns = {"Name", "Length", "Creation date"};

        public String getColumnName(int col) {
            return columns[col];
        }

        public int getColumnCount() {
            return columns.length;
        }
    }

    public RoutesTable() {
        super();
        setLayout(new BorderLayout());
        index = -1;
        routes = new ArrayList<>();

        tableSelectionListeners = new ArrayList<>();
        tableSelectedListeners = new ArrayList<>();

        table.setModel(new DataModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        MouseListener[] l = table.getMouseListeners();
        table.removeMouseListener(l[1]);

        table.addMouseListener(new MouseAdapter() {

            private void setSelection(int index) {
                if (index < 0 || index >= table.getRowCount()) {
                    table.clearSelection();
                } else {
                    table.setRowSelectionInterval(index, index);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row < 0 || row >= table.getRowCount() || row == index) {
                    return;
                }

                for (TableSelectionListener listener : tableSelectionListeners) {
                    if (!listener.canSelect()) {
                        setSelection(index);
                        return;
                    }
                }

                for (TableSelectedListener listener : tableSelectedListeners) {
                    if (!listener.select(getIDAt(row))) {
                        setSelection(index);
                        return;
                    }
                }

                index = row;
                setSelection(index);
            }
        });

        add(panel);
    }

    public void addTableSelectionListener(TableSelectionListener listener) {
        tableSelectionListeners.add(listener);
    }

    public void addTableSelectedListener(TableSelectedListener listener) {
        tableSelectedListeners.add(listener);
    }

    public void setRoutes(List<Route> routes) {
        this.routes = new ArrayList<>(routes);

        clear();

        for (Route route : routes) {
            addRoute(route);
        }

        cancelSelection();
    }

    public void setRoutes(List<Route> routes, Route selected) {
        this.routes = new ArrayList<>(routes);

        clear();

        int index = 0;
        for (Route route : routes) {
            addRoute(route);
            if (route.getId() == selected.getId()) {
                setSelection(index);
            }
            index++;
        }
    }


    private int getIDAt(int i) {
        return  routes.get(i).getId();
    }

    private void setSelection(int i) {
        index = i;
        table.setRowSelectionInterval(i, i);
    }

    private void addRoute(Route route) {
        DataModel model = (DataModel)table.getModel();
        model.addRow(new Object[] {route.getName(), route.getLength(), route.getDate()});
    }

    private void cancelSelection() {
        index = -1;
        table.clearSelection();
    }

    private DataModel getModel() {
        return (DataModel) table.getModel();
    }

    private void clear() {
        DataModel model = getModel();
        model.setRowCount(0);
    }
}
