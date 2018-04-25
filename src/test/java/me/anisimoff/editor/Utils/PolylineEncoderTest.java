package me.anisimoff.editor.Utils;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PolylineEncoderTest {

    @Test
    public void encode() {
        assertEquals(PolylineEncoder.encode(null), "");

        ArrayList<Point> points = new ArrayList<>();
        assertEquals(PolylineEncoder.encode(points), "");

        points.add(new Point(1.0,1.0));
        assertEquals(PolylineEncoder.encode(points), "_ibE_ibE");

        points.add(new Point(2.0,2.0));
        assertEquals(PolylineEncoder.encode(points), "_ibE_ibE_ibE_ibE");

        points.add(new Point(-3.0,5.0));
        assertEquals(PolylineEncoder.encode(points), "_ibE_ibE_ibE_ibE~po]_}hQ");

        points.add(new Point(-0.00000001,0.00000001));
        assertEquals(PolylineEncoder.encode(points), "_ibE_ibE_ibE_ibE~po]_}hQ_}hQ~po]");
    }

    @Test
    public void decode() throws PolylineEncoderException {
        assertEquals(PolylineEncoder.decode(null), null);

        ArrayList<Point> points = new ArrayList<>();
        assertEquals(PolylineEncoder.decode(""), points);

        points.add(new Point(1.0,1.0));
        assertEquals(PolylineEncoder.decode("_ibE_ibE"), points);

        points.add(new Point(2.0,2.0));
        assertEquals(PolylineEncoder.decode("_ibE_ibE_ibE_ibE"), points);

        points.add(new Point(-3.0,5.0));
        assertEquals(PolylineEncoder.decode("_ibE_ibE_ibE_ibE~po]_}hQ"), points);

        points.add(new Point(-0.00000001,0.00000001));
        assertEquals(PolylineEncoder.decode("_ibE_ibE_ibE_ibE~po]_}hQ_}hQ~po]"), points);

    }
}