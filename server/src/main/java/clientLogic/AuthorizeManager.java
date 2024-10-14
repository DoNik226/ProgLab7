package clientLogic;

import authorization.AuthorizedUserData;
import authorization.authCredentials.AuthenticationData;
import authorization.authCredentials.RegistrationData;
import databaseLogic.databaseUserLogic.DBUserManager;
import databaseLogic.databaseUserLogic.PasswordEncryptionImplSHA512;
import exceptions.authorizationExceptions.AuthorizeException;
import exceptions.authorizationExceptions.UnauthorizedException;
import requestLogic.CallerBack;

import java.io.IOException;
import java.sql.SQLException;

public class AuthorizeManager {
    public static AuthorizedUserData register(CallerBack requester, RegistrationData regData) throws AuthorizeException {
        AuthorizedUserData userData;
        try (DBUserManager manager = new DBUserManager(new PasswordEncryptionImplSHA512())) {
            if (manager.checkExistence(regData.getLogin()))
                throw new AuthorizeException("User with that login already exists.");
            userData = manager.addUserToDatabase(requester, regData);
            AuthorizedCallerBack callerBack = new AuthorizedCallerBack(userData, requester);
            SessionHandler.getInstance().registerSession(callerBack);
        } catch (SQLException | IOException e) {
            System.out.println(e);
            throw new AuthorizeException("Сервер авторизации недоступен или завершил запрос с ошибкой. " +
                    "Обратитесь к администратору сервера");
        }
        return userData;
    }

    public static AuthorizedUserData authorize(CallerBack requester, AuthenticationData regData) throws AuthorizeException {
        AuthorizedUserData userData;
        try (DBUserManager manager = new DBUserManager(new PasswordEncryptionImplSHA512())) {
            userData = manager.getUserFromDatabase(regData);
            AuthorizedCallerBack callerBack = new AuthorizedCallerBack(userData, requester);
            SessionHandler.getInstance().registerSession(callerBack);
        } catch (SQLException | IOException e) {
            System.out.println(e);
            throw new AuthorizeException("Сервер авторизации недоступен или завершил запрос с ошибкой. " +
                    "Обратитесь к администратору сервера");
        }
        return userData;
    }

    public static AuthorizedCallerBack login(CallerBack requester) throws UnauthorizedException {
        return SessionHandler.getInstance().getSession(requester).getAuthorizedCallerBack();
    }
}
