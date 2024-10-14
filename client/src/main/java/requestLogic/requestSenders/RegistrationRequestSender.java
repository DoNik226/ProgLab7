package requestLogic.requestSenders;

import authorization.authCredentials.RegistrationData;
import requests.RegistrationRequest;
import responses.AuthorizeResponse;

public class RegistrationRequestSender {
    public AuthorizeResponse sendRegisterRequest(RegistrationData data) {
        RegistrationRequest request = new RegistrationRequest(data);
        return new LoginRequestSender().sendLoginRequest(request);
    }
}
