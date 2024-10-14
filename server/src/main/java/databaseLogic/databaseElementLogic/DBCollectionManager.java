package databaseLogic.databaseElementLogic;

import exceptions.ElementNotAddedException;
import models.Coordinates;
import models.Difficulty;
import models.Discipline;
import models.LabWork;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBCollectionManager implements Closeable {
    private final Connection connection;

    protected DBCollectionManager() throws SQLException, IOException {
        Properties info = new Properties();
        info.load(this.getClass().getResourceAsStream("/db.cfg"));
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs", info);
    }

    protected boolean updateElementInDataBase(LabWork labWork, Long id) throws SQLException {
        try {
            Coordinates coordinates = labWork.getCoordinates();
            PreparedStatement updateCoordStatement = connection.prepareStatement("UPDATE Coordinates AS c " +
                    "SET x = ?, " +
                    "    y = ? " +
                    "FROM LabWork AS l " +
                    "WHERE l.labwork_id = ? AND l.coordinates_id = c.coord_id;");
            updateCoordStatement.setInt(1, coordinates.getX());
            updateCoordStatement.setDouble(2, coordinates.getY());
            updateCoordStatement.setLong(3, id);
            updateCoordStatement.close();
            Discipline discipline = LabWork.getDiscipline();
            Statement getDisId = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            ResultSet lastDisIdRs = getDisId.executeQuery(
                    "SELECT * FROM labwork " +
                            "WHERE labwork_id = " + id);
            updateNullableDiscipline(discipline, lastDisIdRs, 5);
            lastDisIdRs.close();
            getDisId.close();
            PreparedStatement updateLabWorkStatement = connection.prepareStatement(
                    "UPDATE LabWork " +
                            "SET name = ?, " +
                            "    creation_date = ?, " +
                            "    minimal_point = ?, " +
                            "    tuned_in_works = ?, " +
                            "    average_point = ?, " +
                            "    difficulty = ? " +
                            "WHERE labwork_id = ?;");
            updateLabWorkStatement.setString(1, labWork.getName());
            updateLabWorkStatement.setTimestamp(2, Timestamp.from(labWork.getCreationDate().toInstant()));
            updateLabWorkStatement.setInt(3, labWork.getMinimalPoint());
            updateLabWorkStatement.setLong(4, labWork.getTunedInWorks());
            updateLabWorkStatement.setLong(5, labWork.getAveragePoint());
            updateLabWorkStatement.setObject(6, labWork.getDifficulty());
            updateLabWorkStatement.setLong(7, id);
            updateLabWorkStatement.execute();
            updateLabWorkStatement.close();
            return true;
        } catch (ElementNotAddedException e) {
            System.out.println("Something went wrong during fetching ID: " + e);
        }
        return false;
    }

    protected void updateNullableDiscipline(Discipline discipline, ResultSet lastDisciplineIdRs, int columnIndex) throws ElementNotAddedException, SQLException {
        Long disciplineId = getIdFromStatement(lastDisciplineIdRs, columnIndex);
        if (disciplineId == null) {
            Long newId = addDiscipline(discipline);
            if (newId != null) {
                lastDisciplineIdRs.updateLong(columnIndex, newId);
                lastDisciplineIdRs.updateRow();
            }
        } else updateNonNullDiscipline(discipline, disciplineId);
    }

    private void updateNonNullDiscipline(Discipline discipline, long disciplineId) throws SQLException {
        PreparedStatement updateDisciplineStatement = connection.prepareStatement(
                "UPDATE Discipline AS d " +
                        "SET discipline_name = ?," +
                        "    discipline_practice_hours = ?," +
                        "    discipline_self_study_hours = ?," +
                        "    discipline_labs_count = ? " +
                        "WHERE d.discipline_id = ?;");
        updateDisciplineStatement.setString(1, discipline.getName());
        updateDisciplineStatement.setLong(2, discipline.getPracticeHours());
        updateDisciplineStatement.setLong(3, discipline.getSelfStudyHours());
        updateDisciplineStatement.setInt(4, discipline.getLabsCount());
        updateDisciplineStatement.setLong(5, disciplineId);
        updateDisciplineStatement.executeUpdate();
        updateDisciplineStatement.close();
    }

    protected boolean addElementToDataBase(LabWork labWork) throws SQLException {
        try {
            Long coordId = addCoordinates(labWork.getCoordinates());
            Long disciplineId = addDiscipline(labWork.getDiscipline());
            PreparedStatement labWorkAddStatement = connection.prepareStatement("INSERT INTO labwork(labwork_id, name, coordinates_id, creation_date, minimal_point, tuned_in_works, average_point, difficulty, discipline_id) " +
                    "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            labWorkAddStatement.setString(1, labWork.getName());
            labWorkAddStatement.setObject(2, coordId, Types.BIGINT);
            labWorkAddStatement.setObject(3, Timestamp.from(labWork.getCreationDate().toInstant()), Types.TIMESTAMP);
            labWorkAddStatement.setObject(4, labWork.getMinimalPoint());
            labWorkAddStatement.setObject(5, labWork.getTunedInWorks());
            labWorkAddStatement.setObject(6, labWork.getAveragePoint());
            labWorkAddStatement.setObject(7, labWork.getDifficulty());
            labWorkAddStatement.setObject(8, disciplineId, Types.BIGINT);
            labWorkAddStatement.execute();
            labWorkAddStatement.close();

            // get id
            Statement getLastIdStatement = connection.createStatement();
            ResultSet rs = getLastIdStatement.executeQuery("SELECT currval('labwork_labwork_id_seq');");
            labWork.setId(getIdFromStatement(rs, 1));

            return true;
        } catch (ElementNotAddedException e) {
            System.out.println("We couldn't push element to a database");
        }
        return false;
    }

    private Long addCoordinates(Coordinates coordinates) throws SQLException, ElementNotAddedException {
        if (coordinates == null) return null;
        // add coords
        PreparedStatement preparedStatementCoordinates = connection.prepareStatement("INSERT INTO coordinates(coord_id, x, y) " +
                "VALUES(DEFAULT, ?, ?)");
        preparedStatementCoordinates.setInt(1, coordinates.getX());
        preparedStatementCoordinates.setDouble(2, coordinates.getY());
        preparedStatementCoordinates.execute();
        preparedStatementCoordinates.close();
        // get coordid
        Statement getLastIdStatement = connection.createStatement();
        ResultSet rs = getLastIdStatement.executeQuery("SELECT currval('coordinates_coord_id_seq');");
        var coordId = getIdFromStatement(rs, 1);
        System.out.println("gen coordid: " + coordId);
        return coordId;
    }

    private Long addDiscipline(Discipline discipline) throws SQLException, ElementNotAddedException {
        if (discipline == null) return null;
        // add coords
        PreparedStatement preparedStatementDiscipline = connection.prepareStatement("INSERT INTO location(discipline_id, discipline_name, discipline_practice_hours, discipline_self_study_hours, discipline_labs_count) " +
                "VALUES(DEFAULT, ?, ?, ?, ?)");
        preparedStatementDiscipline.setString(1, discipline.getName());
        preparedStatementDiscipline.setLong(2, discipline.getPracticeHours());
        preparedStatementDiscipline.setLong(3, discipline.getSelfStudyHours());
        preparedStatementDiscipline.setInt(4, discipline.getLabsCount());
        preparedStatementDiscipline.execute();
        preparedStatementDiscipline.close();
        // get disID
        Statement getLastIdStatement = connection.createStatement();
        ResultSet rs = getLastIdStatement.executeQuery("SELECT currval('discipline_discipline_id_seq1');");
        var disciplineID = getIdFromStatement(rs, 1);
        System.out.println("gen locID: " + disciplineID);
        return disciplineID;
    }

    private Long getIdFromStatement(ResultSet statementResult, int columnIndex) throws SQLException, ElementNotAddedException {
        long result;
        if (statementResult.next()) {
            result = statementResult.getLong(columnIndex);
            if (statementResult.wasNull()) {
                return null;
            }
        } else {
            throw new ElementNotAddedException();
        }
        return result;
    }

    protected boolean removeElementFromDatabase(Long id) throws SQLException {
        Statement getLabWorkToRemove = connection.createStatement();
        ResultSet rs = getLabWorkToRemove.executeQuery("SELECT * FROM labwork " +
                "WHERE labwork_id = " + id);
        if (rs.next()) {
            long coordId = rs.getLong("coordinates_id");
            long disciplineId = rs.getLong("discipline_id");

            Statement removeLabWorkStatement = connection.createStatement();
            removeLabWorkStatement.executeUpdate("DELETE FROM labwork " +
                    "WHERE labwork_id = " + id);
            removeLabWorkStatement.close();

            Statement removeCoordStatement = connection.createStatement();
            removeCoordStatement.executeUpdate("DELETE FROM coordinates " +
                    "WHERE coord_id = " + coordId);
            removeCoordStatement.close();

            Statement removeDisciplineStatement = connection.createStatement();
            removeDisciplineStatement.executeUpdate("DELETE FROM discipline " +
                    "WHERE discipline_id = " + disciplineId);
            removeDisciplineStatement.close();
            return true;
        } else return false;
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
