package me.anisimoff.editor.Model;

import me.anisimoff.editor.Point;
import me.anisimoff.editor.Route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class State  implements Serializable {
    private enum RouteState  implements Serializable {NONE, NEW, MODIFIED, NOT_MODIFIED}

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

    public Route getRoute() {
        return route;
    }

    public boolean isNone(){
        return state == RouteState.NONE;
    }

    public boolean isModified() {
        return state == RouteState.MODIFIED;
    }

    public void setNotModified() {
        state = RouteState.NOT_MODIFIED;
    }

}
