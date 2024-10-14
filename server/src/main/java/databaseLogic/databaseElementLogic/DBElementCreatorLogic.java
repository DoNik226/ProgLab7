package databaseLogic.databaseElementLogic;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBElementCreatorLogic implements Closeable {
    private final Connection connection;

    protected DBElementCreatorLogic() throws SQLException, IOException {
        Properties info = new Properties();
        info.load(this.getClass().getResourceAsStream("/db.cfg"));
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs", info);
    }

    protected void addCreatorToDB(long routeID, long creatorID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO route_creator(" +
                "route_id, user_id) VALUES (?, ?)");
        statement.setLong(1, routeID);
        statement.setLong(2, creatorID);
        statement.executeUpdate();
        statement.close();
    }

    protected boolean checkNonAccessory(long creatorID, long routeID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM route_creator " +
                        "WHERE route_id = ?");
        statement.setLong(1, routeID);
        ResultSet rs = statement.executeQuery();
        return !rs.next() || rs.getLong(2) != creatorID;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
