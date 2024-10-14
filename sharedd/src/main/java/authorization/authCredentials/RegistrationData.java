package authorization.authCredentials;

import java.io.Serializable;

public class RegistrationData extends AuthenticationData implements Serializable {

    private final String name;

    public RegistrationData(String name, String login, char[] password) {
        super(login, password);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
