package commandManager.commands;

import databaseLogic.databaseElementLogic.DBIntegrationUtility;
import models.LabWork;
import models.comparators.LabWorkTunedInWorkComparator;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;

/**
 * Add element if it's value greater than max value.
 *
 * @author Nikita
 * @since 1.0
 */
public class AddIfMaxCmd implements Cmd, ArgumentConsumer<LabWork>, AuthorizableCommand {
    private CommandStatusResponse response;
    private LabWork obj;
    private long callerID;

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescr() {
        return "Add element if it's value greater than max value. Max value takes from all collection";
    }

    @Override
    public String getArgs() {
        return "{element}";
    }

    @Override
    public void execute(String[] args) {
        CollectionHandler<HashSet<LabWork>, LabWork> collectionHandler = LabWorksHandler.getInstance();

        if (obj.compareTo(collectionHandler.getMax(new LabWorkTunedInWorkComparator())) > 0) {
            response = DBIntegrationUtility.getInstance().addRouteToDBAndCollection(obj, callerID).toCommandResponse();
        } else {
            response = new CommandStatusResponse("Element not added: it's not greater than max value.", 3);
        }

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
