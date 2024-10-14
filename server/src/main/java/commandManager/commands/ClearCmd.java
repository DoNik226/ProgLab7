package commandManager.commands;

import databaseLogic.databaseElementLogic.DBIntegrationUtility;
import responses.CommandStatusResponse;

/**
 * Clears collection
 *
 * @author Nikita
 * @since 1.0
 */
public class ClearCmd implements Cmd, AuthorizableCommand {
    private CommandStatusResponse response;

    private long callerID;

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescr() {
        return "Clears the collection. N/B: you may only remove elements belongs to you";
    }

    @Override
    public void execute(String[] args) {
        response = DBIntegrationUtility.getInstance().clearCollectionInDBAndMemory(callerID).toCommandResponse();
        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    @Override
    public void setCallerID(long id) {
        this.callerID = id;
    }
}
