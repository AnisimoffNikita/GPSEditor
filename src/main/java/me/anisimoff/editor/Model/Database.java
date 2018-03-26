package me.anisimoff.editor.Model;

import me.anisimoff.editor.Constants;
import me.anisimoff.editor.PolylineEncoder;
import me.anisimoff.editor.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Model {

    private Connection conn;

    public Database() {
        connect();
        createTableIfNotExist();

    }

    @Override
    public int getUntitledCount() {
        String sql = "select  name from routes;";
        int index = 0;

        try (Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            String pattern = String.format("%s(\\(\\d+\\))?", Constants.UNTITLED);

            while (rs.next()) {
                String name = rs.getString("name");
                if (name.matches(pattern)) {
                    if (name.indexOf("(") > 0) {
                        String strIndex = name.substring(name.indexOf("(") + 1, name.indexOf(")"));
                        int current = Integer.valueOf(strIndex);
                        if (current > index) {
                            index = current;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return index + 1;
    }

    @Override
    public boolean saveRoute(Route route) {
        String sql = "insert into routes(name,polyline,creationDate) values(?,?,?)";
        boolean result = true;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, route.getName());
            pstmt.setString(2, PolylineEncoder.encode(route.getPath()));
            pstmt.setLong(3, route.getDate().getTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            result = false;
        }
        return result;
    }

    @Override
    public Route loadRouteByName(String name) {
        Route route = null;
        String sql = "SELECT name, polyline, creationDate "
                + "FROM routes WHERE name = ?";

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)){

            pstmt.setString(1, name);

            ResultSet rs  = pstmt.executeQuery();

            if (rs.next()) {
                route = new Route(rs.getString("name"),
                        PolylineEncoder.decode(rs.getString("polyline")),
                        new Date(rs.getLong("creationDate")));
            }

        } catch (SQLException e) {
            route = null;
        }

        return route;
    }

    @Override
    public boolean removeRouteByName(String name) {
        String sql = "DELETE FROM routes WHERE name = ?";

        boolean result = true;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            result = false;
        }
        return result;
    }

    @Override
    public List<Route> loadAllRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        String sql = "SELECT name, polyline, creationDate FROM routes;";

        try (Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                routes.add(new Route(rs.getString("name"),
                        PolylineEncoder.decode(rs.getString("polyline")),
                        new Date(rs.getLong("creationDate"))));
            }

        } catch (SQLException ignored) {
        }

        return routes;
    }

    private void connect() {
        String url = String.format("jdbc:sqlite:%s%s",
                Constants.configPath,
                Constants.DB_NAME);
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.exit(Constants.CANNOT_CREATE_DATABASE);
        }
    }

    private void createTableIfNotExist() {
        String sql = "create table if not exists routes (\n"
                + "	name text primary key not null,\n"
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
