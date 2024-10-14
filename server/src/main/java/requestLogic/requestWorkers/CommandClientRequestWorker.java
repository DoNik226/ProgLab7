package requestLogic.requestWorkers;

import commandManager.CommandManager;
import requestLogic.requests.ServerRequest;
import requests.CommandClientRequest;

public class CommandClientRequestWorker implements RequestWorker {

    @Override
    public void workWithRequest(ServerRequest request) {
        CommandClientRequest requestToWork = (CommandClientRequest) request.getUserRequest();
        CommandManager manager = new CommandManager();
        manager.executeCommand(requestToWork, request.getFrom(), request.getConnection());
    }
}
