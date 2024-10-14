package databaseLogic.databaseUserLogic;

import authorization.AuthorizedUserData;
import authorization.authCredentials.AuthenticationData;
import authorization.authCredentials.RegistrationData;
import exceptions.authorizationExceptions.AuthorizeException;
import exceptions.authorizationExceptions.RegistrationFailedException;
import exceptions.authorizationExceptions.UnregisteredException;
import exceptions.authorizationExceptions.WrongPasswordException;
import org.apache.commons.lang3.ArrayUtils;
import requestLogic.CallerBack;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Properties;

public class DBUserManager implements Closeable {
    public static final int MIN_PASSWORD_LENGTH = 8;
    private final PasswordEncryption encryptionAlg;
    private final Connection connection;

    public DBUserManager(PasswordEncryption encryptionAlg) throws IOException, SQLException {
        this.encryptionAlg = encryptionAlg;
        Properties info = new Properties();
        info.load(this.getClass().getResourceAsStream("/db.cfg"));
        connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs", info);
    }

    public AuthorizedUserData addUserToDatabase(CallerBack callerBack, RegistrationData data) throws SQLException, RegistrationFailedException {

        if (data.getPassword().length < MIN_PASSWORD_LENGTH)
            throw new RegistrationFailedException("Password should be more than " + MIN_PASSWORD_LENGTH + " symbols.");

        String login = data.getLogin();
        String name = data.getName();
        char[] passSalt = encryptionAlg.generateSalt();
        String passHash = encryptionAlg.encrypt(ArrayUtils.addAll(PasswordEncryption.getPepper(),
                ArrayUtils.addAll(data.getPassword(), passSalt))); // combining 3 arrays okda...
        String regIP = callerBack.getAddress().toString();
        Timestamp regTime = Timestamp.from(Instant.now());
        Timestamp lastLogin = Timestamp.from(Instant.now());
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"User\"" +
                "(user_id, login, name, pass_hash, pass_salt, reg_ip, reg_time, last_login) " +
                "VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?);");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, passHash);
        preparedStatement.setString(4, new String(passSalt));
        preparedStatement.setString(5, regIP);
        preparedStatement.setTimestamp(6, regTime);
        preparedStatement.setTimestamp(7, lastLogin);

        preparedStatement.execute();
        preparedStatement.close();

        long userID;
        Statement getUserID = connection.createStatement();
        ResultSet rs = getUserID.executeQuery("SELECT currval('usr_id_seq');");
        if (rs.next()) {
            userID = rs.getLong(1);
        } else {
            rs.close();
            getUserID.close();
            throw new RegistrationFailedException("Cannot register user!");
        }
        rs.close();
        getUserID.close();
        return new AuthorizedUserData(userID, name, login, LocalDateTime.ofInstant(lastLogin.toInstant(), ZoneOffset.UTC), regIP, LocalDateTime.ofInstant(regTime.toInstant(), ZoneOffset.UTC));
    }

    public AuthorizedUserData getUserFromDatabase(AuthenticationData authData) throws AuthorizeException, SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"User\" " +
                "WHERE login = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, authData.getLogin());
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            long id = rs.getLong("user_id");
            String login = rs.getString("login");
            String name = rs.getString("name");
            String passHash = rs.getString("pass_hash");
            char[] passSalt = rs.getString("pass_salt").toCharArray();
            String regIp = rs.getString("reg_ip");
            LocalDateTime regTime = LocalDateTime.ofInstant(rs.getTimestamp("reg_time").toInstant(), ZoneOffset.UTC);
            LocalDateTime lastLogin = LocalDateTime.ofInstant(rs.getTimestamp("last_login").toInstant(), ZoneOffset.UTC);

            // check password
            String userPassHash = encryptionAlg.encrypt(ArrayUtils.addAll(PasswordEncryption.getPepper(),
                    ArrayUtils.addAll(authData.getPassword(), passSalt)));
            if (userPassHash.equals(passHash)) {
                AuthorizedUserData userData = new AuthorizedUserData(id, name, login, lastLogin, regIp, regTime);
                rs.updateTimestamp("last_login", Timestamp.from(Instant.now()));
                rs.updateRow();
                rs.close();
                statement.close();
                return userData;
            } else {
                rs.close();
                statement.close();
                throw new WrongPasswordException("Incorrect password!");
            }
        } else {
            throw new UnregisteredException("User cannot be found in database");
        }
    }

    public boolean checkExistence(String login) throws SQLException {
        boolean res = false;
        PreparedStatement statement = connection.prepareStatement(
                "select exists(select 1 from \"User\" where login = ?)");
        statement.setString(1, login);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            res = rs.getBoolean(1);
        }
        return res;
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
