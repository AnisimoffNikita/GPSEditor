package me.anisimoff.editor.Presenter;

import me.anisimoff.editor.Route;
import me.anisimoff.editor.View.TableFinishEditingListener;
import me.anisimoff.editor.View.TableSelectedListener;
import me.anisimoff.editor.View.TableSelectionListener;

import java.io.File;
import java.util.List;

public interface Presenter extends
        TableSelectedListener,
        TableSelectionListener,
        TableFinishEditingListener{

    List<Route> getRoutes();

    void newRoute();

    void openGPX(File opened);

    void openPolyline(File opened);

    void removeSelectedRoute();

    void undo();

    void redo();

    void addPointAfterSelected(int index);

    void removeSelectedPoint(int index);

    void saveRoute();

    boolean needSave();

    boolean isNew();
}
