package exceptions;

import responses.ErrorResponse;

public class GotAnErrorResponseException extends Exception {

    private final ErrorResponse response;

    public GotAnErrorResponseException(ErrorResponse response) {
        this.response = response;
    }

    public ErrorResponse getErrorResponse() {
        return response;
    }
}
