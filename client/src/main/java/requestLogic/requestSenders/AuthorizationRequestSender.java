package requestLogic.requestSenders;

import authorization.authCredentials.AuthenticationData;
import requests.AuthorizationRequest;
import responses.AuthorizeResponse;

public class AuthorizationRequestSender {

    public AuthorizeResponse sendLoginRequest(AuthenticationData data) {
        AuthorizationRequest request = new AuthorizationRequest(data);
        return new LoginRequestSender().sendLoginRequest(request);
    }
}
