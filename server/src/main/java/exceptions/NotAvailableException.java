package exceptions;

import requestLogic.CallerBack;

public class NotAvailableException extends Exception {

    private final CallerBack deniedClient;

    public NotAvailableException(CallerBack deniedClient) {
        this.deniedClient = deniedClient;
        System.out.println("Denied connection: " + deniedClient);
    }

    public CallerBack getDeniedClient() {
        return deniedClient;
    }
}
