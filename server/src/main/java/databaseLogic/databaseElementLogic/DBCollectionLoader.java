package databaseLogic.databaseElementLogic;

import models.Difficulty;
import models.Coordinates;
import models.Discipline;
import models.LabWork;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.Properties;

public class DBCollectionLoader<T extends Collection<LabWork>> implements Closeable {
    private final T collectionToWrite;
    private final Connection connection;

    public DBCollectionLoader(T collectionToWrite) throws SQLException, IOException {
        this.collectionToWrite = collectionToWrite;
        Properties info = new Properties();
        info.load(this.getClass().getResourceAsStream("/db.cfg"));
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs", info);
    }

    public void loadFromDB() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT labwork.labwork_id, labwork.name AS name, coords.x AS coord_x, coords.y AS coord_y, labwork.creation_date, minimalpoint AS minimal_point, tunedinworks AS tuned_in_works, averagepoint AS average_point," +
                        " labwork.difficulty AS difficulty, discipline.name AS discipline_name, discipline.practicehours AS discipline_practice_hours, discipline.selfstudyhours AS discipline_self_study_hours, discipline.labscount AS discipline_labs_count, coordinates_id, discipline_id " +
                        "FROM LabWork " +
                        "LEFT JOIN Coordinates coords ON coordinates_id = coord_id " +
                        "LEFT JOIN Discipline discipline ON discipline_id = discipline.discipline_id ");
        loadFromDB(rs);
        statement.close();
    }

    protected void loadFromDB(long callerID) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT labwork.labwork_id, labwork.name AS name, coords.x AS coord_x, coords.y AS coord_y, labwork.creation_date, minimalpoint AS minimal_point, tunedinworks AS tuned_in_works, averagepoint AS average_point," +
                        " labwork.difficulty AS difficulty, discipline.name AS discipline_name, discipline.practicehours AS discipline_practice_hours, discipline.selfstudyhours AS discipline_self_study_hours, discipline.labscount AS discipline_labs_count, coordinates_id, discipline_id " +
                        "FROM LabWork " +
                        "LEFT JOIN Coordinates coords ON coordinates_id = coord_id " +
                        "LEFT JOIN Discipline discipline ON discipline_id = discipline.discipline_id "+
                        "LEFT JOIN labwork_creator rc on labwork.labwork_id = rc.labwork_id " +
                        "WHERE user_id = " + callerID);
        loadFromDB(rs);
        statement.close();
    }

    private void loadFromDB(ResultSet rs) throws SQLException {
        while (rs.next()) {
            LabWork toAdd = new LabWork();
            toAdd.setId(rs.getLong("labwork_id"));
            toAdd.setName(rs.getString("name"));
            rs.getLong("coordinates_id");
            if (!rs.wasNull()) {
                Coordinates toAddCoords = new Coordinates();
                toAddCoords.setX(rs.getInt("coord_x"));
                toAddCoords.setY(rs.getDouble("coord_y"));
                toAdd.setCoordinates(toAddCoords);
                toAdd.setCreationDate(rs.getDate("creation_date"));
            }
            toAdd.setMinimalPoint(rs.getInt("minimal_point"));
            toAdd.setTunedInWorks(rs.getLong("tuned_in_works"));
            toAdd.setAveragePoint(rs.getLong("average_point"));
            toAdd.setDifficulty(Difficulty.valueOf(rs.getString("difficulty")));
            rs.getLong("discipline_id");
            if (!rs.wasNull()) {
                Discipline toAddDiscipline = new Discipline();
                toAddDiscipline.setName(rs.getString("discipline_name"));
                toAddDiscipline.setPracticeHours(rs.getLong("discipline_practice_hours"));
                toAddDiscipline.setSelfStudyHours(rs.getLong("discipline_self_study_hours"));
                toAddDiscipline.setLabsCount(rs.getInt("discipline_labs_count"));
                toAdd.setDiscipline(toAddDiscipline);
            }
            collectionToWrite.add(toAdd);
        }
        rs.close();
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
