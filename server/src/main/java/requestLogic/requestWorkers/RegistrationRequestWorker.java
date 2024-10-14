package requestLogic.requestWorkers;

import authorization.AuthorizedUserData;
import clientLogic.AuthorizeManager;
import exceptions.authorizationExceptions.AuthorizeException;
import requestLogic.requests.ServerRequest;
import requests.RegistrationRequest;
import responseLogic.responseSenders.ResponseSender;
import responses.AuthorizeResponse;
import responses.BaseResponse;
import responses.ErrorResponse;

public class RegistrationRequestWorker implements RequestWorker {
    @Override
    public void workWithRequest(ServerRequest request) {
        RegistrationRequest requestToWork = (RegistrationRequest) request.getUserRequest();
        BaseResponse response = null;
        try {
            AuthorizedUserData userData = AuthorizeManager.register(request.getFrom(), requestToWork.getRegData());
            response = new AuthorizeResponse(userData);
        } catch (AuthorizeException e) {
            response = new ErrorResponse(e.getMessage());
        } finally {
            ResponseSender.sendResponse(response, request.getConnection(), request.getFrom());
        }
    }
}
