package me.anisimoff.editor.View;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Utils.PolylineEncoder;
import me.anisimoff.editor.Route;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;

public class RouteDataTable extends JPanel {
    private JTable table;
    private JPanel panel;
    private JTextField routeNameField;
    private JTextField polylineField;
    private Date creationDate;

    private TableFinishEditingListener tableFinishEditingListener;
    private RenameListener renameListener;

    private class DataModel extends DefaultTableModel {
        final String[] columns = {"Latitude", "Longitude"};
        public String getColumnName(int col) {
            return columns[col];
        }
        public int getColumnCount() {
            return columns.length;
        }
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

    }

    public RouteDataTable() {
        super();
        setLayout(new BorderLayout());
        table.setModel(new DataModel());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableFinishEditingListener = null;
        renameListener = null;

        table.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int i = table.getSelectedRow();
                DataModel model = getModel();
                Point point;
                try {
                    double latitude = Double.parseDouble(model.getValueAt(i, 0).toString());
                    double longitude = Double.parseDouble(model.getValueAt(i, 1).toString());
                    point = new Point(latitude, longitude);
                } catch (NumberFormatException | NullPointerException ignored) {
                    point = null;
                }
                if (tableFinishEditingListener != null) {
                    tableFinishEditingListener.edited(i, point);
                }
            }
        });

        routeNameField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            };
            public void focusLost(FocusEvent e) {
                if (!e.isTemporary()) {
                    if (renameListener != null) {
                        renameListener.rename(routeNameField.getText());
                    }
                }
            }
        });
    }

    public void setRoute(Route route) {
        if (route == null) {
            getModel().setRowCount(0);
            return;
        }
        creationDate = route.getDate();
        routeNameField.setText(route.getName());
        polylineField.setText(PolylineEncoder.encode(route.getPath()));

        getModel().setRowCount(0);

        ArrayList<Point> path = route.getPath();
        for(Point point : path) {
            addRow(point.getLatitude(), point.getLongitude());
        }
    }

    public Route getRoute() throws RouteParseException {
        String name = routeNameField.getText();
        ArrayList<Point> path = new ArrayList<>();
        DataModel model = getModel();
        int nRow = model.getRowCount();

        try {
            for (int i = 0; i < nRow; i++) {
                double latitude = Double.parseDouble(model.getValueAt(i, 0).toString());
                double longitude = Double.parseDouble(model.getValueAt(i, 1).toString());
                path.add(new Point(latitude, longitude));
            }
        } catch (NumberFormatException | NullPointerException ignored) {
            throw new RouteParseException();
        }

        return new Route(name, path, creationDate);
    }

    public void clearData() {
        routeNameField.setText("");
        polylineField.setText("");
        getModel().setRowCount(0);
    }

    public int getSelectedIndex() {
        return table.getSelectedRow();
    }

    private DataModel getModel() {
        return (DataModel)table.getModel();
    }

    private void addRow(double lat, double lng) {
        DataModel model = getModel();
        int index = model.getRowCount();
        model.insertRow(index, new Object[] {lat, lng});
    }

    public void setFinishEditingListener(TableFinishEditingListener listener) {
        tableFinishEditingListener = listener;
    }

    public void setRenameListener(RenameListener listener) {
        renameListener = listener;
    }

}
