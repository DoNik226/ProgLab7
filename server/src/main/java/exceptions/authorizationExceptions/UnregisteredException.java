package exceptions.authorizationExceptions;

public class UnregisteredException extends AuthorizeException {
    public UnregisteredException(String message) {
        super(message);
    }
}
