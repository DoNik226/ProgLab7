package commandManager.commands;

import databaseLogic.databaseElementLogic.DBIntegrationUtility;
import models.LabWork;
import responses.CommandStatusResponse;
import mains.Utilities;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * Updates element by its ID.
 *
 * @author Nikita
 * @since 1.0
 */
public class UpdateCmd implements Cmd, ArgumentConsumer<LabWork>, AuthorizableCommand {
    private CommandStatusResponse response;
    private LabWork obj;
    private long callerID;

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescr() {
        return "Updates element by it ID. N/B: you may only edit elements belongs to you";
    }

    @Override
    public String getArgs() {
        return "id {element}";
    }

    @Override
    public void execute(String[] args) {
        long id = Optional.ofNullable(Utilities.handleUserInputID(args[1])).orElse(-1L);
        if (id < 0) {
            response = new CommandStatusResponse("You must enter a valid ID", -7);
        }
        response = DBIntegrationUtility.getInstance().updateElementInDBAndCollection(obj, id, callerID).toCommandResponse();
        System.out.println(response.getResponse());
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    @Override
    public void setObj(LabWork obj) {
        this.obj = obj;
        obj.setCreationDate(Date.from(Instant.now()));
    }

    @Override
    public void setCallerID(long id) {
        this.callerID = id;
    }
}
