package requestLogic.requestSenders;

import exceptions.GotAnErrorResponseException;
import exceptions.ProceedException;
import requests.BaseRequest;
import responses.AuthorizeResponse;
import serverLogic.ServerConnectionHandler;

import java.io.IOException;

public class LoginRequestSender {

    // костылииииии.............
    public AuthorizeResponse sendLoginRequest(BaseRequest request) {
        AuthorizeResponse response = null;
        try {
            response = (AuthorizeResponse) new RequestSender().sendRequest(request, ServerConnectionHandler.getCurrentConnection());
        } catch (IOException e) {
            System.out.println("Something went wrong during I/O " + e);
        } catch (GotAnErrorResponseException e) {
            System.out.println("Received error from a server! " + e.getErrorResponse().getMsg());
        } catch (ProceedException e) {
            System.out.println("We've lost some packets during getting response: " + e.getMessage());
        }
        return response;
    }
}
