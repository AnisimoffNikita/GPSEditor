package me.anisimoff.editor.Utils;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.Length;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class GPXReader {
    static public Route parse(File file) throws GPXParseException {
        String absolutePath = file.getAbsolutePath();
        String filename = file.getName();
        ArrayList<Point> path = new ArrayList<>();
        try {
            List<Track> tracks = GPX.read(absolutePath).getTracks();
            for (Track track : tracks) {
                for (TrackSegment segment : track.getSegments()) {
                    for (io.jenetics.jpx.Point gpxPoint : segment.getPoints()) {
                        Optional<Length> mElevation = gpxPoint.getElevation();
                        Double elevation = null;
                        if (mElevation.isPresent()) {
                            elevation = mElevation.get().doubleValue();
                        }
                        path.add(new Point(gpxPoint.getLatitude().doubleValue(),
                                           gpxPoint.getLongitude().doubleValue(),
                                           elevation));
                    }
                }
            }
        } catch (IOException e) {
            throw new GPXParseException();
        }
        return new Route(filename, path, new Date());
    }
}
