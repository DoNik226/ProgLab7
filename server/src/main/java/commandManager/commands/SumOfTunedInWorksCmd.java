package commandManager.commands;

import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;

import java.util.HashSet;
import java.util.List;

/**
 * Shows sum of the elements tunedInWorks value.
 *
 * @author Nikita
 * @since 1.0
 */
public class SumOfTunedInWorksCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "sum_of_tuned_in_works";
    }

    @Override
    public String getDescr() {
        return "Shows sum of the elements tunedInWorks value";
    }

    @Override
    public String getArgs() {
        return "tunedInWorks";
    }

    @Override
    public void execute(String[] args) {
        CollectionHandler<HashSet<LabWork>, LabWork> collectionHandler = LabWorksHandler.getInstance();
        var iterator = collectionHandler.getCollection().iterator();

        int sum = 0;

        while (iterator.hasNext())
        {
            var current = iterator.next();
            sum+=current.getTunedInWorks();
        }
        System.out.println("Sum of the values of the tuned in works = " + sum);
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}
