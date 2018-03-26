package me.anisimoff.editor;

public class Point {
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

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double distance(Point point) {
        return Math.sqrt(Math.pow(this.latitude - point.latitude, 2) +
                         Math.pow(this.longitude - point.longitude, 2));
    }
}
