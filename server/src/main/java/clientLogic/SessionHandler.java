package clientLogic;

import exceptions.authorizationExceptions.UnauthorizedException;
import requestLogic.CallerBack;

import java.util.HashSet;

public class SessionHandler {

    private static final SessionHandler instance;

    static {
        instance = new SessionHandler();
    }

    private final HashSet<Session> sessions;

    {
        sessions = new HashSet<>();
    }

    private SessionHandler() {
    }

    public static SessionHandler getInstance() {
        return instance;
    }

    public Session getSession(CallerBack callerBack) throws UnauthorizedException {
        Session session = sessions.stream().filter(x -> x.getAuthorizedCallerBack().getCallerBack().equals(callerBack)).findAny()
                .orElseThrow(() -> new UnauthorizedException("Client is unauthorized"));
        session.updateTimer();
        return session;
    }

    public void registerSession(AuthorizedCallerBack callerBack) {
        sessions.add(new Session(callerBack));
    }

    public void removeSession(long callerBackID) {
        sessions.remove(sessions.stream()
                .filter(x -> x.getAuthorizedCallerBack().getUserData().userID() == callerBackID)
                .findAny()
                .orElse(null));
    }

    protected void removeSession(Session session) {
        sessions.remove(session);
    }

    public HashSet<Session> getAllSessions() {
        return sessions;
    }
}
