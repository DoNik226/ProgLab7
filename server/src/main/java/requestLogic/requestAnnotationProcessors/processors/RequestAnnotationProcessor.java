package requestLogic.requestAnnotationProcessors.processors;

import exceptions.CannotProceedException;
import requestLogic.requests.ServerRequest;

public interface RequestAnnotationProcessor {
    ServerRequest proceedRequest(ServerRequest requestToProceed) throws CannotProceedException;
}
