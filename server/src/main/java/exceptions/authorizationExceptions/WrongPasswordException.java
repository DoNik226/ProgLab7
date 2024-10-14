package exceptions.authorizationExceptions;

public class WrongPasswordException extends AuthorizeException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
