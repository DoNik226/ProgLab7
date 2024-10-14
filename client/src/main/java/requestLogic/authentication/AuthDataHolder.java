package requestLogic.authentication;

import authorization.authCredentials.AuthenticationData;

public class AuthDataHolder {
    private static final AuthDataHolder holder;

    static {
        holder = new AuthDataHolder();
    }

    private AuthenticationData clientData;

    private AuthDataHolder() {
    }

    public static AuthDataHolder getInstance() {
        return holder;
    }

    public AuthenticationData getClientData() {
        return clientData;
    }

    public void setClientData(String name, char[] password) {
        this.clientData = new AuthenticationData(name, password);
    }
}
