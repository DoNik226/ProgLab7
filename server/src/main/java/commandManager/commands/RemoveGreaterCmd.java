package commandManager.commands;

import databaseLogic.databaseElementLogic.DBIntegrationUtility;
import models.LabWork;
import models.comparators.LabWorkTunedInWorkComparator;
import responses.CommandStatusResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Removes elements from collection greater than given in argument.
 *
 * @author Nikita
 * @since 1.0
 */
public class RemoveGreaterCmd implements Cmd, ArgumentConsumer<LabWork>, AuthorizableCommand {
    private CommandStatusResponse response;
    private LabWork obj;
    private long callerID;

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescr() {
        return "Removes elements from collection greater than given in argument. Comparing is set by tunedInWorks. " +
                "N/B: you may only remove elements belongs to you";
    }

    @Override
    public String getArgs() {
        return "{element}";
    }

    @Override
    public void execute(String[] args) {
        LabWorkTunedInWorkComparator comparator = new LabWorkTunedInWorkComparator();

        System.out.println("TunedInWorks: " + obj.getTunedInWorks());

        Iterator<LabWork> iterator;
        try {
            iterator = DBIntegrationUtility.getInstance().getAccessibleCollection(callerID, HashSet::new).iterator();
        } catch (SQLException | IOException e) {
            response = new CommandStatusResponse("We can't got accessible collection", -501);
            return;
        }

        int count = 0;

        while (iterator.hasNext()) {
            var current = iterator.next();
            System.out.println("Comparing: current -- " + current.getTunedInWorks() + " vs " + obj.getTunedInWorks());
            if (comparator.compare(current, obj) > 0) {
                System.out.println(" -- Greater / Removing...");
                if (DBIntegrationUtility.getInstance().removeFromCollectionAndDB(callerID, current.getId())) {
                    count++;
                } else System.out.println("Element isn't removed...");
            } else {
                System.out.println(" -- Lower.");
            }
        }
        response = CommandStatusResponse.ofString("Removed " + count + " elements");
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