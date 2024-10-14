package requestLogic.requestAnnotationProcessors.processors;

import clientLogic.AuthorizeManager;
import clientLogic.AuthorizedCallerBack;
import exceptions.CannotProceedException;
import exceptions.authorizationExceptions.UnauthorizedException;
import requestLogic.requests.ServerRequest;

public class AuthorizeProcessor implements RequestAnnotationProcessor {
    @Override
    public ServerRequest proceedRequest(ServerRequest requestToProceed) throws CannotProceedException {
        try {
            AuthorizedCallerBack authorizedCallerBack = AuthorizeManager.login(requestToProceed.getFrom());
            System.out.println("Successfully authorized " + authorizedCallerBack.getUserData().login());
            return new ServerRequest(requestToProceed.getUserRequest(),
                    authorizedCallerBack,
                    requestToProceed.getConnection());
        } catch (UnauthorizedException e) {
            throw new CannotProceedException(e);
        }
    }
}
