package commandManager.commands;

import databaseLogic.databaseElementLogic.DBIntegrationUtility;
import models.LabWork;
import responses.CommandStatusResponse;

import java.time.Instant;
import java.util.Date;

/**
 * Adds new element to collection.
 *
 * @author Nikita
 * @since 1.0
 */
public class AddCmd implements Cmd, ArgumentConsumer<LabWork>, AuthorizableCommand {
    private CommandStatusResponse response;
    private LabWork obj;
    private long callerID;

    @Override
    public void setObj(LabWork obj) {
        this.obj = obj;
        obj.setCreationDate(Date.from(Instant.now()));
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescr() {
        return "Adds new element to collection. It also attach created element with user " +
                "who created it";
    }

    @Override
    public String getArgs() {
        return "{element}";
    }

    @Override
    public void execute(String[] args) {
        response = DBIntegrationUtility.getInstance().addRouteToDBAndCollection(obj, callerID).toCommandResponse();
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
