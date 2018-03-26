package me.anisimoff.editor.Presenter;

import me.anisimoff.editor.View.RenameListener;
import me.anisimoff.editor.View.TableFinishEditingListener;
import me.anisimoff.editor.View.TableSelectedListener;
import me.anisimoff.editor.View.TableSelectionListener;

import java.io.File;

public interface Presenter extends
        TableSelectedListener,
        TableSelectionListener,
        TableFinishEditingListener,
        RenameListener{

    void newRoute();

    void openGPX(File opened);

    void openPolyline(File opened);

    void saveRoute();

    void removeSelectedRoute();

    void undo();

    void redo();

    void addPointAfterSelected(int index);

    void removeSelectedPoint(int index);

    boolean needSave();

    boolean isNew();
}
