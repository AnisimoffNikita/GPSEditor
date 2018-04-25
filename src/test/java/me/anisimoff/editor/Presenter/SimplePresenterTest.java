package me.anisimoff.editor.Presenter;

import me.anisimoff.editor.Model.DatabaseModel;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Utils.GPXParseException;
import me.anisimoff.editor.Utils.GPXReader;
import me.anisimoff.editor.View.Editor;
import me.anisimoff.editor.View.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SimplePresenterTest {

    Editor view;
    DatabaseModel model;
    SimplePresenter presenter;


    @Before
    public void setUp() throws Exception {


        File file = new File("/home/nikita/GPSEditor/Test/routes.db");
        file.delete();

        view = new Editor();
        model = new DatabaseModel();
        presenter = new SimplePresenter(view, model);
        view.setPresenter(presenter);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void openGPX() throws GPXParseException {

        File goodGPX = new File("/home/nikita/Projects/labs/Software-Engineering/Tests/route.gpx");
        File badGPX = new File("/home/nikita/Projects/labs/Software-Engineering/Tests/route_broken.gpx");
        Route route;

        presenter.openGPX(goodGPX);
        route = GPXReader.parse(goodGPX);

        assertEquals(model.getRoute().getPath(), route.getPath());
        assertEquals(model.getRoute().getName(), route.getName());

        presenter.openGPX(goodGPX);
        route = GPXReader.parse(goodGPX);

        assertEquals(model.getRoute().getPath(), route.getPath());
        assertEquals(model.getRoute().getName(), route.getName());

        presenter.openGPX(badGPX);

        assertEquals(model.getRoute().getPath(), route.getPath());
        assertEquals(model.getRoute().getName(), route.getName());
    }

    @Test
    public void removeSelectedRoute() {
        File goodGPX = new File("/home/nikita/Projects/labs/Software-Engineering/Tests/route.gpx");
        presenter.openGPX(goodGPX);

        presenter.removeSelectedRoute();
        assertEquals(model.getRoute(), null);

        presenter.removeSelectedRoute();
        assertEquals(model.getRoute(), null);
    }

    @Test
    public void addPointAfterSelected() {
        File goodGPX = new File("/home/nikita/Projects/labs/Software-Engineering/Tests/route.gpx");
        presenter.openGPX(goodGPX);

        presenter.addPointAfterSelected(-1);
        assertEquals(model.getRoute().getPath().get(0), new Point(0.0,0.0,0.0));
    }

    @Test
    public void edited() {
        File goodGPX = new File("/home/nikita/Projects/labs/Software-Engineering/Tests/route.gpx");
        presenter.openGPX(goodGPX);

        presenter.edited(0,new Point(1.0,1.0,1.0));
        assertEquals(model.getRoute().getPath().get(0), new Point(1.0,1.0,1.0));

    }
}