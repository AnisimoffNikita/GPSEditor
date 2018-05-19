package me.anisimoff.editor;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public Point() {
        longitude = 0.0;
        latitude = 0.0;
        altitude = null;
    }

    public Point(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = null;
    }

    public Point(Double latitude, Double longitude, Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Double distance(Point point) {

        final int R = 6371;

        double latDistance = Math.toRadians(point.latitude - latitude);
        double lonDistance = Math.toRadians(point.longitude - longitude);
        double eleDistance = 0;
        if (altitude != null && point.altitude != null)
            eleDistance = Math.toRadians(point.altitude - altitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        return Math.sqrt(Math.pow(distance, 2) + Math.pow(eleDistance,2));
    }

    @Override
    public boolean equals(Object o) {
        final double eps = 1e-6;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Math.abs(this.longitude - longitude) < eps &&
                Math.abs(this.latitude - latitude) < eps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
