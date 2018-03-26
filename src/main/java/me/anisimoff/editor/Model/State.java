package me.anisimoff.editor.Model;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class State {
    private enum RouteState {NONE, NEW, MODIFIED, NOT_MODIFIED}

    private Route route;
    private RouteState state;

    public State() {
        this.route = null;
        this.state = RouteState.NONE;
    }

    private State(Route route, RouteState state) {
        this.state = state;
        this.route = route;
    }


    public static State NoneRoute() {
        return new State(null, RouteState.NONE);
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

    public Route getRoute() {
        return route;
    }

    public boolean isNone(){
        return state == RouteState.NONE;
    }

    public boolean isModified() {
        return state == RouteState.MODIFIED;
    }

    public boolean isNotModified() {
        return state == RouteState.NOT_MODIFIED;
    }

    public void setNone(){
        state = RouteState.NONE;
    }


    public void setModified() {
        state = RouteState.MODIFIED;
    }

    public void setNotModified() {
        state = RouteState.NOT_MODIFIED;
    }
}
