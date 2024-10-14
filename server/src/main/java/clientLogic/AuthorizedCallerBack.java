package clientLogic;

import authorization.AuthorizedUserData;
import requestLogic.CallerBack;

import java.util.Objects;

public class AuthorizedCallerBack extends CallerBack {
    private final AuthorizedUserData userData;

    private final CallerBack callerBack;

    public AuthorizedCallerBack(AuthorizedUserData userData, CallerBack callerBack) {
        super(callerBack.getAddress(), callerBack.getPort());
        this.userData = userData;
        this.callerBack = callerBack;
    }

    public CallerBack getCallerBack() {
        return callerBack;
    }

    public AuthorizedUserData getUserData() {
        return userData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthorizedCallerBack that = (AuthorizedCallerBack) o;
        return Objects.equals(userData, that.userData) && Objects.equals(callerBack, that.callerBack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userData, callerBack);
    }

    @Override
    public String toString() {
        return "AuthorizedCallerBack{" +
                "userData=" + userData +
                ", callerBack=" + callerBack +
                '}';
    }
}
