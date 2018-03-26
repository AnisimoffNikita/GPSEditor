package me.anisimoff.editor.GUI;

import me.anisimoff.editor.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RoutesTable extends JPanel {
    private JTable table;
    private JPanel panel;

    private int index;
    private ArrayList<TableSelectionListener> tableSelectionListeners;
    private ArrayList<TableSelectedListener> tableSelectedListeners;

    private class DataModel extends DefaultTableModel {
        String[] columns = {"Name", "Length", "Creation date"};

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

        tableSelectionListeners = new ArrayList<>();
        tableSelectedListeners = new ArrayList<>();

        table.setModel(new DataModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        MouseListener[] l = table.getMouseListeners();
        table.removeMouseListener(l[1]);

        table.addMouseListener(new MouseAdapter() {

            private void setSelection(int index) {
                if (index >= table.getRowCount()) {
                    table.clearSelection();
                } else {
                    table.setRowSelectionInterval(index, index);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
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
                    if (!listener.select(table.getValueAt(row, 0).toString())) {
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

    public void setRoute(Route route) {
        if (route == null){
            table.clearSelection();
            return;
        }
        DataModel model = (DataModel)table.getModel();
        String name = route.getName();
        int i;
        for (i = 0; i < model.getRowCount(); i++) {
            if (name.equals(model.getValueAt(i, 0))) {
                setRow(i, route);
                return;
            }
        }
        addRow(route);
    }

    private void setRow(int i, Route route) {
        DataModel model = (DataModel)table.getModel();
        model.setValueAt(route.getName(), i, 0);
        model.setValueAt(route.getLength(), i, 1);
        model.setValueAt(route.getDate(), i, 2);
        table.setRowSelectionInterval(i, i);
        index = i;
    }

    private void addRow(Route route) {
        DataModel model = (DataModel)table.getModel();
        int i = model.getRowCount();
        model.addRow(new Object[] {route.getName(), route.getLength(), route.getDate()});
        table.setRowSelectionInterval(i, i);
        index = i;
    }


    public void removeSelected() {
        int index = table.getSelectedRow();
        if (index < 0) {
            return;
        }
        DataModel model = (DataModel)table.getModel();
        model.removeRow(index);
    }

    public void cancelSelection() {
        index = -1;
        table.clearSelection();
    }

    public void addList(java.util.List<Route> routes) {
        for (Route route : routes) {
            addRow(route);
        }
        index = -1;
        table.clearSelection();
    }
}
