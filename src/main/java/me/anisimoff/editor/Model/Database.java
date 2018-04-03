package me.anisimoff.editor.Model;

import me.anisimoff.editor.Constants;
import me.anisimoff.editor.Route;
import me.anisimoff.editor.Utils.PolylineEncoder;
import me.anisimoff.editor.Utils.PolylineEncoderException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Database {

    private Connection conn;

    private final static String ID_COLUMN = "id";
    private final static String NAME_COLUMN = "name";
    private final static String POLYLINE_COLUMN = "polyline";
    private final static String DATE_COLUMN = "creationDate";
    private final static String DB_NAME = "routes.db";


    public Database() {
        connect();
        createTableIfNotExist();
    }

    public int getUntitledNextIndex() {
        String sql = "select name from routes;";
        int index = -1;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            String pattern = String.format("%s(\\(\\d+\\))?", Constants.UNTITLED);

            while (rs.next()) {
                String name = rs.getString(NAME_COLUMN);
                if (name.matches(pattern)) {
                    if (name.indexOf("(") > 0) {
                        String strIndex = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
                        int current = Integer.valueOf(strIndex);
                        if (current > index) {
                            index = current;
                        }
                    } else {
                        index = 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return index + 1;
    }

    public int insertRoute(Route route) {

        String sql = "insert into routes(name,polyline,creationDate) values(?,?,?)";
        int result;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, route.getName());
            pstmt.setString(2, PolylineEncoder.encode(route.getPath()));
            pstmt.setLong(3, route.getDate().getTime());
            pstmt.executeUpdate();
            result = lastInsertedId();
        } catch (SQLException e) {
            result = -1;
        }

        return result;
    }

    public boolean updateRoute(Route route) {

        String sql = "replace into routes(id,name,polyline,creationDate) values(?,?,?,?)";
        boolean result = true;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, route.getId());
            pstmt.setString(2, route.getName());
            pstmt.setString(3, PolylineEncoder.encode(route.getPath()));
            pstmt.setLong(4, route.getDate().getTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            result = false;
        }
        return result;
    }

    public Route loadRouteByID(int id) {
        Route route = null;
        String sql = "SELECT id, name, polyline, creationDate "
                + "FROM routes WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                route = new Route(
                        rs.getInt(ID_COLUMN),
                        rs.getString(NAME_COLUMN),
                        PolylineEncoder.decode(rs.getString(POLYLINE_COLUMN)),
                        new Date(rs.getLong(DATE_COLUMN)));
            }

        } catch (SQLException | PolylineEncoderException e) {
            route = null;
        }

        return route;
    }

    public boolean removeRoute(int id) {
        String sql = "DELETE FROM routes WHERE id = ?";

        boolean result = true;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            result = false;
        }

        return result;
    }

    public List<Route> loadAllRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        String sql = "SELECT id, name, polyline, creationDate FROM routes;";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                routes.add(new Route(
                        rs.getInt(ID_COLUMN),
                        rs.getString(NAME_COLUMN),
                        PolylineEncoder.decode(rs.getString(POLYLINE_COLUMN)),
                        new Date(rs.getLong(DATE_COLUMN))));
            }

        } catch (SQLException | PolylineEncoderException ignored) {
        }

        return routes;
    }

    private int lastInsertedId () {
        String sql = "select last_insert_rowid();";
        int index = -1;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                index = rs.getInt(1);
            }
        } catch (SQLException e) {
        }

        return index;
    }

    private void connect() {
        String url = String.format("jdbc:sqlite:%s%s",
                Constants.configPath,
                DB_NAME);
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.exit(Constants.CANNOT_CREATE_DATABASE);
        }
    }

    private void createTableIfNotExist() {
        String sql = "create table if not exists routes (\n"
                + "	id integer primary key autoincrement not null,\n"
                + "	name text not null,\n"
                + "	polyline text not null,\n"
                + "	creationDate integer not null\n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.exit(Constants.CANNOT_CREATE_DATABASE);
        }
    }
}
