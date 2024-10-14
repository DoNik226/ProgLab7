package exceptions.authorizationExceptions;

public class UnauthorizedException extends AuthorizeException {

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
