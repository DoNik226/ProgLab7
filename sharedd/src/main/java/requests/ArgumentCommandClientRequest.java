package requests;

import commandLogic.CommandDescription;
import requests.requestAnnotations.Authorize;

@Authorize
public class ArgumentCommandClientRequest<T> extends CommandClientRequest {
    private final T argument;

    public ArgumentCommandClientRequest(CommandDescription command, String[] lineArgs, T argument) {
        super(command, lineArgs);
        this.argument = argument;
    }

    public T getArgument() {
        return argument;
    }
}