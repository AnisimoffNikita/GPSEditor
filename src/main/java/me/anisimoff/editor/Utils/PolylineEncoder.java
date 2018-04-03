package me.anisimoff.editor.Utils;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import me.anisimoff.editor.Point;

import java.util.ArrayList;
import java.util.List;

public class PolylineEncoder {

    public static List<Point> decode(String polyline) throws PolylineEncoderException {
        if (polyline == null) {
            return null;
        }
        List<LatLng> pathLatLng;
        try {
            pathLatLng = PolylineEncoding.decode(polyline);
        } catch (StringIndexOutOfBoundsException e) {
            throw new PolylineEncoderException();
        }
        ArrayList<Point> path = new ArrayList<>();
        for (final LatLng latLng : pathLatLng) {
            path.add(new Point(latLng.lat, latLng.lng));
        }

        return path;
    }


    public static String encode(List<Point> path) {
        if (path == null) {
            return "";
        }
        ArrayList<LatLng> pathLatLng = new ArrayList<>();
        for (final Point point : path) {
            pathLatLng.add(new LatLng(point.getLatitude(), point.getLongitude()));
        }
        return PolylineEncoding.encode(pathLatLng);
    }


}
