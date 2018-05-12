package me.anisimoff.editor.View;

import me.anisimoff.editor.Module;
import me.anisimoff.editor.Presenter.Presenter;
import me.anisimoff.editor.Route;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
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
    private JButton loadModuleButton;
    private JPanel modulesPanel;
    private Route route;


    public Editor() {
        setupGUI();
    }

    private void setupGUI() {
        JFrame frame = new JFrame("GPS Editor");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.setResizable(false);
        frame.pack();
        frame.setSize(new Dimension(1600, 600));
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

        loadModuleButton.addActionListener(e -> {
            File opened = openDialog(new FileFilter() {
                public String getDescription() {
                    return "Jar file (*.jar)";
                }

                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".jar");
                }
            }, "Open Jar");

            if (opened != null) {
                presenter.newModule(opened);
            }
        });
    }

    private void setRoutesTable() {
        routesTable.addTableSelectionListener(this::saveIfNeeded);
        routesTable.addTableSelectedListener(presenter);
    }

    private void setRouteDataTable() {
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
        this.route = null;
    }

    @Override
    public void setState(List<Route> routes, Route route) {
        routeDataTable.setRoute(route);
        routesTable.setRoutes(routes, route);
        this.route = route;
    }

    @Override
    public void setModule(List<Module> module) {
        this.modules = module;
        updateModules();
    }


    @Override
    public void warningMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    private void updateModules() {
        modulesPanel.removeAll();
        modulesPanel.revalidate();
        modulesPanel.setLayout(new BoxLayout(modulesPanel, BoxLayout.Y_AXIS));

        for (Module module : modules) {
            JButton button = new JButton();
            String name = module.getName();
            button.setText(name);
            button.addActionListener(e -> {
                if (route != null)
                    module.action(route);
            });
            modulesPanel.add(button);
        }
        modulesPanel.revalidate();
    }

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
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setOpaque(true);
        mainPanel.setPreferredSize(new Dimension(900, 600));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        mainPanel.add(toolBar1, BorderLayout.NORTH);
        newButton = new JButton();
        newButton.setText("New");
        toolBar1.add(newButton);
        openGPXButton = new JButton();
        openGPXButton.setText("Open GPX");
        toolBar1.add(openGPXButton);
        openPolylineButton = new JButton();
        openPolylineButton.setText("Open Polyline");
        toolBar1.add(openPolylineButton);
        saveButton = new JButton();
        saveButton.setEnabled(true);
        saveButton.setText("Save");
        toolBar1.add(saveButton);
        removeSelectedRouteButton = new JButton();
        removeSelectedRouteButton.setEnabled(true);
        removeSelectedRouteButton.setText("Remove selected route");
        toolBar1.add(removeSelectedRouteButton);
        undoButton = new JButton();
        undoButton.setEnabled(true);
        undoButton.setText("Undo");
        toolBar1.add(undoButton);
        redoButton = new JButton();
        redoButton.setEnabled(true);
        redoButton.setText("Redo");
        toolBar1.add(redoButton);
        loadModuleButton = new JButton();
        loadModuleButton.setText("Load Module");
        toolBar1.add(loadModuleButton);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        mainPanel.add(panel1, BorderLayout.CENTER);
        routesTable = new RoutesTable();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipadx = 250;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel1.add(routesTable.$$$getRootComponent$$$(), gbc);
        routeDataTable = new RouteDataTable();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel1.add(routeDataTable.$$$getRootComponent$$$(), gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        mainPanel.add(panel2, BorderLayout.WEST);
        panel2.setBorder(BorderFactory.createTitledBorder("Edit"));
        addPointAfterSelectedButton = new JButton();
        addPointAfterSelectedButton.setText("Add point after selected");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel2.add(addPointAfterSelectedButton, gbc);
        removeSelectedPointButton = new JButton();
        removeSelectedPointButton.setText("Remove selected point");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel2.add(removeSelectedPointButton, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel2.add(spacer1, gbc);
        modulesPanel = new JPanel();
        modulesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(modulesPanel, gbc);
        modulesPanel.setBorder(BorderFactory.createTitledBorder("Modules"));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
