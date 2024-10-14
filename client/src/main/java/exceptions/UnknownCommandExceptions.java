package exceptions;

/**
 * Provides a simple exception, indicates that command isn't found in manager
 */
public class UnknownCommandExceptions extends Exception {
    /**
     * Constructor with message.
     *
     * @param message Message to show
     */
    public UnknownCommandExceptions(String message)
    {
        super(message);
    }
}
