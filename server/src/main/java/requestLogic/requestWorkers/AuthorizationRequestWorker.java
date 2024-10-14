package requestLogic.requestWorkers;

import authorization.AuthorizedUserData;
import clientLogic.AuthorizeManager;
import exceptions.authorizationExceptions.AuthorizeException;
import requestLogic.requests.ServerRequest;
import requests.AuthorizationRequest;
import responseLogic.responseSenders.ResponseSender;
import responses.AuthorizeResponse;
import responses.BaseResponse;
import responses.ErrorResponse;

public class AuthorizationRequestWorker implements RequestWorker {
    @Override
    public void workWithRequest(ServerRequest request) {
        AuthorizationRequest requestToWork = (AuthorizationRequest) request.getUserRequest();
        BaseResponse response = null;
        try {
            AuthorizedUserData authorizedUser = AuthorizeManager.authorize(request.getFrom(), requestToWork.getAuthenticationData());
            response = new AuthorizeResponse(authorizedUser);
        } catch (AuthorizeException e) {
            response = new ErrorResponse(e.getMessage());
        } finally {
            ResponseSender.sendResponse(response, request.getConnection(), request.getFrom());
        }
    }
}
