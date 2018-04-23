package me.anisimoff.editor.View;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Utils.PolylineEncoder;
import me.anisimoff.editor.Route;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;

public class RouteDataTable extends JPanel {
    private JTable table;
    private JPanel panel;
    private JTextField routeNameField;
    private JButton copyToClipboardButton;
    private JPanel chartPanel;
    private Date creationDate;

    private TableFinishEditingListener tableFinishEditingListener;
    private RenameListener renameListener;
    private String polyline;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Route Data"));
        final JScrollPane scrollPane1 = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 300;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(scrollPane1, gbc);
        table = new JTable();
        table.setFillsViewportHeight(true);
        scrollPane1.setViewportView(table);
        final JLabel label1 = new JLabel();
        label1.setText("Name:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        panel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Polyline");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label2, gbc);
        copyToClipboardButton = new JButton();
        copyToClipboardButton.setText("Copy to clipboard");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 0);
        panel.add(copyToClipboardButton, gbc);
        routeNameField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 5, 5, 0);
        panel.add(routeNameField, gbc);
        chartPanel = new JPanel();
        chartPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 1000;
        panel.add(chartPanel, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }


    private class DataModel extends DefaultTableModel {
        final String[] columns = {"Latitude", "Longitude", "Altitude"};

        public String getColumnName(int col) {
            return columns[col];
        }

        public int getColumnCount() {
            return columns.length;
        }


        public Class getColumnClass(int c) {
            return Double.class;
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
                Double latitude, longitude, altitude;
                try {
                    latitude = Double.parseDouble(model.getValueAt(i, 0).toString());
                    longitude = Double.parseDouble(model.getValueAt(i, 1).toString());
                    Object value = model.getValueAt(i, 2);
                    if (value == null) {
                        altitude = null;
                    } else {
                        altitude = Double.parseDouble(value.toString());
                    }
                    point = new Point(latitude, longitude, altitude);
                    if (tableFinishEditingListener != null) {
                        tableFinishEditingListener.edited(i, point);
                    }
                } catch (NumberFormatException | NullPointerException ignored) {
                }
            }
        });


        routeNameField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            }

            public void focusLost(FocusEvent e) {
                if (!e.isTemporary()) {
                    if (renameListener != null) {
                        renameListener.rename(routeNameField.getText());
                    }
                }
            }
        });

        copyToClipboardButton.addActionListener(e -> {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(polyline);
            clipboard.setContents(strSel, null);
        });


    }

    public void setRoute(Route route) {
        if (route == null) {
            getModel().setRowCount(0);
            return;
        }
        creationDate = route.getDate();
        routeNameField.setText(route.getName());
        polyline = PolylineEncoder.encode(route.getPath());

        getModel().setRowCount(0);

        ArrayList<Point> path = route.getPath();
        for (Point point : path) {
            addRow(point.getLatitude(), point.getLongitude(), point.getAltitude());
        }

        setHeights(path);
    }

    private void setHeights(ArrayList<Point> path) {
        chartPanel.removeAll();
        chartPanel.revalidate();
        chartPanel.repaint();
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Map heights",
                "Distance",
                "Height",
                createDataset(path),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanelView = new ChartPanel(xylineChart);
        chartPanelView.setPreferredSize(new Dimension(700, 500));
        chartPanel.add(chartPanelView);

    }

    private XYDataset createDataset(ArrayList<Point> path) {

        if (path.size() == 0) {
            return new XYSeriesCollection();
        }

        final XYSeries heights = new XYSeries("Heights");
        double dist = 0;

        heights.add(dist, path.get(0).getAltitude());
        for (int i = 1; i < path.size(); i++) {
            dist += path.get(i - 1).distance(path.get(i));
            heights.add(dist, path.get(i).getAltitude());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(heights);
        return dataset;
    }

    public Route getRoute() throws RouteParseException {
        String name = routeNameField.getText();
        ArrayList<Point> path = new ArrayList<>();
        DataModel model = getModel();
        int nRow = model.getRowCount();

        try {
            Double latitude, longitude, atitude;
            for (int i = 0; i < nRow; i++) {
                latitude = Double.parseDouble(model.getValueAt(i, 0).toString());
                longitude = Double.parseDouble(model.getValueAt(i, 1).toString());
                atitude = Double.parseDouble(model.getValueAt(i, 2).toString());
                Point point = new Point(latitude, longitude, atitude);
                path.add(point);
            }
        } catch (NumberFormatException | NullPointerException ignored) {
            throw new RouteParseException();
        }

        return new Route(name, path, creationDate);
    }

    public void clearData() {
        routeNameField.setText("");
        polyline = "";
        getModel().setRowCount(0);
    }

    public int getSelectedIndex() {
        return table.getSelectedRow();
    }

    private DataModel getModel() {
        return (DataModel) table.getModel();
    }

    private void addRow(double lat, double lng, Double att) {
        DataModel model = getModel();
        int index = model.getRowCount();
        model.insertRow(index, new Object[]{lat, lng, att});
    }

    public void setFinishEditingListener(TableFinishEditingListener listener) {
        tableFinishEditingListener = listener;
    }

    public void setRenameListener(RenameListener listener) {
        renameListener = listener;
    }

}
