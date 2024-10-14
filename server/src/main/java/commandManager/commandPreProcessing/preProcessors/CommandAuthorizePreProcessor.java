package commandManager.commandPreProcessing.preProcessors;

import clientLogic.AuthorizedCallerBack;
import commandManager.commands.AuthorizableCommand;
import commandManager.commands.Cmd;
import requestLogic.CallerBack;
import serverLogic.ServerConnections;

public class CommandAuthorizePreProcessor implements CommandPreProcessor {

    @Override
    public void proceed(Cmd command, CallerBack callerBack, ServerConnections connection) {
        AuthorizableCommand commandToProceed = (AuthorizableCommand) command;
        AuthorizedCallerBack authorizedCallerBack = (AuthorizedCallerBack) callerBack;
        commandToProceed.setCallerID(authorizedCallerBack.getUserData().userID());
    }
}
