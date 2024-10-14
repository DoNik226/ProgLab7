package commandManager.externalReceivers;

import commandLogic.CommandDescription;
import commandLogic.commandReceiverLogic.receivers.ExternalBaseReceiver;
import requestLogic.requestSenders.CommandRequestSender;
import responses.CommandStatusResponse;
import serverLogic.ServerConnectionHandler;

public class NonArgumentReceiver implements ExternalBaseReceiver {

    @Override
    public boolean receiveCommand(CommandDescription command, String[] args) {
        CommandStatusResponse response = new CommandRequestSender().sendCommand(command, args, ServerConnectionHandler.getCurrentConnection());
        if (response != null) {
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response: \n" + response.getResponse());
            return true;
        }
        return false;
    }
}
