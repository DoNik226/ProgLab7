package requestLogic.requestWorkers;

import exceptions.CannotProceedException;
import exceptions.UnsupportedRequestException;
import requestLogic.requestAnnotationProcessors.AnnotationProcessor;
import requestLogic.requests.ServerRequest;
import requests.*;
import responseLogic.responseSenders.ResponseSender;
import responses.ErrorResponse;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RequestWorkerManager {

    private static final Executor handleRqMtExecutor = Executors.newWorkStealingPool();
    private final LinkedHashMap<Class<?>, RequestWorker> workers = new LinkedHashMap<>();

    public RequestWorkerManager() {
        workers.put(BaseRequest.class, new BaseRequestWorker());
        workers.put(CommandClientRequest.class, new CommandClientRequestWorker());
        workers.put(ArgumentCommandClientRequest.class, new ArgumentCommandClientRequestWorker<>());
        workers.put(CommandDescriptionsRequest.class, new CommandConfigureRequestWorker());
        workers.put(AuthorizationRequest.class, new AuthorizationRequestWorker());
        workers.put(RegistrationRequest.class, new RegistrationRequestWorker());
    }

    public void workWithRequest(ServerRequest request) {
        handleRqMtExecutor.execute(() -> {
            var finalRequest = request;
            try {
                finalRequest = new AnnotationProcessor(finalRequest).proceedAnnotations();
                RequestWorker requestWorker = Optional.ofNullable(workers.get(finalRequest.getUserRequest().getClass())).orElseThrow(()
                        -> new UnsupportedRequestException("Указанный запрос не может быть обработан"));
                requestWorker.workWithRequest(finalRequest);
            } catch (UnsupportedRequestException e) {
                ErrorResponse response = new ErrorResponse("Server understood Request class, but didn't know how to handle it.");
                ResponseSender.sendResponse(response, finalRequest.getConnection(), finalRequest.getFrom());
                System.out.println("Got an invalid request. " + e);
            } catch (CannotProceedException e) {
                ErrorResponse response = new ErrorResponse("Request can't be proceed / passed some checks: " + e.getMessage());
                ResponseSender.sendResponse(response, finalRequest.getConnection(), finalRequest.getFrom());
                System.out.println("Request can't be proceed / passed some checks. " + e);
            }
        });
    }
}
