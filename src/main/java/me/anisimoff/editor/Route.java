package me.anisimoff.editor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Route {
    private String name;
    private ArrayList<Point> path;
    private Date date;

    final static public String UNTITLED = "untitled";

    public Route() {
        this.name = UNTITLED;
        this.path = new ArrayList<>();
        this.date = new Date();
    }

    public Route(String name, List<Point> path) {
        this.name = name;
        this.path = new ArrayList<>(path);
        this.date = new Date();
    }

    public Route(String name, List<Point> path, Date date) {
        this.name = name;
        this.path = new ArrayList<>(path);
        this.date = date;
    }


    public Double getLength() {
        double result = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            result += path.get(i).distance(path.get(i + 1));
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Point> getPath() {
        return path;
    }

    public void setPath(ArrayList<Point> path) {
        this.path = path;
    }

    public void insertAfter(int index, Point point) {
        path.add(index + 1, point);
    }

    public void edit(int index, Point point) {path.set(index, point);}

    public void remove(int index) {
        path.remove(index);
    }
}
