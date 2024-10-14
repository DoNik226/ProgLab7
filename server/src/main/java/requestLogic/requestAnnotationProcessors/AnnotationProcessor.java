package requestLogic.requestAnnotationProcessors;

import exceptions.CannotProceedException;
import requestLogic.requestAnnotationProcessors.processors.AuthorizeProcessor;
import requestLogic.requestAnnotationProcessors.processors.RequestAnnotationProcessor;
import requestLogic.requests.ServerRequest;
import requests.requestAnnotations.Authorize;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

public class AnnotationProcessor {

    private static final LinkedHashMap<Class<? extends Annotation>, RequestAnnotationProcessor> loadedAnnotations;

    static {
        loadedAnnotations = new LinkedHashMap<>();
        loadedAnnotations.put(Authorize.class, new AuthorizeProcessor());
    }

    private final ServerRequest requestToProceed;

    public AnnotationProcessor(ServerRequest requestToProceed) {
        this.requestToProceed = requestToProceed;
    }

    public ServerRequest proceedAnnotations() throws CannotProceedException {
        ServerRequest result = requestToProceed;
        for (Class<? extends Annotation> annotation : loadedAnnotations.keySet()) {
            if (requestToProceed.getUserRequest().getClass().isAnnotationPresent(annotation))
                result = loadedAnnotations.get(annotation).proceedRequest(requestToProceed);
        }
        return result;
    }
}
