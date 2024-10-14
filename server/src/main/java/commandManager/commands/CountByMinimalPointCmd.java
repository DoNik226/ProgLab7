package commandManager.commands;

import models.LabWork;
import models.handlers.CollectionHandler;
import models.handlers.LabWorksHandler;
import responses.CommandStatusResponse;

import java.util.HashSet;
import java.util.List;

/**
 * Shows count of the elements greater than minimalPoint value.
 *
 * @author Nikita
 * @since 1.0
 */
public class CountByMinimalPointCmd implements Cmd {
    private CommandStatusResponse response;

    @Override
    public String getName() {
        return "count_greater_than_minimal_point";
    }

    @Override
    public String getDescr() {
        return "Shows count of the elements greater than minimalPoint value.";
    }

    @Override
    public String getArgs() {
        return "minimalPoint";
    }

    @Override
    public void execute(String[] args) {
        int greaterThan = Integer.parseInt(args[1]);

        CollectionHandler<HashSet<LabWork>, LabWork> collectionHandler = LabWorksHandler.getInstance();
        List<Integer> distances = collectionHandler.getCollection().stream().map(LabWork::getMinimalPoint).toList();

        response = CommandStatusResponse.ofString("Total count: " + distances.stream().map(x -> x.compareTo(greaterThan)).filter(x -> x == 0).count());
        System.out.println(response);
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }
}
