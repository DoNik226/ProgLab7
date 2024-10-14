package exceptions.authorizationExceptions;

public class RegistrationFailedException extends AuthorizeException {
    public RegistrationFailedException(String msg) {
        super(msg);
    }
}
