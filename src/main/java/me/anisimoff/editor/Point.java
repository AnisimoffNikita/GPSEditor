package me.anisimoff.editor;

import java.io.Serializable;

public class Point implements Serializable {
    private Double latitude;
    private Double longitude;

    public Point() {
        longitude = 0.0;
        latitude = 0.0;
    }

    public Point(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double distance(Point point) {

        final int R = 6371;

        double latDistance = Math.toRadians(point.latitude - latitude);
        double lonDistance = Math.toRadians(point.longitude - point.longitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
