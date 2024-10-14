package exceptions;

public class CannotProceedException extends Throwable {
    public CannotProceedException(Exception e) {
        super(e);
    }
}
