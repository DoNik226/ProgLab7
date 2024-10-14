package commandManager.externalReceivers;

import commandLogic.CommandDescription;
import commandLogic.commandReceiverLogic.receivers.ExternalArgumentReceiver;
import exceptions.BuildObjectException;
import models.LabWork;
import models.handlers.ModuleHandler;
import requestLogic.requestSenders.ArgumentRequestSender;
import responses.CommandStatusResponse;
import serverLogic.ServerConnectionHandler;

public class ArgumentLabWorkCommandReceiver implements ExternalArgumentReceiver<LabWork> {

    ModuleHandler<LabWork> handler;
    LabWork labWork;

    {
        labWork = new LabWork();
    }

    public ArgumentLabWorkCommandReceiver(ModuleHandler<LabWork> handler) {
        this.handler = handler;
    }

    @Override
    public boolean receiveCommand(CommandDescription command, String[] args) throws BuildObjectException {
        labWork = handler.buildObject();
        CommandStatusResponse response = new ArgumentRequestSender<LabWork>().sendCommand(command, args, labWork, ServerConnectionHandler.getCurrentConnection());
        if (response != null) {
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response: \n" + response.getResponse());
            return true;
        }
        return false;
    }

    @Override
    public LabWork getArguemnt() {
        return labWork;
    }
}
