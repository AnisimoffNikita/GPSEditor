package me.anisimoff.editor.Utils;

import io.jenetics.jpx.*;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.jenetics.jpx.GPX.Reader.Mode;
import io.jenetics.jpx.GPX.Version;

public class GPXReader {
    static public Route parse(File file) throws GPXParseException {
        String absolutePath = file.getAbsolutePath();
        String filename = file.getName();
        ArrayList<Point> path = new ArrayList<>();

        try {
            GPX gpx = GPX.reader(Version.V10, Mode.STRICT).read(absolutePath);
            final List<WayPoint> points = gpx.tracks()
                    .flatMap(Track::segments)
                    .flatMap(TrackSegment::points)
                    .collect(Collectors.toList());

            //List<Track> tracks = GPX.read(absolutePath).getTracks();

            for (WayPoint point : points) {
                Optional<Length> mElevation = point.getElevation();
                Double elevation = null;
                if (mElevation.isPresent()) {
                    elevation = mElevation.get().doubleValue();
                }
                path.add(new Point(point.getLatitude().doubleValue(),
                        point.getLongitude().doubleValue(),
                        elevation));
            }

        } catch (IOException e) {
            throw new GPXParseException();
        }
        return new Route(filename, path, new Date());
    }
}
