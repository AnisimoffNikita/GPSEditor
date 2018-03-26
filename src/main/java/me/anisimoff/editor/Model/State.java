package me.anisimoff.editor.Model;

import me.anisimoff.editor.Route;

public class State {
    private enum RouteState {NEW, MODIFIED, NOT_MODIFIED}

    private Route route;
    private RouteState state;

    private State(Route route, RouteState state) {
        this.state = state;
        this.route = route;
    }

    public static State NewRoute(Route route) {
        return new State(route, RouteState.NEW);
    }

    public static State ModifiedRoute(Route route) {
        return new State(route, RouteState.MODIFIED);
    }

    public static State NotModifiedRoute(Route route) {
        return new State(route, RouteState.NOT_MODIFIED);
    }

    public RouteState getState() {
        return state;
    }

    public void setState(RouteState state) {
        this.state = state;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public boolean isNew(){
        return state == RouteState.NEW;
    }

    public boolean isModified() {
        return state == RouteState.MODIFIED;
    }

    public boolean isNotModified() {
        return state == RouteState.NOT_MODIFIED;
    }

    public void setNew(){
        state = RouteState.NEW;
    }

    public void setModified() {
        state = RouteState.MODIFIED;
    }

    public void setNotModified() {
        state = RouteState.NOT_MODIFIED;
    }
}
