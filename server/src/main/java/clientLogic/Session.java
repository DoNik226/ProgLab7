package clientLogic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Session implements ActionListener {
    public static final int TIMEOUT = 600000;
    private static long nextSessionId = 0L;
    private final long sessionId;
    private final AuthorizedCallerBack callerBack;
    private final javax.swing.Timer timer;

    public Session(AuthorizedCallerBack callerBack) {
        sessionId = nextSessionId++;
        this.callerBack = callerBack;
        timer = new Timer(TIMEOUT, this);
    }

    public long getSessionId() {
        return sessionId;
    }

    public AuthorizedCallerBack getAuthorizedCallerBack() {
        return callerBack;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SessionHandler.getInstance().removeSession(this);
    }

    public void updateTimer() {
        this.timer.restart();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId == session.sessionId && Objects.equals(callerBack, session.callerBack) && Objects.equals(timer, session.timer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, callerBack, timer);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", callerBack=" + callerBack +
                ", timer=" + timer +
                '}';
    }
}
