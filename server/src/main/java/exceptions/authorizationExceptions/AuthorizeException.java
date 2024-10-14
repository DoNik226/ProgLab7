package exceptions.authorizationExceptions;

public class AuthorizeException extends Exception {
    public AuthorizeException(Exception e) {
        super(e);
    }

    public AuthorizeException(String message) {
        super(message);
    }
}
